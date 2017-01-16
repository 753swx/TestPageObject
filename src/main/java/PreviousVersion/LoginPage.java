package PreviousVersion;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


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

    public boolean isAuthorizationSuccessful(){
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

    public void loginFieldsHighlightMsg(){
        WebElement msg = driver.findElement(By.cssSelector("span.mailbox__title__link__text"));
        WebElement loginField = driver.findElement(By.id("mailbox__login"));
        WebElement passField = driver.findElement(By.id("mailbox__password"));

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].innerHTML = 'Highlighting &#129095';" +
                        "arguments[0].style.color = 'red';" +
                        "arguments[0].style.fontSize = '22px';"+
                         "arguments[0].style.backgroundColor = 'yellow'", msg);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        js.executeScript("arguments[0].style.backgroundColor = 'yellow';" +
                "arguments[1].style.backgroundColor = 'yellow';" , loginField, passField);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
