
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
@WebServlet(description = "Searches friends given parameters", urlPatterns = { "/eventsearch" })
public class EventSearch extends HttpServlet {
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
	public EventSearch() {
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

			String date_time= request.getParameter("date_time"); //DOES THIS WORK!!
			String name = request.getParameter("name"); 

			String query="SELECT e1.EID, e1.NAME, e1.DESCRIPTION, e1.DATE_TIME, i1.ADDRESS FROM EVENT e1, IS_IN i1 WHERE e1.EID=i1.EID ";
			
			if (!name.equals("")){
				query+="AND NAME='"+name+"' ";
			}
			
			if (!date_time.equals("")){
				query+="AND DATE_TIME='"+date_time+"' ";
			}
			
			System.out.println("QUERY: "+query);
			Statement search_stmt = conn.createStatement(); 
			ResultSet result=search_stmt.executeQuery(query);

			while(result.next()){
				String result_eid=result.getString(1);
				String result_name=result.getString(2);
				String result_description=result.getString(3);
				String result_dt=result.getString(4);
				String result_address=result.getString(5);

				System.out.println(result_eid);
				System.out.println(result_name);
				System.out.println(result_description);
				System.out.println(result_dt);
				System.out.println(result_address);
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