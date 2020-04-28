package model;

public class employee
{
    private String name;
    private int id;
    private long salary;
    private int age;
    private String profile_image;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProfile_image() {
        return profile_image;
    }
    
    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
    
    
    public long getSalary() {
        return salary;
    }
    
    public void setSalary(long salary) {
        this.salary = salary;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
}
