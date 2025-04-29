package model;

import java.util.HashMap;
import java.util.LinkedList;

public class Student extends User {
    private String fname;
    private String lname;
    private Major major;
    private LinkedList<Integer> skills;
    private LinkedList<Integer> orgs;

    public Student(int id, String email, String fname, String lname, School school){
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.school = school;
        this.type = "student";
        this.skills = new LinkedList<>();
        this.orgs = new LinkedList<>();

        this.ownedImgs = new LinkedList<>();
        this.interests = new LinkedList<>();
        this.posts = new LinkedList<>();
        this.followingList = new LinkedList<>();
    }

    public void changeSchool(School newSchool){
        this.setSchool(newSchool);
        //this.college = null;
        this.major = null;
    }
    //public College getCollege(){return this.college;}
    //public void setCollege(College college){this.college = college;}
    public Major getMajor(){return this.major;}
    public void setMajor(int majorId){this.major = ModelManager.getMajor(majorId);}

    public void setOrgList(LinkedList<Integer> orgList){this.orgs = orgList;}
    public void joinOrg(int orgId){
        // TODO: If statement to check if org has added them to members list
        if(!this.orgs.contains(orgId))this.orgs.add(orgId);
    }
    public void leaveOrg(int orgId){
        // TODO: Add call to Org obj to remove student from members list
        this.orgs.remove((Integer)orgId);
    }
    public LinkedList<Integer> getOrgsList(){return this.orgs;}

    public String getFName(){return this.fname;}
    public String getLName(){return this.lname;}
    public String getName(){return String.format("%s %s", this.fname, this.lname);}

    public void setSkillList(LinkedList<Integer> skillList){this.skills = skillList;}
    public void addSkill(int skillId){if(!this.skills.contains(skillId))this.skills.add(skillId);}
    public void removeSkill(int skillId){this.skills.remove((Integer)skillId);}
    public LinkedList<Integer> getSkillsList(){return this.skills;}
    public boolean checkForSkill(int skillId){return this.skills.contains(skillId);}
}
