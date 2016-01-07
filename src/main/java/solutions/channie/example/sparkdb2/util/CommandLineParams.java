package solutions.channie.example.sparkdb2.util;

import com.beust.jcommander.Parameter;

public class CommandLineParams {
	@Parameter(names = "--debug", description = "Debug mode")
    public boolean debug = false;

    @Parameter(names = {"--service-port"}, description = "Change default port for service")
    public Integer servicePort = 4567;

    @Parameter(names = {"--db-host"}, description = "Hostname for database")
    public String dbHost = "localhost";
    
    @Parameter(names = {"--db-port"}, description = "Database port")
    public Integer dbPort = 5432;
    
    @Parameter(names = {"--database"}, description = "Database name")
    public String database = "DefaultDatabase";
    
    @Parameter(names = {"--db-username"}, description = "Username for database")
    public String dbUsername = "SomeUser";

    @Parameter(names = {"--db-password"}, description = "Password for database user")
    public String dbPassword = "SomePassword";

    
}
