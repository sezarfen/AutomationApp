package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import magazaOtomasyon.Entities.Sales;

public class SalesDal extends DataAccess implements IDataAccessLayer<Sales> {

	@Override
	public Sales get(int id) {
		return null;
	}

	@Override
	public ArrayList<Sales> getAll() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Sales> sales = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM sales");
			sales = new ArrayList<Sales>();

			while (resultSet.next()) {
				Sales newSale = new Sales(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
				sales.add(newSale);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return sales;

	}

	@Override
	public void add(Sales entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO sales (productName , productCode , productAmount, productSinglePrice , productTotalGain) values (?,?,?,?,?)");
			preparedStatement.setString(1, entity.getProductName());
			preparedStatement.setInt(2, entity.getProductCode());
			preparedStatement.setInt(3, entity.getProductAmount());
			preparedStatement.setInt(4, entity.getProductSinglePrice());
			preparedStatement.setInt(5, entity.getProductTotalGain());
			
			int effectedRows = preparedStatement.executeUpdate();
			System.out.println(effectedRows + " row effected!");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public void update(Sales entity) {

	}

}
