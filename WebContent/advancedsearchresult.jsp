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

String organization_name = request.getParameter("list_org");
System.out.println("org " + organization_name);



try{ 
	conn = DriverManager.getConnection("jdbc:mysql://cs4111.cebi8wvrzeok.us-west-2.rds.amazonaws.com:3306/cs4111","anm2147","databases");
}catch(SQLException e){ 
	conn.close();
}
String query="SELECT s1.UNI, s1.NAME, s1.MAJOR, s1.SCHOOL, s1.YEAR, o1.NAME AS 'ORGANIZATION NAME', p1.CURRENT_POSITION, p1.START, p1.END FROM STUDENT s1, ORGANIZATION o1, PART_OF p1 WHERE p1.OID=o1.OID AND p1.UNI=s1.UNI ";

if(!uni.equals("")){
	query+="AND s1.UNI='"+uni+"'";
	Statement search_stmt = conn.createStatement(); 
	 result=search_stmt.executeQuery(query);
	 crs.populate(result);
	System.out.println("QUERY: "+query);

	while(result.next()){
		String result_uni=result.getString(1);
		String result_name=result.getString(2);
		String result_major=result.getString(3);
		String result_school=result.getString(4);
		int result_year=result.getInt(5);
		String result_org_name=result.getString(6);
		String result_curpos=result.getString(7);
		String result_start=result.getString(8);
		String result_end=result.getString(9);							

		System.out.println(result_uni);
		System.out.println(result_name);
		System.out.println(result_major);
		System.out.println(result_year);
		System.out.println(result_school);
		System.out.println(result_org_name);
		System.out.println(result_curpos);
		System.out.println(result_start);
		System.out.println(result_end);
	}
} else {
	if (!school.equals("")){
		query+="AND s1.SCHOOL='"+school+"' ";

	}
	if(!year.equals("")){
		query+="AND s1.YEAR="+year+" ";
	}
	if(!organization_name.equals("")){

		query+="AND o1.NAME='"+organization_name+"' ";

	}
	if(!school.equals("")){

		query+="AND s1.SCHOOL='"+school+"' ";

	}
	if(!major.equals("")){

		query+="AND s1.MAJOR='"+major+"' ";

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
		String result_org_name=result.getString(6);
		String result_curpos=result.getString(7);
		String result_start=result.getString(8);
		String result_end=result.getString(9);							

		System.out.println(result_uni);
		System.out.println(result_name);
		System.out.println(result_major);
		System.out.println(result_year);
		System.out.println(result_school);
		System.out.println(result_org_name);
		System.out.println(result_curpos);
		System.out.println(result_start);
		System.out.println(result_end);
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
<TH> Org Name </TH>
<TH> current_pos </TH>
<TH> start </TH>
<TH> end </TH>

</TR>
<TR>
<TD><%=crs.getString(1)%></TD>
<TD><%=crs.getString(2)%></TD>
<TD><%=crs.getString(3)%></TD>
<TD><%=crs.getString(4)%></TD>
<TD><%=crs.getString(5)%></TD>
<TD><%=crs.getString(6)%></TD>
<TD><%=crs.getString(7)%></TD>
<TD><%=crs.getString(8)%></TD>
<TD><%=crs.getString(9)%></TD>









</TR>
</TABLE>



<%  } 
%>	
<p> 
Click <a href = "home.jsp"> here </a> to go back to home page
</p>
</body>
</html>

