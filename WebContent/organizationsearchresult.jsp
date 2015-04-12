<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*"%>
    <%@ page import="com.sun.rowset.CachedRowSetImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
Connection conn = null;
ResultSet result = null;
CachedRowSetImpl crs = new CachedRowSetImpl();

String member_count= request.getParameter("member_count"); //DOES THIS WORK!!
String position_count= request.getParameter("position_count"); //DOES THIS WORK!!
String domain=request.getParameter("domain");
String name = request.getParameter("name"); 

try{ 
	conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
}catch(SQLException e){ 
	conn.close();

}
String query="SELECT * FROM ORGANIZATION WHERE ";
boolean first=true;
if (!name.equals("")){
	query+="NAME='"+name+"' ";
	first=false;
}
if(!position_count.equals("")){
	if(!first){
		query+="AND POSITION_COUNT="+position_count+" ";
	} else {
		query+="POSITION_COUNT="+position_count+" ";
		first=false;
	}
}
if(!domain.equals("")){
	if(!first){
		query+="AND DOMAIN='"+domain+"' ";
	} else {
		query+="DOMAIN='"+domain+"' ";
		first=false;
	}
}
if(!member_count.equals("")){
	if(!first){
		query+="AND MEMBER_COUNT="+member_count+" ";
	} else {
		query+="MEMBER_COUNT="+member_count+" ";
		first=false;
	}
}
if(first){
	query="SELECT * FROM ORGANIZATION";
}

System.out.println("QUERY: "+query);
Statement search_stmt = conn.createStatement(); 
result=search_stmt.executeQuery(query);
crs.populate(result);
while(result.next()){
	String result_oid=result.getString(1);
	String result_name=result.getString(2);
	String result_domain=result.getString(3);
	String result_pos_count=result.getString(4);
	int result_member_count=result.getInt(5);

	System.out.println(result_oid);
	System.out.println(result_name);
	System.out.println(result_domain);
	System.out.println(result_pos_count);
	System.out.println(result_member_count);
}
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
<TH> OID</TH>
<TH> Name </TH>
<TH> Domain</TH>
<TH> Position Count</TH>
<TH> Member Count </TH>

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

