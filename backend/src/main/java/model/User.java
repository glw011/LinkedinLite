package model;

import java.util.LinkedList;

public class User {
    public int id;
    public String email;
    public String hashPass;
    public String type;

    public School school;
    public String bio;
    public Picture profilePic;
    public LinkedList<Integer> ownedImgs;
    public LinkedList<Integer> interests;
    public LinkedList<Integer> posts;
    public LinkedList<Integer> followingList;

    public int getID(){return this.id;}

    public void setEmail(String email){
        // TODO: Regex expression to identify valid email address?
        this.email = email;
    }
    public String getEmail(){return this.email;}

    public void setProfilePic(Picture img){
        if(this.ownedImgs.contains(img.getID())) this.profilePic = img;
        //else raise Exception;//TODO: implement what happens if set pfp to an img that is not owned by user
    }
    public Picture getProfilePic(){return this.profilePic;}

    public String getType(){return this.type;}

    public void setSchool(School school){this.school = school;}
    public School getSchool(){return this.school;}

    public void setBio(String bio){this.bio = bio;}
    public String getBio(){return this.bio;}

    public void setFollowingList(LinkedList<Integer> followList){this.followingList = followList;}
    public void followUser(int userId){if(!this.followingList.contains(userId))this.followingList.add(userId);}
    public void unfollowUser(int userId){this.followingList.remove((Integer)userId);}
    public LinkedList<Integer> getFollowingList(){return this.followingList;}

    public void setOwnedImgsList(LinkedList<Integer> imgList){this.ownedImgs = imgList;}
    public LinkedList<Integer> getOwnedImgsList(){return this.ownedImgs;}

    public void setInterestList(LinkedList<Integer> intrstList){this.interests = intrstList;}
    public void addInterest(int id){if(!this.interests.contains(id))this.interests.add(id);}
    public void removeInterest(int id){this.interests.remove((Integer)id);}
    public LinkedList<Integer> getInterestsList(){return this.interests;}

    public void setPostsList(LinkedList<Integer> postList){this.posts = postList;}
    public void pushPost(Post post){this.posts.add(post.getID());}
    public void removePost(int id){this.posts.remove((Integer)id);}
    public LinkedList<Integer> getPostsList(){return this.posts;}

}
