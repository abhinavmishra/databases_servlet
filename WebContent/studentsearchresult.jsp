<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*"%>
    <%@ page import="com.sun.rowset.CachedRowSetImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
Connection conn = null;
ResultSet result = null;
CachedRowSetImpl crs = new CachedRowSetImpl();
String major = request.getParameter("major"); 
String school = request.getParameter("school"); 
String year = request.getParameter("year"); 
String uni = request.getParameter("uni"); 
String name = request.getParameter("name"); 



try{ 
	conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
}catch(SQLException e){ 
	conn.close();
}
if(!uni.equals("")){
	String query="SELECT * FROM STUDENT WHERE UNI='"+uni+"'";
	Statement search_stmt = conn.createStatement(); 
	 result=search_stmt.executeQuery(query);

crs.populate(result);
	System.out.println("QUERY: "+query);
	
	while(result.next()){
		String result_name=result.getString(2);
		String result_major=result.getString(3);
		String result_school=result.getString(4);
		int result_year=result.getInt(5);
		
		System.out.println(result_name);
		System.out.println(result_major);
		System.out.println(result_year);
		System.out.println(result_school);
	}
} else {
	String query="SELECT * FROM STUDENT WHERE ";
	boolean first=true;
	if (!school.equals("")){
		query+="SCHOOL='"+school+"' ";
		first=false;
	}
	if(!year.equals("")){
		if(!first){
			query+="AND YEAR="+year+" ";
		} else {
			query+="YEAR="+year+" ";
			first=false;
		}
	}
	if(!name.equals("")){
		if(!first){
			query+="AND NAME='"+name+"' ";
		} else {
			query+="NAME='"+name+"' ";
			first=false;
		}
	}
	if(!school.equals("")){
		if(!first){
			query+="AND SCHOOL='"+school+"' ";
		} else {
			query+="SCHOOL='"+year+"' ";
			first=false;
		}
	}
	if(!major.equals("")){
		if(!first){
			query+="AND MAJOR='"+major+"' ";
		} else {
			query+="MAJOR='"+major+"' ";
			first=false;
		}
	}
	System.out.println("QUERY: "+query);
	Statement search_stmt = conn.createStatement(); 
	 result=search_stmt.executeQuery(query);

crs.populate(result);
	while(result.next()){
		String result_uni=result.getString(1);
		String result_name=result.getString(2);
		String result_major=result.getString(3);
		String result_school=result.getString(4);
		int result_year=result.getInt(5);
		
		System.out.println(result_uni);
		System.out.println(result_name);
		System.out.println(result_major);
		System.out.println(result_year);
		System.out.println(result_school);
	}

	
}



while(crs.next()){
System.out.println("yo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StudentSearchResult</title>
</head>
<body>
<TABLE cellpadding="15" border="1" style="background-color: #ffffcc;">
<TR>
<TH> UNI</TH>
<TH> Name </TH>
<TH> Major</TH>
<TH> School</TH>
<TH> Year</TH>

</TR>
<TR>
<TD><%=crs.getString(1)%></TD>
<TD><%=crs.getString(2)%></TD>
<TD><%=crs.getString(3)%></TD>
<TD><%=crs.getString(4)%></TD>
<TD><%=crs.getString(5)%></TD>




</TR>
</TABLE>



<%  } 
%>	
<p> 
Click <a href = "home.jsp"> here </a> to go back to home page
</p>
</body>
</html>

