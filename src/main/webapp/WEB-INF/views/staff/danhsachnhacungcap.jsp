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
	href="<c:url value='/resource/assets/img/logo.png'/>">

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
.search-container {
  text-align: left;
  padding: 10px;
}

.search-form {
  display: inline-block;
}

.search-form input[type="text"] {
  padding: 10px;
  border: none;
  border-radius: 5px;
}

.search-form button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  margin-left: 10px;
  cursor: pointer;
}

.search-form button:hover {
  background-color: #45a049;
}
.button-link {
        display: inline-block;
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        text-decoration: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-weight: bold;
    }
    .button-link:hover {
        color: white;
        box-shadow: 0 0 5px rgba(0, 0, 255, 0.5);
    }
</style>
</head>
<body>
	<%@include file="headerNV.jsp"%>
	<section class="bg-light">
	<h2 style="text-align: center;">Danh sách nhà cung cấp</h2>
	<div class="container mt-4">
		<div class="search-container">
		  <form class="search-form" action="timkiemncc.htm" method="post">
		    <input type="text" id="searchInput" name ="idncc" placeholder="Nhập mã..." required="required">
		    <button type="submit">Tìm kiếm</button>
		    <a href="themnhacungcap.htm" class="button-link">Thêm mới</a>
		  </form>
		</div>
		<form action="" method="post">
			<table class="table table-light table-striped table-hover bordered-dark"
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
							<td><a href="ctncc.htm?id=${NCC.maNCC}"> <i
									class="fa fa-pencil-alt"></i>
							</a></td>
							<td ><a href="deletencc.htm?id=${NCC.maNCC}" onclick="return confirmDelete(event)"
								${NCC.getDonDatHang().size() == 0 ? '' : 'hidden' }> <i
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
	</section>
	<%@include file="footerQL.jsp" %>
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