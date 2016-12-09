package POtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;

/**
 * Created by Наташа on 08.12.2016.
 */
public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    By loginField = By.id("mailbox__login");
    By passField = By.id("mailbox__password");
    By loginButton = By.id("mailbox__auth__button");

    public void typeLogin(String login)
    {
        driver.findElement(loginField).sendKeys(login);

    }

    public void typePassword(String pass)
    {
        driver.findElement(passField).sendKeys(pass);

    }

    public void clickOnLoginButton ()
    {
        driver.findElement(loginButton).click();

    }

    public String getTitle ()
    {
        try
        {
            Thread.sleep(5000);
        }catch(Exception ex)
        {
        }
        return driver.getTitle();
    }
}
