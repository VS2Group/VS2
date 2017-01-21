package de.hska.exablog.GUI.Controller.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15.01.2017.
 */
public class RandomProfilePicture {

    private static List<String> profilePictures;

    static {
        profilePictures = new ArrayList<>();
        profilePictures.add("/image/profile/p1.jpg");
        profilePictures.add("/image/profile/p2.jpg");
        profilePictures.add("/image/profile/p3.jpg");
        profilePictures.add("/image/profile/p4.jpg");
        profilePictures.add("/image/profile/p5.jpg");
        profilePictures.add("/image/profile/p6.jpg");
        profilePictures.add("/image/profile/hipster.jpg");
    }

    public static String getRandomFile() {
        int randomIndex = (int)(Math.random() * profilePictures.size());
        return profilePictures.get(randomIndex);
    }

}
