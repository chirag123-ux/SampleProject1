package SampleProject1;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NopCommerce extends UtilsPage {


    @BeforeMethod
    public void openBrowser() {

        System.setProperty("webdriver.chrome.driver", "src\\main\\Resources\\BrowserDriver\\chromedriver.exe");

        driver = new ChromeDriver();

        //maximise the browser window screen
        driver.manage().window().fullscreen();

        //set implicity wait for driver object
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //open the website
        driver.get("https://demo.nopcommerce.com/");

    }

    @AfterMethod
    public void closeBrowser() {
        //driver.quit();
    }


    @Test

    public void UserCompareTheProduct() {

        //click on electronics
        clickElement(By.xpath("//li/a[@href=\"/electronics\"]"));
        //click on cellphone
        clickElement(By.xpath("//img[@alt = \"Picture for category Cell phones\"]"));
        //Click on First Product HTC One M8 Android L 5.0 Lollipop
        clickElement(By.xpath("//div/a[@href = \"/htc-one-m8-android-l-50-lollipop\"]"));
        //Click add to Compare
        clickElement(By.xpath("//div[@class=\"compare-products\"]"));

        //To confirm if the First product is added to compare
        String actualResultTest1Module1 = extractText(By.xpath("//p/a[@href=\"/compareproducts\"]"));
        String expectedResultTest1Module1 = ("product comparison");
        Assert.assertEquals(actualResultTest1Module1, expectedResultTest1Module1);

        //click on second product htc one mini blue
        clickElement(By.xpath("//a[@title=\"Show details for HTC One Mini Blue\"]"));

        //click add to compare
        clickElement(By.xpath("//div[@class=\"compare-products\"]"));

        //To confirm if the second product is added to compare
        String actualResultTest1Module2 = extractText(By.xpath("//p/a[@href=\"/compareproducts\"]"));
        String expectedResultTest1Module2 = ("product comparison");
        Assert.assertEquals(actualResultTest1Module2, expectedResultTest1Module2);

        //Click on compare prodcuts
        clickElement(By.xpath("//p/a[@href=\"/compareproducts\"]"));

        //To confirm the products in the compare list are the ones which we have added
        String actualResultTest1Module3 = extractText(By.xpath("//tr[@class=\"product-name\"]"));
        String expectedResultTest1Module3 = ("Name HTC One Mini Blue HTC One M8 Android L 5.0 Lollipop");
        Assert.assertEquals(actualResultTest1Module3, expectedResultTest1Module3);

        //Click on Clear button
        clickElement(By.xpath("//a[@class=\"clear-list\"]"));

        //To confirm all is cleared and message is desplayed
        String actualResultTest1Module4 = extractText(By.xpath("//div[@class=\"no-data\"]"));
        String expectedResultTest1Module4 = ("You have no items to compare.");
        Assert.assertEquals(actualResultTest1Module4, expectedResultTest1Module4);
    }


    @Test

    public void userShouldNavigateToNewsAndPutCommentSucessfully() {

        //Click on news
        clickElement(By.xpath("//li/a[@href=\"/news\"]"));
        //Click on Details
        clickElement(By.xpath("//a[@href=\"/new-online-store-is-open\" and @class=\"read-more\" ]"));

        //To confirm we can Leave our comments
        String actualResultTest2Module1 = extractText(By.id("comments"));
        String expectedResultTest2Module1 = ("Leave your comment\nTitle:\nComment:");
        Assert.assertEquals(actualResultTest2Module1, expectedResultTest2Module1);
        //To enter Title
        enterText(By.id("AddNewComment_CommentTitle"), getProperty("Firstname") + " " + getProperty("Lastname"));

        //To enter comment
        enterText(By.id("AddNewComment_CommentText"), getProperty("Comment"));
        //Click Add Comments
        clickElement(By.name("add-comment"));

        //To confirm the message has been added

        String actualResultTest2Module2 = extractText(By.xpath("//div[@class=\"result\"]"));

        String expectedResultTest2Module2 = ("News comment is successfully added.");

        Assert.assertEquals(actualResultTest2Module2, expectedResultTest2Module2);

        //To create list of all the comments
        List<WebElement> commentList = driver.findElements(By.xpath("//strong[@class=\"comment-text\"]"));

        //to get the last comment
        WebElement last_element = commentList.get(commentList.size() - 1);

        //get text from location
        getTextFromElement(By.xpath("//strong[@class=\"comment-text\"]"));

        System.out.println(last_element.getText());

        //Actual Results
        String actualR = last_element.getText();
        //expected results
        String expectedR = getProperty("Firstname") + " " + getProperty("Lastname");

        //Assert actual and expected
        Assert.assertEquals(actualR, expectedR);


    }


    @Test

    public void userShouldAbleToSearchTheKeyWordOfProudct() {

        //Enter the key word in searchbox :card
        enterText(By.id("small-searchterms"),getProperty("Keyword"));
        //click search button
        clickElement(By.xpath("//input[@class=\"button-1 search-box-button\"]"));
        //result1

        List<WebElement> al = driver.findElements(By.xpath("//div[@class=\"product-grid\"]"));

        System.out.println(al.size());
        int count = 0;

        for (WebElement e : al){
            if (e.getAttribute("outerHTML").contains(getProperty("Keyword"))){
                count++;

                System.out.println(e.getText());
                Assert.assertTrue(e.getText().contains(getProperty("Keyword")));
            }
            else {
                System.out.println("No Keyword"+ e.getText());

            }
            System.out.println(count);
            Assert.assertEquals(al.size(),count);

        }

        //enter random alphabets in search
        enterText(By.id("small-searchterms"),getProperty("Alphabets"));

        //click  search
        clickElement(By.xpath("//input[@class=\"button-1 search-box-button\"]"));

        //To confirm it should show the error message
        String actualR = extractText(By.xpath("//div[@class=\"no-result\"]"));
        String expectedR = ("No products were found that matched your criteria.");
        Assert.assertEquals(actualR,expectedR);



        }



    }


