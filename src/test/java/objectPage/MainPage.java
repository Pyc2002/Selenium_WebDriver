package objectPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import objectPage.tableElements.GroupTableRow;
import objectPage.tableElements.StudentTableRow;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private SelenideElement createNewGroupButton = $(By.id("create-btn"));
    private SelenideElement createNewGroupField = $("form#update-item input[type='text']");
    private SelenideElement saveNewGroupButton = $("form#update-item button");
    private SelenideElement getUsername = $("nav li.mdc-menu-surface--anchor a");
    private SelenideElement closeCreateGroupIcon = $x("//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button");
    private ElementsCollection rowsInGroupTable = $$x("//table[@aria-label='Tutors list']/tbody/tr");
    private SelenideElement addStudentIcon = $x("//button[@class='mdc-button smui-button--color-secondary mdc-ripple-upgraded']");
    private SelenideElement quantityOfStudentsInput = $("form#generate-logins input[type='number']");
    private SelenideElement saveStudentsButton = $("form#generate-logins button");
    private SelenideElement closeAddStudentsButton = $x("//span[text()='Creating new logins']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button");
    private ElementsCollection rowsInStudentTable = $$x("//table[@aria-label='User list']/tbody/tr");
    private SelenideElement profileButton = $x("//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']");

    public void checkSavingNewGroupByText(String groupName){
        String path = String.format("//td[contains(text(), '%s')]", groupName);
        $(By.xpath(path)).should(Condition.visible);
    }

    public void clickProfileButton(){
        getUsername.should(Condition.visible).click();
        profileButton.should(Condition.visible).click();
    }

    public void createGroup(String groupName){
        createNewGroupButton.should(Condition.visible).click();
        createNewGroupField.should(Condition.visible).setValue(groupName);
        saveNewGroupButton.should(Condition.visible).click();
        checkSavingNewGroupByText(groupName);
    }

    public String getUsername(){
       return getUsername.should(Condition.visible).getText().replace("\n", " ");
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.should(Condition.visible).click();
        closeCreateGroupIcon.should(Condition.disappear);
    }

    public void clickTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getGroupRowByTitle(title).getStatusOfGroup();
    }

    public void addStudentsInGroup(int quantityOfStudents){

        addStudentIcon.should(Condition.visible).click();
        quantityOfStudentsInput.should(Condition.visible).setValue(String.valueOf(quantityOfStudents));
        saveStudentsButton.should(Condition.visible).click();
        closeAddStudentsButton.should(Condition.visible).click();
    }

    public void waitStudentsCount(String groupTitle, int quantityOfStudents) {
        getGroupRowByTitle(groupTitle).waitStudentsCount(quantityOfStudents);
    }

    public void zoomInButton(String groupTitle){
        getGroupRowByTitle(groupTitle).clickZoomInButton();
    }

    public int getQuantityOfStudentsInTable(){
        return rowsInStudentTable.should(sizeGreaterThan(0)).size();
    }

    public void clickTrashIconOnStudentWithTitle(String title) {
        getStudentsRowByTitle(title).clickStudentTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithTitle(String title) {
        getStudentsRowByTitle(title).clickStudentRestoreFromTrashIcon();
    }

    public String getStatusOfStudent(String title) {
        return getStudentsRowByTitle(title).getStatusOfStudent();
    }

    public String getStudentNameByIndex(int index) {
        rowsInStudentTable.should(sizeGreaterThan(0));
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable()
                .stream()
                .map(StudentTableRow::new)
                .toList().get(index).getTitleOfStudent();
    }

    private StudentTableRow getStudentsRowByTitle(String title) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable()
                .stream()
                .map(StudentTableRow::new)
                .filter(row -> row.getTitleOfStudent().equals(title))
                .findFirst().orElseThrow();
    }

    private GroupTableRow getGroupRowByTitle(String title) {
        return rowsInGroupTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable()
                .stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitleOfGroup().equals(title))
                .findFirst().orElseThrow();
    }
}
