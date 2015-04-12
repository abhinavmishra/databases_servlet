<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>

<!-- Database connection and query -->
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
		// Checking to make sure that ISBN and pagenum are numbers
		var option = document.forms["join"]["option"].value; 
		
		var uni = document.forms["join"]["uni"].value;

		var org = document.forms["join"]["org"].value;
		var start_time = document.forms["join"]["start_time"].value;
		var end_time = document.forms["join"]["end_time"].value;
		var current_pos= document.forms["join"]["current_pos"].value;
		

		if (uni === "" || org === "") {
			alert("uni, name and organization cannot be left blank");
			return false;
		}
		if (option === "update") {
			
			if (!((start_time !== "" && isFinite(start_time))
					|| (end_time !== "" && isFinite(end_time)) || current_pos !== "")) {

				alert("must have at least start, end or current time/pos");
				return false;
			}
		}

	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Join Organization</title>
</head>
<body>
	<form name="join" action="joinorg" method="post"
		onsubmit="return validateForm()">
		<p>
			Add/Remove/Update: <select name="option">
				<option value="join">Add</option>
				<option value="leave">Remove</option>
				<option value="update">Update</option>
			</select>
		<p>
			UNI: <input type="text" name="uni" size="5" maxlength="10">
		</p>
				Organization: <select name="list_org">
			<option value=""></option>
			<%
				while (rset.next()) {
			%>
			<option><%=rset.getString(1) + " " + rset.getString(2)%></option>
			<%
				}
			%>
		</select>
		<p>
			Current position <input type="text" name="current_pos" size="10" maxlength="20">
		</p>
		<p>
			Start Time (year) <input type="text" name="start_date" size="10" maxlength="20">
		</p>
		<p>
			End Time (year) <input type="text" name="end_date" size="10" maxlength="20">
		</p>

		
		<input type="submit">
	</form>
</body>
</html>