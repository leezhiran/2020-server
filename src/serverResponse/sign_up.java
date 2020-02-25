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

import com.mysql.cj.protocol.Resultset;


/**
 * Servlet implementation class sign_up
 */
@WebServlet("/sign_up")
public class sign_up extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String username="leezhiran";
    private static final String password="3721qwer";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/serverdb?useSSL=false&serverTimezone=UTC";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sign_up() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		java.sql.Connection connection=null;
		Statement statement=null;
		PrintWriter out= response.getWriter();
		try {
			Class.forName(JDBC_DRIVER);
			connection=DriverManager.getConnection(DB_URL,username,password);
			statement =connection.createStatement();
			
			String user_name=request.getParameter("user_name");
			String user_password=request.getParameter("user_password");
			String user_nickname=request.getParameter("user_nickname");
			String user_e_mail=request.getParameter("user_e_mail");
			String user_telephone=request.getParameter("telephone_number");
			String user_university_no=request.getParameter("user_university_no");
					
			String test_duplicate= "select * from user where user_name=\""+user_name+"\";";
			ResultSet rs=statement.executeQuery(test_duplicate);
			if(!rs.next()) {
				out.println("DUPLICATE_USER_ERROR");
				throw new Exception();
			}//检查用户是否已经存在
			else{
				String sql_command="insert into user(user_name,user_password,user_nick_name,user_e_mail,register_time,administrator_of,member_of,event_participated_in,telephone_number,university_no) values("
					+"\""+user_name
					+"\","
					+"\""+user_password
					+"\","
					+"\""+user_nickname
					+"\","
					+"\""+user_e_mail
					+"\","
					+"NOW(),\";\",\";\",\";\""
					+"\""+user_telephone
					+"\","
					+"\""+user_university_no
					+"\","
					+ ");";
			System.out.println(sql_command);
			connection.createStatement().executeUpdate(sql_command);
			out.println("OK");
			//若存在则根据post内容注册账户						
			}
			
		}catch(SQLException se) {
			out.println("ERR");
			se.printStackTrace();
		}catch(Exception e) {
			out.println("ERR");
			e.printStackTrace();
		}finally {
			try {
				if(statement!=null) statement.close();
			}catch(SQLException se2) {
				se2.printStackTrace();
			}
		}
	}

}
