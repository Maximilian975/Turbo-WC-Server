package server;

import java.io.IOException;
import db.SQL_connection;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;
import db.Stamp;
import utils.Utils;

import java.sql.SQLException;

public class GetLatestStampServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SQL_connection connection = WC_server.getConnect();
        String bathroom = request.getQueryValue("bathroom");
        Stamp stamp = new Stamp(null, null, null);
        try {
			stamp = connection.get_latest_stamp(bathroom);

		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println(Utils.getMillis(stamp.timeDiff));
        byte[] msg = Utils.getMillis(stamp.timeDiff).getBytes();
        response.setContentLength(msg.length);
		response.setContentType("utf-8");
        response.write(msg);

    }
    
}
