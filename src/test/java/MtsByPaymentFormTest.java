import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
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
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        servicePaymentPage = new ServicePaymentPage(driver);
        mainPage.clickRejectCookies(driver);
    }

    @Test
    public void testPlaceholdersInPaymentFields() {
        mainPage.selectTab("Услуги связи");
        mainPage.checkPlaceholdersForFields(
                new String[]{"Номер телефона", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInHomeInternetPaymentFields() {
        mainPage.selectTab("Домашний интернет");
        mainPage.checkPlaceholdersForFields(
                new String[]{"Номер абонента", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInInstalmentPaymentFields() {
        mainPage.selectTab("Рассрочка");
        mainPage.checkPlaceholdersForFields(
                new String[]{"Номер счета на 44", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testPlaceholdersInArrearsPaymentFields() {
        mainPage.selectTab("Задолженность");
        mainPage.checkPlaceholdersForFields(
                new String[]{"Номер счета на 2073", "Сумма", "E-mail для отправки чека"}
        );
    }

    @Test
    public void testServiceContinueButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Тестовые переменные объявляются в начале теста
        String enteredPhoneNumber = "297777777";
        String enteredSum = "100.00";
        String expectedSum = enteredSum + " BYN";
        String expectedPhoneText = "Оплата: Услуги связи Номер:375" + enteredPhoneNumber;

        assertAll("Service Payment Form Test",
                () -> {

                    //на главной транице заполняем поля
                    mainPage.selectTab("Услуги связи");
                    mainPage.enterPhoneNumber(enteredPhoneNumber);
                    mainPage.enterSum(enteredSum);
                    mainPage.clickContinueButton();
                    // переходим на второе окно
                    mainPage.switchToIframe(driver);

                    // ожидание появления второго окна по привязке к его элементу
                    wait.until(ExpectedConditions.visibilityOfElementLocated(servicePaymentPage.payButtonLocator));

                    String actualSum = servicePaymentPage.getPopupSum();
                    assertEquals(expectedSum, actualSum, "Сумма в попапе неверная!");

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
                    wait.until(ExpectedConditions.visibilityOfElementLocated(servicePaymentPage.mirLogoLocator));
                    assertTrue(servicePaymentPage.isMirLogoCorrectlyDisplayed(), "Логотип МИР не отображается корректно!");
                },
                () -> {
                    // Проверка текста на кнопке "Оплатить"
                    String payButtonText = servicePaymentPage.getPayButtonText();
                    assertTrue(payButtonText.contains(expectedSum), "Текст на кнопке 'Оплатить' неверный!");
                });
    }

    // Далее три теста из Lesson 15

    @Test
    public void testBlockTitle() {
        // Проверка названия блока "Онлайн пополнение без комиссии"
        //WebElement blockTitle = driver.findElement(By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/h2"));
        String expectedTitle = "Онлайн пополнение\n" +
                "без комиссии";
        String actualTitle = mainPage.getBlockTitle();
        assertEquals(expectedTitle, actualTitle, "Название блока не соответствует ожидаемому");
    }

    @Test
    public void testVisaLogoPresence() {
        // логотипы по className
        //WebElement visaLogo = driver.findElement(By.className("pay__partners"));
        boolean result = mainPage.visaLogo.isDisplayed();
        assertTrue(result, "Логотип платежной системы VISA не найден на странице");
    }

    @Test
    public void testLearnMoreAboutServiceLink() {
        // Ищу ссылку по тексту "Подробнее о сервисе"
        //WebElement serviceLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a"));
        boolean result = mainPage.serviceLink.isDisplayed();
        assertTrue(result, "Ссылка 'Подробнее о сервисе' не найдена на странице");
        mainPage.serviceLink.click();
        // Ожидаю, что браузер перейдет на страницу с нужным URL
        String expectedUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Ссылка не ведет на правильную страницу");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
