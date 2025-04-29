package model;

import java.util.HashMap;

public class Student extends User {
    private String fname;
    private String lname;
    //private College college;
    private Major major;
    private HashMap<Integer, Skill> skills;
    private HashMap<Integer, Org> orgs;

    public Student(int id, String email, String fname, String lname, School school){
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.school = school;
        this.type = "student";
        this.skills = new HashMap<>();
        this.orgs = new HashMap<>();

        this.ownedImgs = new HashMap<>();
        this.interests = new HashMap<>();
        this.posts = new HashMap<>();
        this.followingList = new HashMap<>();
    }

    public void changeSchool(School newSchool){
        this.setSchool(newSchool);
        //this.college = null;
        this.major = null;
    }
    //public College getCollege(){return this.college;}
    //public void setCollege(College college){this.college = college;}
    public Major getMajor(){return this.major;}
    public void setMajor(Major major){this.major = major;}

    public void joinOrg(Org org){
        // TODO: If statement to check if org has added them to members list
        this.orgs.putIfAbsent(org.getID(), org);
    }
    public void leaveOrg(Org org){
        // TODO: Add call to Org obj to remove student from members list
        this.orgs.remove(org.getID());
    }
    public Org getOrg(int id){return this.orgs.get(id);}
    public HashMap<Integer, Org> getOrgsList(){return this.orgs;}

    public String getFName(){return this.fname;}
    public String getLName(){return this.lname;}
    public String getName(){return String.format("%s %s", this.fname, this.lname);}

    public void addSkill(Skill skill){this.skills.putIfAbsent(skill.getID(), skill);}
    public void removeSkill(Skill skill){this.skills.remove(skill.getID());}
    public Skill getSkill(int id){return this.skills.get(id);}
    public HashMap<Integer, Skill> getSkillsList(){return this.skills;}
    public boolean checkForSkill(Skill skill){return this.skills.containsKey(skill.getID());}
}
