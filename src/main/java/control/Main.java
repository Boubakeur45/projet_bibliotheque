package control;

import dao.AuteurDao;
import dao.DaoException;
import dao.DaoFactory;

public class Main {

	public static void main(String[] args) {
		
		
		try {
			AuteurDao auteurDao=DaoFactory.getInstance().getAuteurDao();
			
			System.out.println(auteurDao.list());
		   
			System.out.println(auteurDao.read(2));
		
		
		
		} catch (DaoException e) {
			
			e.printStackTrace();
		}
		

	}

}
