package serverResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	private static final long serialVersionUID = 1L;
	private static final String username = "leezhiran";
	private static final String password = "3721qwer";
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/serverdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";   
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
		java.sql.Connection connection = null;
		Statement statement = null;
		PrintWriter out = response.getWriter();
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, username, password);
			statement = connection.createStatement();
			String query_type = request.getParameter("type");
			String parameter_input=request.getParameter("input");
			String query_dup=null;
			if(query_type.equals("0"))
				query_dup = "select * from user where user_name=\"" + parameter_input+ "\";";
			else
				query_dup = "select * from user where user_e_mail=\"" + parameter_input+ "\";";
			ResultSet rs = statement.executeQuery(query_dup);
			System.out.println(query_dup);
			if(!rs.next()) {
				System.out.println("1");
				out.println("OK");
			}else {
				System.out.println("2");
				out.println("ERR");
			}
			connection.close();
			out.close();
			statement.close();
		} catch(ClassNotFoundException cne1) {
			cne1.printStackTrace();
		} catch(SQLException se1) {
			se1.printStackTrace();
		}

	}
}
