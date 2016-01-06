package solutions.channie.example.sparkdb2;

import static spark.Spark.exception;
import static spark.Spark.get;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import solutions.channie.example.sparkdb2.data.DataConnect;
import solutions.channie.example.sparkdb2.data.JsonTransformer;
import solutions.channie.example.sparkdb2.util.ServiceException;

public class Service {

	static final Logger logger = LogManager.getLogger(Service.class.getName());
	static String dbName = "SAMPLE";
	static int HTTP_BAD_REQUEST = 400;

	public static void main(String[] args) {

		get("/connect", (req, res) -> {
			new DataConnect();
			return DataConnect.testConnection(dbName);
		}, new JsonTransformer());

		get("/getstaff",
				(req, res) -> {
					logger.info("getemployee");
					String name = req.queryParams("name");
					String errorMsg;
					if (StringUtils.isNotEmpty(name)) {
						logger.info("get request - search - searching for "
								+ name);
						String sql = "select * from STAFF where NAME='"+name+"'";
						JSONArray ja = DataConnect.executeQuery(sql,dbName);
						if(ja.length() > 0)
							return ja;
						else
							errorMsg = "error - name not found";
					} else
						errorMsg = "error - name required";
					res.status(HTTP_BAD_REQUEST);
					return errorMsg;
				}, new JsonTransformer());
		
		exception(ServiceException.class, (e, request, response) -> {
		    response.status(404);
		    response.body(e.getMessage());
		});


	}
}
