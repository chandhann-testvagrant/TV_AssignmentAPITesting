package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileHelper
{
    public static String fileToStringConverter(String fileName)
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file=new File(classLoader.getResource(fileName).getFile());
        Scanner myReader = null;
        try {
            myReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String content="";
        while (myReader.hasNextLine()) {
            content =  content + myReader.nextLine();
        }
        myReader.close();
        
        return content;
    }
    
    public static employee mapValueToClass(String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        employee employee=null;
        try {
            employee = objectMapper.readValue(json, employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employee;
    }
    
    public static employee[] mapArrayValueToClass(String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        employee []employee=null;
        try {
            employee = objectMapper.readValue(json, employee[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
