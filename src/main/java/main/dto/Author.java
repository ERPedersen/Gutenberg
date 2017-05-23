package main.dto;

public class Author {

    private long UID;
    private String name;

    /**
     * Default constructor.
     */
    public Author() {
    }

    /**
     * Constructor that instantiates the author with a name.
     *
     * @param name The name of the author.
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Constructor that instantiates the Author with an UID and a name.
     *
     * @param UID The UID of the author.
     * @param name The name of the author.
     */
    public Author(long UID, String name) {
        this.UID = UID;
        this.name = name;
    }

    /**
     * Get the UID of the author.
     *
     * @return The UID of the author.
     */
    public long getUID() {
        return UID;
    }

    /**
     * Gets the name of the author.
     *
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name The name of the author.
     */
    public void setName(String name) {
        this.name = name;
    }
}
