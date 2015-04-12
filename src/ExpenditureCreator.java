
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
@WebServlet(description = "Adds a book to the library under the current user", urlPatterns = { "/expenditurecreate" })
public class ExpenditureCreator extends HttpServlet {
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
	public ExpenditureCreator() {
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
			String org = request.getParameter("list_org");
			System.out.println(org);
			String[] split = org.split(" "); 
			String oid = split[0];
			String opt = request.getParameter("option"); 
			String reason = request.getParameter("reason"); 
			String vendor = request.getParameter("vendor"); 
			String reg_num = request.getParameter("reg_num"); 
			String amount = request.getParameter("amount"); 
			if(opt.equals("update")){ 
				if(!reason.equals("")){ 
					String update_query = "UPDATE EXPENDITURE SET REASON='" + reason + "' WHERE REG_NUM=" + reg_num;
					System.out.println(update_query);
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddexp", true);
					}
					else{
						request.getSession().setAttribute("badaddexp", false);

					}
				}
				if(!vendor.equals("")){ 
					String update_query = "UPDATE EXPENDITURE SET VENDOR='" + vendor + "' WHERE REG_NUM=" + reg_num;
					Statement update_stmt = conn.createStatement(); 
					System.out.println(update_query);
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddexp", true);
					}
					else{
						request.getSession().setAttribute("badaddexp", false);

					}
				}
				if(!amount.equals("")){ 
					String update_query = "UPDATE EXPENDITURE SET AMOUNT=" + amount + " WHERE REG_NUM=" + reg_num;
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddexp", true);
					}
					else{
						request.getSession().setAttribute("badaddexp", false);

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
				String query = "DELETE FROM EXPENDITURE WHERE "
						+ 
						"REG_NUM="+reg_num + "";

				System.out.println(query);
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if(result==0){ 
					request.getSession().setAttribute("badaddexp", true);
				}
				else{
					request.getSession().setAttribute("badaddexp", false);

				}
				String run_by_query = "DELETE FROM HAS WHERE REG_NUM="+reg_num + " AND OID=" + oid;
				System.out.println(run_by_query);
				Statement run_by_stmt = conn.createStatement(); 
				int result2 = run_by_stmt.executeUpdate(run_by_query);
				if(result2==0){ 
					request.getSession().setAttribute("badaddexp", true);
				}
				else{
					request.getSession().setAttribute("badaddexp", false);

				}
			}
			else{
			
				String query = ""; 
				
					query = "insert into EXPENDITURE (REG_NUM, AMOUNT, VENDOR, REASON) values ("
							+ reg_num
							+ ", "
							+ amount
							+ ", '"
							+ vendor
							+ "', '"
							+ reason+
							
							"')";

				

				System.out.println(query);
				
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if(result==0){ 
					request.getSession().setAttribute("badaddexp", true);
				}
				else{
					request.getSession().setAttribute("badaddexp", false);

				}
				String run_by_query = "INSERT INTO HAS (REG_NUM,OID) VALUES (" + reg_num+"," + oid+")";
				System.out.println(run_by_query);
				Statement run_by_stmt = conn.createStatement(); 
				int result2 = run_by_stmt.executeUpdate(run_by_query);
				if(result2==0){ 
					request.getSession().setAttribute("badaddexp", true);
				}
				else{
					request.getSession().setAttribute("badaddexp", false);

				}
			}
			response.sendRedirect("home.jsp");
			pw.println("insertion successful");
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("badaddevent", true);
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