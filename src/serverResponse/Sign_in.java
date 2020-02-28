package serverResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Sign_in
 */
@WebServlet("/Sign_in")
public class Sign_in extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String username = "leezhiran";
	private static final String password = "3721qwer";
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/serverdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sign_in() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
			String username_input = request.getParameter("user_name");
			String userpassword_input=request.getParameter("user_password");
			String get_pwd = "select * from user where user_name=\"" + username_input+ "\""+"or "+"user_e_mail=\"" + username_input+ "\";";
			ResultSet rs = statement.executeQuery(get_pwd);

			if(rs.next()==false||!(rs.getString("user_password")).equals(userpassword_input)) {
				out.println("ERR");
				out.println(rs.getString("user_password"));
				out.println(userpassword_input);
			}else{
				out.println("OK");
				out.println(String.valueOf(rs.getInt("user_id")));
				out.println(rs.getString("user_name"));
				out.println(rs.getString("user_e_mail"));
				out.println(rs.getString("user_nick_name"));
				out.println(rs.getString("administrator_of"));
				out.println(rs.getString("member_of"));
				out.println(rs.getString("event_participated_in"));
				out.println(String.valueOf(rs.getDate("register_time")));
				out.println(rs.getString("user_password"));
			}
			connection.close();
			out.close();
			statement.close();
		} catch(ClassNotFoundException cne1) {
			cne1.printStackTrace(out);
		} catch(SQLException se1) {
			se1.printStackTrace(out);
		}

	}
}
