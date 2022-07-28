package control;

import java.util.List;
import java.util.Scanner;



import dao.AuteurDao;
import dao.DaoException;
import dao.DaoFactory;
import dao.LivreDao;
import model.Auteur;
import model.Livre;


public class Main {

	static Scanner input = new Scanner (System.in);

	static AuteurDao auteurDao = DaoFactory.getInstance().getAuteurDao();
	static LivreDao livreDao = DaoFactory.getInstance().getLivreDao();

	public static void main(String[] args) {


		while (true) {

			System.out.println("------------------- Projet Bibliotheque ---------------");
			System.out.println(" Tapez T pour les Auteurs ");
			System.out.println(" Tapez V pour les Livres  ");
			System.out.println(" Tapez Q pour quitter ");

			if (input.nextLine().equals("T")) {
				menuA(); 

				switch (input.nextLine()) {

				case "A" :
					System.out.println("Quel est le nom de l'auteur ?");
					String nom = input.nextLine();
					System.out.println("Quel est le prenom de l'auteur ?");
					String prenom = input.nextLine();
					System.out.println("Quel est le telephone de l'auteur ?");
					String telephone = input.nextLine();
					System.out.println("Quel est l'email de l'auteur ?");
					String mail = input.nextLine();

					try {
						auteurDao.create((new Auteur(nom, prenom, telephone, mail)));
					} catch (DaoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Auteur crée !");
					break;

				case "B"  : 

					try {
						System.out.println(auteurDao.list());
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}break;

				case "C" :    
					System.out.println("Quel est le Id  de l'auteur ?");

					try {
						Auteur a=auteurDao.read(Integer.parseInt(input.nextLine()));
						System.out.println(a);
					} catch (NumberFormatException | DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}break;

				case "D" :   

					System.out.println("Quel est l'ID de l'auteur que vous souhaitez modifier ?");

					String id = input.nextLine();
					try {
						System.out.println("Saisir le  nouveau nom de l'auteur --> ancien nom : "+auteurDao.read(Integer.parseInt(id)).getNom());
						String nouveaunom = input.nextLine();
						System.out.println("Saisir le  nouveau prenom de l'auteur --> ancien prenom : "+auteurDao.read(Integer.parseInt(id)).getPrenom());
						String nouveauprenom = input.nextLine();
						System.out.println("Saisir le nouveau telephone de l'auteur --> ancien telephone : "+auteurDao.read(Integer.parseInt(id)).getTelephone());
						String nouveautelephone = input.nextLine();
						System.out.println("Saisir le nouveau email de l'auteur --> ancien email : "+auteurDao.read(Integer.parseInt(id)).getEmail());
						String nouveauemail = input.nextLine();

						Auteur a = auteurDao.read(Integer.parseInt(id));
						a.setNom(nouveaunom);
						a.setPrenom(nouveauprenom);
						a.setTelephone(nouveautelephone);
						a.setEmail(nouveauemail);

						auteurDao.update(a);
						System.out.println("Auteur modifié !");
					} catch(NumberFormatException e) {
						System.out.println("Vous devez entrer un nombre !");
					} catch(DaoException e) {
						System.out.println("Erreur : Auteur non modifié...");
					}
					break;


				case "E" :   
					try {
						System.out.println(auteurDao.list().toString());
					} catch (DaoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Quel est l'ID de l'auteur que vous souhaitez supprimer ?");

					try {

						auteurDao.delete(Integer.parseInt(input.nextLine()));
						System.out.println("Auteur supprimé !");
					} catch(NumberFormatException e) {
						System.out.println("Vous devez entrer un nombre !");
					} catch(DaoException e) {
						System.out.println("Erreur lors de la suppression...");
					}


					break;  

				case "F" :   

					System.out.println("Quel est le nom de l'auteur ?");
					String nomauteur = input.next();
					try {
						List<Auteur> a=auteurDao.rechercheParNom("%"+nomauteur+"%");
						System.out.println(a);
					} catch (NumberFormatException | DaoException e) {

						e.printStackTrace();
					}



					break;  }

			}else if (input.nextLine().equals("V")) {

				menL() ;

				switch (input.nextLine()) {

				case "A" :

					System.out.println("Quel est le Id de l'auteur ?");
					int idAuteur = Integer.parseInt(input.nextLine());
					System.out.println("Quel est le titre  de livre ?");
					String titre = input.nextLine();
					System.out.println("Quel est le nombre de page de livre ?");
					int nbPage = Integer.parseInt(input.nextLine());
					System.out.println("Quel est la categorie de livre ?");
					String categorie = input.nextLine();

					try {
						livreDao.create((new Livre (auteurDao.read(idAuteur), titre, nbPage, categorie ))); 
					} catch (DaoException e1) {

						e1.printStackTrace();
					}
					System.out.println("Livre crée !");
					break;

				case "B"  : 

					try {
						System.out.println(livreDao.list());
					} catch (DaoException e) {

						e.printStackTrace();
					}break;

				case "C" :    
					System.out.println("Quel est le Id  de livre ?");

					try {
						Livre a=livreDao.read(Integer.parseInt(input.nextLine()));
						System.out.println(a);
					} catch (NumberFormatException | DaoException e) {

						e.printStackTrace();
					}break;

				case "D" :   

					System.out.println("Quel est l'ID de livre  que vous souhaitez modifier ?");

					String id = input.nextLine();
					try {
						System.out.println("Saisir le  nouveau id   de l'auteur  --> ancien id  : "+livreDao.read(Integer.parseInt(id)).getAuteur().getId());
						int  idauteur = Integer.parseInt(input.nextLine());
						System.out.println("Saisir le  nouveau titre  de livre  --> ancien titre  : "+livreDao.read(Integer.parseInt(id)).getTitre());
						String nouveauTitre = input.nextLine();
						System.out.println("Saisir le  nouveau nombre de page  de livre  --> ancien nbr_page : "+livreDao.read(Integer.parseInt(id)).getNb_pages());
						int  nouveaupage = Integer.parseInt(input.nextLine());
						System.out.println("Saisir le nouveau categorie de livre  --> ancien categorie : "+livreDao.read(Integer.parseInt(id)).getCategorie());
						String nouveaucategorie = input.nextLine();


						Livre  l = livreDao.read(Integer.parseInt(id));

						l.setAuteur(auteurDao.read(idauteur));
						l.setTitre(nouveauTitre);
						l.setNb_pages(nouveaupage);
						l.setCategorie(nouveaucategorie);
						livreDao.update(l);
						System.out.println("Livre  modifié !");
					} catch(NumberFormatException e) {
						System.out.println("Vous devez entrer un nombre !");
					} catch(DaoException e) {
						System.out.println("Erreur : de  modification");
					}
					break;


				case "E" :   
					try {
						System.out.println(livreDao.list().toString());
					} catch (DaoException e1) {

						e1.printStackTrace();
					}
					System.out.println("Quel est l'ID de livre  que vous souhaitez  supprimer ?");

					try {

						livreDao.delete(Integer.parseInt(input.nextLine()));
						System.out.println("Livre  supprimé !");
					} catch(NumberFormatException e) {
						System.out.println("Vous devez entrer un nombre !");
					} catch(DaoException e) {
						System.out.println("Erreur de  suppression...");
					}


					break;  

				case "F" :   

					System.out.println("Quel est le titre  de livre ?");
					String titrelivre = input.next();
					try {

						List<Livre> l=livreDao.rechercheLivreParTitre("%"+titrelivre+"%");  
						System.out.println(l);
					} catch (NumberFormatException | DaoException e) {

						e.printStackTrace();
					}

					break;  

				case "G" :   

					System.out.println("Quel est le id de l'auteur ?");
					int idauteur = Integer.parseInt(input.next());
					try {
						List<Livre> l=livreDao.rechercheLivreParAuteur(idauteur);  
						System.out.println(l);
					} catch (NumberFormatException | DaoException e) {

						e.printStackTrace();
					}



					break;  }
			}else  { System.out.println("-------------- Fin Projet Bibliotheque----------------");				break;
			}
		}


	}		


	static String  menuA() {

		System.out.println(" A-   Création d'un Auteur ");
		System.out.println(" B-   Lister les auteurs  ");
		System.out.println(" C-   Afficher le détails d'un auteur ");
		System.out.println(" D-   Modifier un auteur  ");
		System.out.println(" E-   Supprimer un auteur  ");
		System.out.println(" F-   Recherche un auteur par nom  ");
		System.out.println(" Q-   Menu Principal   ");


		return 	input.nextLine();

	}

	static String  menL() {

		System.out.println(" A-   Création d'un Livre ");
		System.out.println(" B-   Lister les Livres  ");
		System.out.println(" C-   Afficher le détails d'un Livre ");
		System.out.println(" D-   Modifier un Livre  ");
		System.out.println(" E-   Supprimer un Livre  ");
		System.out.println(" F-   Recherche un Livre par titre  ");
		System.out.println(" G-   Recherche un Livre par Id auteur  ");
		System.out.println(" Q-   Menu Principal   ");

		return input.nextLine();
	}

}









