package serverResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.util.Pair;

import java.util.*;
/**
 * Servlet implementation class testWorkbench
 */
@WebServlet("/testWorkbench")
public class testWorkbench extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int maxFileSize=1024*1024*10;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testWorkbench() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("user_name");
		String raw_encoded_pic=request.getParameter("encoded_data");
		byte[] pic=DatabaseInterfaceAdapter.decodeBase64(raw_encoded_pic);
		DatabaseInterfaceAdapter.insertBlob(pic,new Pair<String,String>("user_name",username), "user");
		Map<String,String> map=new HashMap<String,String>();
		map.put("user_name",username);
		byte[] retpic=DatabaseInterfaceAdapter.readBlobFromResultSet(
				DatabaseInterfaceAdapter.doQuerySelectWhere(map, "user"),
				"user_avatar", 
				maxFileSize);
		PrintWriter pw=response.getWriter();
		FileOutputStream fos=new FileOutputStream("d:\\b.bmp");
		fos.write(retpic);
		pw.print(DatabaseInterfaceAdapter.encodeBase64(retpic));
		fos.close();
		pw.close();
	}

}
