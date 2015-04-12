<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%
	Connection conn = null;
	ResultSet rset = null;
	ResultSet rset2 = null; 
	String error_msg = "";

	try {
		Class.forName("com.mysql.jdbc.Driver");

		// Edit the following to use your endpoint, database name, username, and password
		conn = DriverManager
				.getConnection(
						"jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111",
						"anm2147", "databases");
		Statement stmt = conn.createStatement();

		rset = stmt.executeQuery("SELECT * FROM ORGANIZATION");
		Statement stmt2 = conn.createStatement(); 
		rset2 = stmt2.executeQuery("SELECT * FROM BUILDING");
	} catch (SQLException e) {
		error_msg = e.getMessage();
		if (conn != null) {
			conn.close();
		}
	}
%>

<html>
<head>
<script type="text/javascript">
	function validateForm() {
	
		
		var name = document.forms["event_search"]["name"].value;
		var date = document.forms["event_search"]["date"].value;
	
	
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Portal</title>
</head>
<body>
<form name="event_search" action="eventsearchresult.jsp" method="post"
		onsubmit="return validateForm()">
	
		<p>
			Name <input type="text" name="name" size="20" maxlength="40">
		</p>
		
		<p>
			Date <input type="text" name="date" size="10" maxlength="25">
		</p>
	
	
		<input type="submit">
	</form>
</body>
</html>