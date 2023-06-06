 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
	<%@include file="headerKH.jsp"%>
<head>
<title>12Shop - Cửa hàng sản phẩm</title>
<meta charset="utf-8">
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

<!-- Slick -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/assets/css/slick.min.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value ='/resource/assets/css/slick-theme.css'/>">
<link rel='stylesheet prefetch'
	href='https://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css'>
<base href="${pageContext.servletContext.contextPath}/">
<style type="text/css">
div.stars {
	width: 270px;
	display: inline-block;
}

input.star {
	display: none;
}

label.star {
	float: right;
	padding: 10px;
	font-size: 36px;
	color: #444;
	transition: all .2s;
}

input.star:checked ~ label.star:before {
	content: '\f005';
	color: #FD4;
	transition: all .25s;
}

input.star-5:checked ~ label.star:before {
	color: #FE7;
	text-shadow: 0 0 20px #952;
}

input.star-1:checked ~ label.star:before {
	color: #F62;
}

label.star:hover {
	transform: rotate(-15deg) scale(1.3);
}

label.star:before {
	content: '\f006';
	font-family: FontAwesome;
}

.comment {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.comment .icons {
	display: flex;
	align-items: flex-end;
}

.comment .icon-spacing {
	margin-left: 20px; /* Khoảng cách giữa hai biểu tượng */
}
</style>
<!--
    
TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

-->
</head>
<body>
	<!-- Start Top Nav -->

	<!-- Close Header -->

	<!-- Modal -->
	<div class="modal fade bg-white" id="templatemo_search" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="w-100 pt-1 mb-5 text-right">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<form action="" method="get"
				class="modal-content modal-body border-0 p-0">
				<div class="input-group mb-2">
					<input type="text" class="form-control" id="inputModalSearch"
						name="q" placeholder="Search ...">
					<button type="submit"
						class="input-group-text bg-success text-light">
						<i class="fa fa-fw fa-search text-white"></i>
					</button>
				</div>
			</form>
		</div>
	</div>



	<!-- Open Content -->
	<section class="bg-light">
		<form action="home/shop-single.htm" method="post"
			enctype="multipart/form-data">
			<input type="hidden" value="${product.getMaLoai()}" name="maLoai">
			<div class="container pb-5">
				<div class="row">
					<div class="col-lg-5 mt-5">
						<div class="card mb-3">
							<img class="card-img img-fluid"
								src="<c:url value ='/resource/images/${product.getAnh()}'/>"
								alt="Card image cap" id="product-detail">
						</div>

					</div>
					<!-- col end -->
					<div class="col-lg-7 mt-5">
						<div class="card">
							<div class="card-body">
								<h1 class="h2">Thông tin sản phẩm:</h1>
								<div class="row g-3">
									<h1 class="h3">${product.getTenSP()}-
										${product.getMaLoai()}</h1>
									<div class="col-md-12">
										<c:if test="${product.getCtDotGiamGia() == null}">
											<p class="h3 py-2">Giá bán:
												${product.getGia().toPlainString()} VND</p>
										</c:if>
										<c:if test="${product.getCtDotGiamGia() != null}">
											<c:set var="giam"
												value="${100-product.getCtDotGiamGia().get(0).getTiLeGiam()}" />
											<c:set var="giaGiam"
												value="${product.getGia().multiply(giam).divide(100)}" />
											<p class="h3 py-2">
												Giá bán: <s>${product.getGia().toPlainString()} VND</s>
											</p>
											<p class="h3 py-2" style="color: red;">${giaGiam.toPlainString()}
												VND Giảm: ${product.getCtDotGiamGia().get(0).getTiLeGiam()}
												%</p>

										</c:if>

									</div>
									<ul class="list-inline">
										<li class="list-inline-item">
											<h6>Thể loại:</h6>
										</li>
										<li class="list-inline-item">
											<p class="text-muted">
												<strong>${product.getMaTheLoai().getTenTL()}</strong>
											</p>
										</li>
										<li class="list-inline-item">
											<h6>Hãng:</h6>
										</li>
										<li class="list-inline-item">
											<p class="text-muted">
												<strong>${product.getMaHang().getTenHang()}</strong>
											</p>
										</li>
									</ul>


									<p>
										- CPU: ${product.getcPU()} -RAM: ${product.getRam()} <br>-
										Hardware: ${product.getHardWare()} <br>- Card màn hình:
										${product.getCard()} <br>- Màn hình:
										${product.getScreen()} <br>- Hệ điều hành:
										${product.getOs()}
									</p>
									<h6>Mô tả:</h6>
									<textarea class="form-control" readonly="readonly">${product.getMoTa()}</textarea>
									<c:choose>
										<c:when test="${product.getSanPham() == null}">
											<h6 style="color: red;">Sản phẩm hết hàng</h6>
										</c:when>
										<c:otherwise>
											<div class="row" style="margin-top: 20px;">
												<div class="col-md-2">
													<label for="productPrice">Số lượng:</label>
												</div>
												<div class="col-md-2">
													<input type="number" class="form-control" name="soLuong"
														value="1" min="1" max="${product.getSanPham().size()}">
												</div>
											</div>
										</c:otherwise>
									</c:choose>


									<div class="row pb-3" style="margin-top: 10px;">
										<div class="col d-grid">
											<button type="submit"
												class="btn btn-success btn-lg ${product.getSanPham() == null ? 'disabled':''}"
												name="btnBuy" value="buy"><i class="fas fa-shipping-fast"></i> Mua ngay</button>
										</div>
										<div class="col d-grid">
											<button type="submit"
												class="btn btn-success btn-lg ${product.getSanPham() == null ? 'disabled':''}">Thêm
												vào giỏ hàng <i class="fas fa-cart-plus"></i></button>

										</div>
									</div>
									${message}
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
	</section>
	<!-- Close Content -->
	<%
	int check = 0;
	%>
	<c:set var="khachHangEmail" value="<%=emailTmp%>" />
	<c:forEach items="${product.getBinhLuan()}" var="binhLuan">
		<c:set var="binhLuanEmail" value="${binhLuan.getEmail().getEmail()}" />
		<c:if test="${binhLuanEmail == khachHangEmail}">
			<%
			check = 1;
			%>
		</c:if>
	</c:forEach>
	<c:set var="check" value="<%=check%>" />
	<section class="bg-light" ${check != 0 ? 'hidden' : '' }
		${khachHangEmail == 'non' ? 'hidden' : '' }>
		<div class="container">
			<form action="home/thembinhluan.htm" method="post">
			<input type="hidden" value="${product.getMaLoai()}" name="maLoai">
			<input type="hidden" value="${khachHangEmail}" name="email">
				<div class="row">
					<div class="col-lg-1 mt-5">
						<div class="card mb-3">
							<img class="card-img img-fluid"
								src="<c:url value ='/resource/images/sp.png'/>"
								alt="Card image cap" id="product-detail">
						</div>
					</div>
					<div class="col-lg-11 mt-5">
						<div class="card">
							<div class="card-body">
								<div class="row">


									<div class="stars">
										<label for="diem" class="form-label">Điểm:</label> <input
											class="star star-5" id="star-5" type="radio" name="star" value="5"/>
										<label class="star star-5" for="star-5"></label> <input
											class="star star-4" id="star-4" type="radio" name="star" value="4"/>
										<label class="star star-4" for="star-4"></label> <input
											class="star star-3" id="star-3" type="radio" name="star" value="3"/>
										<label class="star star-3" for="star-3"></label> <input
											class="star star-2" id="star-2" type="radio" name="star" value="2"/>
										<label class="star star-2" for="star-2"></label> <input
											class="star star-1" id="star-1" type="radio" name="star" value="1"/>
										<label class="star star-1" for="star-1"></label>
									</div>


									<label for="binhLuan" class="form-label">Nội dung:</label>
									<div class="col-md-10">
										<textarea class="form-control" name="binhLuan"></textarea>
									</div>
									<div class="col-md-2">
										<button name="btnBinhLuan" type="submit"
											class="btn btn-success btn-lg">Bình Luận</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</section>

	<section class="bg-light">

		<div class="container">

			<c:forEach items="${product.getBinhLuan()}" var="binhLuan">

				<div class="row">

					<div class="col-lg-1 mt-5">
						<div class="card mb-3">
							<img class="card-img img-fluid"
								src="<c:url value ='/resource/images/sp.png'/>"
								alt="Card image cap" id="product-detail">
						</div>
					</div>
					<div class="col-lg-11 mt-5">

						<div class="card">
							<div class="card-body">
								<h6 class="comment">
									${binhLuan.getEmail().getEmail()}
									<c:set var="binhLuanEmail"
										value="${binhLuan.getEmail().getEmail()}" />
									<span class="icons"
										${binhLuanEmail == khachHangEmail ? '' : 'hidden'}> <a
										href="home/chinhsuabinhluan.htm?id=${ binhLuan.getIdBL()}&maLoai=${product.getMaLoai()}">
											<i class="fa fa-pencil-alt" aria-hidden="true"></i>
									</a> <span class="icon-spacing"></span> <!-- Đoạn trắng -->
									</span>
								</h6>
								<p class="py-2">
									<c:forEach begin="1" end="${binhLuan.getDiem()}" var="i">
										<i class="fa fa-star text-warning"></i>
									</c:forEach>
									<c:forEach begin="${binhLuan.getDiem()+1}" end="5" var="i">
										<i class="fa fa-star text-secondary"></i>
									</c:forEach>
								</p>
								<label for="binhLuan" class="form-label">Nội dung:</label>

								<form action="home/updatebinhluan.htm" method="post">
									<input type="hidden" value="${param.idBL}" name="id"> <input
										type="hidden" value="${product.getMaLoai()}" name="maLoai">

									<textarea class="form-control" name="binhLuan"
										${param.idBL == binhLuan.getIdBL() ? '' : 'readonly' }>${binhLuan.getMoTa()}
								</textarea>
									<div class="col text-end mt-2"
										${param.idBL == binhLuan.getIdBL() ? '' : 'hidden' }>
										<button type="submit" class="btn btn-success btn-lg px-3">
											Lưu <i class="fa fa-check" aria-hidden="true"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>

		</div>
	</section>
	<!-- Start Article -->

	<!-- End Article -->


	<!-- Start Footer -->
	<%@include file="footerKH.jsp"%>
	<!-- End Footer -->

	<!-- Start Script -->
	<script src="<c:url value='/resource/assets/js/jquery-1.11.0.min.js'/>"></script>
	<script
		src="<c:url value='/resource/assets/js/jquery-migrate-1.2.1.min.js'/>"></script>
	<script
		src="<c:url value='/resource/assets/js/bootstrap.bundle.min.js'/>"></script>
	<script src="<c:url value='/resource/assets/js/templatemo.js'/>"></script>
	<script src="<c:url value='/resource/assets/js/custom.js'/>"></script>
	<!-- End Script -->

	<!-- Start Slider Script -->
	<script src="assets/js/slick.min.js"></script>

	<!-- End Slider Script -->

</body>

</html>