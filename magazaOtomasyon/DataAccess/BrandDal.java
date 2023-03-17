package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import magazaOtomasyon.Entities.Brand;

public class BrandDal extends DataAccess implements IDataAccessLayer<Brand> {

	@Override
	public Brand get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Brand> getAll() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Brand> brands = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from brands");

			brands = new ArrayList<Brand>();

			while (resultSet.next()) {
				brands.add(new Brand(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return brands;
	}

	@Override
	public void add(Brand brand) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO brands (brandCode ,brandName) VALUES (?,?)");
			preparedStatement.setInt(1, brand.getBrandCode());
			preparedStatement.setString(2, brand.getName());
			int effectedRows = preparedStatement.executeUpdate();
			System.out.println(effectedRows + " row affected!");
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@Override
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM brands where id = ?");
			preparedStatement.setInt(1, id);
			int deletedRows = preparedStatement.executeUpdate();
			System.out.println(deletedRows + " row deleted");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Brand entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("UPDATE brands set brandCode = ? , brandName = ? where id = ?");
			preparedStatement.setInt(1,entity.getBrandCode());
			preparedStatement.setString(2,entity.getName());
			preparedStatement.setInt(3,entity.getId());
			
			int effectedRows = preparedStatement.executeUpdate();
			
			System.out.println(effectedRows + " rows effected!");
			
		}catch(SQLException exception) {
			exception.printStackTrace();
		}

		
	}

}
