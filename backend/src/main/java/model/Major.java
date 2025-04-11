package model;

public class Major {
    private int majorId;
    private String majorName;
    private School school;      // Foreign key to the School
    private int fieldId;       // Foreign key to FieldOfStudy
    private Discipline discipline;;
    // Default constructor
    public Major() {
    }

    // Parameterized constructor
    public Major(int majorId, String majorName, School school, int fieldId) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.school = school;
        this.fieldId = fieldId;
    }

    // Getters and Setters
    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", school=" + school +
                ", fieldId=" + fieldId +
                '}';
    }
}
