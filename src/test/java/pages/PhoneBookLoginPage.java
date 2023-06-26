package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhoneBookLoginPage extends PageBase{
    public PhoneBookLoginPage(WebDriver driver) {super(driver);
    }

    @FindBy(xpath = "//a[@ng-reflect-router-link ='/user/registration']")
    WebElement registerNewAccountLink;

    public void clickOnRegisterNewAccountLink(){
        click(registerNewAccountLink);
    }

    private void click(WebElement registerNewAccountLink) {
    }
}
