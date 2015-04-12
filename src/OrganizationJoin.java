import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Adds a book to the library under the current user", urlPatterns = { "/joinorg" })
public class OrganizationJoin extends HttpServlet  {
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
	public OrganizationJoin() {
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
			String uni = request.getParameter("uni"); 
			String org = request.getParameter("list_org");
			System.out.println(org);
			String[] split = org.split(" "); 
			String oid = split[0];
			
			String current_pos = request.getParameter("current_pos"); 
			String start_date = request.getParameter("start_date"); 
			String end_date = request.getParameter("end_date"); 
			Statement stmt = conn.createStatement(); 
			if(opt.equals("leave")){ 
				String remove_query = "DELETE FROM PART_OF WHERE UNI='" + uni + "' AND OID=" + oid; 
				int result = stmt.executeUpdate(remove_query);
				if(result==0){ 
					request.getSession().setAttribute("badjoinorg", true);
				}
				else{
					request.getSession().setAttribute("badjoinorg", false);

				}
			}

			else if(opt.equals("join")){ 

				String insert_query = "INSERT INTO PART_OF (UNI,OID,CURRENT_POSITION,START,END) values ('" + uni
						+ "', " + oid + ", '" + current_pos + "', '" + start_date + "', '" + end_date + "')";
				int result = stmt.executeUpdate(insert_query);
				if(result==0){ 
					request.getSession().setAttribute("badjoinorg", true);
				}
				else{
					request.getSession().setAttribute("badjoinorg", false);

				}


			}

			else{ 
				if(!current_pos.equals("")){ 
					String update_query = "UPDATE PART_OF SET CURRENT_POSITION='" + current_pos + "' WHERE UNI='" + uni + "' AND OID=" + oid;   
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badjoinorg", true);
					}
					else{
						request.getSession().setAttribute("badjoinorg", false);

					}
				}
				if(!start_date.equals("")){ 
					String update_query = "UPDATE PART_OF SET START='" + start_date + "' WHERE UNI='" + uni + "' AND OID=" + oid;   
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badjoinorg", true);
					}
					else{
						request.getSession().setAttribute("badjoinorg", false);

					}
				}
				if(!end_date.equals("")){ 
					String update_query = "UPDATE PART_OF SET END='" + start_date + "' WHERE UNI='" + uni + "' AND OID=" + oid;   				
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badjoinorg", true);
					}
					else{
						request.getSession().setAttribute("badjoinorg", false);

					}
				}
			}
		
			response.sendRedirect("home.jsp");
			pw.println("insertion successful");
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("badjoinorg", true);
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
