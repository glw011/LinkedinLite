package model;

public enum UserType {
    STUDENT(0, "student"),
    ORG(1, "org");

    final int val;
    final String str;

    UserType(int val, String str){
        this.val = val;
        this.str = str;
    }

    public String getStr(){return this.str;}

    public int getVal(){return this.val;}
}
