package POtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Playground {
    public static void main(String[] args){

        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        // Go to the Wikipedia home page
        driver.get("https://mail.ru/");
        // Find the text input element by its id and type "Selenium"
        driver.findElement(By.id("mailbox__login")).sendKeys("testtask2016");
        driver.findElement(By.id("mailbox__password")).sendKeys("1123581321test");
        // Click search button
        driver.findElement(By.id("mailbox__auth__button")).click();

        String title = driver.getTitle();
        System.out.println("data-subject is: " + title);

        driver.findElement(By.cssSelector("a[data-shortcut-title='N']")).click();

        driver.findElement(By.cssSelector("textarea[data-original-name='To'")).sendKeys("useful_person@mail.ru");

        driver.findElement(By.cssSelector("input[name='Subject'")).sendKeys("test");

        WebElement iframe = driver.findElement(By.cssSelector("iframe[title='{#aria.rich_text_area}'"));
        driver.switchTo().frame(iframe);
        driver.findElement(By.id("tinymce")).sendKeys("some text");
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("div[data-name='saveDraft']")).click();
        //        черновик сохранен

        driver.findElement(By.linkText("Сохранено")).click();
//поиск письма в черновиках

//        WebElement letter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-subject='test']")));
//        letter.click();
        WebElement draftmail = driver.findElement(By.cssSelector("a[data-subject='test']"));
//         получение значения атрибута
//         String datasubject = draftmail.getAttribute("data-subject");
        String mailToSend = driver.findElement(By.cssSelector("a[data-subject='test'] div[class='b-datalist__item__addr']")).getText();
        System.out.println("data-subject is: " + mailToSend);
        draftmail.click();
//        driver.findElement(By.cssSelector("a[data-subject='test']")).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='js-compose-label compose__labels__label'][2]")));

//        WebElement elementWithAddress = driver.findElement(By.cssSelector("input#compose_to"));
        String sddressFromAttribute = driver.findElement(By.cssSelector("input#compose_to")).getAttribute("value");
//        String sddressFromAttribute = elementWithAddress.getAttribute("value");
        System.out.println("address from drafts: " + sddressFromAttribute);

//        WebElement elementWithAddress = driver.findElement(By.cssSelector("span[class='js-compose-label compose__labels__label' style='max-width: 191px']"));
//        String sddressFromAttribute = elementWithAddress.getAttribute("data-text");
//        System.out.println("address from drafts: " + sddressFromAttribute);


        String addressFromDrafts = driver.findElement(By.cssSelector("span[data-text='useful_person@mail.ru'] span[class='compose__labels__label__text js-label-text'")).getText();
        System.out.println("address from drafts: " + addressFromDrafts);

        String subjectFromDrafts = driver.findElement(By.cssSelector("input[name='Subject'")).getAttribute("value");
        System.out.println("subject from drafts: " + subjectFromDrafts);

        WebElement draftframe = driver.findElement(By.cssSelector("iframe[title='{#aria.rich_text_area}'"));
        driver.switchTo().frame(draftframe);
        String textFromDrafts = driver.findElement(By.id("tinymce").cssSelector("div div div")).getText();
        driver.switchTo().defaultContent();
        System.out.println("text from drafts: " + textFromDrafts);
        String x = "some text";
        System.out.println("presence of the text: " + textFromDrafts.contains(x));
        driver.findElement(By.cssSelector("div[data-name='send']")).click();

//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//в черновики после отправки

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='message-sent__title'")));
//        driver.findElement(By.cssSelector("a[data-mnemo='drafts']")).click();

//        driver.findElement(By.cssSelector("div[class='b-datalist__item js-datalist-item'")).click();

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='b-datalist__item.js-datalist-item'")));

//        try
//        {
//            Thread.sleep(2000);
//        }catch(Exception ex)
//        {
//        }
        driver.findElement(By.cssSelector("a[data-mnemo='drafts']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='b-datalist__body'")));

        try
        {
            Thread.sleep(2000);
        }catch(Exception ex)
        {
        }

        String subjFromDraftSearch = driver.findElement(By.cssSelector("a[data-subject='subj-tema']")).getAttribute("data-title");
        System.out.println("subj of the mail to 753r: " + subjFromDraftSearch);
        try
        {
            Thread.sleep(2000);
        }catch(Exception ex)
        {
        }
        driver.findElement(By.cssSelector("a[href='/messages/sent/']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='b-datalist__body'")));

        try
        {
            Thread.sleep(3000);
        }catch(Exception ex)
        {
        }
        driver.findElement(By.cssSelector("a[id='PH_logoutLink']")).click();
        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
        Scanner input = new Scanner(System.in);
        input.nextLine();
        //Close the browser
        driver.quit();
    }
}