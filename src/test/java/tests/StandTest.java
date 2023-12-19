package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import objectPage.LoginPage;
import objectPage.MainPage;
import objectPage.ProfilePage;
import org.junit.jupiter.api.*;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandTest {
    private static final String LOGIN = "Student-2";
    private static final String PASSWORD = "61adb5a8a8";
    private static final String FULLNAME = "2 Student";

    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll // для Selenide убрать BeforeAll
    public static void selenoid(){
        Configuration.browser = "chrome";
        Configuration.remote = "http://localhost:4444/wd/hub";
        Map<String, Object> map = new HashMap<>();
        map.put("enableVNC",true);
        map.put("enableLog",true);
        Configuration.browserCapabilities.setCapability("selenoid:options", map);
    }

    @BeforeEach
    public void setUp(){
        Selenide.open("https://test-stand.gb.ru/login");
    }

    @Test
    public void groupTest(){
        String groupTitle = "HomeworkTask2" + System.currentTimeMillis();
        login();
        mainPage.createGroup(groupTitle);

//        File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
//        try {
//            FileUtils.copyFile(screenshot, new File("src/main/resources/screenshot.png"));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }

    @Test
    public void testArchiveGroupOnMainPage() {
        login();
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        mainPage.createGroup(groupTestName);
        // Требуется закрыть модальное окно
        mainPage.closeCreateGroupModalWindow();
        // Изменение созданной группы с проверками
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
    }


//    @Test
//    @Disabled
//    public void testDragAndDropActionWithScreenshot() throws InterruptedException {
//        // Навигация на https://www.globalsqa.com/demo-site/draganddrop/
//        webDriver.get("https://www.globalsqa.com/demo-site/draganddrop/");
//
//        // Переключение на фрейм, так как элементы для работы находятся внутри тега iframe
//        webDriver.switchTo().frame(webDriver.findElement(By.cssSelector("iframe.demo-frame.lazyloaded")));
//
//        // Поиск двух элементов для операции drag&drop
//        WebElement pictureElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("ul#gallery li.ui-draggable")));
//        WebElement trashSection = webDriver.findElement(By.id("trash"));
//
//        // Класс Actions в деле
//        Actions action = new Actions(webDriver);
//        action.dragAndDrop(pictureElement, trashSection)
//                .build().perform();
//    }

    @Test
    public void loginWithoutUsernameAndPasswordTest(){
        loginPage = new LoginPage();
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getLoginMistake());
    }

    @Test
    public void testStudentTable(){
        login();
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        int quantityOfStudents = 2;

        mainPage.createGroup(groupTestName);
        mainPage.closeCreateGroupModalWindow();
        // добавляем студентов в группу
        mainPage.addStudentsInGroup(quantityOfStudents);
        mainPage.waitStudentsCount(groupTestName, quantityOfStudents);
        //Проверяем количество студентов в таблице
        mainPage.zoomInButton(groupTestName);
        assertEquals(quantityOfStudents, mainPage.getQuantityOfStudentsInTable());
        // Проверка статуса первого студента
        String firstStudentTitle = mainPage.getStudentNameByIndex(0);
        assertEquals("active", mainPage.getStatusOfStudent(firstStudentTitle));
        mainPage.clickTrashIconOnStudentWithTitle(firstStudentTitle);
        assertEquals("block", mainPage.getStatusOfStudent(firstStudentTitle));
        mainPage.clickRestoreFromTrashIconOnStudentWithTitle(firstStudentTitle);
        assertEquals("active", mainPage.getStatusOfStudent(firstStudentTitle));
    }

    @Test
    public void testNameInProfile(){
      login();
       mainPage.clickProfileButton();
       ProfilePage profilePage = Selenide.page(ProfilePage.class);
       assertEquals(FULLNAME, profilePage.getAdditionalInfoText());
       assertEquals(FULLNAME, profilePage.getAvatarFullName());
    }

    @Test
    public void testAddingAvatar(){
        login();
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        profilePage.editButtonClick();
        assertEquals("", profilePage.getAvatarValue());
        profilePage.uploadNewAvatar(new File("src/test/resources/Avatar.PNG"));
        assertEquals("Avatar.PNG", profilePage.getAvatarValue());
    }

    @Test
    public void testDateOfBirthUpdate(){
        String date = "01.01.2001";
        login();
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        profilePage.editButtonClick();
        profilePage.inputDateOfBirth(date);
        profilePage.saveButtonClick();
        profilePage.closeEditingProfileButtonClick();
        profilePage.checkBirthdayText(date);
    }

    @AfterEach
    public void close(){
        WebDriverRunner.closeWebDriver();
    }

    private void login() {
        loginPage = new LoginPage();
        loginPage.login(LOGIN, PASSWORD);
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsername().contains(LOGIN));
    }
}
