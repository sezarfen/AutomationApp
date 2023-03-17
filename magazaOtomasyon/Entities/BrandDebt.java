package magazaOtomasyon.Entities;

public class BrandDebt {

	private int brandId;
	private int brandCode;
	private String brandName;
	private double brandDebt;

	public BrandDebt(int brandId, int brandCode, String brandName, double brandDebt) {
		this.brandId = brandId;
		this.brandCode = brandCode;
		this.brandName = brandName;
		this.brandDebt = brandDebt;
	}

	public BrandDebt(int brandCode, String brandName, double brandDebt) {
		this.brandCode = brandCode;
		this.brandName = brandName;
		this.brandDebt = brandDebt;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(int brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public double getBrandDebt() {
		return brandDebt;
	}

	public void setBrandDebt(double brandDebt) {
		this.brandDebt = brandDebt;
	}

}
