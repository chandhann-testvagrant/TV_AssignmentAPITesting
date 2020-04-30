package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class testSetup extends constants
{
    public ExtentHtmlReporter report= new ExtentHtmlReporter(System.getProperty("user.dir")+"/report.html");
    public ExtentReports extent =new ExtentReports();
    
    @BeforeSuite
    public void configuration() throws IOException
    {
        extent.attachReporter(report);
        Properties properties= new Properties();
        properties.load(new FileInputStream(System.getProperty("user.dir")+"/local.properties"));
        setEnvironment(properties.getProperty("environment"));
    }
}
