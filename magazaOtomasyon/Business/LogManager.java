package magazaOtomasyon.Business;

import java.util.ArrayList;

import magazaOtomasyon.DataAccess.IDataAccessLayer;
import magazaOtomasyon.Entities.Log;

public class LogManager implements IService<Log> {

	private IDataAccessLayer<Log> dataAccess;

	public LogManager(IDataAccessLayer<Log> dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Log get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Log> getAll() {
		return dataAccess.getAll();
	}

	@Override
	public boolean add(Log entity) {
		if (!entity.getContent().isEmpty() && !entity.getDate().isEmpty()) {
			dataAccess.add(entity);
			return true;
		}
		return false;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(Log entity) {
		return true;
	}

}
