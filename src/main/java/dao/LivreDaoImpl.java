package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Livre;

public class LivreDaoImpl implements LivreDao {

	//-----------------------------------------------------Partie SQL---------------------------------------------------------
			private static final String SQL_SELECT_ALL = "Select * From Livre";
			private static final String SQL_SELECT_ONE = "Select * From Livre Where id=?";
			private static final String SQL_INSERT = "Insert into Livre(id_auteur, titre, nb_pages, categorie) Values (?,?,?,?)";
			private static final String SQL_UPDATE = " Update Livre Set id_auteur=?,titre=?, nb_pages=?, categorie=? Where id=? ";
			private static final String SQL_DELETE = "Delete From Livre Where id=?";
			private static final String SQL_SELECT_ID_Auteur = "Select * From Livre Where id_auteur = ?";
			private static final String SQL_SELECT_Titre = "Select * From Livre Where titre LIKE ?";
			//-------------------------------------------------------------------------------------------------------------------------
			private DaoFactory factory;

			protected LivreDaoImpl(DaoFactory factory) {
				this.factory = factory;
			}

			@Override
			public List<Livre> list() throws DaoException {
				
				List<Livre> listeLivres = new ArrayList<Livre>();
				Connection con = null;
				
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_SELECT_ALL);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						listeLivres.add(mapL(rs));
					}
					
					rs.close();
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Erreur de lecture Table Livre");
				} finally {
					factory.releaseConnection(con);
				}
						
				
				return listeLivres;
			}

			@Override
			public Livre read(int id) throws DaoException {
				Livre livre = new Livre();
				Connection con = null;
				
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_SELECT_ONE);
					pst.setInt(1, id);
					
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()) {
						livre = mapL(rs);
					} else {
						throw new DaoException("Le Livre avec l'id "+id+" n'existe pas.");
					}
					
					rs.close();
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Erreur de lecture Table Livre");
				} finally {
					factory.releaseConnection(con);
				}
				
				return livre;
			}

			@Override
			public void create(Livre livre) throws DaoException {
				Connection con = null;
				
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
					
					if(livre.getAuteur() != null) {
						pst.setInt(1, livre.getAuteur().getId());           
					} else {
						pst.setNull(1, Types.NULL);
						
					}
					
					pst.setString(2, livre.getTitre());
					pst.setInt(3, livre.getNb_pages());
					pst.setString(4, livre.getCategorie());
					
					int result = pst.executeUpdate();
					
					if(result == 1) {
						ResultSet rsKeys = pst.getGeneratedKeys();
						if(rsKeys.next()) {
							livre.setId(rsKeys.getInt(1));
						}
						rsKeys.close();
					} else {
						throw new DaoException("Impossible d'insérer le Livre : "+livre.getTitre());
					}
					
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Impossible d'insérer le Livre : "+livre.getTitre());
				} finally {
					factory.releaseConnection(con);
				}
			}

			@Override
			public void update(Livre livre) throws DaoException {
				Connection con = null;
				
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_UPDATE);
					
					if(livre.getAuteur() != null) {
						
						pst.setInt(1, livre.getAuteur().getId());
					
					} else {
						pst.setNull(1, Types.NULL);
						
					}
					
					pst.setString(2, livre.getTitre());
					pst.setInt(3, livre.getNb_pages());
					pst.setString(4, livre.getCategorie());
					
					pst.setInt(5, livre.getId());
					
					int result = pst.executeUpdate();
					
					if(result != 1) {
						
						throw new DaoException("Impossible de mettre à jour le Livre : "+livre.getTitre());
					}
					
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Impossible de mettre à jour le Livre : "+livre.getTitre());
				} finally {
					factory.releaseConnection(con);
				}
			}

			@Override
			public void delete(int id) throws DaoException {
				Connection con = null;
						
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_DELETE);
					pst.setInt(1, id);
					
					int result = pst.executeUpdate();
					
					if(result != 1) {
						
						throw new DaoException("Impossible de supprimer le Livre avec l'id : "+id);
					}
					
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Impossible de supprimer le Livre avec l'id : "+id);
				} finally {
					factory.releaseConnection(con);
				}
			}
			
			
			private static Livre mapL(ResultSet rs) throws SQLException {
				Livre livre = new Livre();
				
				livre.setId(rs.getInt("id"));
				
				try {
					livre.setAuteur(DaoFactory.getInstance().getAuteurDao().read(rs.getInt("id_auteur")));
				
				} catch (DaoException e) {
					livre.setAuteur(null);
				}
				
				livre.setTitre(rs.getString("titre"));
				livre.setNb_pages(rs.getInt("nb_pages"));
				livre.setCategorie(rs.getString("categorie"));
				
				
				
				
				
				
				return livre;
			}

			@Override
			public List<Livre> rechercheLivreParAuteur(int idauteur) throws DaoException {
				
				List<Livre> listeLivres = new ArrayList<Livre>();
				
				Connection con = null;
				
				try {
					con = factory.getConnection();
					
					PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID_Auteur);
					pst.setInt(1, idauteur);
					
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()) {
						listeLivres.add(mapL(rs));
					} else {
						throw new DaoException("Le Livre avec id_auteur "+idauteur+" n'existe pas.");
					}
					
					rs.close();
					pst.close();
					
				} catch (SQLException e) {
					throw new DaoException("Erreur de lecture Table Livre");
				} finally {
					factory.releaseConnection(con);
				}
				
				return listeLivres;
			}



	public List<Livre> rechercheLivreParTitre(String  titre) throws DaoException {
		
		List<Livre> listeLivres = new ArrayList<Livre>();
		
		Connection con = null;
		
		try {
			con = factory.getConnection();
			
			PreparedStatement pst = con.prepareStatement(SQL_SELECT_Titre);
			pst.setString(1, titre);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				listeLivres.add(mapL(rs));
			
			} else {
			
				throw new DaoException("Le Livre avec titre  "+titre+" n'existe pas.");
			}
			
			rs.close();
			pst.close();
			
		} catch (SQLException e) {
			throw new DaoException("Erreur de lecture Table Livre");
		} finally {
			factory.releaseConnection(con);
		}
		
		return listeLivres;
	}


	}