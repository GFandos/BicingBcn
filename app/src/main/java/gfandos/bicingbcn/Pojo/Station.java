package gfandos.bicingbcn.Pojo;

/**
 * Created by 47989768s on 31/01/17.
 */

public class Station {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String type;
    private int occupation; //%

    public Station(int id_, String name_, double latitude_, double longitude_, String type_, int occupation_) {
        id = id_;
        name = name_;
        latitude = latitude_;
        longitude = longitude_;
        type = type_;
        occupation = occupation_;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type='" + type + '\'' +
                ", occupation=" + occupation +
                '}';
    }
}
