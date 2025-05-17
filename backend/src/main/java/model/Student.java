package model;

import java.util.LinkedList;

public class Student extends User{
    private String fname;
    private String lname;
    private int major;
    private LinkedList<Integer> skillList;
    private LinkedList<Integer> orgList;

    public Student(int stdntId, String email, String fname, String lname, School school) {
        super(stdntId, email, school, UserType.STUDENT.getStr());

        this.fname   = fname;
        this.lname   = lname;
        this.skillList = new LinkedList<>();
        this.orgList = new LinkedList<>();

    }

    // --- getters / setters ---
    public int    getStdntId()              { return getID(); }

    /** Alias for getStdntId(), so DAOs can call student.getID(). */
    public int getID() {
        return getStdntId();
    }

    public String getFname()                { return this.fname; }

    public void   setFname(String fname)    { this.fname = fname; }

    public String getLname()                { return this.lname; }
    public void   setLname(String lname)    { this.lname = lname; }

    public LinkedList<Integer> getOrgList()       { return this.orgList; }
    public void               setOrgList(LinkedList<Integer> list)       { this.orgList = list; }

    public int    getMajor()                { return this.major; }
    public void   setMajor(int major)       { this.major = major; }

    public LinkedList<Integer> getSkillList()     { return this.skillList; }
    public void               setSkillList(LinkedList<Integer> list)     { this.skillList = list; }
}
