<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Chi tiết nhà cung cấp</title>
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
<%@include file="headerNV.jsp"%>
</head>

<body>

<section class="bg-light">
	<h2 style="text-align: center;">Chi tiết nhà cung cấp</h2>
		<div class="container mt-4">
		<form action="ctncc.htm" method="post">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<div class="mb-3"> 
						<input hidden="hidden" name="maNCC" value="${nhaCC.maNCC }">
					</div>

					<div class="mb-3">
						<h6 style="color: red;">${ErrorTenNCC}</h6>
						<label for="tenNCC" class="form-label">Tên nhà cung cấp</label> 
						<input type="text" class="form-control" name="tenNCC" required="required" value="${nhaCC.tenNCC}" 
						${ tontaiDDH == 0 ? '' :'readonly'}>
					</div>

					<div class="mb-3">
						<label for="diaChi" class="form-label">Địa chỉ</label> 
						<input type="text" class="form-control" name="diaChi" required="required" value="${nhaCC.diaChi}">
					</div>
					
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> 
						<input type="email" class="form-control" name="email" required="required" value="${nhaCC.email}">
					</div>
					<div class="mb-3">
						<label for="SDT" class="form-label">Số điện thoại</label> 
						<input type="tel" class="form-control" name="SDT" required="required" value="${nhaCC.sdt}">
					</div>
					
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4"
							style="width: 150px;">Lưu</button>
					</div>

				</div>
			</div>
		</form>
	</div>
	</section>
	<%@include file="footerQL.jsp" %>	
</body>
</html>