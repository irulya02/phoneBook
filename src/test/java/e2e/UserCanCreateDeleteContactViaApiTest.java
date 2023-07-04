package e2e;

import api.address.Address;
import api.contact.Contact;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserCanCreateDeleteContactViaApiTest {
    Contact contact;
       @Test
    public void userCanCreateDeleteContactViaApiTest(){
        contact = new Contact();


        //create new contact TODO:POST
        JsonPath createdContact =contact.createContact(201).jsonPath();
        int id = createdContact.getInt("id");
        // get data for created contact TODO:GET
        JsonPath expectedCreatedContact = contact.getContact(200, id ).jsonPath();
        List<String> listPaths = new ArrayList<>();
        listPaths.add("firstName");
        listPaths.add("lastName");
        listPaths.add("description");
        for (String path: listPaths) {
            String actual = createdContact.getString(path);
            String expected = expectedCreatedContact.getString(path);
            Assert.assertEquals(createdContact.getString(path), expectedCreatedContact.getString(path), "Actual parameter is not equal expected");
        }

        //edit created contact TODO:PUT
        contact.editContact(200, id);
        //get data for edited contact TODO:GET
        JsonPath editedContact = contact.getContact(200, id).jsonPath();
        LinkedHashMap<String, String> objectEditedData = new LinkedHashMap<>();
        objectEditedData.put(editedContact.getString("firstName"),contact.dataForEditContact(id).getFirstName());
        objectEditedData.put(editedContact.getString("lastName"),contact.dataForEditContact(id).getLastName());
        objectEditedData.put(editedContact.getString("description"),contact.dataForEditContact(id).getDescription());

        for (Map.Entry<String,String> object: objectEditedData.entrySet())
        {String actualResult = object.getKey();
        String expectedResult = object.getValue();
        Assert.assertEquals(actualResult, expectedResult, actualResult + "not equal"+ expectedResult);
        }
        // delete edited contact
        contact.deleteContact(200,id);

        //method we will use TODO: DELETE
        // get error message (not existing DB)
        JsonPath actualDeleteContact = contact.getContact(500, id).jsonPath();
        Assert.assertEquals(actualDeleteContact.getString("message"), "Error! This contact doesn't exist in our DB");
        // method we will use TODO:GET
    }

}
