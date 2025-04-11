package model;
@SuppressWarnings("unused")
public class School {
    private int schoolId;
    private String schoolName;
    private String city;
    private String state;
    private String country;
    // default
    public School() {
    }
    // param'd
    public School(int schoolId, String schoolName, String city, String state, String country) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.city = city;
        this.state = state;
        this.country = country;
    }
    // getters and setters
    public int getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}