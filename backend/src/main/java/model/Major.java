package model;

public class Major{
    private int majorId;
    private String name;
    private FoS field;

    public Major(int id, String name, FoS field){
        this.majorId = id;
        this.name = name;
        this.field = field;
    }

    public Integer getID(){return this.majorId;}
    public String getName(){return this.name;}
    public FoS getField(){return this.field;}
}
