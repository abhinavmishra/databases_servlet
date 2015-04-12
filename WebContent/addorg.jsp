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
		// Checking to make sure that ISBN and pagenum are numbers
		var oid = document.forms["add"]["oid"].value;
		var name = document.forms["add"]["name"].value;
		var position_count = document.forms["add"]["position_count"].value;
		if(position_count!=="" && isNaN(position_count)){ 
			
			alert("position_count needs to be a number");
			return false; 
		}
		
		var valid = true;
		if (oid.length <= 0 || isNaN(oid)) {
			alert("You have left the oid field blank or have not put in an integer number")
			return false;
		}
		if (name.length <= 0) {
			alert("you cannot leave the name blank");
			return false;
		}
		var option = document.forms["add"]["option"].value;
		if (option === "add") {
			var member_count = document.forms["add"]["member_count"].value;
			if (member_count === "" || isNaN(member_count)) {
				alert("member count must be a number");
				return false;

			} else {
				if (member_count <= 0) {
					return false;
				}
			}
		}
		if(option ==="update"){
			var member_count = document.forms["add"]["member_count"].value;
			var position_count = document.forms["add"]["position_count"].value;
			var domain = document.forms["add"]["domain"].value;
			if(!(domain!==""  || (position_count!=="" && isFinite(position_count))||
					(member_count!=="" && isFinite(member_count)))){ 
				alert("for update must have domain, position_count or member_count");
				return false; 
				
			}
		}

	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add/Remove Organization</title>
</head>
<body>
	<form name="add" action="addorg" method="post"
		onsubmit="return validateForm()">
		<p>
			Add/Remove/Update: <select name="option">
				<option value="add">Add</option>
				<option value="remove">Remove</option>
				<option value="update">Update</option>
			</select>
		<p>
			OID: <input type="text" name="oid" size="5" maxlength="11">
		</p>
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
		Governing Body: <select name="gov_body">
			<option value=""></option>
			<%
				while (rset.next()) {
			%>
			<option><%=rset.getString(1)%></option>
			<%
				}
			%>
		</select>
		<p>
		Advisor: <select name="advisor">
			<option value=""></option>
			<%
				while (rset2.next()) {
			%>
			<option><%=rset2.getString(1)%></option>
			<%
				}
			%>
		</select>
		</p>
		<p>
			Liaison <input type="text" name="liaison" size="5" maxlength="10">
		</p>
		<input type="submit">
	</form>
</body>
</html>