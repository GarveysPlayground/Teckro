package uiTestSuite.tests;

import apiTestSuite.tests.CheckAvailability;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import uiTestSuite.core.BrowserLauncher;
import uiTestSuite.pages.BookingPage;
import utils.DateUtils;

public class CheckBookARoom extends BrowserLauncher {

    private static WebDriver driver = null;
    private DateUtils dateUtils = new DateUtils();
    private final static Logger log = LoggerFactory.getLogger(CheckBookARoom.class);

    @BeforeClass
    public void suiteSetUp() {
        driver = launchBrowser();
    }

    @Test
    public void validBookRoom(){
        String arrivalDate = dateUtils.getDateAsStringOffsetDays(0);
        BookingPage bookingPage = new BookingPage();
        bookingPage.enterBookDate(arrivalDate);
        bookingPage.enterBookNoOfDays(3);
        bookingPage.clickBookRoom();
        Assert.assertTrue(bookingPage.isBookingResponseBoxDisplayed());
        log.info(bookingPage.getBookingResponseBoxText());
    }

    //Compares the returned booking cost, with the cost shown on checked Availability.
    @Test
    public void verifyCorrectRoomCost(){
        String arrivalDate = dateUtils.getDateAsStringOffsetDays(14);
        BookingPage bookingPage = new BookingPage();
        bookingPage.enterBookDate(arrivalDate);
        bookingPage.enterBookNoOfDays(3);
        bookingPage.clickBookRoom();

        int roomCost = calculatePriceOfStay(3, arrivalDate);

        log.info(bookingPage.getBookingResponseBoxText());
        Assert.assertTrue(bookingPage.isBookingResponseBoxDisplayed());
        Assert.assertTrue(bookingPage.getBookingResponseBoxText().contains(String.valueOf(roomCost)));
    }

    //Cycles through the cost of a room on each given date and returns their sum
    public int calculatePriceOfStay(int daysOfStay, String arrivalDate){
        int cost = 0;
        for(int i=0;i<daysOfStay;i++){
            CheckAvailability availabilityApi = new CheckAvailability();
            cost = cost + availabilityApi.getPrice(dateUtils.getDateAsStringOffsetDays(i));
            log.info("Room cost on " + dateUtils.getDateAsStringOffsetDays(i) + " is : " + availabilityApi.getPrice(dateUtils.getDateAsStringOffsetDays(i)));
        }
        log.info("Total cost : " + cost);
        return cost;
    }

    @AfterClass
    public static void suiteTearDown() {
        driver.quit();
    }


}
