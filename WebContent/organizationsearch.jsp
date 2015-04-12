<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>

<!-- Database connection and query -->
<%
	Connection conn = null;
	ResultSet rset = null;
	ResultSet rset2= null;

	String error_msg = "";

	try {
		Class.forName("com.mysql.jdbc.Driver");

		// Edit the following to use your endpoint, database name, username, and password
		conn = DriverManager
				.getConnection(
						"jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111",
						"anm2147", "databases");
		Statement stmt = conn.createStatement();

		rset = stmt.executeQuery("SELECT * FROM GOVERNING_BODY");
		Statement stmt2 = conn.createStatement();
		rset2 = stmt2.executeQuery("SELECT DISTINCT NAME FROM ADVISOR_ADVISES");

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
	
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Organization Search</title>
</head>
<body>
	<form name="add" action="organizationsearchresult.jsp" method="post"
		onsubmit="return validateForm()">

		<p>
			Name <input type="text" name="name" size="40" maxlength="100">
		</p>
		<p>
			Domain <input type="text" name="domain" size="40" maxlength="100">
		</p>
		<p>
			Position Count <input type="text" name="position_count" size="5"
				maxlength="11">
		</p>
		<p>
			Member Count <input type="text" name="member_count" size="5"
				maxlength="11">
		</p>
		
		<input type="submit">
	</form>
</body>
</html>