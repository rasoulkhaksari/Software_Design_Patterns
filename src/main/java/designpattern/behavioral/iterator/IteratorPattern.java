package designpattern.behavioral.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

interface SocialNetwork {
    ProfileIterator createFriendsIterator(int profileId);

    ProfileIterator createCoworkersIterator(int profileId);
}

class Facebook implements SocialNetwork {
    @Override
    public ProfileIterator createFriendsIterator(int profileId) {
        return new FacebookIterator(this, profileId, "friends");
    }

    @Override
    public ProfileIterator createCoworkersIterator(int profileId) {
        return new FacebookIterator(this, profileId, "coworkers");
    }

    public List<Profile> socialGraphRequest(int profileId, String type) {
        System.out.println(String.format("Facebook Social Graph Request for profile:%d and type:%s", profileId, type));
        return new ArrayList<Profile>(Arrays.asList(new Profile(1, "user1", "user1@facebook.com"),
                new Profile(2, "user2", "user2@facebook.com")));
    }

}

class Profile {
    int id;
    String title;
    String email;
    List<Profile> firends;
    List<Profile> coworkers;

    public Profile(int id, String title, String email) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.firends = new ArrayList<>();
        this.coworkers = new ArrayList<>();
    }

    public String getEmail() {
        return this.email;
    }

    public int getId(){
        return this.id;
    }
}

interface ProfileIterator {
    Profile getNext();

    boolean hasMore();
}

class FacebookIterator implements ProfileIterator {
    Facebook facebook;
    int profileId;
    String type;
    int currentPosition;
    List<Profile> cache;

    public FacebookIterator(Facebook facebook, int profileId, String type) {
        this.facebook = facebook;
        this.profileId = profileId;
        this.type = type;
    }

    private void lazyInit() {
        if (cache == null) {
            cache = facebook.socialGraphRequest(profileId, type);
        }
    }

    @Override
    public Profile getNext() {
        if (hasMore()) {
            return cache.get(currentPosition++);
        }
        return null;
    }

    @Override
    public boolean hasMore() {
        lazyInit();
        return currentPosition<cache.size() ;
    }
}

class SocialSpammer {
    public void send(ProfileIterator iterator, String message) {
        while (iterator.hasMore()) {
            Profile profile = iterator.getNext();
            System.out.println(String.format("Sending Email to:%s , Message:%s", profile.getEmail(), message));
        }
    }
}

class Application{
    SocialNetwork network;
    SocialSpammer spammer;

    public void config(){
        Scanner inputDevice = new Scanner(System.in);
        System.out.print("Which Social network do you want to send spam emails?(facebook/linkedin):");
        if (inputDevice.next().equals("facebook")) {
            this.network=new Facebook();
        }else{
            System.out.println("LinkedIn Implemenation is same as facebook");
            this.network=new Facebook();
        }
        inputDevice.close();
        this.spammer=new SocialSpammer();
    }
    public void sendSpamToFriends(Profile profile){
        ProfileIterator iterator= network.createFriendsIterator(profile.getId());
        this.spammer.send(iterator, "Very important message");
    }

    public void sendSpamToCoworkers(Profile profile){
        ProfileIterator iterator=network.createCoworkersIterator(profile.getId());
        this.spammer.send(iterator, "Very important message");
    }
}

public class IteratorPattern {
    public static void demo() {
        Application app=new Application();
        app.config();
        Profile sampleProfile =new Profile(100, "test user", "test@facebook.com"); 
        app.sendSpamToFriends(sampleProfile);
    }
}
