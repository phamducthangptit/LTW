<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<title>Đổi mật khẩu</title>
<style>
.button-container {
	display: flex;
	justify-content: center;
}
</style>
</head>
<body>
	<%@include file="../sp/headerKH.jsp"%>
	<div class="container mt-4">
		<form action="doimatkhauuser.htm" method="post">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<h3 style="text-align: center;">Đổi mật khẩu</h3>
					<div class="mb-3">
						<h6 style="color: red;">${ErrorMkCu }</h6>
						<label for="mkCu" class="form-label">Nhập mật khẩu cũ:</label> 
						<input type="password" class="form-control" name="mkCu" required="required">
					</div>
					<div class="mb-3">
					<h6 style="color: red;">${ErrorMkMoi }</h6>
						<label for="mkMoi1" class="form-label">Nhập mật khẩu mới:</label> 
						<input type="password" class="form-control" name="mkMoi1" required="required">
					</div>
					<div class="mb-3">
						<label for="mkMoi2" class="form-label">Nhập lại mật khẩu mới:</label> 
						<input type="password" class="form-control" name="mkMoi2" required="required">
					</div>
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4" style="width: 180px;">Đổi mật khẩu</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@include file="../sp/footerKH.jsp"%>
</body>
</html>