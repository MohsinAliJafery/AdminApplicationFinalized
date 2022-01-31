package com.bhjbestkalyangame.adminapplication;

public class Upload {

    private String Title;
    private String Description;
    private String SubTitle;
    private String ImageUrl;
    public Upload() {
        //empty constructor needed
    }
    public Upload(String title, String description, String subTitle, String imageUrl) {

        Title = title;
        SubTitle = subTitle;
        Description = description;
        ImageUrl = imageUrl;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }
}
