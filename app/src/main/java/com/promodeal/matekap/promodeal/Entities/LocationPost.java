package com.promodeal.matekap.promodeal.Entities;

/**
 * Created by Ali on 02/05/2016.
 */
public class LocationPost {
    private int IdLocation;
    private double LongitudeLocation;
    private double LatitudeLocation;
    private String NameLocation;

    public LocationPost() {
    }

    public LocationPost(double longitudeLocation, double latitudeLocation, String nameLocation) {
        LongitudeLocation = longitudeLocation;
        LatitudeLocation = latitudeLocation;
        NameLocation = nameLocation;
    }

    public LocationPost(int idLocation, double longitudeLocation, double latitudeLocation, String nameLocation) {
        IdLocation = idLocation;
        LongitudeLocation = longitudeLocation;
        LatitudeLocation = latitudeLocation;
        NameLocation = nameLocation;
    }

    public int getIdLocation() {
        return IdLocation;
    }

    public void setIdLocation(int idLocation) {
        IdLocation = idLocation;
    }

    public double getLongitudeLocation() {
        return LongitudeLocation;
    }

    public void setLongitudeLocation(double longitudeLocation) {
        LongitudeLocation = longitudeLocation;
    }

    public double getLatitudeLocation() {
        return LatitudeLocation;
    }

    public void setLatitudeLocation(double latitudeLocation) {
        LatitudeLocation = latitudeLocation;
    }

    public String getNameLocation() {
        return NameLocation;
    }

    public void setNameLocation(String nameLocation) {
        NameLocation = nameLocation;
    }
}
