import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;

public class StandTest {
    private static final String login = "Student-2";
    private static final String password = "61adb5a8a8";
    private static WebDriver webDriver;
    private static ChromeOptions chromeOptions;
    private static WebDriverWait wait;
    private String url = "https://test-stand.gb.ru/login";

    public void login(){
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#login input[type='text']")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#login input[type='password']")));

        usernameField.sendKeys(login);
        passwordField.sendKeys(password);

        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#login button")));
        button.click();

        wait.until(ExpectedConditions.invisibilityOf(button));
    }

    @BeforeAll
    public static void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
    }

    @BeforeEach
    public void setUp(){
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        webDriver.get(url);

    }

    @Test
    void loginTest(){

//        WebElement usernameField = webDriver.findElement(By.cssSelector("form#login input[type='text']"));
//        WebElement passwordField = webDriver.findElement(By.cssSelector("form#login input[type='password']"));
//        WebElement button = webDriver.findElement(By.cssSelector("form#login button"));
        login();
//        WebElement userName = webDriver.findElement(By.partialLinkText(login));
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(login)));
        Assertions.assertEquals("Hello, " + login, username.getText());
    }

    @Test
    void groupTest(){
        String groupTitle = "HomeworkTask2" + System.currentTimeMillis();
        // login
       login();
        // opening window createNewGroup
        WebElement createNewGroupButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-btn")));
        createNewGroupButton.click();
        // checking if window createNewGroup is opened
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("studyGroupForm-title"),"Creating Study Group"));
        // filling field createNewGroup
        WebElement createNewGroupField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#update-item input[type='text']")));
        createNewGroupField.sendKeys(groupTitle);
        // saving NewGroup
        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#update-item button")));
        saveButton.click();

        WebElement newGroupTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath ( String.format("//td[contains(text(), '%s')]", groupTitle))));
        Assertions.assertEquals(groupTitle, newGroupTitle.getText());

        File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/main/resources/screenshot.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    @AfterEach
    public void close(){
        webDriver.quit();
    }




}
