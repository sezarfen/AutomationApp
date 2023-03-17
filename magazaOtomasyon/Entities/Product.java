package magazaOtomasyon.Entities;

public class Product {
	// productId , productName , productProvider , productPrice , productAmount , TotalPrice;
	private int productId;
	private int productCode;
	private String productName;
	private String productProvider;
	private double productPrice;
	private int productAmount;
	
	public Product(int productId,int productCode, String productName, String productProvider, double productPrice, int productAmount) {
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.productProvider = productProvider;
		this.productPrice = productPrice;
		this.productAmount = productAmount;
	}
	
	public Product(int productCode, String productName, String productProvider, double productPrice, int productAmount) {
		this.productCode = productCode;
		this.productName = productName;
		this.productProvider = productProvider;
		this.productPrice = productPrice;
		this.productAmount = productAmount;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productId = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductProvider() {
		return productProvider;
	}

	public void setProductProvider(String productProvider) {
		this.productProvider = productProvider;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	@Override
	public String toString() {
		return "Product id =" + productId + ", name =" + productName + ", provider ="
				+ productProvider + ", price =" + productPrice + ", amount =" + productAmount + "]";
	}

	
	
}
