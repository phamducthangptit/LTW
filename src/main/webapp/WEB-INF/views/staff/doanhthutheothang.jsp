<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Doanh thu tháng</title>
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

.custom-select {
	width: 200px;
	height: 40px;
	padding: 8px;
	font-size: 16px;
	border: 1px solid #ccc;
	border-radius: 4px;
	background-color: #fff;
	color: #333;
}

.custom-select:focus {
	outline: none;
	border-color: #6c757d;
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
</style>
</head>

<body>
<%@include file ="headerNV.jsp" %>
	<section class="bg-light">
	
		<h2 style="text-align: center;" ${thang == null ? '' : 'hidden' }>Doanh thu tháng</h2>
		<div class="container mt-4">
			<div class="search-container mb-2 mt-2">
				<form class="search-form" action="doanhthutheothang.htm"
					method="post">
					<select name="thang" class="custom-select" required="required">
						<option value="1" ${thang == 1 ? 'selected' : '' }>Tháng
							1</option>
						<option value="2" ${thang == 2 ? 'selected' : '' }>Tháng
							2</option>
						<option value="3" ${thang == 3 ? 'selected' : '' }>Tháng
							3</option>
						<option value="4" ${thang == 4 ? 'selected' : '' }>Tháng
							4</option>
						<option value="5" ${thang == 5 ? 'selected' : '' }>Tháng
							5</option>
						<option value="6" ${thang == 6 ? 'selected' : '' }>Tháng
							6</option>
						<option value="7" ${thang == 7 ? 'selected' : '' }>Tháng
							7</option>
						<option value="8" ${thang == 8 ? 'selected' : '' }>Tháng
							8</option>
						<option value="9" ${thang == 9 ? 'selected' : '' }>Tháng
							9</option>
						<option value="10" ${thang == 10 ? 'selected' : '' }>Tháng
							10</option>
						<option value="11" ${thang == 11 ? 'selected' : '' }>Tháng
							11</option>
						<option value="12" ${thang == 11 ? 'selected' : '' }>Tháng
							12</option>
					</select> <select name="nam" class="custom-select" required="required">
						<option value="2023" ${nam == 2023 ? 'selected' : '' }>Năm
							2023</option>
						<option value="2024" ${nam == 2024 ? 'selected' : '' }>Năm
							2024</option>
						<option value="2025" ${nam == 2025 ? 'selected' : '' }>Năm
							2025</option>
						<option value="2026" ${nam == 2026 ? 'selected' : '' }>Năm
							2026</option>
						<option value="2027" ${nam == 2027 ? 'selected' : '' }>Năm
							2027</option>
					</select>
					<button type="submit">In doanh thu</button>
					<button type="submit" ${XF == 0 ? 'hidden':''} name="XuatExcel" value="XuatExcel">Xuất file Excel</button>
				</form>
			</div>
			<form>
			<h3 style="text-align: center;" ${thang == null ? 'hidden' : '' }>Chi tiết doanh thu tháng ${thang } năm ${nam }</h3>
				<table
				
					class="table table-light table-striped table-hover bordered-dark mt-1"
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
					<%int count = 1; %>
						<c:forEach items="${doanhThuTungNgay}" var="dt">
							<tr class="body">
								<td><%=count %></td>
								<td>${dt.maLoai }</td>
								<td>${dt.tenSP }</td>
								<td>${dt.giaBan }VND</td>
								<td>${dt.giamGia }%</td>
								<td>${dt.getGiaBanRa() }VND</td>
							</tr>
							<%count++; %>
						</c:forEach>
						
						<tr class="separator">
							<td colspan="6">------------------------------------------------------</td>
						</tr>
						<tr class="footer">
							<td colspan="5">Tổng số lượng sản phẩm bán ra:</td>
							<td>${doanhThuTungNgay.size()}</td>
						</tr>
						<tr class="footer">
							<td colspan="5">Tổng tiền:</td>
							<td><c:set var="totalPrice" value="0" /> <c:forEach
									items="${doanhThuTungNgay}" var="dt">
									<c:set var="totalPrice"
										value="${totalPrice + dt.getGiaBanRa()}" />
								</c:forEach> ${totalPrice} VND</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</section>
	<%@include file = "footerQL.jsp" %>
</body>
</html>