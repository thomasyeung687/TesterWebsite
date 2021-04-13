<!DOCTYPE html>
<%@page import="java.util.Date"%>
<%@page import="com.testersite.model.ClassObject"%>
<%@page import="java.util.*"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="java.sql.* "%>
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
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idadminprofiles")==null){
			response.sendRedirect("LoginAdmin.jsp");
		}
	%>
    <%
    	
    	Connection connection = DBConnection.getDBConnection();
    	String studentid = ((String) session.getAttribute("idstudentprofiles")).trim();
    	Set<String> classnames = null;
    	TreeMap<String, ClassObject> classtoprof = null;
    	System.out.println("studentid "+studentid);
    	try {
			Statement st = connection.createStatement();
			Statement st1 = connection.createStatement(); //will be used to fetch class data.
			String query = "SELECT * FROM testersitedatabase.studenttoclass WHERE idstudentprofiles = '"+studentid+"';";
			ResultSet rSet = st.executeQuery(query);
			ResultSet rSet1;
			ArrayList<String> classids = new ArrayList<>();
			while(rSet.next()){
				classids.add(rSet.getString("classid"));
			}
			classtoprof = new TreeMap<>(); //classname(CSE 214) is the key and professor name is the value
			for(String classid : classids){ //for each class that the student is in, we fetch that classes information and create a class object with it putting it into its own arraylist
				rSet = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idclass = '"+classid+"'"); //getting the row in allclasses in db (basically the class information)
				rSet.next();
				String professorid = rSet.getString("idprofessorprofiles");
				rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.professorprofiles WHERE idprofessorprofiles = '"+professorid+"';");
				rSet1.next();
				String coursename = rSet.getString("courseprefix")+rSet.getString("coursenumber");
				String professorname = rSet1.getString("name");
				
				ClassObject newclass = new ClassObject();
				newclass.setClassid(classid);
				newclass.setCoursename(coursename);
				newclass.setInstructorName(professorname);
				newclass.setdateStart(rSet.getString("datestart"));
				newclass.setdateEnd(rSet.getString("dateend"));
				
				
				classtoprof.put(coursename, newclass);
				//System.out.println(coursename +" "+ professorname);
			}
			classnames = classtoprof.keySet();
			/* for(String cname : classnames){
				System.out.println(cname+" "+classtoprof.get(cname));
			} */
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SClasses.jsp SQL ERROR");
			out.print("SClasses.jsp SQL ERROR");
		}
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
                     <h2>Classes</h2>
                    <form action="joinClassServlet" method="post">
	              		<span style =  "color: red;"> ${joinclasserror} </span><span style =  "color: blue;"> ${joinclassSuccess} </span><br>
	              		<%session.removeAttribute("joinclasserror"); 
	              		session.removeAttribute("joinclassSuccess");%>
						<input type="text" name="coursecode" placeholder="Course Code">
						<input type="submit" value="Join Class">
					</form>
					<hr>
					<form action="AdminSGradesServlet" method="get">
						<%for(String cname : classnames){
							ClassObject thisclass = classtoprof.get(cname); //getting classobject from classtoprof tree using cname as the key
						%>
							<button class="SClassesbutton" name="classid" value = "<%out.println(thisclass.getClassid());%>"> 
								<%out.println(thisclass.getCoursename());%><br>
								Instructor: <%out.println(thisclass.getInstructorName());%><br>
								DateStart-DateEnd: <%out.println(thisclass.getdateStart()+" to "+thisclass.getdateEnd());%><br>
							</button> <br>
						<%} %>
					</form>
                    </div>
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
