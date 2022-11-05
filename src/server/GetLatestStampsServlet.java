package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import db.SQL_connection;
import db.Stamp;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;

public class GetLatestStampsServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SQL_connection connection = WC_server.getConnect();
		List<Stamp> stamps = new LinkedList<>();
		try {
			stamps = connection.get_latest_stamps();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String message = "";
		if (stamps.size() > 0) {
			String color = '"' + "color:green" + '"';
			for (Stamp stamp : stamps) {
				String diff = stamp.timeDiff;
				int ind = diff.indexOf(':')+1;
			    int hrs = Integer.valueOf(diff.substring(0,diff.indexOf(':')));
			    int mins = Integer.valueOf(diff.substring(ind,diff.indexOf(':',ind)));
			    if (hrs >= 2) {
			    	 color = '"' + "color:red" + '"';
			    
			    }
				message += String.format("<li style=%s> %s rengjordes senast vid %s av %s"
						+ " vilket �r %d timmar och %d minuter sedan" + "</li>",color, stamp.bathroom, stamp.date,stamp.user, hrs, mins);
			}
		}
		String html = String.format(
				"<article>" + "<header><h1>De senaste st�dningarna av toaletterna p� Turbo</h1>"
						+ "<p>Gjord av Max Mattsson</p>" + "<ul>" + "%s" + "</ul>" + "</header>" + "</article>",
				message);
		byte[] byteMessage = html.getBytes();

		response.setContentLength(byteMessage.length);
		response.setContentType("utf-8");
		response.write(byteMessage);
	}
}
