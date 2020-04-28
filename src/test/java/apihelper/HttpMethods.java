package apihelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class HttpMethods
{
    public HttpMethods(String uri)
    {
        RestAssured.baseURI=uri;
    }

    public void overRideUri(String uri)
    {
        RestAssured.baseURI=uri;
    }

    private String url="";
    public HttpMethods setEndPoint(String endPointName)
    {
        url="/"+endPointName+"?";

        return this;
    }
    
    public HttpMethods withPath(String path)
    {
        url="/"+path;
        
        return this;
    }

    int parameterCount;
    public HttpMethods addParameter(String key, String value)
    {
        if(parameterCount>0)
        {
            url=url+"&"+key+"="+value;
        }else {
            url=url+key+"="+value;
        }
        parameterCount++;

        return this;
    }

    public void cleanObject()
    {
        parameterCount=0;
        url="";
    }

    public String get200()
    {
        RequestSpecification request=RestAssured.given();
        Response rs=request.when().get(url);
        Assert.assertEquals(200,rs.statusCode());

        return rs.getBody().asString();

    }
    
    public String postWithBody(String postBody)
    {
        RequestSpecification request=RestAssured.given().contentType(ContentType.JSON).body(postBody);
        Response rs=request.post(url);
        if (rs.statusCode()==200)
        {
            Assert.assertEquals(200,rs.statusCode());
        } else
        {
            Assert.assertEquals(201,rs.statusCode());
        }
        
        return rs.getBody().asString();
        
    }
    
}
