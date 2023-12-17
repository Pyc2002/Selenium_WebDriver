package objectPage.tableElements;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;


public class GroupTableRow {
    private SelenideElement element;
    public GroupTableRow(SelenideElement element) {
        this.element = element;
    }

//    private SelenideElement trashIcon = element.$x("./td/button[text()='delete']");
//    private SelenideElement restoreFromTrashIcon = element.$x("./td/button[text()='restore_from_trash']");
//    private SelenideElement titleOfGroupFromTable = element.$x("./td[2]");
//    private SelenideElement statusOfGroupFromTable = element.$x("./td[3]");
//    private SelenideElement quantityOfStudents = element.$x("./td[4]//span");
//    private SelenideElement zoomInIcon = element.$x(".//td/button[contains(., 'zoom_in')]");



    public String getTitleOfGroup() {
//        return titleOfGroupFromTable.should(visible).text();
        return element.$x("./td[2]").should(visible).getText();
    }

    public String getStatusOfGroup() {
//        return statusOfGroupFromTable.should(visible).text();
        return element.$x("./td[3]").should(visible).getText();
    }

    public void clickTrashIcon(){
//        trashIcon.should(visible).click();
//        restoreFromTrashIcon.shouldBe(visible, Duration.ofSeconds(40));

        element.$x("./td/button[text()='delete']").should(visible).click();
        element.$x("./td/button[text()='restore_from_trash']").shouldBe(visible, Duration.ofSeconds(40));

    }

    public void clickRestoreFromTrashIcon() {
//        restoreFromTrashIcon.should(visible).click();
//        trashIcon.shouldBe(visible, Duration.ofSeconds(40));

        element.$x("./td/button[text()='restore_from_trash']").shouldBe(visible).click();
        element.$x("./td/button[text()='delete']").should(visible, Duration.ofSeconds(40));
    }

    public void waitStudentsCount(int expectedCount) {
        element.$x("./td[4]//span[text()='%s']".formatted(expectedCount)).shouldBe(visible);
    }

    public void clickZoomInButton(){
//        zoomInIcon.should(visible).click();

        element.$x(".//td/button[contains(., 'zoom_in')]").should(visible).click();
    }
}
