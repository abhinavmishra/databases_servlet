
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

			// Edit the following to use your endpoint, database name, username, and password
			conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");

			String opt = request.getParameter("option"); 
			String domain = request.getParameter("domain"); 
			String position_count = request.getParameter("position_count"); 
			String member_count = request.getParameter("member_count"); 
			String oid = request.getParameter("oid"); 
			String advisor = request.getParameter("advisor");
			System.out.println(advisor);
			if(opt.equals("update")){ 
				if(!domain.equals("")){ 
					String update_query = "UPDATE ORGANIZATION SET DOMAIN='" + domain + "' WHERE OID=" + oid; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddorg", true);
					}
					else{
						request.getSession().setAttribute("badaddorg", false);

					}
				}
				if(!position_count.equals("")){ 
					String update_query = "UPDATE ORGANIZATION SET POSITION_COUNT=" + position_count + " WHERE OID=" + oid; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddorg", true);
					}
					else{
						request.getSession().setAttribute("badaddorg", false);

					}
				}
				if(!member_count.equals("")){ 
					String update_query = "UPDATE ORGANIZATION SET MEMBER_COUNT=" + member_count + " WHERE OID=" + oid; 
					Statement update_stmt = conn.createStatement(); 
					int result = update_stmt.executeUpdate(update_query);
					if(result==0){ 
						request.getSession().setAttribute("badaddorg", true);
					}
					else{
						request.getSession().setAttribute("badaddorg", false);

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
				String query = "DELETE FROM ORGANIZATION WHERE "
						+ 
						"oid="+request.getParameter("oid")
						+ " AND name=" + "'" + request.getParameter("name") +"'"; 

				System.out.println(query);
				Statement stmt = conn.createStatement(); 
				int result = stmt.executeUpdate(query);
				if (result == 0){
					request.getSession().setAttribute("badaddorg", true);

				}
				else{
					request.getSession().setAttribute("badaddorg", false);

				}
				
			}
			else{
			
				String query = ""; 
				
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
				String gov_body = request.getParameter("gov_body"); 
				System.out.println(gov_body);
				if(!gov_body.equals("")){ 


					Statement gov_stmt = conn.createStatement(); 
					String gov_query = "insert into MANAGES (NAME, OID, LIAISON) VALUES ('"
							+ gov_body +"', " + request.getParameter("oid") + ", '" + request.getParameter("liaison") +"')";
					int gov_result = gov_stmt.executeUpdate(gov_query);
					if (gov_result == 0){
						request.getSession().setAttribute("badaddorg", true);

					}
					else{
						request.getSession().setAttribute("badaddorg", false);

					}
				}
				if(!advisor.equals("")){ 
					String adv_info_query = "SELECT DISTINCT UNI, PHONE_NUMBER, SINCE FROM ADVISOR_ADVISES WHERE NAME='"+advisor+"'" ;
					System.out.println(adv_info_query);
					Statement stmt3 = conn.createStatement(); 
					ResultSet rset3 = stmt3.executeQuery(adv_info_query);
					rset3.next();
					String uni = rset3.getString(1);
					System.out.println(uni);
					String phone_num = rset3.getString(2);
					System.out.println(phone_num);
					String since = rset3.getString(3);
					System.out.println(since);
					Statement adv_stmt = conn.createStatement(); 
					String adv_query = "insert into ADVISOR_ADVISES (UNI, OID, NAME, DOMAIN, PHONE_NUMBER, SINCE) VALUES ('"
							+ uni+"', " + oid + ", '" + advisor + "', '" + domain + "', " + phone_num + ", " + since + ")"; 
					System.out.println(adv_query);
					int adv_result = adv_stmt.executeUpdate(adv_query); 
					if (adv_result == 0){
						request.getSession().setAttribute("badaddorg", true);

					}
					else{
						request.getSession().setAttribute("badaddorg", false);

					}
				}
			}
			response.sendRedirect("home.jsp");
			pw.println("insertion successful");
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			request.getSession().setAttribute("badaddorg", true);

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