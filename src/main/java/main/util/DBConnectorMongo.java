package main.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

public class DBConnectorMongo implements IDBConnectorMongo {

    private String uri;
    private String user;
    private String password;
    private String database;

    /**
     * Default constructor.
     */
    public DBConnectorMongo() {
        this.uri = System.getenv("MONGO_URI");
        this.user = System.getenv("MONGO_USER");
        this.password = System.getenv("MONGO_PWD");
        this.database = System.getenv("MONGO_DB");
    }

    /**
     * Dependency injector.
     *
     * @param uri String to the mongoDB.
     * @param user String username to log in with.
     * @param password String passqord to log in with.
     * @param database String the database to connect to.
     */
    public DBConnectorMongo(String uri, String user, String password, String database) {
        this.uri = uri;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    /**
     * Create a mongodatabase client.
     *
     * @return MongoDatabase.
     */
    @Override
    public MongoDatabase getConnection() {
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(uri), Arrays.asList(credential));
        MongoDatabase db = mongoClient.getDatabase(database);

        return db;
    }


}
