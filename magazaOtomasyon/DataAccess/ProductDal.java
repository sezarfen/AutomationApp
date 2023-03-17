package magazaOtomasyon.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import magazaOtomasyon.Entities.Product;

public class ProductDal extends DataAccess implements IDataAccessLayer<Product>{

	@Override
	public Product get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Product> getAll() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Product> products = null;
		
		
		try {
			connection = getConnection();	
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM products");
			products = new ArrayList<Product>();
			
			while(resultSet.next()) {
				products.add(new Product(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return products;
	}

	public void add(Product product) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(
					"INSERT INTO products (productCode ,productName , productProvider , productPrice , productAmount) VALUES (?,?,?,?,?)");
			preparedStatement.setInt(1, product.getProductCode());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setString(3, product.getProductProvider());
			preparedStatement.setDouble(4, product.getProductPrice());
			preparedStatement.setInt(5, product.getProductAmount());
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
			preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
			preparedStatement.setInt(1, id);
			
			int deletedRows = preparedStatement.executeUpdate();
			System.out.println(deletedRows + " adet row silindi");
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Product entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("UPDATE products set productCode = ? ,productName = ? ,productProvider = ?,productPrice = ?,productAmount = ? where id = ?");
			preparedStatement.setInt(1, entity.getProductCode());
			preparedStatement.setString(2, entity.getProductName());
			preparedStatement.setString(3, entity.getProductProvider());
			preparedStatement.setDouble(4, entity.getProductPrice());
			preparedStatement.setInt(5, entity.getProductAmount());
			preparedStatement.setInt(6, entity.getProductId());
			
			int effectedRowCount = preparedStatement.executeUpdate();
			
			System.out.println(effectedRowCount +" adet kayıt güncellendi");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateForBrand(String newBrandName, String oldBrandName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("UPDATE products set productProvider = ? where productProvider = ?");
			preparedStatement.setString(1, newBrandName);
			preparedStatement.setString(2, oldBrandName);
			
			int effectedRowCount = preparedStatement.executeUpdate();
			
			System.out.println(effectedRowCount +" adet kayıt güncellendi");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
