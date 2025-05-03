package service;

import dao.UserDAO;
import model.UserType;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Service layer for user-related operations.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Thrown by UserService on any failure.
     */
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) {
            super(message);
        }
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Authenticate user by email & password hash.
     * @return a session token if successful.
     * @throws ServiceException on auth failure or underlying error.
     */
    public String authenticate(String email, String hashedPass) {
        if (email == null || hashedPass == null) {
            throw new ServiceException("Email and password must be provided");
        }
        try {
            String token = userDAO.authUser(email, hashedPass);
            if (token == null || token.isEmpty()) {
                throw new ServiceException("Invalid credentials");
            }
            return token;
        } catch (SQLException e) {
            throw new ServiceException("Error during authentication", e);
        }
    }

    /**
     * Register a new user.
     * @return true if registration succeeded.
     * @throws ServiceException on failure or underlying error.
     */
    public boolean registerUser(String email, String hashedPass, UserType userType) {
        if (email == null || !email.contains("@")) {
            throw new ServiceException("Invalid email address");
        }
        if (hashedPass == null || hashedPass.length() < 8) {
            throw new ServiceException("Password hash too short");
        }
        try {
            boolean created = UserDAO.addUser(email, hashedPass, userType);
            if (!created) {
                throw new ServiceException("Registration failed for " + email);
            }
            return true;
        } catch (SQLException e) {
            throw new ServiceException("Error during user registration", e);
        }
    }

    /**
     * Update a user's bio.
     */
    public boolean updateBio(int userId, String bio) {
        if (bio != null && bio.length() > 500) {
            throw new ServiceException("Bio exceeds 500 characters");
        }
        try {
            return userDAO.setBio(userId, bio);
        } catch (SQLException e) {
            throw new ServiceException("Error updating bio", e);
        }
    }

    /**
     * Fetch a user's bio.
     */
    public String fetchBio(int userId) {
        try {
            return userDAO.getBio(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching bio", e);
        }
    }

    /**
     * Create a new post.
     */
    public boolean createPost(int userId, String content, LinkedList<Integer> tags) {
        if (content == null || content.trim().isEmpty()) {
            throw new ServiceException("Post content cannot be empty");
        }
        try {
            return userDAO.addPost(userId, content, tags);
        } catch (SQLException e) {
            throw new ServiceException("Error creating post", e);
        }
    }

    /**
     * Delete a post by ID.
     */
    public boolean deletePost(int postId) {
        try {
            return userDAO.delPost(postId);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting post", e);
        }
    }

    /**
     * Get all posts by a user.
     */
    public LinkedList<Integer> fetchAllPosts(int userId) {
        try {
            return userDAO.getAllUserPosts(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching user posts", e);
        }
    }

    /**
     * Get recent posts (e.g. last 7 days) by a user.
     */
    public LinkedList<Integer> fetchRecentPosts(int userId) {
        try {
            return userDAO.getAllRecentPosts(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching recent posts", e);
        }
    }

    /**
     * Add an interest.
     */
    public boolean addInterest(int userId, int interestId) {
        try {
            return userDAO.addInterest(userId, interestId);
        } catch (SQLException e) {
            throw new ServiceException("Error adding interest", e);
        }
    }

    /**
     * Remove an interest.
     */
    public boolean removeInterest(int userId, int interestId) {
        try {
            return userDAO.delInterest(userId, interestId);
        } catch (SQLException e) {
            throw new ServiceException("Error removing interest", e);
        }
    }

    /**
     * Fetch all interest IDs.
     */
    public LinkedList<Integer> fetchInterests(int userId) {
        try {
            return userDAO.getAllInterests(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching interests", e);
        }
    }

    /**
     * Follow another user.
     */
    public boolean followUser(int userId, int toFollowId) {
        if (userId == toFollowId) {
            throw new ServiceException("Cannot follow yourself");
        }
        try {
            return userDAO.followUser(userId, toFollowId);
        } catch (SQLException e) {
            throw new ServiceException("Error following user", e);
        }
    }

    /**
     * Unfollow a user.
     */
    public boolean unfollowUser(int userId, int toUnfollowId) {
        try {
            return userDAO.unfollowUser(userId, toUnfollowId);
        } catch (SQLException e) {
            throw new ServiceException("Error unfollowing user", e);
        }
    }

    /**
     * Fetch IDs of users this user is following.
     */
    public LinkedList<Integer> fetchFollowing(int userId) {
        try {
            return userDAO.getAllFollowedUsers(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching following list", e);
        }
    }

    /**
     * Fetch IDs of users following this user.
     */
    public LinkedList<Integer> fetchFollowers(int userId) {
        try {
            return userDAO.getAllUsersFollowing(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error fetching followers list", e);
        }
    }

    /**
     * Update the user's school affiliation.
     */
    public boolean updateSchool(int userId, int schoolId) {
        try {
            return userDAO.setSchool(userId, schoolId);
        } catch (SQLException e) {
            throw new ServiceException("Error updating school", e);
        }
    }

    /**
     * Fetch the user's school ID.
     */
    public int fetchSchool(int userId) {
        try {
            return userDAO.getSchool(userId).getId();
        } catch (SQLException e) {
            throw new ServiceException("Error fetching school", e);
        }
    }

    /**
     * Update the user's profile image.
     */
    public boolean updateProfileImage(int userId, int imgId) {
                // DAO setProfileImg() is unchecked, so we just call it directly
                        return userDAO.setProfileImg(userId, imgId);
           }

    /**
     * Fetch the user's profile image ID.
     */
    public int fetchProfileImage(int userId) {
        try {
            return userDAO.getProfileImg(userId).getID();
        } catch (SQLException e) {
            throw new ServiceException("Error fetching profile image", e);
        }
    }
}
