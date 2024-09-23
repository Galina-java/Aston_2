import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

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
}