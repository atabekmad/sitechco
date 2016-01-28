import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.concurrent.TimeUnit;
import java.util.List;


public class SitechcoTest {

    private WebDriver driver;
    private String url;

    @Before
    public void setUp()  {
	url = "https://chlist.sitechco.ru/";
	FirefoxProfile profile = new FirefoxProfile();
	profile.setPreference("browser.startup.homepage", "about:blank");
	profile.setPreference("startup.homepage_welcome_url", "about:blank");
	profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
	driver = new FirefoxDriver(profile);
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testSeleniumIde() throws Exception {
	// Open up the browser with the given url
	driver.get(url);

	// Enter Login
	driver.findElement(By.id("user_auth_email")).sendKeys("atabekm21@gmail.com");

	// Enter Password
	driver.findElement(By.id("user_auth_password")).sendKeys("gfhjkm");

	// Click Войти button
	driver.findElement(By.xpath("//input[@value='Войти']")).click();	

	// If there is an error message box displayed then close it. Error message box
	// is always displayed if user has no project (except for first time log in situation)
	if (!driver.findElements(By.cssSelector("div.mbox.error")).isEmpty()) {
	    driver.findElement(By.cssSelector("div.exit")).click();
	    TimeUnit.MILLISECONDS.sleep(1000);	
	}

	// Go to Проекты page
	driver.findElement(By.linkText("Проекты")).click();

	// Click Create New Project Icon
	driver.findElement(By.cssSelector("div.icon-bg.icon-plus")).click();

	// Type Project's name
	driver.findElement(By.id("project_name")).clear();
	driver.findElement(By.id("project_name")).sendKeys("New Test Project");

	// Type Project's description
	driver.findElement(By.id("project_description")).clear();
	driver.findElement(By.id("project_description")).sendKeys("This is a test project");
 
	// Click Сохранить button
	driver.findElement(By.xpath("//input[@value='Сохранить']")).click();
	TimeUnit.MILLISECONDS.sleep(1000);

	// Go to Проекты page
	driver.findElement(By.linkText("Проекты")).click();

	// Here we want to find our new created test project in order to select it (click on a checkbox)
	// because there might be several projects on a page. 
	List<WebElement> projects = driver.findElements(By.cssSelector("div.prj"));
	for (WebElement project : projects) {
	    if (!project.findElements(By.linkText("New Test Project")).isEmpty()) {
		project.findElement(By.tagName("input")).click();
	    }
	}

	// Click on Delete Icon
	driver.findElement(By.cssSelector("div.icon-bg.icon-delete")).click();

	// Click Ok
	driver.findElement(By.id("popup_ok")).click();

	// Wait for the notification to appear
	WebElement successNotification = (new WebDriverWait(driver, 10))
	    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.exit")));

	// Click Log Out
	driver.findElement(By.cssSelector("#quit > img")).click();
	TimeUnit.MILLISECONDS.sleep(1000);	
    }

    @After
    public void tearDown() {
	driver.quit();
    }
}
