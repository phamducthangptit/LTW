<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Tạo đợt giảm giá</title>
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
		<div class="container mt-4" ${DGG.ngayBD.toString() != null ? 'hidden' : ''}>
		<h2 style="text-align: center;">Tạo đợt giảm giá</h2>
		<form action="taodotgiamgia.htm" method="post">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<div class="mb-3">
						<label for="ngayBD" class="form-label">Ngày bắt đầu</label> 
						<input type="date" class="form-control" name="ngayBD" required="required" value="${DGG.ngayBD}">
					</div>

					<div class="mb-3">
						<h6 style="color: red;">${ErrorGG }</h6>
						<label for="ngayKT" class="form-label">Ngày kết thúc</label> 
						<input type="date" class="form-control" name="ngayKT" required="required" value="${DGG.ngayKT}">
					</div>

					<div class="mb-3">
						<label for="ten" class="form-label">Mô tả</label> 
						<textarea name ="moTa" rows="5" cols="70"></textarea>
					</div>
					<div class="mb-3">
						<label for="ten" class="form-label">Số lượng loại sản phẩm được giảm giá:</label> 
						<textarea name ="moTa" rows="5" cols="70"></textarea>
					</div>
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4"
							style="width: 170px;">Tạo đợt giảm giá</button>
					</div>

				</div>
			</div>
		</form>
	</div>
	<div class="container mt-4" ${DGG.ngayBD.toString() != null ? '' : 'hidden'}>
		<h4 style="text-align: center;">Đợt giảm giá bắt đầu ngày: ${DGG.ngayBD }, kết thúc ngày: ${DGG.ngayKT }</h4>
	</div>
</body>
</html>