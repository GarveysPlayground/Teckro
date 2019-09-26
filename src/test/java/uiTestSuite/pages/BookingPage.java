package uiTestSuite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uiTestSuite.core.BrowserLauncher;


public class BookingPage {

    public BookingPage(){
        PageFactory.initElements(BrowserLauncher.getDriver(), this);
    }

    @FindBy(className = "room-availabilty")
    WebElement checkAvailabilityBox;

    @FindBy(className= "mat-stroked-button")
    WebElement checkButton;

    @FindBy(id = "mat-input-0")
    public WebElement dateTextField;

    @FindBy(xpath = "//*[contains(@class, 'room-availabilty')]/*[contains(@class, 'response availabilty')]")
    public WebElement availabilityResponse;

    @FindBy(className = "book-room")
    WebElement bookRoomBox;

    @FindBy(id = "mat-input-1")
    WebElement bookingDateTextField;

    @FindBy(id = "mat-input-2")
    WebElement bookingNoOfDays;

    @FindBy (xpath = "//*[contains(@class, 'book-room')]//*[contains(@class, 'mat-stroked-button')]")
    WebElement bookingBookButton;

    @FindBy(xpath = "//*[contains(@class, 'book-room')]/*[contains(@class, 'response booking')]")
    public WebElement bookRoomResponseBox;

    public Boolean isCheckAvailabilityBoxDisplayed(){
        if(checkAvailabilityBox.isEnabled()){
            return true;
        }
        return false;
    }

    public void insertCheckAvailabilityDate(String date){
        dateTextField.clear();
        dateTextField.click();
        dateTextField.sendKeys(date);
    }

    public void cleanCheckAvailabilityDate(){
        dateTextField.click();
        dateTextField.clear();
    }

    public void clickCheckButton(){
       checkButton.click();
    }

    public Boolean isAvailabilityResponseDisplayed(){
        if(availabilityResponse.isDisplayed()){
            return true;
        }
        return false;
    }

    public String getAvailibilityResponse(){
        return availabilityResponse.getText();
    }

    public void enterBookDate(String date){
        bookingDateTextField.click();
        bookingDateTextField.clear();
        bookingDateTextField.sendKeys(date);
    }

    public void enterBookNoOfDays(int noOfDays){
        bookingNoOfDays.click();
        bookingNoOfDays.clear();
        bookingNoOfDays.sendKeys(String.valueOf(noOfDays));
    }

    public void clickBookRoom(){
        bookingBookButton.click();
    }

    public Boolean isBookingResponseBoxDisplayed(){
        return bookRoomResponseBox.isDisplayed();
    }

    public String getBookingResponseBoxText(){
        return bookRoomResponseBox.getText();
    }


}
