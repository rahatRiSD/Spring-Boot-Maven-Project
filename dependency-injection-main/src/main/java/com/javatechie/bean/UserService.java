package com.javatechie.bean;

public class UserService {

    private SocialMediaService socialMediaService;

//    public SocialMediaService getSocialMediaService() {
//        return socialMediaService;
//    }
//
//    public void setSocialMediaService(SocialMediaService socialMediaService) {
//        this.socialMediaService = socialMediaService;
//    }


    public UserService(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    public void displayFeeds(){
        //approach 1
//        SocialMediaApp app=new SocialMediaApp();
//        FacebookService facebookService=new FacebookService();
//        InstagramService instagramService=new InstagramService();
        //app.getUserFeeds();

        //approach 2
       // SocialMediaService service=new FacebookService();
       // service.getUserFeeds();

        //approach 3 (depends on class/bean alias name)
       // SocialMediaService instance = SocialMediaServiceFactory.getInstance("instagram");
       // instance.getUserFeeds();

        socialMediaService.getUserFeeds();
    }

    //IOC
    public static void main(String[] args) {
        UserService userService=new UserService(new InstagramService());
       // userService.setSocialMediaService(new FacebookService());
        userService.displayFeeds();
    }
}
