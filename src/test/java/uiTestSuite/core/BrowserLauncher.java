package uiTestSuite.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import utils.readPropFile;

import java.util.concurrent.TimeUnit;

public class BrowserLauncher {
    public readPropFile getProps = new readPropFile();

    private static WebDriver driver;

    private  WebDriver launchFirefoxBrowser(){
        System.setProperty("webdriver.gecko.driver", getProps.getProperty("gekoDriver"));
        driver = new FirefoxDriver();
        driver.get(getProps.getProperty("URL"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        setDriver(driver);
        return driver;
    }

    private WebDriver launchGoogleChromeBrowser(){
        System.setProperty("webdriver.chrome.driver", getProps.getProperty("chromeDriver"));
        driver = new ChromeDriver();
        driver.get(getProps.getProperty("URL"));
        setDriver(driver);
        return driver;
    }

    private WebDriver launchIExplorerBrowser(){
        System.setProperty("webdriver.ie.driver", getProps.getProperty("iExplorerDriver"));
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        driver.get(getProps.getProperty("URL"));
        setDriver(driver);
        return driver;
    }

    public void setDriver(WebDriver setDriver){
        driver = setDriver;
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public WebDriver launchBrowser(){
        if(getProps.getProperty("browser").equalsIgnoreCase("iexplorer")){
            driver = launchIExplorerBrowser();
        } else if(getProps.getProperty("browser").equalsIgnoreCase("chrome")){
            driver = launchGoogleChromeBrowser();
        }else{
            driver = launchFirefoxBrowser();
        }
        return driver;
    }


}
