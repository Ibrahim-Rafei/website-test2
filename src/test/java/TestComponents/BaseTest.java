package TestComponents;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pageobject.LandingPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException

    {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources//GlobalData.properties");
        prop.load(fis);
        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
        if (browserName.contains("chrome")) {

            ChromeOptions options = new ChromeOptions();
            // Add headless mode argument
            options.addArguments("--headless");

            // Disable GPU to prevent issues in headless mode
            options.addArguments("--disable-gpu");

            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            // Firefox
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;

    }



    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "/reports/" + testCaseName +".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "/reports/" + testCaseName+  ".png";
    }

    @BeforeClass()
    public LandingPage launchApplication() throws IOException
    {

        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTOLandingPage();
        return landingPage ;

    }

    @AfterClass()
    public void tearDown()
    {
        driver.close();
    }

}
