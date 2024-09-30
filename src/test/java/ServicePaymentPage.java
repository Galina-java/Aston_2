import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ServicePaymentPage {

    //Локаторы
    public By mirLogoLocator = By.xpath("//img[contains(@src, 'mir-system-ru.svg')]");
    public By payButtonLocator = By.xpath("//div[@class='card-page__card']//button[@class='colored disabled']");

    //Элементы
    @FindBy(xpath = "//div[@class='pay-description__cost']/span[1]")
    private WebElement sumInPopup;

    @FindBy(xpath = "//div[@class='pay-description__text']/span")
    private WebElement phoneInPopup;

    @FindBy(xpath = "//app-card-input//label[text()='Номер карты']")
    private WebElement cardNumberLabel;

    @FindBy(xpath = "//div[@class= 'content ng-tns-c46-4']/label")
    private WebElement expirationDateLabel;

    @FindBy(xpath = "//div[@class= 'content ng-tns-c46-5']/label")
    private WebElement cvcLabel;

    @FindBy(xpath = "//div[@class= 'content ng-tns-c46-3']/label")
    private WebElement holderNameLabel;

    @FindBy(xpath = "//div[@class='card-page__card']//button[@class='colored disabled']")
    public WebElement payButton;

    @FindBy(xpath = "//div[@class='cards-brands ng-tns-c46-1']/div/img[1]")
    private WebElement visaLogo;

    @FindBy(xpath = "//div[@class='cards-brands ng-tns-c46-1']/div/img[2]")
    private WebElement mastercardLogo;

    @FindBy(xpath = "//div[@class='cards-brands ng-tns-c46-1']/div/img[3]")
    private WebElement belkartLogo;

    @FindBy(xpath = "//img[contains(@src, 'mir-system-ru.svg')]")
    public WebElement mirLogo;

    //Конструктор
    public ServicePaymentPage(WebDriver driver) {
        // Инициализация веб элементов
        PageFactory.initElements(driver, this);
    }

    //Методы getText()
    public String getPopupSum() {
        return sumInPopup.getText();
    }

    public String getPopupPhoneNumber() {
        return phoneInPopup.getText();
    }

    public String getCardNumberLabelText() {
        return cardNumberLabel.getText();
    }

    public String getExpirationDateLabelText() {
        return expirationDateLabel.getText();
    }

    public String getCvcLabelText() {
        return cvcLabel.getText();
    }

    public String getHolderNameLabelText() {
        return holderNameLabel.getText();
    }

    public String getPayButtonText() {
        return payButton.getText();
    }

    // Методы isDisplayed()
    public boolean isVisaLogoDisplayed() {
        return visaLogo.isDisplayed();
    }

    public boolean isMasterCardLogoDisplayed() {
        return mastercardLogo.isDisplayed();
    }

    public boolean isBelkartLogoDisplayed() {
        return belkartLogo.isDisplayed();
    }

    public boolean isMirLogoCorrectlyDisplayed() {
        return mirLogo.isDisplayed();
    }
}
