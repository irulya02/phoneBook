package e2e;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import pages.PhoneBookLoginPage;
import pages.PhoneBookPasswordRecoveryPage;
import pages.PhoneBookRegistrationPage;


public class PhoneBookTest extends TestBase{

    Faker faker = new Faker();
    PhoneBookLoginPage phoneBookLoginPage;
    PhoneBookRegistrationPage phoneBookRegistrationPage;
    PhoneBookPasswordRecoveryPage phoneBookPasswordRecoveryPage;

    @Test
    public void addNewAccount(){
        phoneBookLoginPage = new PhoneBookLoginPage(app.driver);
        phoneBookLoginPage.clickOnRegisterNewAccountLink();

        String email = faker.internet().uuid();
        String password = faker.internet().uuid();
        String confirmPassword = faker.internet().uuid();

        phoneBookRegistrationPage = new PhoneBookRegistrationPage(app.driver);
        phoneBookRegistrationPage.clickOnSignUpButton();

    }


    //WebElement.findElement(By.xpath(//*[contains (text(),'Phone book')]))
    // Enter mail
            //[id='defaultRegisterFormEmail']
    //Enter password
               //input[@type='password']
    //click login
              // button[@type='submit']
    //create a new contact


}

