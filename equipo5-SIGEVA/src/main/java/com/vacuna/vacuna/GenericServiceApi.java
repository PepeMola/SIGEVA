package com.vacuna.vacuna;

import java.io.Serializable;
import java.util.List;

public interface GenericServiceApi<T, DNI extends Serializable> {

	T save(T entity);
	
	void delete(DNI dni);
	
	T get(DNI dni);
	
	List<T> getAll();
}