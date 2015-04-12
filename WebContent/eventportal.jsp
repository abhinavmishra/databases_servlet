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
		var option = document.forms["add"]["option"].value; 
		
		var eid = document.forms["event_create"]["eventid"].value;
		var name = document.forms["event_create"]["name"].value;
		var description = document.forms["event_create"]["description"].value;
		var date = document.forms["event_create"]["date"].value;
		var building = document.forms["event_create"]["building"].value; 
		if(eid==="" || name==="" || date===""){
			alert("eid name and date cannot be left blank"); 
			return false; 
		}
		
		if(option==="update"){ 
			
			if(!(name!==""  || date!==""|| (description!==""))){
				
				alert("must have at least one of name, date, description"); 
				return false; 
			}
		}
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Portal</title>
</head>
<body>
<form name="event_create" action="eventportal" method="post"
		onsubmit="return validateForm()">
		<p>
			Add/Remove/Update: <select name="option">
				<option value="add">Add</option>
				<option value="remove">Remove</option>
				<option value="update">Update</option>
			</select>
		</p>
		
		<p>
			EID: <input type="text" name="eventid" size="5" maxlength="10">
		
		</p>
		
		<p>
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
		</p>
		<p>
			Name <input type="text" name="name" size="20" maxlength="40">
		</p>
		
		<p>
			Description <input type="text" name="description" size="50" maxlength="100">
		</p>
		<p>
			Date <input type="text" name="date" size="10" maxlength="25">
		</p>
		<p>
					Building: <select name="building">
			<option value=""></option>
			<%
				while (rset2.next()) {
			%>
			<option><%=rset2.getString(2) + ":" + rset2.getString(1)%></option>
			<%
				}
			%>
		</select>
		</p>
	
		<input type="submit">
	</form>
</body>
</html>