package objectPage;

import objectPage.tableElements.GroupTableRow;
import objectPage.tableElements.StudentTableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    private final WebDriverWait wait;

    @FindBy(id = "create-btn")
    private WebElement createNewGroupButton;
    @FindBy(css = "form#update-item input[type='text']")
    private WebElement createNewGroupField;
    @FindBy(css = "form#update-item button")
    private WebElement saveNewGroupButton;
    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private WebElement getUsername;
    @FindBy(xpath = "//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button")
    private WebElement closeCreateGroupIcon;
    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
    private List<WebElement> rowsInGroupTable;

    @FindBy(xpath = "//button[@class='mdc-button smui-button--color-secondary mdc-ripple-upgraded']")
    private WebElement addStudentIcon;
    @FindBy(css = "form#generate-logins input[type='number']")
    private WebElement quantityOfStudentsInput;
    @FindBy(css = "form#generate-logins button")
    private WebElement saveStudentsButton;
    @FindBy(xpath = "//span[text()='Creating new logins']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button")
    private WebElement closeAddStudentsButton;

    @FindBy(xpath = "//table[@aria-label='User list']/tbody/tr")
    private List<WebElement> rowsInStudentTable;
    @FindBy(xpath = "./td[2]")
    private WebElement studentTitle;

    @FindBy(xpath="//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']")
    private WebElement profileButton;

    public MainPage(WebDriver webDriver, WebDriverWait wait) {
        PageFactory.initElements(webDriver, this);
        this.wait = wait;
    }

    public void checkSavingNewGroupByText(String groupName){
        String path = String.format("//td[contains(text(), '%s')]", groupName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
    }

    public void clickProfileButton(){
        wait.until(ExpectedConditions.visibilityOf(getUsername)).click();
        wait.until(ExpectedConditions.visibilityOf(profileButton)).click();
    }

    public void createGroup(String groupName){
        // opening window createNewGroup
        wait.until(ExpectedConditions.visibilityOf(createNewGroupButton)).click();
//        // checking if window createNewGroup is opened
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("studyGroupForm-title"),"Creating Study Group"));
        // filling field createNewGroup
        wait.until(ExpectedConditions.visibilityOf(createNewGroupField)).sendKeys(groupName);
        // saving NewGroup
        wait.until(ExpectedConditions.visibilityOf(saveNewGroupButton)).click();
        checkSavingNewGroupByText(groupName);
    }

    public String getUsername(){
       return wait.until(ExpectedConditions.visibilityOf(getUsername)).getText().replace("\n", " ");
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.click();
        wait.until(ExpectedConditions.invisibilityOf(closeCreateGroupIcon));
    }

    private GroupTableRow getRowByTitle(String title) {
        return rowsInGroupTable.stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitleOfGroupFromTable().equals(title))
                .findFirst().orElseThrow();
    }

    public void clickTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getRowByTitle(title).getStatusOfGroupFromTable();
    }

    public String getQuantityOfStudentsInGroupTable(String title){
        return getRowByTitle(title).getQuantityOfStudents();
    }

    public void addStudentsInGroup(String quantityOfStudents){
        wait.until(ExpectedConditions.visibilityOf(addStudentIcon)).click();
        wait.until(ExpectedConditions.visibilityOf(quantityOfStudentsInput)).sendKeys(quantityOfStudents);
        wait.until(ExpectedConditions.visibilityOf(saveStudentsButton)).click();
        wait.until(ExpectedConditions.visibilityOf(closeAddStudentsButton)).click();
        checkSavingAddingStudentsByText(quantityOfStudents);
    }

    public void waitStudentsCount(String groupTitle, String quantityOfStudents) {
        getRowByTitle(groupTitle).waitStudentsCount(quantityOfStudents);
    }

    public void checkSavingAddingStudentsByText(String quantityOfStudents){
        String path = String.format("//td[contains(text(), '%s')]", quantityOfStudents);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
    }

    private StudentTableRow getStudentsRowByTitle(String title) {
        return rowsInStudentTable.stream()
                .map(StudentTableRow::new)
                .filter(row -> row.getTitleOfStudentFromTable().equals(title))
                .findFirst().orElseThrow();
    }
    public void zoomInButton(String groupTitle){
        getRowByTitle(groupTitle).clickZoomInButton();
    }

    public String getQuantityOfStudentsInTable(){
        return String.valueOf(wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentTable)).size());
    }

    public void clickTrashIconOnStudentWithTitle(String title) {
        getStudentsRowByTitle(title).clickStudentTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithTitle(String title) {
        getStudentsRowByTitle(title).clickStudentRestoreFromTrashIcon();
    }

    public String getStatusOfStudent(String title) {
        return getStudentsRowByTitle(title).getStatusOfStudentFromTable();
    }

    public String getStudentNameByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentTable));
        return rowsInStudentTable.stream()
                .map(StudentTableRow::new)
                .toList().get(index).getTitleOfStudentFromTable();
    }
}
