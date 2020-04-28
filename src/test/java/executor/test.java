package executor;

import apihelper.HttpMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.employee;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class test
{
    @Test
    public void test1() throws JsonProcessingException {
        HttpMethods hp=new HttpMethods("http://dummy.restapiexample.com/api/v1");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String expectedbody=new File(classLoader.getResource("employee.json").getFile()).toString();
        String actualBody = hp.withPath("create")
                .postWithBody(expectedbody);
       
          actualBody = new JSONObject(actualBody).getJSONObject("data").toString();
    
        ObjectMapper objectMapper = new ObjectMapper();
        employee actualEmployee = objectMapper.readValue(actualBody, employee.class);
        employee expectedEmployee = objectMapper.readValue(expectedbody, employee.class);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedEmployee,actualEmployee));
    }
}
