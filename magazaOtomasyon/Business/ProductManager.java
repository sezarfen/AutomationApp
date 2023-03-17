package magazaOtomasyon.Business;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import magazaOtomasyon.DataAccess.IDataAccessLayer;
import magazaOtomasyon.DataAccess.ProductDal;
import magazaOtomasyon.Entities.Product;

public class ProductManager implements IService<Product>{

	private IDataAccessLayer<Product> dataAccess;
	
	public ProductManager(IDataAccessLayer<Product> dataAccess) {
		this.dataAccess = dataAccess;
	}
	
	public Product get() {
		return null;
	}
	
	public ArrayList<Product> getAll() {
		return dataAccess.getAll();
	}
	
	public boolean add(Product product) {
		if(productValidator(product)) {
			dataAccess.add(product);	
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "Ürün bilgileri kontrol edilirken hata oluştu , ekleme yapılmadı");
			System.out.println("Ürün bilgileri kontrol edilirken hata oluştu , ekleme yapılmadı");
			return false;
		}
	}
	
	public void delete(int id) {
		dataAccess.delete(id);
	}
	
	public boolean update(Product product) {
		if(productValidator(product)) {
			dataAccess.update(product);	
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "Ürün bilgileri kontrol edilirken hata oluştu , ekleme yapılmadı");
			System.out.println("Ürün bilgileri kontrol edilirken hata oluştu , güncelleme yapılmadı");
			return false;
		}
	}
	
	private boolean productValidator(Product product) {
		if(product.getProductName().isEmpty())
			return false;
		else if(product.getProductCode() <= 0)
			return false;
		else if(product.getProductAmount() <= 0)
			return false;
		else if(product.getProductPrice() <= 0)
			return false;
		else if(product.getProductProvider().isEmpty())
			return false;
		
		return true;
		
	}
	
	public void updateForBrand(String newBrandName , String oldBrandName) {
		ProductDal productDal = new ProductDal();
		productDal.updateForBrand(newBrandName, oldBrandName);
	}
	
}
