package com.penthouse_bogmjary;

public class BookmarkData {
    String buildingAddressBookmark;
    String buildingInfo;
    int buildingScore;

    public String getBuildingAddressBookmark(){
        return buildingAddressBookmark;
    }
    public void setBuildingAddressBookmark(String buildingAddressBookmark){
        this.buildingAddressBookmark = buildingAddressBookmark;
    }

    public String getBuildingInfo(){
        return buildingInfo;
    }
    public void setBuildingInfo(String buildingInfo){
        this.buildingInfo = buildingInfo;
    }

    public int getBuildingScore(){
        return buildingScore;
    }

    public void setBuildingScore(int buildingScore){
        this.buildingScore = buildingScore;
    }
}
