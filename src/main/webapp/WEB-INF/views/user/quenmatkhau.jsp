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
<title>Quên mật khẩu</title>
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
		<form action="quenmatkhau.htm" method="post">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<h3 style="text-align: center;">Lấy lại mật khẩu</h3>
					<div class="mb-3">
						<label for="email" class="form-label">Nhập email đã đăng kí:</label> 
						<input type="email" class="form-control" name="email" value="${email }">
					</div>
					<div class="button-container" ${email == null ? '' : 'hidden'}>
						<button type="submit" class="btn btn-success mb-4" 
						name="guiMa" value="guiMa" style="width: 170px;">Gửi mã</button>
					</div>
					<div class="mb-3" ${email == null ? 'hidden' : ''}>
						<h5 style="color: red;">${ErrorMa }</h5>
						<label for="maXN" class="form-label">Nhập mã:</label> 
						<input type="text" class="form-control" name="maXN" value="${maXN}">
					</div>
					<div class="button-container" ${email == null || maXN != null ? 'hidden' : ''} >
						<button type="submit" class="btn btn-success mb-4"
							name="xacNhanMa" value="xacNhanMa" style="width: 170px;">Xác nhận</button>
					</div>
					<div class="mb-3" ${maXN == null ? 'hidden' : ''}>
						<label for="passN" class="form-label">Nhập mật khẩu mới:</label> 
						<input type="password" class="form-control" name="passN">
					</div>
					<div class="button-container" ${maXN == null ? 'hidden' : ''}>
						<button type="submit" class="btn btn-success mb-4"
							name="xacNhanMk" value="xacNhanMk" style="width: 210px;">Xác nhận mật khẩu mới</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@include file="../sp/footerKH.jsp"%>
</body>
</html>