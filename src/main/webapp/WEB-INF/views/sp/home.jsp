<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Trang chủ</title>
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
	 <%@include file="headerKH.jsp"%>
    <!--
    
TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

-->
</head>

<body>
    <!-- Start Top Nav -->




    <!-- Start Content -->
    <!-- Modal -->
    


	<c:if test="${product1 != null}">
    <!-- Start Banner Hero -->
    <div id="template-mo-zay-hero-carousel" class="carousel slide" data-bs-ride="carousel">
        <ol class="carousel-indicators">
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="0" class="active"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="1"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="2"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="3"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="4"></li>
        </ol>
        
        <div class="carousel-inner">
        
        <div class="carousel-item active">
                <div class="container">
                    <div class="row p-5">
                        <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                        <a href="/BanLaptop/home/shop-single.htm?lsp=${product1.getMaLoai()}">
                            <img class="img-fluid" src="<c:url value ='/resource/images/${product1.getAnh()}'/>" style="height:400px;">
                        </a>
                        </div>
                        <div class="col-lg-6 mb-0 d-flex align-items-center">
                            <div class="text-align-left">
                                <h1 class="h1">${product1.getTenSP()}</h1>
                                <h3 class="h2">${product1.getMaLoai()} </h3>
                                <p>
                                    ${product1.getMoTa()}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          <c:if test="${listProducts != null}">
        <c:forEach items="${listProducts}" var="product">
            <div class="carousel-item">
                <div class="container">
                    <div class="row p-5">
                        <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                        <a href="/BanLaptop/home/shop-single.htm?lsp=${product.getMaLoai()}">
                            <img class="img-fluid" src="<c:url value ='/resource/images/${product.getAnh()}'/>" style="height:400px;">
                            </a>
                        </div>
                        <div class="col-lg-6 mb-0 d-flex align-items-center">
                            <div class="text-align-left">
                                <h1 class="h1">${product.getTenSP()}</h1>
                                <h3 class="h2">${product.getMaLoai()} </h3>
                                <p>
                                    ${product.getMoTa()}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
            </c:if>
        </div>
        <a class="carousel-control-prev text-decoration-none w-auto ps-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="prev">
            <i class="fas fa-chevron-left"></i>
        </a>
        <a class="carousel-control-next text-decoration-none w-auto pe-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="next">
            <i class="fas fa-chevron-right"></i>
        </a>
    </div>
       </c:if>
    <!-- End Banner Hero -->


    <!-- Start Categories of The Month -->
    <section class="container py-5">
        <div class="row text-center pt-3">
            <div class="col-lg-6 m-auto">
                <h1 class="h1">Sản Phẩm Của 12Shop</h1>
                <p>
                    
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-12 col-md-4 p-5 mt-3">
                <a href="/BanLaptop/home/shop/search.htm?searchInput=Laptop"><img src="<c:url value ='/resource/images/laptop.png'/>" class="rounded-circle img-fluid border" ></a>
                <h5 class="text-center mt-3 mb-3">Laptop</h5>
                <p class="text-center"><a class="btn btn-success" href="/BanLaptop/home/shop/search.htm?searchInput=Laptop">Go Shop</a></p>
            </div>
            <div class="col-12 col-md-4 p-5 mt-3">
                <a href="/BanLaptop/home/shop/search.htm?searchInput=Desktop"><img src="<c:url value ='/resource/images/desktop.png'/>" class="rounded-circle img-fluid border"></a>
                <h2 class="h5 text-center mt-3 mb-3">Desktop</h2>
                <p class="text-center"><a class="btn btn-success" href="/BanLaptop/home/shop/search.htm?searchInput=Desktop">Go Shop</a></p>
            </div>
            <div class="col-12 col-md-4 p-5 mt-3">
                <a href="/BanLaptop/home/shop/search.htm?searchInput=Tablet"><img src="<c:url value ='/resource/images/tablet.png'/>" class="rounded-circle img-fluid border"></a>
                <h2 class="h5 text-center mt-3 mb-3">Tablet</h2>
                <p class="text-center"><a class="btn btn-success" href="/BanLaptop/home/shop/search.htm?searchInput=Tablet">Go Shop</a></p>
            </div>
        </div>
    </section>
    <!-- End Categories of The Month -->


    <!-- Start Featured Product -->
    <section class="bg-light">
        <div class="container py-5">
            <div class="row text-center py-3">
                <div class="col-lg-6 m-auto">
                    <h1 class="h1">Sản Phẩm Tiêu Biểu</h1>
                    <p>
                        
                    </p>
                </div>
            </div>
            <div class="row">
            <c:if test="${listLoaiSanPham != null}">
            <c:forEach items="${listLoaiSanPham}" var="product2">
                <div class="col-12 col-md-4 mb-4">
                    <div class="card h-100">
                        <a href="/BanLaptop/home/shop-single.htm?lsp=${product2.getMaLoai()}">
                            <img src="<c:url value ='/resource/images/${product2.getAnh()}'/>" class="card-img-top" alt="...">
                        </a>
                        <div class="card-body">
                            
                            <a href="/BanLaptop/home/shop-single.htm?lsp=${product2.getMaLoai()}" class="h2 text-decoration-none text-dark">${product2.getMaLoai()} ${product.getTenSP()}</a>
                            <p class="card-text">
								<c:if test="${product2.getSanPham() != null}">
                                <p class="text-center mb-0">Lượt bán: ${product2.getSanPham().size()}</p>
                                </c:if>
                                <c:if test="${product2.getSanPham() == null}">
                                <p class="text-center mb-0">Lượt bán: 0</p>
                                </c:if>
                            </p>
                             <c:if test="${product2.getBinhLuan() == null}">
                                <p class="text-center">Lượt bình luận: 0</p>
                                </c:if>
                                <c:if test="${product2.getBinhLuan() != null}">
                                <p class="text-center">Lượt bình luận: ${product2.getBinhLuan().size()}</p>
                                </c:if>
                        </div>
                    </div>
                </div>
                </c:forEach>
                </c:if>
               </div>
        </div>
    </section>
    <!--End Brands-->


    <!-- Start Footer -->
      <%@include file="footerKH.jsp"%>

    <!-- Start Script -->
    <script src="<c:url value='/resource/assets/js/jquery-1.11.0.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/jquery-migrate-1.2.1.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/bootstrap.bundle.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/templatemo.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/custom.js'/>"></script>
    <!-- End Script -->
</body>

</html>