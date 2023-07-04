package e2e;

import api.contact.Contact;
import e2e.pages.ContactPage;
import e2e.pages.LoginPage;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class CreateContactViaApiEditContactViaTest extends TestBase {
    LoginPage loginPage;
    Contact contact;
    ContactPage contactPage;

    @Test
    public void createContactViaApiEditContactViaTest() {
        contact = new Contact();
        JsonPath createdContact = contact.createContact(201).jsonPath();
        int id = createdContact.getInt("id");

        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoading();
        loginPage.login();
        loginPage.confirmSuccessfulLogin();

        contactPage = new ContactPage(app.driver);
        contactPage.waitForLoading();
        contactPage.openContactById(String.valueOf(id));
    }
}
