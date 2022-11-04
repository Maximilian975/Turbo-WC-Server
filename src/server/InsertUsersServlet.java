package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import db.SQL_connection;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;
import wse.utils.http.StreamUtils;

public class InsertUsersServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SQL_connection connection = WC_server.getConnect();
		InputStream inputStream = request.getContent();
		byte[] byteArray = StreamUtils.readAll(inputStream);
		int status = -1; 
		Scanner scanner = new Scanner(new ByteArrayInputStream(byteArray));
		scanner.useLocale(Locale.US);
		String username = scanner.next();
		try {
			 status = connection.insert_user(username);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//TODO return status variable too. 
		response.writeHeader();
		scanner.close();
	
	}
}
