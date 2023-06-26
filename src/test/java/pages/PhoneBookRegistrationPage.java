package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhoneBookRegistrationPage extends PageBase{

    public PhoneBookRegistrationPage (WebDriver driver){super(driver);}

    @FindBy(xpath = "//input[@type='email']")
    WebElement emailField;

    @FindBy(xpath = "//input[@class='form-control rounded-pill ml-auto ng-pristine ng-invalid ng-touched']")
    WebElement passwordField;

    @FindBy(xpath = "//input[@name='confirm-password']")
    WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement signUpButton;

    public void setEmailField(String email){emailField.sendKeys(email);}

    public void setPasswordField(String password){passwordField.sendKeys(password);}

    public void setConfirmPasswordField (String confirmPassword) {confirmPasswordField.sendKeys(confirmPassword);}

    public void clickOnSignUpButton(){
        click(signUpButton);
    }

    private void click(WebElement signUpButton) {
    }
}
