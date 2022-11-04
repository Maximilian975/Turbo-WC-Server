package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import db.SQL_connection;
import wse.WSE;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;

public class GetBathroomsServlet extends HttpServlet{
	static Logger logger = WSE.getLogger();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bathrooms = "";
		SQL_connection connection = WC_server.getConnect();
		try {
			bathrooms = connection.get_bathrooms();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		byte[] byteMessage = bathrooms.getBytes();
		response.setContentLength(byteMessage.length);
		response.write(byteMessage);
	}
}
