package executor;

import apihelper.HttpMethods;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import model.employee;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import setup.assertClass;
import setup.testSetup;
import utils.fileHelper;
import java.io.IOException;
import java.lang.reflect.Method;


public class test extends testSetup
{
    ExtentTest logger;
   @BeforeMethod
   public void testSetup(Method method)
   {
        logger=extent.createTest(method.getName());
   }
   @AfterSuite
   public void afterSetup()
   {
       extent.flush();
   }
    @Test(priority = 1)
    public void creationOfEmployee() throws IOException
    {
        
        
        HttpMethods hp=new HttpMethods(uri);
        
        String expectedbody= fileHelper.fileToStringConverter("employee.json");
         Response rs=hp.withPath("create")
                .postWithBody(expectedbody);
    
        assertClass asserter= new assertClass(logger);
        asserter.assertInt(200,rs.getStatusCode(),"Validation of statusCode");
        
        String actualBody = rs.getBody().asString();
         
          JSONObject response = new JSONObject(actualBody);
        asserter.assertString("success",response.getString("status"),"validation of status in response body");
          response=response.getJSONObject("data");
        int id= (int) response.remove("id");
        actualBody=response.toString();
      

        employee actualEmployee = fileHelper.mapValueToClass(actualBody);
        employee expectedEmployee = fileHelper.mapValueToClass(expectedbody);
       asserter.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee),"Comparing two employee objects");
    
        rs=hp.cleanObject()
                        .withPath("employee/"+id)
                        .get200();
    
         asserter= new assertClass(logger);
        asserter.assertInt(200,rs.getStatusCode(),"Validation of statusCode");
    
         actualBody = rs.getBody().asString();
        response = new JSONObject(actualBody);
        asserter.assertString("success",response.getString("status"),"validation of status in response body");
        actualEmployee = fileHelper.mapValueToClass(actualBody);
        asserter.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee),"Comparing two employee objects");
        
    }
 
    @Test(priority = 2)
    public void getAllEmployee()
    {
        HttpMethods hp=new HttpMethods(uri);
    
        
        Response rs  = hp.withPath("employees")
                .get200();
    
        assertClass asserter= new assertClass(logger);
        asserter.assertInt(200,rs.getStatusCode(),"Validation of statusCode");
    
         String actualBody = rs.getBody().asString();
        
        JSONObject response= new JSONObject(actualBody);
        
        JSONArray actualEmployeeList = response.getJSONArray("data");
        JSONArray expectedEmployeeList=new JSONArray(fileHelper.fileToStringConverter("allEmployees.json"));
        
        JSONObject tempArray= new JSONObject();
        for(Object obj:actualEmployeeList)
        {
            JSONObject temp=(JSONObject)obj;
            tempArray.put(temp.getString("id"),temp);
        }
    
        for(Object obj:expectedEmployeeList)
        {
            JSONObject temp=(JSONObject)obj;
            Assert.assertEquals(temp.getString("employee_name"),tempArray.getJSONObject(temp.getString("id")).getString("employee_name"));
            Assert.assertEquals(temp.getString("employee_salary"),tempArray.getJSONObject(temp.getString("id")).getString("employee_salary"));
            Assert.assertEquals(temp.getString("employee_age"),tempArray.getJSONObject(temp.getString("id")).getString("employee_age"));
            Assert.assertEquals(temp.getString("profile_image"),tempArray.getJSONObject(temp.getString("id")).getString("profile_image"));
        }
        logger.log(Status.PASS,"scenario complete");
    }
    @Test(priority = 3)
    public void updateEmployeeDetails()
    {
        HttpMethods hp=new HttpMethods(uri);
    
        String expectedbody= fileHelper.fileToStringConverter("employee.json");
        Response rs=  hp.withPath("create")
                .postWithBody(expectedbody);
        
        assertClass asserter= new assertClass(logger);
        asserter.assertInt(200,rs.getStatusCode(),"Validation of statusCode");
    
        String actualBody = rs.getBody().asString();
        JSONObject response = new JSONObject(actualBody);
        Assert.assertEquals(response.get("status"),"success","validation of status in response body");
        response=response.getJSONObject("data");
        int id= (int) response.remove("id");
        actualBody=response.toString();
         expectedbody = new JSONObject(expectedbody).put("age", "30").toString();
         System.out.println(expectedbody);
        hp.cleanObject()
                .withPath("update/"+id)
                .putWithBody(expectedbody);
        
        rs=hp.cleanObject()
                .withPath("employee/"+id)
                .get200();
    
         asserter= new assertClass(logger);
        asserter.assertInt(200,rs.getStatusCode(),"Validation of statusCode");
    
         actualBody = rs.getBody().asString();
        
        response = new JSONObject(actualBody);
        Assert.assertEquals(response.get("status"),"success","validation of status in response body");
    
        employee expectedEmployee = fileHelper.mapValueToClass(expectedbody);
        employee actualEmployee = fileHelper.mapValueToClass(actualBody);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee));
       
    }
    
}
