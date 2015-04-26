package com.example.aman.housing;

/**
 * Created by Aman on 4/26/2015.
 */
public class Land {

    public String propid;
    public String ownerid;
    public String price;
    public String bhk;
    public String lat;
    public String lon;
    public String numppl;
    public String rentable;
    public String description;
    public String address;

    public Land() {

    }

    public Land(String propid, String ownerid, String price, String bhk, String lat, String lon, String numppl, String rentable, String description, String address) {
        this.propid=propid;
        this.ownerid=ownerid;
        this.price=price;
        this.bhk=bhk;
        this.lat=lat;
        this.lon=lon;
        this.numppl=numppl;
        this.rentable=rentable;
        this.description=description;
        this.address=address;
    }


}
