<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Đăng nhập</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="<c:url value='/resource/images/icons/favicon.ico'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/vendor/bootstrap/css/bootstrap.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/fonts/font-awesome-4.7.0/css/font-awesome.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/vendor/animate/animate.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/vendor/css-hamburgers/hamburgers.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/vendor/select2/select2.min.css'/>">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/util.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/main.css'/>">
<!--===============================================================================================-->
</head>
<body>

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-pic js-tilt" data-tilt>
					<img src="<c:url value='/resource/images/img-01.png'/>" alt="IMG">
				</div>

				<form class="login100-form validate-form" action="dangnhap.htm" method="post">
					<span class="login100-form-title"> Member login </span>
					<h6 style="color: red; margin-left: 15px; margin-bottom: 5px">${ErrorLogin }</h6>
					<div class="wrap-input100 validate-input" data-validate="Username is required">
						<input class="input100" type="text" name="username" value="${username }"
							 placeholder="Tên đăng nhập(Email)">
						<span class="focus-input100"></span> <span class="symbol-input100">
							<i class="fa fa-user-o" aria-hidden="true"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="Password is required">
						<input class="input100" type="password" name="pass"
							placeholder="Mật khẩu"> <span class="focus-input100"></span>
						<span class="symbol-input100"> <i class="fa fa-lock"
							aria-hidden="true"></i>
						</span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Đăng nhập</button>
					</div>

					<div class="text-center p-t-12">
						<span class="txt1"> Quên </span> <a class="txt2" href="quenmatkhau.htm"> Mật
							khẩu? </a>
					</div>

					<div class="text-center p-t-136">
						<a class="txt2" href="dangkitaikhoan.htm"> Đăng kí tài khoản <i
							class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>




	<!--===============================================================================================-->
	<script
		src="<c:url value='/resource/vendor/jquery/jquery-3.2.1.min.js'/>"></script>
	<!--===============================================================================================-->
	<script src="<c:url value='/resource/vendor/bootstrap/js/popper.js'/>"></script>
	<script
		src="<c:url value='/resource/vendor/bootstrap/js/bootstrap.min.js'/>"></script>
	<!--===============================================================================================-->
	<script src="<c:url value='/resource/vendor/select2/select2.min.js'/>"></script>
	<!--===============================================================================================-->
	<script src="<c:url value='/resource/vendor/tilt/tilt.jquery.min.js'/>"></script>
	<script>
		$('.js-tilt').tilt({
			scale : 1.1
		})
	</script>
	<!--===============================================================================================-->
	<script src="<c:url value='/resource/js/main.js'/>"></script>

</body>
</html>