package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestContent {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testContent() throws Exception {
		
		driver.get("http://localhost:2000/site/index.html");
		String text = driver.findElement(By.xpath("//a[ @href=\"integration.html\" ]")).getText();
		assertEquals("Continuous Integration", text);

		driver.findElement(By.linkText("Project Reports")).click();
		text = driver.findElement(By.xpath("//a[ @href=\"project-info.html\" ]")).getText();
		assertEquals("Project Information", text);
		
		driver.findElement(By.linkText("Cobertura Test Coverage")).click();
		driver.findElement(By.xpath("//frame[ @src=\"frame-packages.html\" ]"));
		driver.findElement(By.xpath("//frame[ @src=\"frame-sourcefiles.html\" ]"));
		driver.findElement(By.xpath("//frame[ @src=\"frame-summary.html\" ]"));
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
