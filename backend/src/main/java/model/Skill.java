package Backend;

public class Skill {
    private int skillId;
    private String name;
    private FoS field;

    public Skill(int id, String name, FoS field){
        this.skillId = id;
        this.name = name;
        this.field = field;
    }

    public int getID(){return this.skillId;}
    public String getName(){return this.name;}
    public FoS getField(){return this.field;}
}
