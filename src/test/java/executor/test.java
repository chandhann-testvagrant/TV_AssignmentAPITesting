package executor;

import apihelper.HttpMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.employee;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.fileHelper;
import java.io.IOException;


public class test
{
    @Test(priority = 1)
    public void creationOfEmployee() throws IOException {
        HttpMethods hp=new HttpMethods("http://dummy.restapiexample.com/api/v1");
        
        String expectedbody= fileHelper.fileToStringConverter("employee.json");
        String actualBody = hp.withPath("create")
                .postWithBody(expectedbody);
       
          JSONObject response = new JSONObject(actualBody);
          Assert.assertEquals(response.get("status"),"success","validation of status in response body");
          response=response.getJSONObject("data");
        int id= (int) response.remove("id");
        actualBody=response.toString();
      

        employee actualEmployee = fileHelper.mapValueToClass(actualBody);
        employee expectedEmployee = fileHelper.mapValueToClass(expectedbody);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee));
        
        actualBody=hp.cleanObject()
                        .withPath("employee/"+id)
                        .get200();
        response = new JSONObject(actualBody);
        Assert.assertEquals(response.get("status"),"success","validation of status in response body");
        actualEmployee = fileHelper.mapValueToClass(actualBody);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee));
    }
 
    @Test(priority = 2)
    public void getAllEmployee()
    {
        HttpMethods hp=new HttpMethods("http://dummy.restapiexample.com/api/v1");
    
        
        String actualBody = hp.withPath("employees")
                .get200();
        
        JSONObject response= new JSONObject(actualBody);
        
        JSONArray employeeList = response.getJSONArray("data");
        employee[] expectedEmployeeList=fileHelper.mapArrayValueToClass(fileHelper.fileToStringConverter("allEmployees.json").replaceAll("\"employee_name\":","\"name\":").replaceAll("\"employee_salary\":","\"salary\":").replaceAll("\"employee_age\":","\"age\":"));
       
        System.out.println(expectedEmployeeList);
        employee[] actualEmployeeList=fileHelper.mapArrayValueToClass(employeeList.toString().replaceAll("\"employee_name\":","\"name\":").replaceAll("\"employee_salary\":","\"salary\":").replaceAll("\"employee_age\":","\"age\":"));
    
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployeeList,actualEmployeeList));
        
        for(Object obj:employeeList)
        {
            JSONObject temp=(JSONObject)obj;
            Assert.assertNotNull(temp.getString("id"));
            Assert.assertNotNull(temp.getString("employee_name"));
            Assert.assertNotNull(temp.getString("employee_salary"));
            Assert.assertNotNull(temp.getString("employee_age"));
            Assert.assertNotNull(temp.getString("profile_image"));
        }
        
    }
    
}
