package com.javatechie.bean;

public class SocialMediaServiceFactory {


    public static SocialMediaService getInstance(String socialMediaType){
        if(socialMediaType.equals("facebook")){
            return new FacebookService();
        }if(socialMediaType.equals("instagram")){
            return new InstagramService();
        }
        return null;
    }
}
