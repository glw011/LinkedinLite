package model;

import java.util.HashMap;
import java.util.LinkedList;

public class Org extends User {
    private String name;
    private String orgType;
    private LinkedList<Integer> members;

    public Org(int id, String email, String name, String orgType, School school){
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = "org";
        this.school = school;
        this.members = new LinkedList<>();

        this.ownedImgs = new LinkedList<>();
        this.interests = new LinkedList<>();
        this.posts = new LinkedList<>();
        this.followingList = new LinkedList<>();
    }

    public String getName(){return this.name;}

    public void setMembersList(LinkedList<Integer> memberList){this.members = memberList;}
    public void addMember(int userId){
        UserType newMemberType = ModelManager.getUserType(userId);
        if(newMemberType == UserType.STUDENT) {
            if(!this.members.contains(userId)) this.members.add(userId);
        }
    }
    public void removeMember(int id){this.members.remove((Integer)id);}
    public boolean checkMembership(int userId){return this.members.contains(userId);}
    public LinkedList<Integer> getMembersList(){return this.members;}

}
