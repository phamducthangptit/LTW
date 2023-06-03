<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Thông tin cá nhân</title>
<style>
.button-container {
	display: flex;
	justify-content: center;
}
</style>
<%@include file="headerNV.jsp"%>
</head>
<body>
	<div class="container mt-4">
		<form action="luuthongtinnv.htm" method="post">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<h3 style="text-align: center;">Thông tin cá nhân</h3>


					<div class="mb-3">
						<label for="ho" class="form-label">Họ</label> 
						<input hidden="hidden" value="${nhanVien.maNV }" name="maNV"/>
						<input type="text"
							class="form-control" name="ho" value="${nhanVien.ho}"
							required="required">
					</div>

					<div class="mb-3">
						<label for="ten" class="form-label">Tên</label> <input type="text"
							class="form-control" name="ten" value="${nhanVien.ten}"
							required="required">
					</div>

					<div class="mb-3">
						<label for="ngaySinh" class="form-label">Ngày sinh</label> <input
							type="date" class="form-control" name="ngaySinh"
							value="${nhanVien.ngaySinh}">
					</div>

					<div class="mb-3">
						<label for="diaChi" class="form-label">Địa chỉ</label> <input
							type="text" class="form-control" name="diaChi"
							value="${nhanVien.diaChi}" required="required">
					</div>

					<div class="mb-3">
						<label for="sdt" class="form-label">Số điện thoại</label> <input
							type="tel" class="form-control" name="sdt"
							value="${nhanVien.sDT}" required="required">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> <input
							type="text" class="form-control" name="email"
							value="${nhanVien.email}">
					</div>
					<div class="mb-3">
						<label for="pass" class="form-label">Mật khẩu</label> <input
							type="password" class="form-control" name="pass"
							value="${nhanVien.password}" required="required">
					</div>
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4"
							style="width: 170px;">Lưu thông tin</button>
					</div>

				</div>
			</div>
		</form>
	</div>
	<%-- <%@include file="footer.jsp"%> --%>
</body>
</html>