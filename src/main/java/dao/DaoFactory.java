package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

	private String url;
	private String username;
	private String passwd;
	private Connection con = null;

	private static DaoFactory instanceSingleton = null;

	
	private DaoFactory(String url, String username, String passwd) {
		this.url = url;
		this.username = username;
		this.passwd = passwd;
	}

	//------------------------------------------------
		public AuteurDao getAuteurDao() {
			return new AuteurDaoImpl( this );
		}
	//------------------------------------------------	
		public LivreDao getLivreDao() {
			return new LivreDaoImpl (this);
		}
	//---------------------------------------------------
		
	/*	public PageDao getPageDao() {
			return new PageDaoImpl(this);
			
		}
*/
	public static DaoFactory getInstance() {
		if ( DaoFactory.instanceSingleton == null ) {
			try {
				Class.forName("org.postgresql.Driver");
				DaoFactory.instanceSingleton = new DaoFactory("jdbc:postgresql://localhost:5432/bibliotheque", "utilisateur", "utilisateur");

			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return DaoFactory.instanceSingleton;
	}



	Connection getConnection() throws SQLException {
		if ( this.con == null ) {
			this.con = DriverManager.getConnection(url,username,passwd);
		}
		return this.con;
	}

	
	void releaseConnection( Connection connectionRendue ) {
		if (this.con==null) {
			return;
		}
		try {
			if ( ! this.con.isValid(10) ) {
				this.con.close();
				this.con = null;
			}
		} catch (SQLException e) {
			this.con = null;
		}
	}

}