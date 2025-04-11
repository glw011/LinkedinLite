package model;

public class Discipline {
    private int fieldId;
    private String fieldName;

    // Default constructor
    public Discipline() {
    }

    // Parameterized constructor
    public Discipline(int fieldId, String fieldName) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
    }

    // Getters and Setters
    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "FieldOfStudy{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
