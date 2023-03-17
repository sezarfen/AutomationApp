package magazaOtomasyon.Business;

import java.util.ArrayList;

public interface IService<T>{
	T get();
	ArrayList<T> getAll();
	boolean add(T entity);
	void delete(int id);
	boolean update(T entity);
}
