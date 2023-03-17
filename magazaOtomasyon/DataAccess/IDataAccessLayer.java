package magazaOtomasyon.DataAccess;

import java.util.ArrayList;

public interface IDataAccessLayer<T> {
	T get(int id);
	ArrayList<T> getAll();
	void add(T entity);
	void delete(int id);
	void update(T entity);
}
