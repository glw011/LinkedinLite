package service;

import dao.OrgDAO;
import model.ModelManager;
import model.Org;
import model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service layer for Org operations.
 * Handles input validation, wraps DAO exceptions, and exposes simple methods
 * for your frontend/UI to call.
 */
public class OrgService {

    /** default cnstrctr */
    public OrgService() {
        this(new OrgDAO());
    }

    /** ctnstrctr for tests */
    public OrgService(OrgDAO dao) {
        Objects.requireNonNull(dao, "dao must not be null");
    }

    /**
     * Create a new Org.
     * @throws IllegalArgumentException if any arg is null/blank or schoolId ≤ 0
     * @throws OrgServiceException on any DAO/SQL error or failure
     */
    public int createOrg(String name, String email, String hashedPass, int schoolId) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be empty");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("email must not be empty");
        if (hashedPass == null || hashedPass.isBlank())
            throw new IllegalArgumentException("password hash must not be empty");
        if (schoolId <= 0)
            throw new IllegalArgumentException("invalid schoolId");

        try {
            int orgId = OrgDAO.addOrg(name, email, hashedPass, ModelManager.getSchool(schoolId).getSchoolName());
            if (orgId > 0) {
                return orgId;
            }
            else{
                throw new OrgServiceException("DAO failed to create org");
            }

        } catch (SQLException e) {
            throw new OrgServiceException("Error creating org", e);
        }
    }

    /**
     * Fetch an Org by its ID
     * @throws OrgServiceException if not found or on SQL error
     */
    public Org getOrgById(int orgId) {
        try {
            Org org = OrgDAO.getOrgById(orgId);
            if (org == null) {
                throw new OrgServiceException("Org not found: ID=" + orgId);
            }
            return org;
        } catch (SQLException e) {
            throw new OrgServiceException("Error fetching org ID=" + orgId, e);
        }
    }

    /**
     * Retrieve all Orgs in the system
     * @throws OrgServiceException on SQL error
     */
    public static Map<Integer, Org> getAllOrgs() {
        try {
            return OrgDAO.getAllOrgs();
        } catch (SQLException e) {
            throw new OrgServiceException("Error fetching all orgs", e);
        }
    }

    /**
     * Add a student to an Org’s membership
     * @throws OrgServiceException on SQL error
     */
    public boolean addMember(int orgId, int studentId) {
        try {
            return OrgDAO.addMember(orgId, studentId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    String.format("Error adding student %d to org %d", studentId, orgId), e);
        }
    }

    /**
     * Remove a student from an Org’s membership.
     * @throws OrgServiceException on SQL error
     */
    public boolean delMember(int orgId, int studentId) {
        try {
            return OrgDAO.delMember(orgId, studentId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    String.format("Error removing student %d from org %d", studentId, orgId), e);
        }
    }

    /**
     * List all current members of an Org.
     * @throws OrgServiceException on SQL error
     */
    public List<Integer> listMembers(int orgId) {
        try {
            return OrgDAO.getAllMembers(orgId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    "Error listing members for org " + orgId, e);
        }
    }

    /**
     * Invite a student to join an Org.
     * @throws OrgServiceException on SQL error
     */
    public boolean inviteMember(int orgId, int studentId) {
        try {
            return OrgDAO.inviteMember(orgId, studentId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    String.format("Error inviting student %d to org %d", studentId, orgId), e);
        }
    }

    /**
     * Fetch all pending membership requests for an Org.
     * @throws OrgServiceException on SQL error
     */
    @SuppressWarnings("unchecked")
    public Map<Integer, Student> getPendingRequests(int orgId) {
        try {
            return (Map<Integer, Student>) OrgDAO.getAllPendingRequests(orgId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    "Error fetching pending requests for org " + orgId, e);
        }
    }

    /**
     * Approve a pending membership request (and then add to members).
     * @throws OrgServiceException on SQL error
     */
    public boolean approveMemberRequest(int orgId, int studentId) {
        try {
            boolean approved = OrgDAO.approveMemberRequest(orgId, studentId);
            if (!approved) return false;
            return OrgDAO.addMember(orgId, studentId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    String.format("Error approving request for student %d in org %d", studentId, orgId), e);
        }
    }

    /**
     * Deny a pending membership request.
     * @throws OrgServiceException on SQL error
     */
    public boolean denyMemberRequest(int orgId, int studentId) {
        try {
            return OrgDAO.denyMemberRequest(orgId, studentId);
        } catch (SQLException e) {
            throw new OrgServiceException(
                    String.format("Error denying request for student %d in org %d", studentId, orgId), e);
        }
    }

    /**
     * Unchecked exception for service‐layer errors.
     */
    public static class OrgServiceException extends RuntimeException {
        public OrgServiceException(String message) {
            super(message);
        }
        public OrgServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
