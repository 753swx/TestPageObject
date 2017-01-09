package PageFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public LoginPagePF typeLogin(String login)
    {
        loginField.sendKeys(login);
        return this;
    }

    public LoginPagePF typePassword(String pass)
    {
        passField.sendKeys(pass);
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

}
