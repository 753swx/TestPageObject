package PageFactory;


import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AuthorizedPagePF extends Page{

    public AuthorizedPagePF(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[data-shortcut-title='N']")
    private WebElement newMailButton;

    @FindBy(css = "textarea[data-original-name='To']")
    private WebElement addresseeField;

    @FindBy(css = "input[name='Subject']")
    private WebElement subjectField;

    @FindBy(css = "iframe[title='{#aria.rich_text_area}']")
    private WebElement inputTextFrame;

    @FindBy(id = "tinymce")
    private WebElement inputTextField;

    @FindBy(css = "div[data-name='saveDraft']")
    private WebElement saveDraftButton;

    @FindBy(css = "a[data-mnemo='drafts']")
    private WebElement draftsLink;

    @FindBy(css = "div[data-name='send']")
    private WebElement sendButton;

    @FindBy(css = "div[class='message-sent__title']")
    private WebElement sentMessage;

    @FindBy(css = "a[href='/messages/sent/']")
    private WebElement sentLink;

    @FindBy(css = "a[id='PH_logoutLink']")
    private WebElement logoutLink;

    public AuthorizedPagePF createNewMail(String addressee, String subject, String text)
    {
        newMailButton.click();
        addresseeField.sendKeys(addressee);
        subjectField.sendKeys(subject);
        driver.switchTo().frame(inputTextFrame);
        inputTextField.sendKeys(text);
        driver.switchTo().defaultContent();
        return this;
    }

    public AuthorizedPagePF saveDraft()
    {
        saveDraftButton.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("черновиках")));
        setImplicitlyWait(20);
        return this;
    }

    public AuthorizedPagePF openDrafts() {
        try {
            draftsLink.click();
            return this;
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            openDrafts();
        }

        return null;
    }

    public boolean presenceBySubject(String subject)
    {
        try {
            driver.findElement(By.cssSelector("a[data-subject='"+ subject +"']"));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex){
            return false;
        }
    }

    public AuthorizedPagePF openMailBySubject(String subject)
    {
        driver.findElement(By.cssSelector("a[data-subject='"+ subject +"'][href$='drafts/']")).click();
        return this;
    }

    public String getAddressee(){
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='js-compose-label compose__labels__label'][2]")));
        setImplicitlyWait(20);
        return driver.findElement(By.cssSelector("input#compose_to")).getAttribute("value");
    }

    public String getSubject(){
        return driver.findElement(By.cssSelector("input[name='Subject'")).getAttribute("value");
    }

    public String getText(){
        driver.switchTo().frame(inputTextFrame);
        String textFromDrafts = driver.findElement(By.id("tinymce").cssSelector("div div div")).getText();
        driver.switchTo().defaultContent();
        return textFromDrafts;
    }

    public AuthorizedPagePF sendMail(){
        sendButton.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='message-sent__title']")));
        setImplicitlyWait(20);
        return this;
    }

    public boolean absenceBySubject(String subject){
        try {
            setImplicitlyWait(0);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"']")));
            setImplicitlyWait(20);
            return true;
        } catch (org.openqa.selenium.TimeoutException e){
            try {
                openDrafts();
                setImplicitlyWait(0);
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[data-subject='"+ subject +"']")));
                setImplicitlyWait(20);
                return true;
            } catch (org.openqa.selenium.TimeoutException e1){
                setImplicitlyWait(20);
                return false;
            }
        }
    }

    public AuthorizedPagePF openSent() {
        sentLink.click();
        return this;
    }

    public LoginPagePF logout(){
        logoutLink.click();
        setImplicitlyWait(0);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("mailbox__auth__button")));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new LoginPagePF(driver);
    }


}
