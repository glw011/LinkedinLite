


package model;

public class Student {
    private int stdntId;
    private String fname;
    private String lname;
    private int schoolId;
    private int majorId;
    private String email;
    private String hashedPass;
    private String schoolName;
    private School school;
    private Major major;

    public int getStdntId() { return stdntId; }
    public void setStdntId(int stdntId) { this.stdntId = stdntId; }
    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }
    public String getLname() { return lname; }
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setLname(String lname) { this.lname = lname; }
    public int getMajorId() { return majorId; }
    public void setMajorId(int majorId) { this.majorId = majorId; }
    public int getSchoolId() { return schoolId; }
    public void setSchoolId(int schoolId) { this.schoolId = schoolId; }
    public String getHashedPass() { return hashedPass; }
    public void setHashedPass(String hashedPass) { this.hashedPass = hashedPass; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() {return email;}
}

