
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * Servlet implementation class MySQLServlet
 */
@WebServlet(description = "Searches friends given parameters", urlPatterns = { "/orgsearch" })
public class OrgSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		} catch(ClassNotFoundException cnfe) {}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrgSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		response.setContentType("text/html");
		try { 

			// Edit the following to use your endpoint, database name, username, and password
			conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");

			String member_count= request.getParameter("member_count"); //DOES THIS WORK!!
			String position_count= request.getParameter("position_count"); //DOES THIS WORK!!
			String domain=request.getParameter("domain");
			String name = request.getParameter("name"); 

			String query="SELECT * FROM ORGANIZATION WHERE ";
			boolean first=true;
			if (!name.equals("")){
				query+="NAME='"+name+"' ";
				first=false;
			}
			if(!position_count.equals("")){
				if(!first){
					query+="AND POSITION_COUNT="+position_count+" ";
				} else {
					query+="POSITION_COUNT="+position_count+" ";
					first=false;
				}
			}
			if(!domain.equals("")){
				if(!first){
					query+="AND DOMAIN='"+domain+"' ";
				} else {
					query+="DOMAIN='"+domain+"' ";
					first=false;
				}
			}
			if(!member_count.equals("")){
				if(!first){
					query+="AND MEMBER_COUNT="+member_count+" ";
				} else {
					query+="MEMBER_COUNT="+member_count+" ";
					first=false;
				}
			}
			if(first){
				query="SELECT * FROM ORGANIZATION";
			}
			
			System.out.println("QUERY: "+query);
			Statement search_stmt = conn.createStatement(); 
			ResultSet result=search_stmt.executeQuery(query);

			while(result.next()){
				String result_oid=result.getString(1);
				String result_name=result.getString(2);
				String result_domain=result.getString(3);
				String result_pos_count=result.getString(4);
				int result_member_count=result.getInt(5);

				System.out.println(result_oid);
				System.out.println(result_name);
				System.out.println(result_domain);
				System.out.println(result_pos_count);
				System.out.println(result_member_count);
			}


		response.sendRedirect("home.jsp");
		pw.println("insertion successful");
		//conn.commit();
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
		request.getSession().setAttribute("badSearch", true);
		response.sendRedirect("home.jsp");

		try {
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//	response.sendError(arg0, arg1);

	}

	pw.close();
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
}
}