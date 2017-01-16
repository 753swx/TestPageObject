package Patterns.FactoryMethod;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;


public class FirefoxDriverCreator extends WebDriverCreator {
    public WebDriver factoryMethod() {
        FirefoxBinary binary = new FirefoxBinary(new File(System.getProperty("user.dir") + "/geckodriver.exe"));
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        WebDriver driver = new FirefoxDriver(binary,firefoxProfile);
        return driver;
    }
}
