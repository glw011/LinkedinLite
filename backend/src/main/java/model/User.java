package Model;

import java.util.HashMap;

public class User {
    public int id;
    public String email;
    public String hashPass;
    public String type;

    public School school;
    public String bio;
    public Picture profilePic;
    public HashMap<Integer, Picture> ownedImgs;
    public HashMap<Integer, Interest> interests;
    public HashMap<Integer, Post> posts;
    public HashMap<Integer, User> followingList;

    public int getID(){return this.id;}

    public void setEmail(String email){
        // TODO: Regex expression to identify valid email address?
        this.email = email;
    }
    public String getEmail(){return this.email;}

    public void setProfilePic(Picture img){
        if(this.ownedImgs.containsKey(img.getID())) this.profilePic = img;
        //else raise Exception;//TODO: implement what happens if set pfp to an img that is not owned by user
    }
    public Picture getProfilePic(){return this.profilePic;}

    public String getType(){return this.type;}

    public void setSchool(School school){this.school = school;}
    public School getSchool(){return this.school;}

    public void setBio(String bio){this.bio = bio;}
    public String getBio(){return this.bio;}

    public void followUser(User user){this.followingList.putIfAbsent(user.getID(), user);}
    public void unfollowUser(User user){this.followingList.remove(user.getID());}
    public User getFollowedUser(int id){return this.followingList.get(id);}
    public HashMap<Integer, User> getFollowingList(){return this.followingList;}

    public void uploadImg(Picture img){this.ownedImgs.putIfAbsent(img.getID(), img);}
    public void removeImg(Picture img){this.ownedImgs.remove(img.getID());}
    public Picture getImg(int id){return this.ownedImgs.get(id);}
    public HashMap<Integer, Picture> getOwnedImgsList(){return this.ownedImgs;}

    public void addInterest(Interest interest){this.interests.putIfAbsent(interest.getID(), interest);}
    public void removeInterest(Interest interest){this.interests.remove(interest.getID());}
    public Interest getInterest(int id){return this.interests.get(id);}
    public HashMap<Integer, Interest> getInterestsList(){return this.interests;}

    public void pushPost(Post post){this.posts.put(post.getID(), post);}
    public void removePost(Post post){this.posts.remove(post.getID());}
    public Post getPost(int id){return this.posts.get(id);}
    public HashMap<Integer, Post> getPostsList(){return this.posts;}

}
