
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
@WebServlet(description = "Searches friends given parameters", urlPatterns = { "/yo" })
public class AdvancedSearch extends HttpServlet {
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
	public AdvancedSearch() {
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

			String major = request.getParameter("major"); 
			String school = request.getParameter("school"); 
			String year = request.getParameter("year"); 
			String uni = request.getParameter("uni"); 
			String organization_name = request.getParameter("organization_name"); 

			String query="SELECT s1.UNI, s1.NAME, s1.MAJOR, s1.SCHOOL, s1.YEAR, o1.NAME AS 'ORGANIZATION NAME', p1.CURRENT_POSITION, p1.START, p1.END FROM STUDENT s1, ORGANIZATION o1, PART_OF p1 WHERE p1.OID=o1.OID AND p1.UNI=s1.UNI ";

			if(!uni.equals("")){
				query+="AND s1.UNI='"+uni+"'";
				Statement search_stmt = conn.createStatement(); 
				ResultSet result=search_stmt.executeQuery(query);

				System.out.println("QUERY: "+query);

				while(result.next()){
					String result_uni=result.getString(1);
					String result_name=result.getString(2);
					String result_major=result.getString(3);
					String result_school=result.getString(4);
					int result_year=result.getInt(5);
					String result_org_name=result.getString(6);
					String result_curpos=result.getString(7);
					String result_start=result.getString(8);
					String result_end=result.getString(9);							

					System.out.println(result_uni);
					System.out.println(result_name);
					System.out.println(result_major);
					System.out.println(result_year);
					System.out.println(result_school);
					System.out.println(result_org_name);
					System.out.println(result_curpos);
					System.out.println(result_start);
					System.out.println(result_end);
				}
			} else {
				if (!school.equals("")){
					query+="AND s1.SCHOOL='"+school+"' ";

				}
				if(!year.equals("")){
					query+="AND s1.YEAR="+year+" ";
				}
				if(!organization_name.equals("")){

					query+="AND o1.NAME='"+organization_name+"' ";

				}
				if(!school.equals("")){

					query+="AND s1.SCHOOL='"+school+"' ";

				}
				if(!major.equals("")){

					query+="AND s1.MAJOR='"+major+"' ";

				}
				System.out.println("QUERY: "+query);
				Statement search_stmt = conn.createStatement(); 
				ResultSet result=search_stmt.executeQuery(query);

				while(result.next()){
					String result_uni=result.getString(1);
					String result_name=result.getString(2);
					String result_major=result.getString(3);
					String result_school=result.getString(4);
					int result_year=result.getInt(5);
					String result_org_name=result.getString(6);
					String result_curpos=result.getString(7);
					String result_start=result.getString(8);
					String result_end=result.getString(9);							

					System.out.println(result_uni);
					System.out.println(result_name);
					System.out.println(result_major);
					System.out.println(result_year);
					System.out.println(result_school);
					System.out.println(result_org_name);
					System.out.println(result_curpos);
					System.out.println(result_start);
					System.out.println(result_end);
				}


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
