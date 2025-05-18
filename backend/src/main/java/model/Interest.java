package model;

import java.util.HashMap;

public class Interest{
    private int interestId;
    private String name;
    private FoS field;
    private HashMap<Integer, Interest> relatedInterests;

    public Interest(int id, String name){
        this.interestId = id;
        this.name = name;

        this.relatedInterests = new HashMap<>();
    }

    public int getID(){return this.interestId;}
    public String getName(){return this.name;}
    public FoS getField(){return this.field;}

    public Interest getRelatedInterest(int id){return this.relatedInterests.get(id);}
    public void addRelatedInterest(Interest interest){this.relatedInterests.putIfAbsent(interest.getID(), interest);}
    public HashMap<Integer, Interest> getRelatedInterestsList(){return this.relatedInterests;}
}
