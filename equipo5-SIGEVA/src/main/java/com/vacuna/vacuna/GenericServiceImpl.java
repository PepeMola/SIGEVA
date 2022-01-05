package com.vacuna.vacuna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class GenericServiceImpl<T, DNI extends Serializable> implements GenericServiceApi<T, DNI> {

	@Override
	public T save(T entity) {
		return getDao().save(entity);
	}

	@Override
	public void delete(DNI dni) {
		getDao().deleteById(dni);
	}

	@Override
	public T get(DNI dni) {
		Optional<T> obj = (getDao()).findById(dni);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<T> getAll() {
		List<T> returnList = new ArrayList<>();
		getDao().findAll().forEach(obj -> returnList.add(obj));
		return returnList;
	}

	public abstract CrudRepository<T, DNI> getDao();

}