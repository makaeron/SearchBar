import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TelusSearchAutomation {
    public static void main(String[] args) {
        // Set the path to the WebDriver executable
        System.setProperty("webdriver.chrome.driver", "<path/to/chromedriver>");
        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();
        try {
            // Navigate to the homepage
            driver.get("https://www.telus.com");
            // Locate the search bar
            WebElement searchBar = driver.findElement(By.xpath("//input[@name='searchBar']"));
            // Enter the keyword "Internet"
            searchBar.sendKeys("Internet");
            // Wait for the drop-down list to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tag[contains(@class, 'Prop1')]")));
            // Select the 3rd option from the drop-down list
            WebElement thirdOption = driver.findElement(By.xpath("(//a[contains(@class, 'item')])[3]"));
            if (thirdOption != null && thirdOption.isDisplayed()) {
                thirdOption.click();
            } else {
                System.out.println("The third search option is not available");
                return;
            }
            // Wait for the search results page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'prop02')]")));
            // Compare the text in the search field with the heading of the search page
            String searchFieldText = searchBar.getAttribute("value");
            WebElement ThirdOption_Heading = driver.findElement(By.xpath("//h1[contains(@class, 'title03')]"));
            String ThirdOption_HeadingText = ThirdOption_Heading.getText();
            if (searchFieldText.equals(ThirdOption_HeadingText)) {
                System.out.println("Search field text matches the heading text.");
            } else {
                System.out.println("Search field text does not match the heading text.");
            }
            // Verify that a minimum of 6 results are displayed.
            for (int i = 1; i <= 6; i++) {//Added Loop to verify for the 6 results.                
                WebElement searchResult = driver.findElement(By.xpath("(//div[contains(@class, 'prop04')])[" + i + "]"));//i will be index
                WebElement link = searchResult.findElement(By.xpath("//a"));            
                if (link != null && link.isDisplayed() && link.isEnabled()) {//Verify for the broken links
                    System.out.println("Clickable link found: " + link.getAttribute("href"));
                } else {
                    System.out.println("Clickable link not found in search result " + i);
                }
            }
            // select the appropriate result to navigate further.
            WebElement firstResult = driver.findElement(By.xpath("(//div[contains(@class, 'prop04')])[1]//a"));//Same as step49
            firstResult.click();//Click the web element
        } 
        driver.quit();//Close the Web Driver
    }
}