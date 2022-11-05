package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;

import db.SQL_connection;
import wse.WSE;
import wse.server.ServiceManager;
import wse.server.WSEServer;
import wse.utils.log.LogPrintStream;

public class WC_server extends WSEServer{
	static SQL_connection sqlConnection;
	
	public static void main(String[] args) throws SecurityException, FileNotFoundException, SQLException {
		WSE.setLogLevel(Level.FINEST);
		WSE.initDefaultStandaloneLogging();
		WSE.initFileLogging();
		System.setErr(new LogPrintStream(WSE.getLogger(), Level.SEVERE));
		System.setOut(new LogPrintStream(WSE.getLogger(), Level.INFO));
		sqlConnection = new SQL_connection();
		new WC_server().start();
		

	}

	public WC_server() {

		ServiceManager manager = getServiceManager();
		manager.register("/GetUsers", GetUsersServlet.class);
		manager.register("/InsertUser", InsertUsersServlet.class);
		manager.register("/InsertBathroom", InsertBathroomServlet.class);
		manager.register("/InsertStamp", InsertStampServlet.class);
		manager.register("/GetStamps", GetStampsServlet.class);
		manager.register("/GetBathrooms", GetBathroomsServlet.class);
		manager.register("/GetLatestStamps", GetLatestStampsServlet.class);
		manager.register("/GetLatestStamp", GetLatestStampServlet.class);
		
		addHttp(1307);
	}
	
	public static SQL_connection getConnect() {
		return sqlConnection;
	}

}
