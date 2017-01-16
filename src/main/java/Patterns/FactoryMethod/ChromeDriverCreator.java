package Patterns.FactoryMethod;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;


public class ChromeDriverCreator extends WebDriverCreator {
    public WebDriver factoryMethod() {
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(
                new File(System.getProperty("user.dir") + "/chromedriver.exe")).build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = new ChromeDriver(service);
        return  driver;

    }
}
