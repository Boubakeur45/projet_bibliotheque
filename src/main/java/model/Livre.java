package model;

public class Livre {

	private int id;
	private Auteur auteur ;
	private String titre;
	private int nb_pages;
	private String categorie;
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Livre [id=" + id + ", auteur=" + auteur + ", titre=" + titre + ", nb_pages=" + nb_pages + ", categorie="
				+ categorie + "]";
	}
	
	public Livre(Auteur auteur, String titre, int nb_pages, String categorie) {
		
		this.auteur = auteur;
		this.titre = titre;
		this.nb_pages = nb_pages;
		this.categorie = categorie;
	}
	public Livre() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Auteur getAuteur() {
		return auteur;
	}
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public int getNb_pages() {
		return nb_pages;
	}
	public void setNb_pages(int nb_pages) {
		this.nb_pages = nb_pages;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
	

}