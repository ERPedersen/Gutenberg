package main.dto;

import java.util.List;

public class Book {
    private long UID;
    private String title;
    private List<Author> authors;
    private List<Location> locations;
    private List<Location> locationsWithinRadius;
    private String text;

    /**
     * Default constructor.
     */
    public Book() {
    }

    /**
     * Constructor that initiates the Book with a title, a list of Authors, a list of Locations and text.
     *
     * @param title The title of the Book.
     * @param authors The Authors of the Book.
     * @param locations The Locations of the Book.
     * @param text The text of the Book.
     */
    public Book(String title, List<Author> authors, List<Location> locations, String text) {
        this.title = title;
        this.authors = authors;
        this.locations = locations;
        this.text = text;
    }

    /**
     * Constructor that initiates the Book with an UID, a title, a list of Authors, a list of Locations and text.
     *
     * @param UID The UID of the Book.
     * @param title The title of the Book.
     * @param authors The Authors of the Book.
     * @param locations The Locations of the Book.
     * @param text The text of the Book.
     */
    public Book(long UID, String title, List<Author> authors, List<Location> locations, String text) {
        this.UID = UID;
        this.title = title;
        this.authors = authors;
        this.locations = locations;
        this.text = text;
    }

    /**
     * Constructor that initiates the Book with an UID, a title, a list of Authors, a list of Locations, a list of Locations within a radius and text.
     * @param UID The UID of the Book.
     * @param title The title of the Book.
     * @param authors The authors of the Book.
     * @param locations The locations of the Book.
     * @param locationsWithinRadius The locations within a given radius of the Book.
     * @param text The text of the Book.
     */
    public Book(long UID, String title, List<Author> authors, List<Location> locations, List<Location> locationsWithinRadius, String text) {
        this.UID = UID;
        this.title = title;
        this.authors = authors;
        this.locations = locations;
        this.locationsWithinRadius = locationsWithinRadius;
        this.text = text;
    }

    /**
     * Gets the UID of the Book.
     *
     * @return The UID of the Book.
     */
    public long getUID() {
        return UID;
    }

    /**
     * Sets the UID of the Book.
     *
     * @param UID The UID of the Book.
     */
    public void setUID(long UID) {
        this.UID = UID;
    }

    /**
     * Gets the Title of the Book.
     *
     * @return The title of the Book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Title of the Book.
     *
     * @param title The title of the Book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets a List of Authors associated with the Book.
     *
     * @return The Authors associated with the Book.
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets a List of Authors associated with the Book.
     *
     * @param authors The Authors associated with the Book.
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * Gets a List of Locations mentioned in the Book.
     *
     * @return The Locations mentioned in the Book.
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Sets a List of Locations mentioned in the Book.
     *
     * @param locations The Locations mentioned in the Book.
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    /**
     * Gets the Url for the text of the Book.
     *
     * @return The Url for the text of the Book.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the Urls for the text of the Book.
     *
     * @param text the Url for the text of the Book.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Adds a location to the locations list.
     *
     * @param location The location to add.
     */
    public void addLocation(Location location) {
        this.locations.add(location);
    }

    /**
     * Adds a location to the locations list.
     *
     * @param author The author to add.
     */
	public void addAuthor(Author author) {
        this.authors.add(author);
	}

    /**
     * Gets a list of locations within a radius of another point.
     *
     * @return The list of locations within the radius of another point.
     */
    public List<Location> getLocationsWithinRadius() {
        return locationsWithinRadius;
    }

    /**
     * Sets a list of locations within a radius of another point.
     *
     * @param locationsWithinRadius The list of locations within the radius of another point.
     */
    public void setLocationsWithinRadius(List<Location> locationsWithinRadius) {
        this.locationsWithinRadius = locationsWithinRadius;
    }

    /**
     * Adds a locations to the location within locations list.
     *
     * @param location The location to add.
     */
    public void addLocationWithinRadius(Location location) {
        this.locationsWithinRadius.add(location);
    }
}
