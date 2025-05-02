package model;

public class School {
    private int    schoolId;
    private String schoolName;
    private String city;
    private String state;
    private String country;

    // no-arg for DAO
    public School() {}

    // *** ADD THIS 5-ARGCTOR ***
    public School(int schoolId,
                  String schoolName,
                  String city,
                  String state,
                  String country) {
        this.schoolId   = schoolId;
        this.schoolName = schoolName;
        this.city       = city;
        this.state      = state;
        this.country    = country;
    }

    // existing 6-arg used by ModelManager
    public School(int schoolId,
                  String schoolName,
                  String city,
                  String state,
                  String country,
                  int    dummy) {
        this(schoolId, schoolName, city, state, country);
        // ignore dummy or assign to a field if you want
    }

    public int getSchoolId()         { return schoolId; }
    public void setSchoolId(int id)  { this.schoolId = id; }
    // in model/School.java

    /** Alias so ModelManager.getAllSchools() can key by getId(). */
    public int getId() {
        return getSchoolId();
    }

    /** Alias so ModelManager can map by name. */
    public String getName() {
        return getSchoolName();
    }


    public String getSchoolName()        { return schoolName; }
    public void   setSchoolName(String n){ this.schoolName = n; }

    public String getCity()        { return city; }
    public void   setCity(String c){ this.city = c; }

    public String getState()         { return state; }
    public void   setState(String s) { this.state = s; }

    public String getCountry()          { return country; }
    public void   setCountry(String c)  { this.country = c; }
}
