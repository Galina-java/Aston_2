import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPage {
    private WebDriver driver;
    //Для 15 дз
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        //Для 15 дз
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // Локаторы для элементов на главной странице для 15 дз
    private By blockTitleLocator = By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/h2");
    private By visaLogoLocator = By.className("pay__partners");
    private By learnMoreLinkLocator = By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div/a");


    public void clickRejectCookies() {
        try {
            WebElement rejectButton = driver.findElement(By.xpath("//button[contains(text(), 'Принять')]"));
            rejectButton.click();
        } catch (Exception e) {
            System.out.println("Окно с куками не появилось.");
        }
    }

    public void clickTab(String tabName) {
        WebElement tab = driver.findElement(By.className("select__now"));
        tab.click();
    }
    // Для 15 дз проверка названия блока
    public void checkBlockTitle(String expectedTitle) {
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(blockTitleLocator));
        assertEquals(expectedTitle, blockTitle.getText(), "Название блока не соответствует ожидаемому");
    }

    // Проверка наличия логотипа Visa
    public void checkVisaLogoPresence() {
        WebElement visaLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(visaLogoLocator));
        assertTrue(visaLogo.isDisplayed(), "Логотип платежной системы VISA не найден на странице");
    }

    // Проверка и клик по ссылке "Подробнее о сервисе"
    public void clickLearnMoreAboutServiceLink(String expectedUrl) {
        WebElement serviceLink = wait.until(ExpectedConditions.visibilityOfElementLocated(learnMoreLinkLocator));
        assertTrue(serviceLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не найдена на странице");
        serviceLink.click();
        wait.until(ExpectedConditions.urlContains(expectedUrl));
        assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Ссылка не ведет на правильную страницу");
    }
}
