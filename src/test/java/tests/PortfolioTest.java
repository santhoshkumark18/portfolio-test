package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class PortfolioTest extends BaseTest {

    String url = "https://santhoshkumark.me/";

    @Test
    public void testTitle() {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Santhosh Kumar K | SDET Portfolio"));
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("Santhosh Kumar K | SDET Portfolio"), "Title check failed");
    }

    @Test
    public void testNavbarLinks() {
        driver.get(url);

        String[] sections = {"about", "skills", "projects", "experience", "contact"};
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String section : sections) {
            WebElement navLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + capitalize(section) + "']")));
            navLink.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(section)));
            WebElement sectionEl = driver.findElement(By.id(section));
            Assert.assertTrue(sectionEl.isDisplayed(), section + " section not visible");
        }
    }

    @Test
    public void testThemeToggle() {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement themeToggle = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Toggle Theme']")));
        String initialClass = driver.findElement(By.tagName("html")).getAttribute("class");
        themeToggle.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(By.tagName("html"), "class", initialClass)));
        String toggledClass = driver.findElement(By.tagName("html")).getAttribute("class");
        Assert.assertNotEquals(initialClass, toggledClass, "Theme did not toggle");
    }

    @Test
    public void testExternalLinks() {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String[][] links = {
            {"LinkedIn", "https://www.linkedin.com/in/santhoshkumark18/"},
            {"GitHub", "https://github.com/santhoshkumark18"},
            {"Gmail", "mailto:santhoshkumark1801@gmail.com"} // this opens gmail compose
        };

        for (String[] link : links) {
            WebElement anchor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '" + link[1] + "')]")));
            Assert.assertTrue(anchor.isDisplayed(), link[0] + " link not found");
        }
    }

    @Test
    public void testResumeDownloadLink() {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement resumeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'Resume')]")));
        Assert.assertTrue(resumeButton.isDisplayed(), "Resume button not visible");
        Assert.assertTrue(resumeButton.getAttribute("href").endsWith(".pdf"), "Resume link does not point to PDF");
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
