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
        this.uri=uri;
        RestAssured.baseURI=uri;
    }

    public void overRideUri(String uri)
    {
        RestAssured.baseURI=uri;
    }

    private String url="",uri;
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

    public HttpMethods cleanObject()
    {
        parameterCount=0;
        url="";
        return this;
    }

    public String get200()
    {
        RequestSpecification request=RestAssured.given().contentType(ContentType.JSON);
        System.out.println(uri+url);
        Response rs=request.when().get(url);
        Assert.assertEquals(200,rs.statusCode());

        return rs.getBody().asString();

    }
    
    public String postWithBody(String postBody)
    {
        System.out.println(uri+url);
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
