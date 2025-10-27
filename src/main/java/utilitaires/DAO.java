package utilitaires;

import java.util.List;

public abstract class DAO<T> {
	public abstract T findById(int id);
	public abstract List<T> findAll();
	public abstract T create(T object);
	public abstract T delete(T object);
	public abstract T update(T object);
}
