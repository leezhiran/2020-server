package serverResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class dup_query_email_and_username
 */
@WebServlet("/dup_query_email_and_username")
public class dup_query_email_and_username extends HttpServlet { 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dup_query_email_and_username() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		Map<String,String> items=new HashMap<String,String>();
		if(request.getParameter("type").equals("0")) {
			items.put("user_name",request.getParameter("input"));
		}else {
			items.put("user_e_mail",request.getParameter("input"));
		}
		if(!DatabaseInterfaceAdapter.isResultSetEmpty(DatabaseInterfaceAdapter.doQuerySelectWhere(items, "user"))) {
			out.println("OK");
		}else {
			out.println("ERR");
		}
	}
}
