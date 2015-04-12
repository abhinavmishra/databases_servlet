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
		var name = document.forms["join"]["name"].value;
		var company_name = document.forms["join"]["company_name"].value;
		var job_title = document.forms["join"]["job_title"].value;
		var contact_info= document.forms["join"]["contact_info"].value;
		

		if (uni === "" || name === "") {
			alert("uni, name and organization cannot be left blank");
			return false;
		}
		if (option === "update") {
			
			if (!((company_name !== "")
					|| (job_title !== "") || contact_info !== "")) {

				alert("must have at least contact info, job title, or company name");
				return false;
			}
		}

	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alumni Registration</title>
</head>
<body>
	<form name="join" action="alumniorg" method="post"
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
		<p>
			Name: <input type="text" name="name" size="10" maxlength="40">
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
			Contact info: <input type="text" name="contact_info" size="30" maxlength="50">
		</p>
		<p>
			Job Title: <input type="text" name="job_title" size="30" maxlength="50">
		</p>
		<p>
			Company Name: <input type="text" name="company_name" size="30" maxlength="50">
		</p>
		
		
		<input type="submit">
	</form>
</body>
</html>