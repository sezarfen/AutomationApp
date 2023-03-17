package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import magazaOtomasyon.Entities.Log;

public class LogDal extends DataAccess implements IDataAccessLayer<Log> {

	@Override
	public Log get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Log> getAll() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Log> logs = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM logs");
			logs = new ArrayList<Log>();

			while (resultSet.next()) {
				logs.add(new Log(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logs;
	}

	@Override
	public void add(Log entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO logs (kind ,content , date) values (?,?,?)");
			preparedStatement.setString(1, entity.getKind());
			preparedStatement.setString(2, entity.getContent());
			preparedStatement.setString(3, entity.getDate());

			int effectedRows = preparedStatement.executeUpdate();
			System.out.println(effectedRows + " effected!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Log entity) {
		// TODO Auto-generated method stub

	}

}
