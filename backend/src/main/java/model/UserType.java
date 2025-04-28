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
}
