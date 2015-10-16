package com.google.project.Utilites;

/**
 * Created by OmarAli on 07/10/2015.
 */
public class Child {

    private String TrailerName;
    private int TrailerImage;
    private String ReviewerName;
    private String ReviewerContent;

    public String getReviewerName() {return ReviewerName;}

    public void setReviewerName(String reviewerName) {ReviewerName = reviewerName;}

    public String getReviewerContent() {return ReviewerContent;}

    public void setReviewerContent(String reviewerContent) {ReviewerContent = reviewerContent;}

    public String getTrailerName() {
        return TrailerName;
    }

    public void setTrailerName(String Name) {
        this.TrailerName = Name;
    }

    public int getTrailerImage() {
        return TrailerImage;
    }

    public void setTrailerImage(int Image) {
        this.TrailerImage = Image;
    }
}
