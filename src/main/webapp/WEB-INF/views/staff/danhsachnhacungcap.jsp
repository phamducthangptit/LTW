<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Danh sách nhà cung cấp</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon"
	href="<c:url value='/resource/assets/img/apple-icon.png'/>">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value='/resource/assets/img/favicon.ico'/>">

<link rel="stylesheet"
	href="<c:url value='/resource/assets/css/bootstrap.min.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resource/assets/css/templatemo.css'/>">
<link rel="stylesheet"
	href="<c:url value='/resource/assets/css/custom.css'/>">

<!-- Load fonts style after rendering the layout styles -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="stylesheet"
	href="<c:url value='/resource/assets/css/fontawesome.min.css'/>">
<style>
.button-container {
	display: flex;
	justify-content: center;
}
</style>
</head>

<body>
	<h2 style="text-align: center;">Danh sách nhà cung cấp</h2>
	<div class="container mt-4">
		<form action="" method="post">
			<table class="table table-success table-striped"
				style="text-align: center;">
				<thead>
					<tr>
						<th scope="col">STT</th>
						<th scope="col">Mã NCC</th>
						<th scope="col">Tên NCC</th>
						<th scope="col">Địa chỉ</th>
						<th scope="col">Email</th>
						<th scope="col">SDT</th>
						<th scope="col"></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<%
					int count = 1;
					%>
					<c:forEach items="${listNCC}" var="NCC">
						<tr class="body">
							<td><%=count%></td>
							<td>${NCC.maNCC }</td>
							<td>${NCC.tenNCC }</td>
							<td>${NCC.diaChi }</td>
							<td>${NCC.email }</td>
							<td>${NCC.sdt }</td>
							<td><a href="ctdotgg.htm?id=${DGG.maDot}"> <i
									class="fa fa-pencil-alt"></i>
							</a></td>
							<td><a href="deletencc.htm?id=${NCC.maNCC}"
								onclick="return confirmDelete(event)"> <i
									class="fa fa-trash"></i>
							</a></td>

						</tr>
						<%
						count += 1;
						%>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
<script>
	function confirmDelete(event) {
		event.preventDefault(); // ngăn chặn trình duyệt chuyển hướng đến đường dẫn của thẻ a
		var link = event.currentTarget.href; // lấy đường dẫn từ thuộc tính href của thẻ a
		if (window.confirm("Bạn có chắc chắn muốn xóa nhà cung cấp này?")) {
			// Nếu người dùng xác nhận, gửi yêu cầu xóa đến server
			window.location = link;
		} else {
			// Nếu người dùng huỷ, không làm gì cả
		}
	}
</script>

</html>