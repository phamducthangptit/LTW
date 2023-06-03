<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Doanh thu theo ngày</title>
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

.search-form input[type="date"] {
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
<%@include file="headerNV.jsp" %>
	<section class="bg-light">
		<h2 style="text-align: center;" ${ngayTK == null ? '' : 'hidden' }>Doanh thu theo ngày</h2>
		<div class="container mt-4">
			<div class="search-container">
				<form class="search-form" action="doanhthutheongay.htm"
					method="post">
					<input type="date" id="ngayTK" name="ngayTK" value="${ngayTK }" required="required">
					<button type="submit">In doanh thu</button>
					<a href="doanhthutheothang.htm" class="button-link">Doanh thu theo tháng</a>
					<button type="submit" ${XF == 0 ? 'hidden':''} name="XuatExcel" value="XuatExcel">Xuất file Excel</button>
					
				</form>
			</div>
			<form>
			<h2 style="text-align: center;" ${ngayTK == null ? 'hidden' : '' }>Chi tiết doanh thu ngày ${ngayTK }</h2>
				<table
					class="table table-light table-striped table-hover bordered-dark"
					style="text-align: center;">
					<thead>
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã loại</th>
							<th scope="col">Tên sản phẩm</th>
							<th scope="col">Giá bán</th>
							<th scope="col">Khuyến mãi</th>
							<th scope="col">Giá bán ra</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${doanhThuNgay}" var="dt">
							<tr class="body">
								<td>${dt.id}</td>
								<td>${dt.maLoai }</td>
								<td>${dt.tenSP }</td>
								<td>${dt.giaBan } VND</td>
								<td>${dt.giamGia }%</td>
								<td>${dt.getGiaBanRa() } VND</td>
							</tr>
						</c:forEach>
						<tr class="separator">
							<td colspan="6">------------------------------------------------------</td>
						</tr>
						<tr class="footer">
							<td colspan="5">Tổng số lượng sản phẩm bán ra:</td>
							<td>${doanhThuNgay.size()}</td>
						</tr>
						<tr class="footer">
							<td colspan="5">Tổng tiền:</td>
							<td><c:set var="totalPrice" value="0" /> <c:forEach
									items="${doanhThuNgay}" var="dt">
									<c:set var="totalPrice"
										value="${totalPrice + dt.getGiaBanRa()}" />
								</c:forEach> ${totalPrice} VND</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</section>
	<%@include file="footerQL.jsp" %>
</body>
</html>