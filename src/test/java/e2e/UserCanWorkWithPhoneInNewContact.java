package e2e;

import api.contact.Contact;
import api.phone.Phone;
import io.restassured.path.json.JsonPath;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;
public class UserCanWorkWithPhoneInNewContact {
    Contact contact;
    Phone phone;
    @Test
    public void userCanWorkWithPhoneInNewContact() throws JSONException {
        contact = new Contact();
        JsonPath createdContact = contact.createContact(201).jsonPath();
        int contactId = createdContact.getInt("id");

        phone = new Phone();
        phone.createPhone(201, contactId);
        JsonPath createdPhone = phone.getPhones(200, contactId).jsonPath();
        int phoneId = createdPhone.getInt("[0].id");
        String countryCode = createdPhone.getString("[0].countryCode");
        String phoneNumber = createdPhone.getString("[0].phoneNumber");

        JSONObject actualPhoneJson = new JSONObject();
        actualPhoneJson.put("id", phoneId);
        actualPhoneJson.put("countryCode", countryCode);
        actualPhoneJson.put("phoneNumber", phoneNumber);
        actualPhoneJson.put("contactId", contactId);

        String actualJson = actualPhoneJson.toString();
        String expectedJson = "{\n" +
                "        \"id\": " + phoneId + ",\n" +
                "        \"countryCode\": \"+49\",\n" +
                "        \"phoneNumber\": \"676848938980\",\n" +
                "        \"contactId\": " + contactId + "\n" +
                "    }";
        JSONAssert.assertEquals(actualJson, expectedJson, JSONCompareMode.STRICT);

        int id = phoneId;
        phone.editPhone(200, id, contactId);
        JsonPath editedPhone = phone.getPhone(200, id).jsonPath();
        LinkedHashMap<String, String> objectEditedPhone = new LinkedHashMap<>();
        objectEditedPhone.put(editedPhone.getString("countryCode"), phone.dataForEditPhone(id, contactId).getCountryCode());
        objectEditedPhone.put(editedPhone.getString("phoneNumber"), phone.dataForEditPhone(id, contactId).getPhoneNumber());

        for (Map.Entry<String, String> object : objectEditedPhone.entrySet()) {
            String actualResult = object.getKey();
            String expectedResult = object.getValue();
            Assert.assertEquals(actualResult, expectedResult, actualResult + "not equal" + expectedResult);
            // delete edited address
        }
        phone.deletePhone(200, id);
//        //method we will use TODO: DELETE
//        // get error message (not existing DB)
        JsonPath actualDeletePhone = phone.getPhone(500, id).jsonPath();
        Assert.assertEquals(actualDeletePhone.getString("message"), "Error! This phone number doesn't exist in our DB");

        contact.deleteContact(200, contactId);
        JsonPath actualDeleteContact = contact.getContact(500, id).jsonPath();
        Assert.assertEquals(actualDeleteContact.getString("message"), "Error! This contact doesn't exist in our DB");
        // method we will use TODO:GET
    }
}

