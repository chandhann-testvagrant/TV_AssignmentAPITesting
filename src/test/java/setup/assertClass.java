package setup;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class assertClass
{
    ExtentTest logger;
    public assertClass(ExtentTest logger)
    {
        this.logger=logger;
    }
    public void assertInt(int expected, int actual, String field)
    {
        if(expected==actual)
        {
            logger.log(Status.PASS,field);
        }else
        {
            logger.log(Status.FAIL,field);
        }
    }
    
    public void assertString(String expected, String actual, String field)
    {
        if(expected.equals(actual))
        {
            logger.log(Status.PASS,field);
        }else
        {
            logger.log(Status.FAIL,field);
        }
    }
    
    public void assertTrue(boolean flag,String Message)
    {
        if(flag)
        {
            logger.log(Status.PASS,Message);
        }else
        {
            logger.log(Status.FAIL,Message);
        }
    }
}
