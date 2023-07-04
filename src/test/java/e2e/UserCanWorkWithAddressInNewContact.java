package e2e;

import api.address.Address;
import api.contact.Contact;
import com.google.gson.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserCanWorkWithAddressInNewContact {

    Contact contact;
    Address address;


    @Test
    public void userCanWorkWithAddressInNewContact() throws JSONException {
        contact = new Contact();
        JsonPath createdContact = contact.createContact(201).jsonPath();
        int contactId = createdContact.getInt("id");

        address = new Address();
        address.createAddress(201, contactId);
        JsonPath createdAddress = address.getAddresses(200, contactId).jsonPath();
        int addressId = createdAddress.getInt("[0].id");
        String city = createdAddress.getString("[0].city");
        String country = createdAddress.getString("[0].country");
        String street = createdAddress.getString("[0].street");
        String zip = createdAddress.getString("[0].zip");
        JSONObject actualAddressJson = new JSONObject();
        actualAddressJson.put("id", addressId);
        actualAddressJson.put("city", city);
        actualAddressJson.put("country", country);
        actualAddressJson.put("street", street);
        actualAddressJson.put("zip", zip);
        actualAddressJson.put("contactId", contactId);
        String actualJson = actualAddressJson.toString();
        String expectedJson = "{\n" +
                "        \"id\": " + addressId + ",\n" +
                "        \"city\": \"Berlin\",\n" +
                "        \"country\": \"Germany\",\n" +
                "        \"street\": \"Lenin\",\n" +
                "        \"zip\": \"12345\",\n" +
                "        \"contactId\": " + contactId + "\n" +
                "    }";
        JSONAssert.assertEquals(actualJson, expectedJson, JSONCompareMode.STRICT);

        int id = addressId;
        address.editAddress(200, id, contactId);
        JsonPath editedAddress = address.getAddress(200, id).jsonPath();
        LinkedHashMap<String, String> objectEditedAddress = new LinkedHashMap<>();
        objectEditedAddress.put(editedAddress.getString("city"), address.dataForEditAddress(id, contactId).getCity());
        objectEditedAddress.put(editedAddress.getString("country"), address.dataForEditAddress(id, contactId).getCountry());
        objectEditedAddress.put(editedAddress.getString("street"), address.dataForEditAddress(id, contactId).getStreet());

        for (Map.Entry<String, String> object : objectEditedAddress.entrySet()) {
            String actualResult = object.getKey();
            String expectedResult = object.getValue();
            Assert.assertEquals(actualResult, expectedResult, actualResult + "not equal" + expectedResult);
            // delete edited address
        }
            address.deleteAddress(200, id);
//        //method we will use TODO: DELETE
//        // get error message (not existing DB)
            JsonPath actualDeleteAddress = address.getAddress(500, id).jsonPath();
            Assert.assertEquals(actualDeleteAddress.getString("message"), "Error! This address doesn't exist in our DB");
        }
    }
