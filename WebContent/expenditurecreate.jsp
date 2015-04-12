<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
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
		var option = document.forms["exp_create"]["option"].value; 
		
		var reg_num= document.forms["exp_create"]["reg_num"].value;
		var amount = document.forms["exp_create"]["amount"].value;
		var vendor = document.forms["exp_create"]["vendor"].value;
		var reason = document.forms["exp_create"]["reason"].value;
		
		if(reg_num===""){
			alert("reg_num cannot be blank"); 
			return false; 
		}
		if(option==="add"){
			
			if((amount===""  ||(isNaN(amount)))|| vendor==="" || reason===""){ 
				
				alert("all fields must be filled");
				return false;
			}
		}
		if(option==="update"){ 
			
			if(!(amount!==""  || vendor!==""|| (reason!==""))){
				
				alert("must have at least one of amt, vendor, reason"); 
				return false; 
			}
		}
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expenditures</title>
</head>
<body>
<form name="exp_create" action="expenditurecreate" method="post"
		onsubmit="return validateForm()">
		<p>
			Add/Remove/Update: <select name="option">
				<option value="add">Add</option>
				<option value="remove">Remove</option>
				<option value="update">Update</option>
			</select>
		</p>
		
		<p>
			REG_NUM: <input type="text" name="reg_num" size="5" maxlength="10">
		
		</p>
		<p>
			Amount <input type="text" name="amount" size="50" maxlength="100">
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
			Vendor <input type="text" name="vendor" size="100" maxlength="200">
		</p>
		
		<p>
			Reason <input type="text" name="reason" size="200" maxlength="400">
		</p>
		
		
	
		<input type="submit">
	</form>
</body>
</html>