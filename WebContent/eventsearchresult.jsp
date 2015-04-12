<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*"%>
    <%@ page import="com.sun.rowset.CachedRowSetImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
Connection conn = null;
ResultSet result = null;
CachedRowSetImpl crs = new CachedRowSetImpl();

String name = request.getParameter("name");
String date_time = request.getParameter("date");

String query="SELECT e1.EID, e1.NAME, e1.DESCRIPTION, e1.DATE_TIME, i1.ADDRESS FROM EVENT e1, IS_IN i1 WHERE e1.EID=i1.EID ";
try{ 
	conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
}catch(SQLException e){ 
	conn.close();

}
if (!name.equals("")){
	query+="AND NAME='"+name+"' ";
}

if (!date_time.equals("")){
	query+="AND DATE_TIME='"+date_time+"' ";
}

System.out.println("QUERY: "+query);
Statement search_stmt = conn.createStatement(); 
result=search_stmt.executeQuery(query);
crs.populate(result);
while(crs.next()){

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EventSearchResult</title>
</head>
<body>
<TABLE cellpadding="15" border="1" style="background-color: #ffffcc;">
<TR>
<TH> EID</TH>
<TH> Name </TH>
<TH> Description</TH>
<TH> Date</TH>
<TH> Address </TH>

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

