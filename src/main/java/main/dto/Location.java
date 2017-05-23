package main.dto;

public class Location {

    private Long UID;
    private double latitude;
    private double longitude;
    private String name;

    /**
     * Default constructor.
     */
    public Location() {
    }

    /**
     * Constructor that instantiates a locations with id, latitude, longitude and name.
     *
     * @param UID The UID of the location.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param name The name of the location.
     */
    public Location(Long UID, double latitude, double longitude, String name) {
        this.UID = UID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    /**
     * Gets the UID of the location.
     *
     * @return The UID of the location.
     */
    public Long getUID() {
        return UID;
    }

    /**
     * Sets the UID of the location.
     *
     * @param UID The UID of the location.
     */
    public void setUID(Long UID) {
        this.UID = UID;
    }

    /**
     * Get the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the location.
     *
     * @param latitude The latitude of the location.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the latitude of the location.
     *
     * @param longitude The longitude of the location.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the location.
     *
     * @param name The name of the location.
     */
    public void setName(String name) {
        this.name = name;
    }
}
