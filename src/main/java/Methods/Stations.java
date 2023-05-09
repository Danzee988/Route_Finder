package Methods;

import java.util.*;

public class Stations {

    int id;
    String name;

    float longitude;

    float latitude;

    //------------------Constructor------------------
    public Stations(int id, String name, float longitude, float latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

//    public Stations(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    //------------------Getters------------------
    public float getLongitude() {
        return longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    //------------------Setters------------------


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    //------------------Methods------------------



    @Override
    public String toString() {
        return "Stations{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
