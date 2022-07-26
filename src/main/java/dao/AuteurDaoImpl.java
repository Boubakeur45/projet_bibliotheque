package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Auteur;

public class AuteurDaoImpl implements AuteurDao {
	//-----------------------------------------------------Partie SQL---------------------------------------------------------
		private static final String SQL_SELECT_ALL = "Select * From auteur";
		private static final String SQL_SELECT_ONE = "Select * From auteur Where id=?";
		private static final String SQL_INSERT = "Insert into Auteur(nom, prenom, telephone, email) Values (?,?,?,?)";
		private static final String SQL_UPDATE = " Update Auteur Set nom=?, prenom=?, telephone=?, email=? Where id=? ";
		private static final String SQL_DELETE = "Delete From Auteur Where id=?";
		private static final String SQL_SELECT_NOM= "Select * From Auteur Where nom  LIKE ?";
	//-------------------------------------------------------------------------------------------------------------------------
		private DaoFactory factory;

		protected AuteurDaoImpl(DaoFactory factory) {
			this.factory = factory;
		}

	//-------------------------------------------------------Afficher tous les Auteurs
		@Override
		
		public List<Auteur> list() throws DaoException {
			Connection con = null;
			
			List<Auteur> listeAuteurs = new ArrayList<Auteur>();
		
			
			try {
				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_SELECT_ALL);

				ResultSet rs = pst.executeQuery();

				while(rs.next()) {
					
					listeAuteurs.add(mapA(rs));
				}

				rs.close();
				pst.close();

			} catch (SQLException e) {
				
				throw new DaoException(e);
			
			} finally {
				factory.releaseConnection(con);
			}


			return listeAuteurs;
		}


	//---------------------------------------------Afficher un Auteur 

		@Override
		public Auteur read(int id) throws DaoException {
			Auteur auteur = new Auteur();
			Connection con = null;
			
			try {

				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_SELECT_ONE);
				
				pst.setInt(1,id);

				ResultSet rs = pst.executeQuery();

				if(rs.next()) {

					auteur = mapA(rs);
				}

				rs.close();
				pst.close();


			} catch (SQLException e) {
				throw new DaoException(" erreur SQL !!");
			} finally {
				factory.releaseConnection(con);		}

			return auteur;
		}
		
	//------------------------------------------------------------ Insert Auteur
		@Override
		public void create(Auteur auteur) throws DaoException {
			Connection con = null;

			try {
				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

				pst.setString(1, auteur.getNom());
				pst.setString(2, auteur.getPrenom());
				pst.setString(3, auteur.getTelephone());
				pst.setString(4, auteur.getEmail());

				int result = pst.executeUpdate();

				if(result == 1) {
					System.out.println("Création réussi");
					
					ResultSet rsKeys = pst.getGeneratedKeys();
					
					if(rsKeys.next()) {
						
						auteur.setId(rsKeys.getInt(1));
						
				} rsKeys.close();
				
				}else {
					System.out.println("Problème création");
				}

				
				System.out.println(auteur);

				pst.close();



			} catch ( SQLException e) {
				throw new DaoException("erreur d'insertion");
			} 
			finally
			{
				factory.releaseConnection(con);		}
		}
		
		//------------------------------------------------------------- Update

		@Override
		public void update(Auteur auteur) throws DaoException {

			Connection con = null;
			try {

				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);


				pst.setString(1, auteur.getNom());
				pst.setString(2, auteur.getPrenom());
				pst.setString(3, auteur.getTelephone()); 
				pst.setString(4, auteur.getEmail());
				pst.setInt(5, auteur.getId());

				int result = pst.executeUpdate();

				if(result == 1) {
					
					System.out.println("Modification réussi " +auteur );
				
				} else {
					System.out.println("Problème Modification");
				}

				pst.close();


			} catch ( SQLException e) {
				e.printStackTrace();
			} finally
			{
				factory.releaseConnection(con);		
			}
		}
		
	//-----------------------------------------------------------Delete	

		@Override
		public void delete(int id) throws DaoException {
			
			Connection con = null;
			try {

				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_DELETE);
			
				pst.setInt(1, id);

				int result = pst.executeUpdate();

				
				if(result != 1) {
					
					System.out.println("Suppression réussi");
				
				} else {
					System.out.println("Problème suppression");
				}
				pst.close();


			} catch ( SQLException e) {
				e.printStackTrace();
			} 
		}
		
		//------------------------------------------------------------------------
		private static Auteur mapA (ResultSet rs) throws SQLException {
			Auteur r = new Auteur();
			r.setId(rs.getInt("id"));
			r.setNom(rs.getString("nom"));
			r.setPrenom(rs.getString("prenom"));
			r.setTelephone(rs.getString("telephone"));
			r.setEmail(rs.getString("email"));
			return r;
		}
	    //-----------------------------------------------------------------------

		@Override
		public List<Auteur> rechercheParNom(String nom) throws DaoException {
			List<Auteur> auteur=new ArrayList<Auteur>();
			Connection con=null;
			
			try {
				
				con=factory.getConnection();
				PreparedStatement pst=con.prepareStatement(SQL_SELECT_NOM);
				
				pst.setString(1, nom);
				
				ResultSet rs=pst.executeQuery();
			
				while(rs.next()) {
					
					auteur.add(mapA(rs));
				
				}
			} catch (SQLException e) {
				throw new DaoException("Le Auteur avec le nom:  "+nom+" n'existe pas !" );
			} finally {
				factory.releaseConnection(con);
			}

			return auteur;
		}


	}
