package main.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBConnectorMySQL {

	/**
	 * Checks if the connection is null or closed, and returns either a new or the old connection.
	 *
	 * @return The connection to the MySQL database.
	 * @throws SQLException If there is a problem connecting to the database.
	 * @throws ClassNotFoundException If the JDBC driver was not found.
	 */
	Connection getConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Checks if the connection is open and closes it.
	 */
	void closeConnection();

}
