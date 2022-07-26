package model;

public class Auteur {
  int id;
  String nom;
  String prenom;
  String telephone;
  String email;
  
  public Auteur(String nom, String prenom, String telephone, String email) {
      this.nom = nom;
      this.prenom = prenom;
      this.telephone = telephone;
      this.email = email;
  }
  
  public int getId() {
      return this.id;
  }
  
  public String toString() {
      return "Auteur : id=" + getId() + " nom=" + getNom() + " prenom=" + getPrenom() + " telephone=" + getTelephone() + " email=" + getEmail();
  }
  
}
