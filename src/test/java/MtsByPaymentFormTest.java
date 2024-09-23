import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MtsByPaymentFormTest {
    private WebDriver driver;
    private MainPage mainPage;
    private ServicePaymentPage servicePaymentPage;

    @BeforeEach
    public void setUp() {
        // Укажите ваш путь к cromedriver
        System.setProperty("web driver.chrome.driver", "C:\\Users\\davyi\\.cache\\selenium\\chromedriver\\win64\\128.0.6613.137");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        servicePaymentPage = new ServicePaymentPage(driver);
        mainPage.clickRejectCookies();
    }

    @Test
    public void testPlaceholdersInPaymentFields() {
        mainPage.clickTab("Услуги связи");
        checkPlaceholdersForFields(
                new String[]{"connection-phone", "connection-sum", "connection-email"},
                new String[]{"Номер телефона", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInHomeInternetPaymentFields() {
        mainPage.clickTab("Интернет дома");
        checkPlaceholdersForFields(
                new String[]{"internet-phone", "internet-sum", "internet-email"},
                new String[]{"Номер абонента", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInInstalmentPaymentFields() {
        mainPage.clickTab("Рассрочка");
        checkPlaceholdersForFields(
                new String[]{"score-instalment", "score-instalment", "instalment-email"},
                new String[]{"Номер счета на 44", "Номер счета на 44", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInArrearsPaymentFields() {
        mainPage.clickTab("Задолженность");
        checkPlaceholdersForFields(
                new String[]{"score-arrears", "arrears-sum", "arrears-email"},
                new String[]{"Номер счета на 2073", "Сумма", "E-mail для отправки чека"}
        );
    }

    private void checkPlaceholdersForFields(String[] fieldIds, String[] expectedPlaceholders) {
        for (int i = 0; i < fieldIds.length; i++) {
            String actualPlaceholder = servicePaymentPage.getPlaceholderById(fieldIds[i]);
            assertEquals(expectedPlaceholders[i], actualPlaceholder, "Placeholder для поля '" + expectedPlaceholders[i] + "' некорректен");
        }
    }


    @Test
    public void testServiceContinueButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        assertAll("Service Payment Form Test",
                () -> {
                    servicePaymentPage.clickServicesTab();
                    String enteredPhoneNumber = "297777777";
                    servicePaymentPage.enterPhoneNumber(enteredPhoneNumber);
                    String enteredSum = "100.00";
                    servicePaymentPage.enterSum(enteredSum);
                    servicePaymentPage.clickContinueButton();
                    servicePaymentPage.switchToIframe();

                    String expectedSum = enteredSum + " BYN";
                    String actualSum = servicePaymentPage.getPopupSum();
                    assertEquals(expectedSum, actualSum, "Сумма в попапе неверная!");

                    String expectedPhoneText = "Оплата: Услуги связи\nНомер:375" + enteredPhoneNumber;
                    String actualPhone = servicePaymentPage.getPopupPhoneNumber();
                    assertEquals(expectedPhoneText, actualPhone, "Номер телефона неверный!");

                    String cardNumberLabelText = servicePaymentPage.getCardNumberLabelText();
                    assertEquals("Номер карты", cardNumberLabelText, "Метка для поля 'Номер карты' неверная!");

                    String expirationDateLabelText = servicePaymentPage.getExpirationDateLabelText();
                    assertEquals("Срок действия", expirationDateLabelText, "Метка для поля 'Срок действия' неверная!");

                    String cvcLabelText = servicePaymentPage.getCvcLabelText();
                    assertEquals("CVC", cvcLabelText, "Метка для поля 'CVC' неверная!");
                }
        );
        assertAll("Service Payment Form Test",
                () -> {
                    // Проверка метки "Имя держателя (как на карте)"
                    String holderNameLabelText = servicePaymentPage.getHolderNameLabelText();
                    assertEquals("Имя держателя (как на карте)", holderNameLabelText, "Метка для поля 'Имя держателя' неверна!");
                },
                () -> {
                    // Проверка логотипа Visa
                    assertTrue(servicePaymentPage.isVisaLogoDisplayed(), "Логотип Visa не отображается!");
                },
                () -> {
                    // Проверка логотипа MasterCard
                    assertTrue(servicePaymentPage.isMasterCardLogoDisplayed(), "Логотип MasterCard не отображается!");
                },
                () -> {
                    // Проверка логотипа belkart
                    assertTrue(servicePaymentPage.isBelkartLogoDisplayed(), "Логотип belkart не отображается!");
                },
                () -> {
                    // Проверка логотипа МИР
                    assertTrue(servicePaymentPage.isMirLogoCorrectlyDisplayed(), "Логотип МИР не отображается корректно!");
                },
                () -> {
                    // Проверка текста на кнопке "Оплатить"
                    String payButtonText = servicePaymentPage.getPayButtonText();
                    String summFull = "Оплатить  100.00 BYN <!---->";
                    assertEquals(summFull, payButtonText, "Текст на кнопке 'Оплатить' неверный!");
                });
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}