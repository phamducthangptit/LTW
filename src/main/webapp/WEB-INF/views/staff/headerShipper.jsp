<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ptithcm.model.NhanVien"%>
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value='/resource/assets/img/logo.png'/>">
<nav
	class="navbar navbar-expand-lg bg-dark navbar-light d-none d-lg-block"
	id="templatemo_nav_top">
	<div class="container text-light">
		<div class="w-100 d-flex justify-content-between">
			<div>
				<i class="fa fa-envelope mx-2"></i> <a
					class="navbar-sm-brand text-light text-decoration-none"
					href="mailto:banlaptop12ptit@gmail.com">banlaptop12ptit@gmail.com</a> <i
					class="fa fa-phone mx-2"></i> <a
					class="navbar-sm-brand text-light text-decoration-none"
						href="tel:028 3730 6600">028 3730 6600</a>
			</div>
			<div>
				<a class="text-light" href="https://fb.com/templatemo"
					target="_blank" rel="sponsored"><i
					class="fab fa-facebook-f fa-sm fa-fw me-2"></i></a> <a
					class="text-light" href="https://www.instagram.com/"
					target="_blank"><i class="fab fa-instagram fa-sm fa-fw me-2"></i></a>
				<a class="text-light" href="https://twitter.com/" target="_blank"><i
					class="fab fa-twitter fa-sm fa-fw me-2"></i></a> <a class="text-light"
					href="https://www.linkedin.com/" target="_blank"><i
					class="fab fa-linkedin fa-sm fa-fw"></i></a>
			</div>
		</div>
	</div>
</nav>
<!-- Close Top Nav -->


<!-- Header -->
<nav class="navbar navbar-expand-lg navbar-light shadow">
	<div
		class="container d-flex justify-content-between align-items-center">

		<a class="navbar-brand text-success logo h1 align-self-center"
				href="#"> 12Shop </a>

		<button class="navbar-toggler border-0" type="button"
			data-bs-toggle="collapse" data-bs-target="#templatemo_main_nav"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div
			class="align-self-center collapse navbar-collapse flex-fill  d-lg-flex justify-content-lg-between"
			id="templatemo_main_nav">
			<div class="flex-fill">
				<ul class="nav navbar-nav d-flex justify-content-between mx-lg-auto" style="max-width: 650px;">
					<li class="nav-item"><a class="nav-link" href="donhangchuagiao.htm">Đơn Hàng Chưa Nhận Giao</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="donhangdangnhan.htm">Đơn Hàng Đang Nhận Giao</a>
					</li>
			
				</ul>
			</div>
			<div class="navbar align-self-center d-flex">
				<div class="d-lg-none flex-sm-fill mt-3 mb-4 col-7 col-sm-auto pr-3">
					<div class="input-group">
						<input type="text" class="form-control" id="inputMobileSearch"
							placeholder="Search ...">
						<div class="input-group-text">
							<i class="fa fa-fw fa-search"></i>
						</div>
					</div>
				</div>
				
				<%
				String hr = "";
				Object ob = session.getAttribute("user2");
				
				hr = "thongtincanhannv.htm";
				NhanVien nv = new NhanVien();
				nv = (NhanVien) ob;
				if (nv == null) {
					hr = "dangnhap.htm";
				}
				%>
				<a class="nav-icon position-relative text-decoration-none"
					href="<%=hr%>"> <i class="fa fa-fw fa-user text-dark mr-3"></i>
					<span
					class="position-absolute top-0 left-100 translate-middle badge rounded-pill bg-light text-dark"></span>
				</a>
				<%
				if (nv != null) {
				%>
				<a class="nav-icon position-relative text-decoration-none"
					href="dangxuat.htm"> <i
					class="fa fa-fw fa-sign-out-alt text-dark mr-3"></i> <span
					class="position-absolute top-0 left-100 translate-middle badge rounded-pill bg-light text-dark"></span>
				</a>
				<%
				}
				%>
			</div>
		</div>

	</div>
</nav>
<!-- Close Header -->