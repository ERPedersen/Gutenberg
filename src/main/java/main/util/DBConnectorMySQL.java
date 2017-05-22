package main.util;

import main.exception.ConnectionAlreadyClosedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBConnectorMySQL implements IDBConnectorMySQL {

	private String driver;
	private String uri;
	private String user;
	private String password;
	private Connection connection = null;

	/**
	 * Default constructor, that uses environment variables.
	 */
	public DBConnectorMySQL() {
		this.driver = "com.mysql.jdbc.Driver";
		this.uri = System.getenv("MYSQL_URI");
		this.user = System.getenv("MYSQL_USER");
		this.password = System.getenv("MYSQL_PWD");
	}

	/**
	 * Constructor that enables dependency injection.
	 *
	 * @param driver String The JDBC database driver.
	 * @param uri String The URI to connect to.
	 * @param user String The username to log in with.
	 * @param password String The password to authenticate with.
	 */
	public DBConnectorMySQL(String driver, String uri, String user, String password) {
		this.driver = driver;
		this.uri = uri;
		this.user = user;
		this.password = password;
	}

	/**
	 * Checks if the connection is null or closed, and returns either a new or the old connection.
	 *
	 * @return Connection The connection to the MySQL database.
	 * @throws SQLException If there is a problem connecting to the database.
	 * @throws ClassNotFoundException If the JDBC driver was not found.
	 */
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {

		if (null == connection || connection.isClosed()) {
			Class.forName(driver);
			connection = DriverManager.getConnection(uri, user, password);
		}

		return connection;

	}

	/**
	 * Checks if the connection is open and closes it.
	 *
	 * @throws ConnectionAlreadyClosedException If the connection is already closed or non-existent.
	 */
	@Override
	public void closeConnection() {
		try {
			if (null != connection && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			if (Objects.equals(System.getenv("PROCESS_ENV"), "dev")) {
				e.printStackTrace();
			}
		}
	}
}
