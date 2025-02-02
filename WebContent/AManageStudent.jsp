<!DOCTYPE html>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="java.sql.Connection"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Simple Responsive Admin</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   <link href="assets/css/tables.css" rel="stylesheet" /> <!-- styling for table element -->
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idadminprofiles")==null){
			response.sendRedirect("LoginAdmin.jsp");
		}
		
		Connection con = DBConnection.getDBConnection();
		HashMap<Integer, String> idToNameOfStudent = new HashMap<>();
		try{
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.studentprofiles WHERE idadminprofiles = "+session.getAttribute("idadminprofiles")+";");
			while(rset.next()){
				idToNameOfStudent.put(rset.getInt("idstudentprofiles"), rset.getString("name"));
			}
		}catch(Exception e){
			System.out.println("Error Message:"+e.getMessage());
		}
		System.out.println("keys: "+idToNameOfStudent.keySet());
	%>
           
          
    <div id="wrapper">
         <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="adjust-nav">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="assets/img/logo.png" />

                    </a>
                    
                </div>
              
                <span class="logout-spn" >
                  <form action="LogoutServlet">
						<button type="submit" name="logoutfrom" value="admin">Log Out</button>
				  </form>

                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                 


                    <li >
                        <a href="AdminOptions.jsp" ><i class="fa fa-desktop "></i>Dashboard <span class="badge"></span></a>
                    </li>
                    
                    <li >
                        <a href="AManageProfessors.jsp"><i class="fa fa-edit "></i>Manage Professors<span class="badge"></span></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="AManageStudent.jsp"><i class="fa fa-edit "></i>Manage Students<span class="badge"></span></a>
                    </li>
                </ul>
                            </div>

        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Manage Students</h2>
                    </div>
                    <hr/>
                </div>
                <hr/>
                <div>
                    <form action="AdminSeeStudentServlet" method="get">
                    <table>
						<tr>
							<th>Student</th>
						</tr>
                    	<%
	                    for(int key : idToNameOfStudent.keySet()){%>
	                    		<tr><td><%-- <input type="radio" name= "profidradio" value="<%=key%>"><%out.println("   ");%> --%><button type="submit" name="studid" value="<%=key%>"><%out.println(idToNameOfStudent.get(key));%></button></td></tr>
	                    <%}%>
	                    
	                   </hr><br>
	                </table>
	                <input type="submit" name="add" value="Add New Student(s)">
                    </form>
               </div>          
            </div>    
        </div>
        
    <div class="footer">
      
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>
