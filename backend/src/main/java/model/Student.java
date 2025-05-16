package model;

import java.util.LinkedList;

public class Student {
    private int stdntId;
    private String email;
    private String fname;
    private String lname;
    private School school;
    private String bio;
    private int major;
    private int profilePic;

    private int profileBanner;
    private LinkedList<Integer> skillList     = new LinkedList<>();
    private LinkedList<Integer> interestList  = new LinkedList<>();
    private LinkedList<Integer> orgList       = new LinkedList<>();
    private LinkedList<Integer> followingList = new LinkedList<>();
    private LinkedList<Integer> postsList     = new LinkedList<>();
    private LinkedList<Integer> ownedImgsList = new LinkedList<>();

    // no-arg for JSON deserialization / update
    public Student() {}

    // used by DAO:
    // new Student(id, email, fname, lname, ModelManager.getSchool(...))
    public Student(int stdntId, String email, String fname, String lname, School school) {
        this.stdntId = stdntId;
        this.email   = email;
        this.fname   = fname;
        this.lname   = lname;
        this.school  = school;
    }

    // --- getters / setters ---
    public int    getStdntId()              { return stdntId; }
    public void   setStdntId(int stdntId)   { this.stdntId = stdntId; }
    // in model/Student.java

    /** Alias for getStdntId(), so DAOs can call student.getID(). */
    public int getID() {
        return getStdntId();
    }


    public String getEmail()                { return email; }
    public void   setEmail(String email)    { this.email = email; }

    public String getFname()                { return fname; }
    public void   setFname(String fname)    { this.fname = fname; }

    public String getLname()                { return lname; }
    public void   setLname(String lname)    { this.lname = lname; }

    public School getSchool()               { return school; }
    public void   setSchool(School school)  { this.school = school; }

    public String getBio()                  { return bio; }
    public void   setBio(String bio)        { this.bio = bio; }

    public int    getMajor()                { return major; }
    public void   setMajor(int major)       { this.major = major; }

    public int    getProfilePic()           { return profilePic; }
    public void   setProfilePic(int picId)  { this.profilePic = picId; }

    public int    getProfileBanner()        { return profileBanner; }
    public void   setProfileBanner(int picId) { this.profileBanner = picId; }

    public LinkedList<Integer> getSkillList()     { return skillList; }
    public void               setSkillList(LinkedList<Integer> list)     { this.skillList = list; }

    public LinkedList<Integer> getInterestList()  { return interestList; }
    public void               setInterestList(LinkedList<Integer> list)  { this.interestList = list; }

    public LinkedList<Integer> getOrgList()       { return orgList; }
    public void               setOrgList(LinkedList<Integer> list)       { this.orgList = list; }

    public LinkedList<Integer> getFollowingList() { return followingList; }
    public void               setFollowingList(LinkedList<Integer> list) { this.followingList = list; }

    public LinkedList<Integer> getPostsList()     { return postsList; }
    public void               setPostsList(LinkedList<Integer> list)     { this.postsList = list; }

    public LinkedList<Integer> getOwnedImgsList() { return ownedImgsList; }
    public void               setOwnedImgsList(LinkedList<Integer> list) { this.ownedImgsList = list; }
}
