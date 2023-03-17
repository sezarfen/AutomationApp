package magazaOtomasyon.Business;

import java.util.ArrayList;

import magazaOtomasyon.DataAccess.IDataAccessLayer;
import magazaOtomasyon.Entities.Brand;

public class BrandManager implements IService<Brand>{

	private IDataAccessLayer<Brand> dataAccess;
	
	public BrandManager(IDataAccessLayer<Brand> dataAccess) {
		this.dataAccess = dataAccess;
	}
	
	@Override
	public Brand get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Brand> getAll() {
		
		return dataAccess.getAll();
	}

	@Override
	public boolean add(Brand brand) {
		dataAccess.add(brand);
		return true;
	}

	@Override
	public void delete(int id) {
		dataAccess.delete(id);
		
	}

	@Override
	public boolean update(Brand entity) {
		dataAccess.update(entity);
		
		return true;
		
	}

	// Do it later
	public boolean validateBrand(Brand brand) {
		
		return true;
	}
	
	
}
