package model;

import java.util.LinkedList;

public class Org extends User {
    private String name;
    private LinkedList<Integer> members;

    public Org(int id, String email, String name, School school){
        super(id, email, school, UserType.ORG.getStr());

        this.name = name;
        this.members = new LinkedList<>();
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
