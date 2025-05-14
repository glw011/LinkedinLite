package service;

import dao.UserDAO;

import model.Org;
import model.Student;


import java.sql.SQLException;
import java.util.*;

public class UserService {


    private final UserDAO dao;

    public UserService() {
        this(new UserDAO());
    }

    public UserService(UserDAO dao) {
        this.dao = Objects.requireNonNull(dao, "dao must not be null");
    }

    /**
     * Authenticate with raw password.
     * @param email   the user’s email
     * @param rawPass the plaintext password from the UI
     * @return        a token if credentials valid, or null if bad login
     * @throws AuthException on unexpected SQL errors
     */
    public String authenticate(String email, String rawPass) {
        try {
            return dao.authUser(email, rawPass);
        } catch (SQLException e) {
            throw new AuthException("Failed to authenticate user", e);
        }
    }

    public List<Object> getAllUsers() {
        try {

            Collection<Org>   orgs     = OrgService.getAllOrgs().values();
            List<Student>     students = StudentService.getAllStudents();

            List<Object> all = new ArrayList<>(orgs.size() + students.size());
            all.addAll(orgs);
            all.addAll(students);

            return all;

        } catch (SQLException e) {
            throw new UserServiceException("Error fetching all users", e);
        }
    }

    public static class UserServiceException extends RuntimeException {
        public UserServiceException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
    public static class AuthException extends RuntimeException {
        public AuthException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}



