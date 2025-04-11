package model;

public class Org {
    private int orgId;
    private String orgName;
    private String orgType; // e.g., "Company", "Nonprofit", etc.

    // Default constructor
    public Org() {
    }

    // Parameterized constructor
    public Org(int orgId, String orgName, String orgType) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgType = orgType;
    }

    // Getters and Setters
    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {

        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", orgType='" + orgType + '\'' +
                '}';
    }
}
