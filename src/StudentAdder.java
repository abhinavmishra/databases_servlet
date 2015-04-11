
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
@WebServlet(description = "Adds a book to the library under the current user", urlPatterns = { "/addstudent" })
public class StudentAdder extends HttpServlet {
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
	public StudentAdder() {
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
			
			String opt = request.getParameter("option"); 
			String major = request.getParameter("major"); 
			String school = request.getParameter("school"); 
			String year = request.getParameter("year"); 
			String uni = request.getParameter("uni"); 
			String name = request.getParameter("name"); 
			if(opt.equals("update")){ 
				if(!major.equals("")){ 
					String update_query = "UPDATE STUDENT SET MAJOR='" + major + "' WHERE UNI=" + uni; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddschool", true);
					}
					else{
						request.getSession().setAttribute("badaddschool", false);

					}
				}
				if(!school.equals("")){ 
					String update_query = "UPDATE STUDENT SET SCHOOL='" + school + "' WHERE UNI=" + uni; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddschool", true);
					}
					else{
						request.getSession().setAttribute("badaddschool", false);

					}
				}
				if(!year.equals("")){ 
					String update_query = "UPDATE STUDENT SET YEAR=" + year + " WHERE UNI=" + uni; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddschool", true);
					}
					else{
						request.getSession().setAttribute("badaddschool", false);

					}
				}
				
			}
			else if(opt.equals("remove")){ 
//				String manages_query = "SELECT oid FROM MANAGES WHERE oid=" + request.getParameter("oid");
//				Statement manages_stmt = conn.createStatement(); 
//				ResultSet mrset = manages_stmt.executeQuery(manages_query);
//				mrset.next();
//				if(mrset.getString(1).equals(request.getParameter("oid"))){ 
//					Statement del_manages_stmt = conn.createStatement(); 
//					String del_manages_update = "DELETE FROM MANAGES WHERE oid=" + request.getParameter("oid"); 
//					int result2 = del_manages_stmt.executeUpdate(del_manages_update);
//					if (result2 == 0){
//						request.getSession().setAttribute("badaddorg", true);
//
//					}
//					else{
//						request.getSession().setAttribute("badaddorg", false);
//
//					}
//				}
				String query = "DELETE FROM STUDENT WHERE "
						+ 
						"uni='"+uni
						+ "' AND name=" + "'" + name +"'"; 

				System.out.println(query);
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if (result == 0){
					request.getSession().setAttribute("badaddstudent", true);

				}
				else{
					request.getSession().setAttribute("badaddstudent", false);

				}
			}
			else{
			
				String query = ""; 
				
				if(major.equals("")){ 
					query = "insert into STUDENT (UNI, NAME, SCHOOL, YEAR) values ('"
							+ request.getParameter("uni")
							+ "', '"
							+ request.getParameter("name")
							+ "', '"
							+ request.getParameter("school")
							+ "', "+request.getParameter("year")+
							
							")";
				}
				
				else{ 
					query = "insert into STUDENT (UNI, NAME, MAJOR, SCHOOL, YEAR) values ('"
							+ request.getParameter("uni")
							+ "', '"
							+ request.getParameter("name")
							+ "', '"
							+ request.getParameter("major")
							+ "', '"
							+ request.getParameter("school")
							+ "', "+request.getParameter("year")+
							
							")";

				}

				System.out.println(query);
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if (result == 0){
					request.getSession().setAttribute("badaddstudent", true);

				}
				else{
					request.getSession().setAttribute("badaddstudent", false);

				}
	
			}
			response.sendRedirect("home.jsp");
			pw.println("insertion successful");
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			request.getSession().setAttribute("badaddstudent", true);

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