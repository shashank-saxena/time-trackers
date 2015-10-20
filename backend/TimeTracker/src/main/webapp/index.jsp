<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="rest/employee/register">Add Employee</a>
    <p><a href="rest/employee/login">Login Employee</a>    
    <form action="rest/employee/excelExport" method="post">
		<h1 align="center">Export Excel Sheet</h1>
		<table border="1" align="center">
			<tr>
				<td>Employee Id</td>
				<td><input type="text" name="empId"></td>
			</tr>
			<tr>
				<td>Month</td>
				<td><input type="text" name="month"></td>
			</tr>
			<tr>
				<td>Year</td>
				<td><input type="text" name="year"></td>
			</tr>			
			<tr>
				<td colspan="2" align="right"><input type="submit"
					value="Submit"></td>
			</tr>
		</table>
	</form>    
</body>
</html>
