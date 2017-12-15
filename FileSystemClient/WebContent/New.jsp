<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<div class="login">
		Welcome
		<%
		String fooParameter = (String) session.getAttribute("fname");
	%>
		<%=fooParameter%>
		<br> <br> To read a file, enter the name of the file or
		enter * to see a list of all files
		<form method="post" action="/FileSystemClient/ReadFile">
			<input type="text" name="fn" placeholder="FileName"
				required="required" />
			<button type="submit" class="btn btn-primary btn-block btn-large">Read
				File</button>
		</form>
		<br>
		<br> To write to a file, enter the name of the file.
		 If a file does not exist, a new file will be created
		<form method="post" action="/FileSystemClient/FileWrite">
			<input type="text" name="fn" placeholder="FileName"
				required="required" />
			<button type="submit" class="btn btn-primary btn-block btn-large">Write to
				File</button>
		</form>
	</div>
	<script src="js/index.js"></script>
</body>
</html>