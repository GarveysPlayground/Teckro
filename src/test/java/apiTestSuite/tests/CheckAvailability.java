package apiTestSuite.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DateUtils;

public class CheckAvailability {

    DateUtils dateUtils = new DateUtils();
    private final static Logger log = LoggerFactory.getLogger(CheckAvailability.class);

    //The GET request for check availability
    public Response getCheckAvailabilityResponse(String date){
        RestAssured.baseURI="http://localhost:9090/";
        RequestSpecification httpRequest = RestAssured.given();
        return httpRequest.request(Method.GET, "/checkAvailability/" + date);
    }

    //pull price value from response
    public int getPrice(String date){
        Response response = getCheckAvailabilityResponse(date);
        return response.path("price");
    }

    //pull date value from response
    public String getDate(String date){
        Response response = getCheckAvailabilityResponse(date);
        return response.path("date");
    }

    //pull rooms_available value from response
    public int getNoRoomsAvailable(String date){
        Response response = getCheckAvailabilityResponse(date);
        return response.path("rooms_available");
    }

    @Test
    void checkValidRequests() {
        {
            Response response = getCheckAvailabilityResponse(dateUtils.getDateAsString(1));
            log.info(response.getBody().toString());
            Assert.assertEquals(response.getStatusCode(), 200);
        }
    }

    //A data object of what should be invalid data
    @DataProvider(name = "inValidDataTypes")
    public Object[][] createTestDataRecords() {
        return new Object[][]{
                {"Test a past date", dateUtils.getDateAsString(-1)},
                {"Test if year format is all zeros", "0000-00-00"},
                {"Test if regular text entered", "raceCar"},
                {"Test if special character text entered", "&^*%$"},
                {"Test if date is left empty", ""},
                {"Test Invalid Date Format", "01/01/2019"},
                {"Test Negative Numbers in Date Format", "-2019-08--05"},
                {"Test out Of Bounds month", dateUtils.yearAsString() + "-13-01"},
                {"Test out Of Bounds day", dateUtils.yearAsString() + "-12-32"},
                {"Test large digits", "33333-444-555"},
        };
    }

    //All scenarios below should return a 400 status code. Not all do!
    @Test(dataProvider = "inValidDataTypes")
    void testInvalidRequests(String dataType, String dataValue) {
        {
            log.info(dataType);
            log.info("Value passed into request : " + dataValue);
            Response response = getCheckAvailabilityResponse(dataValue);
            Assert.assertEquals(response.getStatusCode(), 400);
        }
    }
}
