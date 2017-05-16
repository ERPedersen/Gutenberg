package main.dto;

public class Location {

    private Long UID;
    private double latitude;
    private double longitude;
    private String name;
    private double distance;

    /**
     * Default constructor.
     */
    public Location() {
    }

    /**
     * Constructor that instantiates a locations with id, latitude, longitude and name.
     *
     * @param UID Long The UID of the location.
     * @param latitude Double The latitude of the location.
     * @param longitude Double The longitude of the location.
     * @param name String The name of the location.
     */
    public Location(Long UID, double latitude, double longitude, String name) {
        this.UID = UID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    /**
     * Constructor that instantiates a location with latitude, longitude and name.
     *
     * @param latitude Double The latitude of the Location.
     * @param longitude Double The longitude of the Location.
     * @param name String The name of the Location.
     */
    public Location(Long UID, double latitude, double longitude, String name, double distance) {
        this.UID = UID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.distance = distance;
    }

    /**
     * Gets the UID of the location.
     *
     * @return Long The UID of the location.
     */
    public Long getUID() {
        return UID;
    }

    /**
     * Sets the UID of the location.
     *
     * @param UID Long The UID of the location.
     */
    public void setUID(Long UID) {
        this.UID = UID;
    }

    /**
     * Get the latitude of the location.
     *
     * @return Double The latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the location.
     *
     * @param latitude Double The latitude of the location.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the longitude of the location.
     *
     * @return Double The longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the latitude of the location.
     *
     * @param longitude Double The longitude of the location.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the name of the location.
     *
     * @return String the name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the location.
     *
     * @param name String the name of the location.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the distance to another location.
     * @return distance Double The distance to the other location.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set the distance to another location.
     *
     * @param distance double The distance to another location.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
