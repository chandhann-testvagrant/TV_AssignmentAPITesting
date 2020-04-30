package setup;

public class constants
{
  public static String environment;
  public static String uri;
  public void setEnvironment(String environment)
  {
      this.environment=environment;
      uri="http://"+environment+".restapiexample.com/api/v1";
  }
}
