import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsByTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Укажите путь к chromedriver.exe
        System.setProperty("web driver.chrome.driver", "C:\\Users\\davyi\\.cache\\selenium\\chromedriver\\win64\\128.0.6613.137");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");
    }

    @Test
    public void testBlockTitle() {
        // Проверка названия блока "Онлайн пополнение без комиссии"
        WebElement blockTitle = driver.findElement(By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/h2"));
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, blockTitle.getText(), "Название блока не соответствует ожидаемому");
    }

    @Test
    public void testVisaLogoPresence() {
        // логотипы по className
        WebElement visaLogo = driver.findElement(By.className("pay__partners"));
        assertTrue(visaLogo.isDisplayed(), "Логотип платежной системы VISA не найден на странице");
    }

    @Test
    public void testLearnMoreAboutServiceLink() {
        // Ищу ссылку по тексту "Подробнее о сервисе"
        WebElement serviceLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a"));
        assertTrue(serviceLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не найдена на странице");
        serviceLink.click();
        // Ожидаю, что браузер перейдет на страницу с нужным URL
        String expectedUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Ссылка не ведет на правильную страницу");
    }

    @Test
    public void testServiceContinueButton() {
        //вкладка "Услуги связи"
        WebElement servicesTab = driver.findElement(By.className("select__now"));
        servicesTab.click();
        WebElement phoneNumberField = driver.findElement(By.id("connection-phone"));
        phoneNumberField.sendKeys("297777777");
        WebElement sumField = driver.findElement(By.id("connection-sum"));
        sumField.sendKeys("100");
        // Кнопка Продолжить
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"pay-connection\"]/button"));
        continueButton.click();

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}