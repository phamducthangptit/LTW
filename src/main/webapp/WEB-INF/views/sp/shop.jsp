<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Cửa hàng</title>
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
    <div class="container py-5">
        <div class="row">

            <div class="col-lg-3">
                <h1 class="h2 pb-4">Mục sản phẩm</h1>
                <ul class="list-unstyled templatemo-accordion">
                    <li class="pb-3">
                        <a class="collapsed d-flex justify-content-between h3 text-decoration-none" href="#">
                            Thể Loại
                            <i class="fa fa-fw fa-chevron-circle-down mt-1"></i>
                        </a>
                        <ul class="collapse show list-unstyled pl-3">
                         <c:forEach items="${listTheLoai}" var="tl">
                            <li><a class="text-decoration-none" href="/BanLaptop/home/shop/search/${tl.getTenTL()}.htm?linkSearch">${tl.getTenTL()}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                    <li class="pb-3">
                        <a class="collapsed d-flex justify-content-between h3 text-decoration-none" href="#">
                            Hãng
                            <i class="pull-right fa fa-fw fa-chevron-circle-down mt-1"></i>
                        </a>
                        <ul id="collapseTwo" class="collapse list-unstyled pl-3">
                            <c:forEach items="${listHang}" var="hang">
                            <li><a class="text-decoration-none" href="/BanLaptop/home/shop/search/${hang.getTenHang()}.htm?linkSearch">${hang.getTenHang()}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </div>

            <div class="col-lg-9">
                <div class="row">
                    <div class="col-md-4">
                        <ul class="list-inline shop-top-menu pb-3 pt-1" style="display: none;">
                            <li class="list-inline-item">
                                <a class="h3 text-dark text-decoration-none mr-3" href="#">All</a>
                            </li>
                            <li class="list-inline-item">
                                <a class="h3 text-dark text-decoration-none mr-3" href="#">Men's</a>
                            </li>
                            <li class="list-inline-item">
                                <a class="h3 text-dark text-decoration-none" href="#">Women's</a>
                            </li>
                        </ul>
                    </div>
                    
                    <div class="col-md-8 pb-4">
                    	<form action="shop/search.htm">
                        <div class="row">
                        <div class="col-md-8">
                        <input list="tenLoais" name="searchInput" class="form-control" value="${searchInput}">
							  	<datalist id="tenLoais">
							      <c:forEach items="${listLoais}" var="product">
							        <option value="${product.getMaLoai()} - ${product.getTenSP()}"></option>
							      </c:forEach>
							    </datalist>
					</div>
                            <div class="col-md-4">
	                    	<button name="btnsearch" type="submit"
	                    	  class="btn btn-success btn-lg">Tìm kiếm</button>
	                    	</div>
	                    </div>
 						</form>
                    </div>
                   
                </div>
                <div class="row">
                <c:forEach items="${listLoaiSanPham}" var="product">
                    <div class="col-md-4">
                        <div class="card mb-4 product-wap rounded-0">
                            <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${product.getAnh()}'/>">
                                <div
                                    class="card-img-overlay rounded-0 product-overlay d-flex align-items-center justify-content-center">
                                    <ul class="list-unstyled">
                                        
                                        <li ><a class="btn btn-success text-white mt-2 ${product.getSanPham() == null ? 'disabled' : ''}" href="${url}?page=${currentPage}&sp=${product.getMaLoai()}"><i
                                                    class="fas fa-cart-plus"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="card-body">
                                <a href="shop-single.htm?lsp=${product.getMaLoai()}" class="h3 text-decoration-none">${product.getTenSP()}</a>
                                <ul class="w-100 list-unstyled d-flex justify-content-between mb-0">
                                    <li><h6>${product.getMaLoai()}( ${product.getcPU()} + ${product.getRam()} + ${product.getHardWare()} )</h6>
                                    </li>
                                    
                                </ul>
                                <c:if test="${product.getCtDotGiamGia() == null}">
                                	<p class="text-center mb-0">${product.getGia().toPlainString()} VND</p>
                                </c:if>
                                <c:if test="${product.getCtDotGiamGia() != null}">
                                <c:set var="giam" value ="${100-product.getCtDotGiamGia().get(0).getTiLeGiam()}"/>
                                <c:set var="giaGiam" value ="${product.getGia().multiply(giam).divide(100)}"/>
                                	<p class="text-center mb-0"><s>${product.getGia().toPlainString()} VND</s></p>
                                	<h6 class="text-center mb-0" style="color:red;">${giaGiam.toPlainString()} VND</h6>
                                </c:if>
                                <c:if test="${product.getSanPham().size() > 0}">
                                <p class="text-center mb-0">Còn hàng</p>
                                </c:if>
                                <c:if test="${product.getSanPham() == null}">
                                <p class="text-center mb-0">Hết hàng</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                <div div="row">
                    <ul class="pagination pagination-lg justify-content-end">
			    			<li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
					        <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark" href="${url}?page=${currentPage - 1}">Trước</a>
					    </li>
					    <c:if test="${currentPage > 1}">
					        <li class="page-item">
					            <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark" href="${url}?page=0">1</a>
					        </li>
					        <li class="page-item disabled">
					            <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark">...</a>
					        </li>
					    </c:if>
					    <c:forEach begin="${startPage}" end="${endPage}" var="i">
					        <li class="page-item ${currentPage == i ? 'active' : ''}">
					            <a class="${currentPage == i ? 'page-link active rounded-0 mr-3 shadow-sm border-top-0 border-left-0' : 'page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark'}" href="${url}?page=${i}"
					            style="border-color:transparent;"
					            >${i + 1}
					            </a>
					        </li>
					    </c:forEach>
					    <c:if test="${currentPage < totalPages - 2}">
					        <li class="page-item disabled">
					            <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark">...</a>
					        </li>
					        <li class="page-item">
					            <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark" href="${url}?page=${totalPages - 1}">${totalPages}</a>
					        </li>
					    </c:if>
					    <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
					        <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark" href="${url}?page=${currentPage + 1}">Sau</a>
					    </li>
					</ul>
                </div>
            </div>

        </div>
    </div>
    <!-- End Content -->

    <!-- Start Brands -->
    <section class="bg-light py-5">
        <div class="container my-4">
            <div class="row text-center py-3">
                <div class="col-lg-6 m-auto">
                    <h1 class="h1">Our Brands</h1>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                        Lorem ipsum dolor sit amet.
                    </p>
                </div>
                <div class="col-lg-9 m-auto tempaltemo-carousel">
                    <div class="row d-flex flex-row">
                        <!--Controls-->
                        <div class="col-1 align-self-center">
                            <a class="h1" href="#multi-item-example" role="button" data-bs-slide="prev">
                                <i class="text-light fas fa-chevron-left"></i>
                            </a>
                        </div>
                        <!--End Controls-->

                        <!--Carousel Wrapper-->
                        <div class="col">
                            <div class="carousel slide carousel-multi-item pt-2 pt-md-0" id="multi-item-example"
                                data-bs-ride="carousel">
                                <!--Slides-->
                                <div class="carousel-inner product-links-wap" role="listbox">

                                    <!--First slide-->
                                    <div class="carousel-item active">
                                        <div class="row">
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_01.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_02.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_03.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_04.png" alt="Brand Logo"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <!--End First slide-->

                                    <!--Second slide-->
                                    <div class="carousel-item">
                                        <div class="row">
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_01.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_02.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_03.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_04.png" alt="Brand Logo"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <!--End Second slide-->

                                    <!--Third slide-->
                                    <div class="carousel-item">
                                        <div class="row">
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_01.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_02.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_03.png" alt="Brand Logo"></a>
                                            </div>
                                            <div class="col-3 p-md-5">
                                                <a href="#"><img class="img-fluid brand-img"
                                                        src="assets/img/brand_04.png" alt="Brand Logo"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <!--End Third slide-->

                                </div>
                                <!--End Slides-->
                            </div>
                        </div>
                        <!--End Carousel Wrapper-->

                        <!--Controls-->
                        <div class="col-1 align-self-center">
                            <a class="h1" href="#multi-item-example" role="button" data-bs-slide="next">
                                <i class="text-light fas fa-chevron-right"></i>
                            </a>
                        </div>
                        <!--End Controls-->
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--End Brands-->


    <!-- Start Footer -->
     <%@include file="footerKH.jsp"%>
    <!-- End Footer -->

    <!-- Start Script -->
    <script src="<c:url value='/resource/assets/js/jquery-1.11.0.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/jquery-migrate-1.2.1.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/bootstrap.bundle.min.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/templatemo.js'/>"></script>
    <script src="<c:url value='/resource/assets/js/custom.js'/>"></script>
    <!-- End Script -->
</body>

</html>