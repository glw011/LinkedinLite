package model;

import java.util.HashMap;

public class School{
    private int schoolId;
    private String name;
    private String city;
    private String country;
    private String state;
    public int zip;

    //private HashMap<Integer, College> colleges;
    private HashMap<Integer, Org> orgs;
    private HashMap<Integer, Student> students;

    // TODO: Add zip column to db table
    public School(int schoolId, String name, String city, String state, String country, int zip){
        this.schoolId = schoolId;
        this.name = name;
        this.city = city;
        this.country = country;
        this.state = state;
        this.zip = zip;

        this.students = new HashMap<>();
        this.orgs = new HashMap<>();
        //this.colleges = new HashMap<>();
    }

    public int getId(){return this.schoolId;}
    public String getName(){return this.name;}
    public String getCity(){return this.city;}
    public String getCountry(){return this.country;}
    public String getState(){return this.state;}
    public int getZip(){return this.zip;}
    public String getAddress(){return String.format("%s, %s %d", this.city, this.state, this.zip);}

    //public void addCollege(College college){this.colleges.putIfAbsent(college.getID(), college);}
    //public College getCollege(int id){return this.colleges.get(id);}
    //public HashMap<Integer, College> getCollegesList(){return this.colleges;}

    public void addStudent(Student student){this.students.putIfAbsent(student.getID(), student);}
    public Student getStudent(int id){return this.students.get(id);}
    public boolean checkForStudent(Student student){return this.students.containsKey(student.getID());}
    public HashMap<Integer, Student> getStudentsList(){return this.students;}
    public void removeStudent(Student student){this.students.remove(student.getID());}

    public void addOrg(Org org){this.orgs.putIfAbsent(org.getID(), org);}
    public Org getOrg(int id){return this.orgs.get(id);}
    public HashMap<Integer, Org> getOrgsList(){return this.orgs;}
    public void removeOrg(Org org){this.orgs.remove(org.getID());}
}
