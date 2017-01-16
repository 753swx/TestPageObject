package PageFactory;


import BusinessObjects.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPagePF extends Page {


    public LoginPagePF(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "mailbox__login")
    private WebElement loginField;

    @FindBy(id = "mailbox__password")
    private WebElement passField;

    @FindBy(id = "mailbox__auth__button")
    private WebElement loginButton;

    @FindBy(css = "span.mailbox__title__link__text")
    private WebElement highlightMsg;

    public LoginPagePF typeLogin(String login){
        highLightBackground(loginField);
        loginField.sendKeys(login);
        return this;
    }
    public LoginPagePF typeLogin(User user){
        highLightBackground(loginField);
        loginField.sendKeys(user.getLogin());
        return this;
    }

    public LoginPagePF typePassword(String pass)
    {
        highLightBackground(passField);
        passField.sendKeys(pass);
        return this;
    }

    public LoginPagePF typePassword(User user)
    {
        highLightBackground(passField);
        passField.sendKeys(user.getPassword());
        return this;
    }

    public AuthorizedPagePF clickOnLoginButton ()
    {
        loginButton.click();
        return new AuthorizedPagePF(driver);
    }

    public boolean isAuthorizationSuccessful(){
        try {
            setImplicitlyWait(0);
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.titleContains("Входящие - Почта Mail.Ru"));
            setImplicitlyWait(20);
            return true;
        }catch (org.openqa.selenium.TimeoutException e){
            return false;
        }
    }

    public LoginPagePF loginFieldsHighlightMsg(){

        WebElement msg = this.highlightMsg;

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

        return this;

    }

}
