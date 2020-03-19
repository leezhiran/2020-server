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

import com.mysql.cj.protocol.Resultset;


/**
 * Servlet implementation class sign_up
 */
@WebServlet("/sign_up")
public class sign_up extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String username="leezhiran";
    private static final String password="3721qwer";
    private static final String databaseColumnIndexs[]=new String[] {"user_name","user_e_mail","user_nick_name","user_password","university_no","telephone_number"};
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
		PrintWriter out=response.getWriter();
		Map<String,String> items=new HashMap<String,String>();
		for(String indexs:databaseColumnIndexs) {
			items.put(indexs,request.getParameter(indexs));
		}
		DatabaseInterfaceAdapter.doInsert(items, "user");
	}

}
