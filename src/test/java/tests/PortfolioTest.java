package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PortfolioTest extends BaseTest {

    @Test
    public void testTitle() {
        Assert.assertEquals(driver.getTitle(), "Santhosh Kumar K | SDET Portfolio");
    }

    @Test
    public void testHeaderElements() {
        WebElement header = driver.findElement(By.tagName("nav"));
        Assert.assertTrue(header.isDisplayed());
        String[] sections = {"About", "Skills", "Projects", "Experience", "Contact"};
        for (String section : sections) {
            Assert.assertTrue(driver.getPageSource().contains(section));
        }
    }

    @Test
    public void testSkillsSection() {
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        WebElement skills = driver.findElement(By.id("skills"));
        
        Assert.assertTrue(skills.isDisplayed());
        String[] skillList = {"Java", "Selenium", "TestNG", "Cucumber", "UiPath", "Azure DevOps", "Git", "MySQL", "Postman", "Playwright"};
        for (String skill : skillList) {
            Assert.assertTrue(skills.getText().contains(skill));
        }
    }

    @Test
    public void testSectionScrollNavigation() {
        String[] sections = {"about", "skills", "projects", "experience", "contact"};
        for (String section : sections) {
            WebElement navLink = driver.findElement(By.xpath("//li[contains(text(),'" + section.substring(0, 1).toUpperCase() + section.substring(1) + "')]"));
            navLink.click();
            WebElement sectionElement = driver.findElement(By.id(section));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sectionElement);
            Assert.assertTrue(sectionElement.isDisplayed());
        }
    }

    @Test
    public void testThemeToggleSwitch() {
        WebElement toggle = driver.findElement(By.cssSelector("button[aria-label='Toggle Theme']"));
        String initialTheme = driver.findElement(By.tagName("html")).getAttribute("class");
        toggle.click();
        String newTheme = driver.findElement(By.tagName("html")).getAttribute("class");
        Assert.assertNotEquals(initialTheme, newTheme);
    }

    @Test
    public void testSocialLinks() {
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Wait for the page to load completely
        WebElement linkedIn = driver.findElement(By.cssSelector("a[href*='linkedin.com/in/santhoshkumark18']"));
        Assert.assertTrue(linkedIn.isDisplayed());

        WebElement github = driver.findElement(By.cssSelector("a[href*='github.com/santhoshkumark18']"));
        Assert.assertTrue(github.isDisplayed());

        WebElement email = driver.findElement(By.cssSelector("a[href*='santhoshkumark1801@gmail.com']"));
        Assert.assertTrue(email.isDisplayed());
    }

    @Test
    public void testResumeDownloadButton() {
        WebElement resumeBtn = driver.findElement(By.cssSelector("a[download]"));
        Assert.assertTrue(resumeBtn.isDisplayed());
        Assert.assertTrue(resumeBtn.getAttribute("href").endsWith(".pdf"));
    }
}
