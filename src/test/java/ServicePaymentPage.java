import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ServicePaymentPage {
    private WebDriver driver;
    //второе задание
    private By servicesTab = By.className("select__now");
    private By phoneNumberField = By.id("connection-phone");
    private By sumField = By.id("connection-sum");
    private By continueButton = By.xpath("//*[@id=\"pay-connection\"]/button");
    private By iframeLocator = By.xpath("//iframe[@class='bepaid-iframe']");
    private By sumInPopup = By.xpath("//div[@class='pay-description__cost']/span[1]");
    private By phoneInPopup = By.xpath("//div[@class='pay-description__text']/span");
    private By cardNumberLabel = By.xpath("//app-card-input//label[text()='Номер карты']");
    private By expirationDateLabel = By.xpath("//div[@class= 'content ng-tns-c46-4']/label");
    private By cvcLabel = By.xpath("//div[@class= 'content ng-tns-c46-5']/label");
    private By holderNameLabel = By.xpath("//div[@class= 'content ng-tns-c46-3']/label");
    private By visaLogo = By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[1]");
    private By mastercardLogo = By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[2]");
    private By belkartLogo = By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[2]");
    private By mirLogo = By.xpath("//img[contains(@src, 'mir-system-ru.svg')]");
    private By payButton = By.xpath("//div[@class='card-page__card']//button[@class='colored disabled']");
    private By maestroLogo = By.xpath("//img[contains(@src, 'maestro-system.svg')]");

    public ServicePaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPlaceholderById(String fieldId) {
        WebElement field = driver.findElement(By.id(fieldId));
        return field.getAttribute("placeholder");
    }
    //второе задание
    public void clickServicesTab() {
        driver.findElement(servicesTab).click();
    }

    public void enterPhoneNumber(String phoneNumber) {
        driver.findElement(phoneNumberField).sendKeys(phoneNumber);
    }

    public void enterSum(String sum) {
        driver.findElement(sumField).sendKeys(sum);
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    public void switchToIframe() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
    }

    public String getPopupSum() {
        return driver.findElement(sumInPopup).getAttribute("innerHTML").trim();
    }

    public String getPopupPhoneNumber() {
        return driver.findElement(phoneInPopup).getAttribute("innerHTML").trim();
    }

    public String getCardNumberLabelText() {
        return driver.findElement(cardNumberLabel).getAttribute("innerHTML").trim();
    }

    public String getExpirationDateLabelText() {
        return driver.findElement(expirationDateLabel).getAttribute("innerHTML").trim();
    }

    public String getCvcLabelText() {
        return driver.findElement(cvcLabel).getAttribute("innerHTML").trim();
    }
    public String getHolderNameLabelText() {
        return driver.findElement(holderNameLabel).getAttribute("innerHTML").trim();
    }

    public boolean isVisaLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[1]")));
        return driver.findElement(visaLogo).isDisplayed();
    }

    public boolean isMasterCardLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[2]")));
        return driver.findElement(mastercardLogo).isDisplayed();
    }

    public boolean isBelkartLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cards-brands ng-tns-c46-1']/div/img[2]")));
        return driver.findElement(belkartLogo).isDisplayed();
    }

    public boolean isMirLogoCorrectlyDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src, 'mir-system-ru.svg')]")));
        return driver.findElement(mirLogo).getAttribute("style").contains("opacity: 0");
    }
    public boolean isMaestroLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src, 'maestro-system.svg')]")));
        return driver.findElement(maestroLogo).isDisplayed();
    }

    public String getPayButtonText() {
        return driver.findElement(payButton).getAttribute("innerHTML").trim();
    }
}
