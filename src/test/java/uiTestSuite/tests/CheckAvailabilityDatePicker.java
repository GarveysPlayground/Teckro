package uiTestSuite.tests;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import uiTestSuite.core.BrowserLauncher;
import uiTestSuite.pages.BookingPage;
import utils.DateUtils;

public class CheckAvailabilityDatePicker extends BrowserLauncher {

    private static WebDriver driver = null;
    private DateUtils dateUtils = new DateUtils();
    private final static Logger log = LoggerFactory.getLogger(CheckAvailabilityDatePicker.class);

    @BeforeClass
    public void suiteSetUp() {
        driver = launchBrowser();
    }

    //Check Availibility : Check valid date input
    @Test
        public void testAValidDate(){
        BookingPage bookingPage = new BookingPage();
        Assert.assertTrue(bookingPage.isCheckAvailabilityBoxDisplayed());
        bookingPage.insertCheckAvailabilityDate(dateUtils.getDateAsString(1));
        bookingPage.clickCheckButton();
        bookingPage.isAvailabilityResponseDisplayed();
        log.info(bookingPage.getAvailibilityResponse());

        Assert.assertTrue(bookingPage.getAvailibilityResponse().contains("date"));
        Assert.assertTrue(bookingPage.getAvailibilityResponse().contains("rooms_available"));
        Assert.assertTrue(bookingPage.getAvailibilityResponse().contains("price"));
        bookingPage.cleanCheckAvailabilityDate();
    }

    //A data object of all bad/invalid input types
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

    //A single test to cycle through the bad scenarios found in the above object
    @Test(dataProvider="inValidDataTypes")
    public void TestInvalidInputHandledWithWarning(String dataType, String dataValue){
        BookingPage bookingPage = new BookingPage();
        Assert.assertTrue(bookingPage.isCheckAvailabilityBoxDisplayed());
        bookingPage.insertCheckAvailabilityDate(dataValue);
        bookingPage.clickCheckButton();
        bookingPage.isAvailabilityResponseDisplayed();

        log.info("Scenario : " + dataType);
        log.info("Input : " + dataValue);
        log.info("Response : " + bookingPage.getAvailibilityResponse());

        bookingPage.cleanCheckAvailabilityDate();
        Assert.assertTrue(bookingPage.getAvailibilityResponse().contains("Something bad happened"));
    }

    @AfterClass
    public static void TearDown() {
        driver.quit();
    }
}
