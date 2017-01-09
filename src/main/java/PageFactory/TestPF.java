package PageFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestPF {
    private WebDriver driver;
    private LoginPagePF logPage;
    private AuthorizedPagePF authPage;

    private String login = "testtask2016";
    private String pass = "1123581321test";
    private String addressee = "testtask2016@mail.ru";
    private String subject = "some subject";
    private String text = "some text";

    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        logPage = new LoginPagePF(driver);
        logPage.setImplicitlyWait(20);
        driver.get("https://mail.ru/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AuthorizationTest() {
        logPage.typeLogin(login)
                .typePassword(pass);
        authPage = logPage.clickOnLoginButton();
        Assert.assertTrue(logPage.isAuthorizationSuccessful());
    }
}
