package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import db.SQL_connection;
import wse.WSE;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;

public class GetUsersServlet extends HttpServlet {
	static Logger logger = WSE.getLogger();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String users = "";
		SQL_connection connection = WC_server.getConnect();
		try {
			users = connection.get_users();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		byte[] byteMessage = users.getBytes();
		response.setContentLength(byteMessage.length);
		response.write(byteMessage);
	}

}
