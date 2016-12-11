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

//    public String getTitle ()
//    {
//        try
//        {
//            Thread.sleep(5000);
//        }catch(Exception ex)
//        {
//        }
//        return driver.getTitle();
//
//    }

    public boolean isAuthorizationSuccessfull(){
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.titleContains("Входящие - Почта Mail.Ru"));
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return true;
        }catch (org.openqa.selenium.TimeoutException e){
            return false;
        }
    }
}
