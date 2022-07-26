package dao;

import java.util.List;

import model.Auteur;

public interface AuteurDao {
	
	List<Auteur> list() throws DaoException;
	   
	List<Auteur> rechercheParNom(String nom) throws DaoException;
	
	Auteur read(int id) throws DaoException;
	
	void create(Auteur auteur) throws DaoException;
	
	void update(Auteur auteur) throws DaoException;
	
	void delete(int id) throws DaoException;
}
