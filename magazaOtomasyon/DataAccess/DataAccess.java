package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataAccess {
	private final String connectionString = "jdbc:sqlite://C:/SQLite/databases/automation.db";

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(this.connectionString);
	}
}
