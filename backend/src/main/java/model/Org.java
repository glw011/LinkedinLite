package Model;

import java.util.HashMap;

public class Org extends User {
    private String name;
    private String orgType;
    private HashMap<Integer, Student> members;

    public Org(int id, String email, String name, String orgType, School school){
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = "org";
        this.orgType = orgType;
        this.school = school;
        this.members = new HashMap<>();

        this.ownedImgs = new HashMap<>();
        this.interests = new HashMap<>();
        this.posts = new HashMap<>();
        this.followingList = new HashMap<>();
    }

    public String getName(){return this.name;}
    public String getOrgType(){return this.orgType;}

    public void addMember(Student student){this.members.putIfAbsent(student.getID(), student);}
    public void removeMember(Student student){this.members.remove(student.getID());}
    public Student getMember(int id){return this.members.get(id);}
    public boolean checkMembership(Student student){return this.members.containsKey(student.getID());}
    public HashMap<Integer, Student> getMembersList(){return this.members;}

}
