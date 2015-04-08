<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- This import is necessary for JDBC -->
<%@ page import="java.sql.*"%>

<!-- Database connection and query -->
<%
	Connection conn = null;
	ResultSet rset = null;
	String error_msg = "";
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		
		// Edit the following to use your endpoint, database name, username, and password
		conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
		Statement stmt = conn.createStatement();
		rset = stmt.executeQuery("SHOW TABLES");
	} catch (SQLException e) {
		error_msg = e.getMessage();
		if( conn != null ) {
			conn.close();
		}
	}
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>JSP Sample</title>
</head>
<body>
	<H2>Tables</H2>
	<TABLE>
		<tr>
			<td>TABLE</td>
		</tr>
		<tr>
			<td><b>----------</b></td>
		</tr>
 
	<%
		if(rset != null) {
 			while(rset.next()) {
	 			out.print("<tr>");
	 			out.print("<td>" + rset.getString(1) + "</td>");
	 			out.print("</tr>");
 			}
 		} else {
 			out.print(error_msg);
 		}
	
		if( conn != null ) {
			
			conn.close();
		}
	%>
	</TABLE>
</body>
</html>