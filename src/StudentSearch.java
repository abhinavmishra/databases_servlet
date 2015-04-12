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

@WebServlet(description = "Searches friends given parameters", urlPatterns = { "/studentsearch" })

public class StudentSearch extends HttpServlet {

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

	public StudentSearch() {

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

			System.out.println("entered page!!!");

			String major = request.getParameter("major"); 

			String school = request.getParameter("school"); 

			String year = request.getParameter("year"); 

			String uni = request.getParameter("uni"); 

			String name = request.getParameter("name"); 


			if(!uni.equals("")){

				String query="SELECT * FROM STUDENT WHERE UNI='"+uni+"'";

				Statement search_stmt = conn.createStatement(); 

				ResultSet result=search_stmt.executeQuery(query);


				System.out.println("QUERY: "+query);


				while(result.next()){

					String result_name=result.getString(2);

					String result_major=result.getString(3);

					String result_school=result.getString(4);

					int result_year=result.getInt(5);


					System.out.println(result_name);

					System.out.println(result_major);

					System.out.println(result_year);

					System.out.println(result_school);

				}

			} else {

				String query="SELECT * FROM STUDENT WHERE ";

				boolean first=true;

				if (!school.equals("")){

					query+="SCHOOL='"+school+"' ";

					first=false;

				}

				if(!year.equals("")){

					if(!first){

						query+="AND YEAR="+year+" ";

					} else {

						query+="YEAR="+year+" ";

						first=false;

					}

				}

				if(!name.equals("")){

					if(!first){

						query+="AND NAME='"+name+"' ";

					} else {

						query+="NAME='"+name+"' ";

						first=false;

					}

				}

				if(!school.equals("")){

					if(!first){

						query+="AND SCHOOL='"+school+"' ";

					} else {

						query+="SCHOOL='"+year+"' ";

						first=false;

					}

				}

				if(!year.equals("")){

					if(!first){

						query+="AND MAJOR='"+major+"' ";

					} else {

						query+="MAJOR='"+major+"' ";

						first=false;

					}

				}

				System.out.println("QUERY: "+query);

				Statement search_stmt = conn.createStatement(); 

				ResultSet result=search_stmt.executeQuery(query);


				while(result.next()){

					String result_name=result.getString(2);

					String result_major=result.getString(3);

					String result_school=result.getString(4);

					int result_year=result.getInt(5);


					System.out.println(result_name);

					System.out.println(result_major);

					System.out.println(result_year);

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