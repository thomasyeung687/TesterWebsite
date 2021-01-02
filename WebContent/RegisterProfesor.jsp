
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="assets/css/loginregister.css">
<style>
.center{
	top:50% !important;
}
form .text_field{
		margin:20px 0 !important;
}
</style>
</head>
	<script type="text/javascript">
		function checkpasswordandemail(){
			var email =        document.f1.email.value;
			var confirmemail = document.f1.confirmemail.value;
			var p =            document.f1.password.value;
			var cp =           document.f1.confirmpassword.value;
			const emailformat = /\S+@\S+\.\S+/;
			if(email !== confirmemail){
				alert("Emails must be the same!")
				return false;
			}else if(p !== cp){
				alert("Passwords must be the same!")
				return false;
			}else if (!(emailformat.test(email))){
				alert("Please enter a valid email!")
				return false;
			}else{
				return true;
			}
			
		}
	</script>
	
<body>
	<div class="center">
	<h2>Professor Registry</h2>
	<form name="f1" onsubmit="return checkpasswordandemail()" action="ProfRegisterServlet" method="post">
		<div class="text_field">
			<input type="text" name="name" placeholder="Name" required><br>
		</div>
		<div class="text_field">
			<input type="text" name="email" placeholder="Email" required><br>
		</div>
		<div class="text_field">
			<input type="text" name="confirmemail" placeholder="ConfirmEmail" required><br>
		</div>
		<div class="text_field">
			<input type="text" name="username" placeholder="Username" required><br>
		</div>
		<div class="text_field">
			<input type="password" name="password" placeholder="Password" required><br>
		</div>
		<div class="text_field">
			<input type="password" name="confirmpassword" placeholder="Password" required><br>
		</div>
			<input type="submit" value = "Create Account">
		</form>
		<div class="loginStudent">Are you a member? <a href="LoginProf.jsp">Login Instead</a></div>
	</div>
</body>
</html>