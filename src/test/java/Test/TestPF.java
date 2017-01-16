package Test;


import BusinessObjects.Mail;
import BusinessObjects.User;
import PageFactory.AuthorizedPagePF;
import PageFactory.LoginPagePF;
import Patterns.Decorator.WebDriverDecorator;
import Patterns.FactoryMethod.ChromeDriverCreator;
import Patterns.FactoryMethod.WebDriverCreator;
import Patterns.Singleton.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


 public class TestPF {
    private WebDriver driver;
    private LoginPagePF logPage;
    private AuthorizedPagePF authPage;

//    business objects
    private User user = new User("testtask2016", "1123581321test", "Artur", "Kananchuk", 24 );
    private Mail mail = new Mail("testtask2016@mail.ru", "business object", "BO test");

    @BeforeClass
    public void setUp() {
        WebDriverCreator webDriverCreator = new  ChromeDriverCreator();
        driver = webDriverCreator.factoryMethod();
//        getting WebDriver instance via singleton
//        driver = WebDriverSingleton.getInstance();
        driver.manage().window().maximize();
        logPage = new LoginPagePF(driver);
        logPage.setImplicitlyWait(20);
        driver.get("https://mail.ru/");
    }

    @AfterClass
    public void tearDown() {
//        Quit driver instance using Singleton
//        WebDriverSingleton.closeInstance();
        driver.quit();
    }

    @Test
    public void AuthorizationTest() {
//        getting WebDriver instance via singleton
//        driver = WebDriverSingleton.getInstance();
        logPage.loginFieldsHighlightMsg()
                .typeLogin(user)
                .typePassword(user);
        authPage = logPage.clickOnLoginButton();
        Assert.assertTrue(logPage.isAuthorizationSuccessful());
    }

    @Test(dependsOnMethods = { "AuthorizationTest" })
    public void PresenceInDrafts() {
        //using keybord actions for managing with hotkeys
        authPage.actionCreateNewMail(mail)
                .actionSaveDraft()
                .openDrafts();
        Assert.assertTrue(authPage.presenceBySubjectInDrafts(mail.getSubject()));
        //making screenshot. The screenshot would be saved in C:\WebDriverTestScreenshots
        authPage.makeScreenshot();
    }

    @Test(dependsOnMethods = { "PresenceInDrafts" })
    public void CheckMailContent() {
        authPage.openMailBySubjectFromDrafts(mail.getSubject());
        Assert.assertEquals(authPage.getAddressee(), mail.getAddressee() + ",");
        Assert.assertEquals(authPage.getSubject(), mail.getSubject());
        Assert.assertTrue(authPage.getText().contains(mail.getText()));
    }

    @Test(dependsOnMethods = { "CheckMailContent" })
    public void SendAndCheckDrafts(){
        authPage.sendMail()
                .openDrafts();
        Assert.assertTrue(authPage.absenceBySubject(mail.getSubject()));
    }

    @Test(dependsOnMethods = { "SendAndCheckDrafts" })
    public void CheckPresenceInSent(){
        authPage.openSent();
        Assert.assertTrue(authPage.presenceBySubjectInSent(mail.getSubject()));
        //making screenshot. The screenshot would be saved in C:\WebDriverTestScreenshots
        authPage.makeScreenshot();
        // deleting the last mail in list using mouse actions (contextClick, moveToElement and click)
        authPage.actionDeleteLastMail()
                .logout();
    }
}
