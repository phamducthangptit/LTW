<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>
    <title>Zay Shop - Product Detail Page</title>
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

    <!-- Slick -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/resource/assets/css/slick.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value ='/resource/assets/css/slick-theme.css'/>">
    <base href="${pageContext.servletContext.contextPath}/">
    
<!--
    
TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

-->
</head>

<body>
     <%@include file="headerKH.jsp"%>

    <!-- Open Content -->
    <section class="bg-light">
        <div class="container pb-5">
        <c:if test="${cart == null}">
        <p>Chưa có sản phẩm nào ....</p>
        </c:if>
         <c:if test="${cart != null}">
        <c:forEach items="${cart}" var="product">
        	<div class="row">
        		
	                <div class="col-lg-1 mt-5">
	                <div class="card">
	                    <div class="card mb-3">
	                        <a href="/BanLaptop/home/shop-single.htm?lsp=${product.getLsp().getMaLoai()}">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${product.getLsp().getAnh()}'/>">
                                </a>
	                    </div> 
	                    </div>
	                </div>
	                <div class="col-lg-11 mt-5">
	                	<div class="card">
	                	<div class="card-body">
	                	<div class="row">
		                		<div class="col-md-5">
		                		<a href="/BanLaptop/home/shop-single.htm?lsp=${product.getLsp().getMaLoai()}" class="h2 text-decoration-none text-dark">- Tên: ${product.getLsp().getMaLoai()}-${product.getLsp().getTenSP()}</a>
			  					<p>
			  					- Đơn giá: 
			  					<c:if test="${product.getLsp().getCtDotGiamGia() == null}">
                                	${product.getLsp().getGia().toPlainString()} VND 
                                	<c:set var="soTien" value ="${product.getLsp().getGia()*product.getSoLuong()}"/>
                                </c:if>
                                <c:if test="${product.getLsp().getCtDotGiamGia() != null}">
                                <c:set var="giam" value ="${100-product.getLsp().getCtDotGiamGia().get(0).getTiLeGiam()}"/>
                                <c:set var="giaGiam" value ="${product.getLsp().getGia().multiply(giam).divide(100)}"/>
                                	<s>${product.getLsp().getGia().toPlainString()} VND</s>
                                	<h6  style="color:red;">${giaGiam.toPlainString()} VND</h6>
                                	<c:set var="soTien" value ="${giaGiam*product.getSoLuong()}"/>
                                </c:if>
			  					</p>
			  					</div>
			  					<div class="col-md-5">
			  					Số lượng : ${product.getSoLuong()} <br>
			  					Tổng tiền: ${soTien.toPlainString()} VND
			  					</div>
			  					<div class="col-md-2">
			  					<h6>Trạng thái:</h6><br>
			  					<c:if test="${product.getCheck() == 1}">
                                	Chờ duyệt đơn
                                </c:if>
                                <c:if test="${product.getCheck() == 2}">
                                	Đang vận chuyển
                                </c:if>
                                <c:if test="${product.getCheck() == 3}">
                                	Hoàn thành
                                </c:if>
			  					</div>
		  					</div>
		  					</div>
	                	</div>
                	</div>
                </div>
        	</c:forEach>
        	</c:if>
       </div>
    </section>
    <!-- Close Content -->

    <!-- Start Article -->
    
    <!-- End Article -->


     <%@include file="footerKH.jsp"%>
    <!-- End Footer -->

    <!-- Start Script -->
    <script src="assets/js/jquery-1.11.0.min.js"></script>
    <script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/templatemo.js"></script>
    <script src="assets/js/custom.js"></script>
    <!-- End Script -->

    <!-- Start Slider Script -->
    <script src="assets/js/slick.min.js"></script>
    
    <!-- End Slider Script -->

</body>

</html>