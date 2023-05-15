package Methods;


public class Stations {

    int id;
    String name;

    int x;
    int y;
    float longitude;

    float latitude;

    //------------------Constructor------------------
    public Stations(int id, String name, float longitude, float latitude, int x, int y) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //------------------Methods------------------



    @Override
    public String toString() {
        return "Stations{ " +
                "id= " + id +
                ", name= " + name +
                ", longitude= " + longitude +
                ", latitude= " + latitude +
                " }";
    }

    //@Override
    public String pathName() {
        return  name;
    }

}
