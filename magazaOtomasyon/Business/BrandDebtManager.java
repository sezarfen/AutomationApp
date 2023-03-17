package magazaOtomasyon.Business;

import java.util.ArrayList;

import magazaOtomasyon.DataAccess.BrandDebtDal;
import magazaOtomasyon.DataAccess.IDataAccessLayer;
import magazaOtomasyon.Entities.BrandDebt;

public class BrandDebtManager implements IService<BrandDebt>{
	private IDataAccessLayer<BrandDebt> dataAccess = null;
	
	public BrandDebtManager(IDataAccessLayer<BrandDebt> dataAccess) {
		this.dataAccess = dataAccess;
	}
	
	@Override
	public BrandDebt get() {
		return null;
	}

	@Override
	public ArrayList<BrandDebt> getAll() {
		return dataAccess.getAll();
	}

	@Override
	public boolean add(BrandDebt entity) {
		dataAccess.add(entity);
		return true;
	}

	@Override
	public void delete(int brandCode) {
		dataAccess.delete(brandCode);
		
	}

	@Override
	public boolean update(BrandDebt entity) {
		return true;
		
	}
	
	public void addDebt(String brandName , double debt) {
		BrandDebtDal brandDebtDal = new BrandDebtDal();
		brandDebtDal.addDebt(brandName, debt);
	}
	
	public void updateForBrand(int oldBrandCode , String newBrandName , int newBrandCode) {
		BrandDebtDal brandDebtDal = new BrandDebtDal();
		brandDebtDal.updateForBrand(oldBrandCode , newBrandName , newBrandCode);
	}

}
