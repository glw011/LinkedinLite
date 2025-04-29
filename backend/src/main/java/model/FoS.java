package model;

import java.util.HashMap;

public class FoS{
    private int fieldId;
    private String name;
    private HashMap<Integer, Skill> relatedSkills;
    private HashMap<Integer, Interest> relatedInterests;

    public FoS(int id, String name){
        this.fieldId = id;
        this.name = name;

        this.relatedInterests = new HashMap<>();
        this.relatedSkills = new HashMap<>();
    }

    public int getID(){return this.fieldId;}
    public String getName(){return this.name;}

    public void addRelatedSkill(Skill skill){this.relatedSkills.putIfAbsent(skill.getID(), skill);}
    public Skill getRelatedSkill(int id){return this.relatedSkills.get(id);}
    public boolean checkSkillRelated(int id){return this.relatedSkills.containsKey(id);}
    public void addRelatedInterest(Interest interest){this.relatedInterests.putIfAbsent(interest.getID(), interest);}
    public Interest getRelatedInterest(int id){return this.relatedInterests.get(id);}
    public boolean checkInterestRelated(int id){return this.relatedInterests.containsKey(id);}

    public HashMap<Integer, Skill> getRelatedSkillsList(){return this.relatedSkills;}
    public HashMap<Integer, Interest> getRelatedInterestsList(){return this.relatedInterests;}
}
