package com.earthwatch.googlemapsv2.app;

public class Marker {
    private long id;
    private String type;
    private String title;
    private String description;
    private String latitude;
    private String longitude;

    public Marker(){
        id=-1;
        type="";
        title="";
        description="";
        latitude="";
        longitude="";
    }

    public Marker(String type, String title, String description, String latitude, String longitude){
        super();
        this.type=type;
        this.title=title;
        this.description=description;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return title;
    }
}
