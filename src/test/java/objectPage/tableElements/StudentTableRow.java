package objectPage.tableElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class StudentTableRow {
    private final WebElement webElement;

    public StudentTableRow(WebElement webElement) {
        this.webElement = webElement;
    }

    private void waitUntil(Function<WebElement, WebElement> condition) {
        new FluentWait<>(webElement)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }

    public String getTitleOfStudentFromTable() {
        return webElement.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatusOfStudentFromTable() {
        return webElement.findElement(By.xpath("./td[4]")).getText();
    }

    public void clickStudentTrashIcon(){
        webElement.findElement(By.xpath("./td/button[text()='delete']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='restore_from_trash']")));
    }

    public void clickStudentRestoreFromTrashIcon() {
        webElement.findElement(By.xpath("./td/button[text()='restore_from_trash']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='delete']")));
    }
}

