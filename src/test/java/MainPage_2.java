import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage_2 {

    //Элементы
    @FindBy(xpath = "//*[@class = 'select__now']")
    private WebElement tab;

    @FindBy(xpath = "//ul[@class = 'select__list']//li//p")
    private List<WebElement> dropDownList;

    @FindBy(xpath = "//button[contains(text(), 'Принять')]")
    private WebElement rejectButton;

    @FindBy(xpath = "//form[@class = 'pay-form opened']//input")
    private List<WebElement> listPlaceholders;

    @FindBy(xpath = "//*[@id = 'connection-phone']")
    private WebElement phoneNumberField;

    @FindBy(xpath = "//*[@id = 'connection-sum']")
    private WebElement sumField;

    @FindBy(xpath = "//*[@id='pay-connection']/button")
    private WebElement continueButton;

    @FindBy(xpath = "//iframe[@class='bepaid-iframe']")
    private WebElement iframe;

    @FindBy(xpath = "//div[@class ='pay__wrapper']//h2")
    private WebElement blockTitle;

    @FindBy(xpath = "//*[@class ='pay__partners']")
    public WebElement visaLogo;

    @FindBy(xpath = "//*[@id='pay-section']/div/div/div[2]/section/div/a")
    public WebElement serviceLink;

    //Конструктор
    public MainPage_2(WebDriver driver){
        // Инициализация веб элементов
        PageFactory.initElements(driver, this);
    }

    //Методы
    public void clickRejectCookies(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(rejectButton));
            rejectButton.click();
        } catch (Exception e) {
            System.out.println("Окно с куками не появилось.");
        }
    }

    public void selectTab(String value) {
        tab.click();
        for (WebElement element: dropDownList) {
            if(element.getText().equals(value)){
                element.click();
                break;
            }
        }
    }

    public void checkPlaceholdersForFields(String[] expectedPlaceholders) {
        for (int i = 0; i < listPlaceholders.size(); i++) {
            String actualPlaceholder = listPlaceholders.get(i).getAttribute("placeholder");
            System.out.println("actualPlaceholder = " + actualPlaceholder + ", expectedPlaceholders = " + expectedPlaceholders[i]);
            assertEquals(expectedPlaceholders[i], actualPlaceholder, "Placeholder для поля '" + expectedPlaceholders[i] + "' некорректен");
        }
    }

    public void enterPhoneNumber(String phoneNumber) {
        phoneNumberField.sendKeys(phoneNumber);
    }

    public void enterSum(String sum) {
        sumField.sendKeys(sum);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public void switchToIframe(WebDriver driver) {
        driver.switchTo().frame(iframe);
    }

    public String getBlockTitle(){
        return blockTitle.getText();
    }
}