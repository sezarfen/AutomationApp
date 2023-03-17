package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import magazaOtomasyon.Entities.BrandDebt;

public class BrandDebtDal extends DataAccess implements IDataAccessLayer<BrandDebt> {

	@Override
	public BrandDebt get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BrandDebt> getAll() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<BrandDebt> brandDebts = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM brand_debt");
			brandDebts = new ArrayList<BrandDebt>();

			while (resultSet.next()) {
				brandDebts.add(new BrandDebt(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getDouble(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return brandDebts;
	}

	@Override
	public void add(BrandDebt entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection
					.prepareStatement("INSERT INTO brand_debt (brandCode ,brandName , debt) VALUES (?,?,?)");
			preparedStatement.setInt(1, entity.getBrandCode());
			preparedStatement.setString(2, entity.getBrandName());
			preparedStatement.setDouble(3, entity.getBrandDebt());
			int effectedRows = preparedStatement.executeUpdate();
			System.out.println(effectedRows + " row affected!");
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public void addDebt(String brandName, double debt) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection
					.prepareStatement("UPDATE brand_debt set debt = debt + ? where brandName = ?");
			preparedStatement.setDouble(1, debt);
			preparedStatement.setString(2, brandName);
			int effectedRow = preparedStatement.executeUpdate();
			System.out.println(effectedRow + " row effected!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void delete(int brandCode) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM brand_debt where brandCode = ?");
			preparedStatement.setInt(1, brandCode);

			int effectedRow = preparedStatement.executeUpdate();
			System.out.println(effectedRow + " row effected!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public void update(BrandDebt entity) {
		// TODO Auto-generated method stub

	}

	public void updateForBrand(int oldBrandCode, String newBrandName, int newBrandCode) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("UPDATE brand_debt set brandCode = ? , brandName = ? where brandCode = ?");
			preparedStatement.setInt(1, newBrandCode);
			preparedStatement.setString(2, newBrandName);
			preparedStatement.setInt(3, oldBrandCode);
			
			int effectedRow = preparedStatement.executeUpdate();
			System.out.println(effectedRow + " row effected!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

}
