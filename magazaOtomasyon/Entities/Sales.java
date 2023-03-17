package magazaOtomasyon.Entities;

public class Sales {
	private int id;
	private String productName;
	private int productCode;
	private int productAmount;
	private int productSinglePrice;
	private int productTotalGain;

	public Sales(int id, String productName, int productCode, int productAmount, int productSinglePrice,
			int productTotalGain) {
		this.id = id;
		this.productCode = productCode;
		this.productName = productName;
		this.productAmount = productAmount;
		this.productSinglePrice = productSinglePrice;
		this.productTotalGain = productTotalGain;
	}
	
	public Sales(String productName, int productCode, int productAmount, int productSinglePrice,
			int productTotalGain) {
		this.productCode = productCode;
		this.productName = productName;
		this.productAmount = productAmount;
		this.productSinglePrice = productSinglePrice;
		this.productTotalGain = productTotalGain;
	}

	public Sales() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public int getProductSinglePrice() {
		return productSinglePrice;
	}

	public void setProductSinglePrice(int productSinglePrice) {
		this.productSinglePrice = productSinglePrice;
	}

	public int getProductTotalGain() {
		return productTotalGain;
	}

	public void setProductTotalGain(int productTotalGain) {
		this.productTotalGain = productTotalGain;
	}

}
