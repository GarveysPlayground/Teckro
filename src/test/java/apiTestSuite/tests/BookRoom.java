package apiTestSuite.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DateUtils;

public class BookRoom {

    DateUtils dateUtils = new DateUtils();
    private final static Logger log = LoggerFactory.getLogger(BookRoom.class);

    public Response postBookRoom(int noOfDays, String date){
        RestAssured.baseURI="http://localhost:9090/";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject postParams=new JSONObject();
        postParams.put("numOfDays",noOfDays);
        postParams.put("checkInDate",date);

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(postParams.toJSONString());
        return httpRequest.request(Method.POST, "/bookRoom");
    }

    @Test
    public void makeAValidBooking() {
        {
            Response response = postBookRoom(3, dateUtils.getDateAsString(0));
            log.info("Response : \n" + response.getBody().asString());
            Assert.assertEquals(response.getStatusCode(), 200);
        }
    }

    @DataProvider(name = "inValidDateTypes")
    public Object[][] createTestDataRecords() {
        return new Object[][]{
                {3, "Rotator"},
                {3, "0000-00-00"},
                {3, "272727"}
        };
    }

    @Test(dataProvider = "inValidDateTypes")
    public void testInvalidDateHandled(int numOfDays, String date) {
        {
            Response response = postBookRoom(numOfDays, "date");
            log.info("Response : \n" + response.getBody().asString());
            Assert.assertEquals(response.getStatusCode(), 400);
        }
    }

    @DataProvider(name = "inValidDaysTypes")
    public Object[][] createTestDaysRecords() {
        return new Object[][]{
                {0, dateUtils.getDateAsString(3)},
                {-5, dateUtils.getDateAsString(2)},
        };
    }

    @Test(dataProvider = "inValidDaysTypes")
    public void testInvalidDaysHandled(int numOfDays, String date) {
        {
            Response response = postBookRoom(numOfDays, "date");
            log.info("Response : \n" + response.getBody().asString());
            Assert.assertEquals(response.getStatusCode(), 400);
        }
    }

    @Test
    public void bookingFailsWhenNoRoomsAvailable(){
        String bookDate = dateUtils.getDateAsStringOffsetDays(21);
        CheckAvailability checkAvailability = new CheckAvailability();
        int availableRooms = checkAvailability.getNoRoomsAvailable(bookDate);
        log.info(availableRooms + " rooms available on " + bookDate);
        for(int i = 0;i< availableRooms; i++){
            postBookRoom(1, dateUtils.getDateAsString(0));
        }
        //The room was fully booked out in the above for loop. The next booking should fail.
        Assert.assertEquals(postBookRoom(1, dateUtils.getDateAsString(0)).getStatusCode(),400);
    }
}
