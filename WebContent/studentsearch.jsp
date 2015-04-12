<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function validateForm() {
	
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add/Remove/Update Student</title>
</head>
<body>
<form name="add" action="studentsearch" method="post"
		onsubmit="return validateForm()">
		<p>
			Add/Remove/Update: <select name="option">
				<option value="add">Add</option>
				<option value="remove">Remove</option>
				<option value="update">Update</option>
			</select>
		<p>
			UNI: <input type="text" name="uni" size="5" maxlength="10">
		</p>
		<p>
			Name <input type="text" name="name" size="20" maxlength="40">
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
	
		<input type="submit">
	</form>
</body>
</html>