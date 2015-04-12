<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Connection conn = null;
	ResultSet rset = null;
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
<title>Advanced search</title>
</head>
<body>
<form name="add" action="advancedsearchresult.jsp" method="post"
		onsubmit="return validateForm()">
		
		<p>
			UNI: <input type="text" name="uni" size="5" maxlength="10">
		</p>
		
		<p>
			Major <input type="text" name="major" size="10" maxlength="20">
		</p>
		<p>
			School <input type="text" name="school" size="10" maxlength="20">
		</p>
		<p>
			Year <input type="text" name="year" size="5" maxlength="11">
		</p>
			Organization: <select name="list_org">
			<option value=""></option>
			<%
				while (rset.next()) {
			%>
			<option><%=rset.getString(2)%></option>
			<%
				}
			%>
		</select>
		
		<input type="submit">
	</form>
</body>
</html>