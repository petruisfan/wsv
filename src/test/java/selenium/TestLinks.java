package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestLinks {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testLinks() throws Exception {
		driver.get("http://localhost:2000/site/index.html");
		assertEquals("webserver - About", driver.getTitle());
		driver.findElement(By.linkText("Continuous Integration")).click();
		assertEquals("webserver - Continuous Integration", driver.getTitle());
		driver.findElement(By.linkText("Dependencies")).click();
		assertEquals("webserver - Project Dependencies", driver.getTitle());
		driver.findElement(By.linkText("Issue Tracking")).click();
		assertEquals("webserver - Issue Tracking", driver.getTitle());
		driver.findElement(By.linkText("Mailing Lists")).click();
		assertEquals("webserver - Project Mailing Lists", driver.getTitle());
		driver.findElement(By.linkText("Plugin Management")).click();
		assertEquals("webserver - Project Plugin Management", driver.getTitle());
		driver.findElement(By.linkText("Project License")).click();
		assertEquals("webserver - Project License", driver.getTitle());
		driver.findElement(By.linkText("Project Plugins")).click();
		assertEquals("webserver - Project Build Plugins", driver.getTitle());
		driver.findElement(By.linkText("Project Summary")).click();
		assertEquals("webserver - Project Summary", driver.getTitle());
		driver.findElement(By.linkText("Project Team")).click();
		assertEquals("webserver - Team list", driver.getTitle());
		driver.findElement(By.linkText("Source Repository")).click();
		assertEquals("webserver - Source Repository", driver.getTitle());
		driver.findElement(By.linkText("Project Reports")).click();
		assertEquals("webserver - Generated Reports", driver.getTitle());
		driver.findElement(By.linkText("CPD Report")).click();
		assertEquals("webserver - CPD Results", driver.getTitle());
		driver.findElement(By.linkText("FindBugs Report")).click();
		assertEquals("webserver - FindBugs Bug Detector Report", driver.getTitle());
		driver.findElement(By.linkText("PMD Report")).click();
		assertEquals("webserver - PMD Results", driver.getTitle());
		driver.findElement(By.linkText("Cobertura Test Coverage")).click();
		assertEquals("Coverage Report", driver.getTitle());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
