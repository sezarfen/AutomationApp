package magazaOtomasyon.Entities;

public class Brand {

	private int id;
	private int brandCode;
	private String name;

	public Brand(int id, int brandCode, String name) {
		this.id = id;
		this.brandCode = brandCode;
		this.name = name;
	}
	
	public Brand(int brandCode, String name) {
		this.brandCode = brandCode;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(int brandCode) {
		this.brandCode = brandCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", brandCode=" + brandCode + ", name=" + name + "]";
	}

}
