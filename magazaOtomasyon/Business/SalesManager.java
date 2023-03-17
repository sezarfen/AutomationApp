package magazaOtomasyon.Business;

import java.util.ArrayList;

import magazaOtomasyon.DataAccess.IDataAccessLayer;
import magazaOtomasyon.Entities.Sales;

public class SalesManager implements IService<Sales>{
	
	private final IDataAccessLayer<Sales> dataAccess;
	
	public SalesManager(IDataAccessLayer<Sales> dataAccess) {
		this.dataAccess = dataAccess;
	}
	

	@Override
	public Sales get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Sales> getAll() {
		
		return dataAccess.getAll();
	}

	@Override
	public boolean add(Sales entity) {
		dataAccess.add(entity);
		return false;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Sales entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
