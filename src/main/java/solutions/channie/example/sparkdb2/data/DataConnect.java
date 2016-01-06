package solutions.channie.example.sparkdb2.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import solutions.channie.example.sparkdb2.util.ServiceException;

public class DataConnect {
	static final Logger logger = LogManager.getLogger(DataConnect.class.getName());
	static String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
	static String url = System.getenv("DB2_CONN");
	static String user = System.getenv("DB2_USER");
	static String password = System.getenv("DB2_PASS");

	public static boolean testConnection(String dbName) throws ServiceException{
		logger.info("testConnection - start");
		logger.info("testConnection - url - "+url);
		logger.info("testConnection - user - "+user);
		logger.info("testConnection - password - "+password);

		boolean connectionStatus = false;
		Connection connection = null;
		try {
			//Load class into memory
			Class.forName(jdbcClassName);
			//Establish connection
			connection = DriverManager.getConnection(url+"/"+dbName, user, password);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			throw new ServiceException("ClassNotFound");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new ServiceException("SQL");
		}finally{
			if(connection!=null){
				try {
					logger.info("Connected successfully.");
					connectionStatus = true;
					connection.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		}
		logger.info("testConnection - end");
		return connectionStatus;
	};

	public static JSONArray executeQuery(String sql, String dbName) throws ServiceException{
		logger.info("executeQuery - start");
		Connection connection = null;
		PreparedStatement pstmt = null;
        ResultSet rset=null;
        JSONArray ja= null;
		try {
			//Load class into memory
			Class.forName(jdbcClassName);
			//Establish connection
			connection = DriverManager.getConnection(url+"/"+dbName, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException("ClassNotFound");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException("SQL");
		}finally{
			if(connection!=null){
				try {
					pstmt=connection.prepareStatement(sql);
					rset=pstmt.executeQuery();
					ja = ResultSetConverter.convert(rset);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new ServiceException("SQL");
				}
			}
		}
		logger.info("executeQuery - end");
		return ja;
	};

}
