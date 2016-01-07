package solutions.channie.example.sparkdb2;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.SparkBase.port;

import java.util.List;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import solutions.channie.example.sparkdb2.util.CommandLineParams;
import solutions.channie.example.sparkdb2.util.ServiceException;

import com.beust.jcommander.JCommander;

public class Service {

	static final Logger logger = LogManager.getLogger(Service.class.getName());
	static Sql2o sql2o;
	static String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
	static int HTTP_BAD_REQUEST = 400;

	public static void main(String[] args) {
	    // Read in all the args from the command line
		CommandLineParams jct = new CommandLineParams(); 
	    new JCommander(jct, args);
	    // Set the Spark port
	    port(jct.servicePort);
	    // Create an sql2o instance using the command line args
	    sql2o = new Sql2o("jdbc:db2://" + jct.dbHost + ":" + jct.dbPort + "/" + jct.database, jct.dbUsername, jct.dbPassword);
	    
	    get("/health", (req, res) -> {
	    	logger.info("connect - get");
	    	Class.forName(jdbcClassName);
		    try(Connection con = sql2o.open()) {
		    	return "Alive";
		    }catch(Sql2oException e){
		    	logger.error(e.getMessage());
		    	throw new ServiceException("Bad Connection");
		    }
		}, new JsonTransformer());
	    
		get("/staff",(req, res) -> {
			logger.info("staff - get");
			String name = req.queryParams("name");
			String errorMsg;
			if (StringUtils.isNotEmpty(name)) {
				logger.info("staff - get - searching for "+ name);
				String sql =
			    	    "SELECT * " +
			    	    "FROM STAFF " +
			    	    "WHERE NAME = :name";
				//Load class into memory
			    Class.forName(jdbcClassName);
			    try(Connection con = sql2o.open()) {
		    	    List<Staff> staff = con.createQuery(sql)
		    	        .addParameter("name", name)
		    	        .executeAndFetch(Staff.class);
		    	    if(!staff.isEmpty())
						return staff;
					else
						errorMsg = "error - name not found";
		    	}catch(Sql2oException e){
			    	logger.error(e.getMessage());
			    	throw new ServiceException("Bad Connection");
			    }
			}else
				errorMsg = "error - name required";
			res.status(HTTP_BAD_REQUEST);
			logger.info("staff - get - "+errorMsg+" - "+ name);
			return errorMsg;
		}, new JsonTransformer());
		
		exception(ServiceException.class, (e, request, response) -> {
		    response.status(404);
		    response.body(e.getMessage());
		});
	}
	
	@Data
	class Staff{
		int id;
		String name;
		int dept;
		String job;
		int years;
		float salary;
		float comm;
	}
	
}
