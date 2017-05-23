package main.util;

import com.mongodb.client.MongoDatabase;

public interface IDBConnectorMongo {
    /**
     * Create a mongodatabase client.
     *
     * @return The client.
     */
    MongoDatabase getConnection();
}
