
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MySQLServlet
 */
@WebServlet(description = "Adds a book to the library under the current user", urlPatterns = { "/addorg" })
public class OrgAdder extends HttpServlet {
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
    public OrgAdder() {
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
			if (conn == null) {
				// Edit the following to use your endpoint, database name, username, and password
				conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
			}
			String opt = request.getParameter("option"); 
			if(opt.equals("remove")){ 
				String query = "DELETE FROM ORGANIZATION WHERE "
						+ 
						"oid="+request.getParameter("oid")
						+ " AND name=" + "'" + request.getParameter("name") +"'"; 
				
				System.out.println(query);
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if (result == 0){
					request.getSession().setAttribute("badremorg", true);
					
				}
				else{
					request.getSession().setAttribute("badremorg", false);
					
				}
			}
			else{
			String domain = request.getParameter("domain"); 
			String query = ""; 
			String position_count = request.getParameter("position_count"); 
			if(domain.equals("") && !position_count.matches("^\\d+$")){ 
				query = "insert into ORGANIZATION (OID, NAME, MEMBER_COUNT) values ("
						+ request.getParameter("oid")
						+ ", '"
						+ request.getParameter("name")
						+ "', '"
						+ request.getParameter("member_count")
						+ 
						 "')";
			}
			else if(domain.equals("") && position_count.matches("^\\d+$")){ 
				query = "insert into ORGANIZATION (OID, NAME, POSITION_COUNT, MEMBER_COUNT) values ("
						+ request.getParameter("oid")
						+ ", '"
						+ request.getParameter("name")
						+ "', '"
						+ request.getParameter("position_count")
						+ "', '"
						+ request.getParameter("member_count")
						+ 
						 "')";
			}
			else{ 
				query = "insert into ORGANIZATION (OID, NAME, DOMAIN, POSITION_COUNT, MEMBER_COUNT) VALUES ("
						+ request.getParameter("oid")
						+ ", '"
						+ request.getParameter("name")
						+ "', '"
						+ request.getParameter("domain")
						+ "', '"
						+ request.getParameter("position_count")
						+ "', '"
						+ request.getParameter("member_count")
						+ 
						 "')";
				
			}
			
				System.out.println(query);
			Statement stmt = conn.createStatement(); 
			int result = stmt.executeUpdate(query);
			if (result == 0){
				request.getSession().setAttribute("badaddorg", true);
				
			}
			else{
				request.getSession().setAttribute("badaddorg", false);
				
			}
			String gov_body = request.getParameter("governing_body"); 
			if(!gov_body.equals(null)){ 
				
			}
			Statement gov_stmt = conn.createStatement(); 
			gov_query = ""; //NEED TO DO 
			}
			response.sendRedirect("home.jsp");
			pw.println("insertion successful");
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			pw.println(e.getMessage());
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