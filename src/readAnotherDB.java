import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class readAnotherDB {
		
	static ResultSet getResult(String sql,String pathDB) throws ClassNotFoundException, SQLException {
		/*
		 * универсальная функция , возвращает результат запроса @sql
		 */
		Class.forName("org.sqlite.JDBC");
		Connection bd 			= DriverManager.getConnection("jdbc:sqlite:"+pathDB);
		ResultSet resultSet 	= null; 
		Statement st 			= bd.createStatement();
		st.setQueryTimeout(60);
		resultSet  				= st.executeQuery( sql );
		return resultSet;
		
	} //ResultSet getResult(String sql) throws ClassNotFoundException, SQLException
	
	static void getResult1(String sql,String pathDB) throws ClassNotFoundException, SQLException {
		/*
		 * выполнит запрос @sql
		 */
		Class.forName("org.sqlite.JDBC");
		Connection bd 			= DriverManager.getConnection("jdbc:sqlite:"+pathDB);
		//ResultSet resultSet 	= null; 
		Statement st 			= bd.createStatement();
		st.setQueryTimeout(60);
		ResultSet resultSet  	= st.executeQuery( sql );
		resultSet.close();
		
	} //static getResult1(String sql) throws ClassNotFoundException, SQLException
	
		
}	//readAnotherDB	

 
