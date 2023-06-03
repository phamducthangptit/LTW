<<<<<<< HEAD
 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
>>>>>>> 79c266c968ed5e81fecf78ed974f71a691a560c4
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
    <!-- Start Top Nav -->

    <!-- Close Top Nav -->
<%@include file="../staff/headerNV.jsp"%>
    <!-- Open Content -->
    <section class="bg-light">
        <div class="container pb-5">
           
                
                <!-- col end -->
               
          <div class="card">
                    
                    
                     
            <div class="card-body">
	              <div class="row">
	                        <div class="col-lg-8 mt-5">
	                    	<h4>Danh mục sản phẩm:</h4>
	                    	</div>
	                    	<div class="col-lg-4 mt-5">
	                    		<div class="col d-grid">
	                    	<a  class="btn btn-success btn-lg" href="${url}/../add.htm">Thêm sản phẩm</a>
	                    		</div>
	                    	</div>
	                    </div>
                    <div class="row">
                        <table class=" table table-light table-striped table-hover bordered-dark">
                        <thead>
                            <tr>
                                <th scope="col" class="text-center align-middle">STT</th>
                                <th scope="col" class="text-center align-middle">Hình ảnh</th>
                                <th scope="col" class="text-center align-middle">Mã <br> Tên sản phẩm</th>
                                <th scope="col" class="text-center align-middle">Chi tiết</th>
                                <th scope="col" class="text-center align-middle">Giá bán</th>
                                <th scope="col" class="text-center align-middle">Chỉnh sửa</th>
                                <th scope="col" class="text-center align-middle">Xoá</th>
                            </tr>
                        </thead>
                        <tbody>
                         	
                        	<c:forEach items="${listLoaiSanPham}" var="product">
                            <tr>
                                <th scope="row" class="text-center align-middle">${stt}</th>
                                <td class="col-md-2">
                                 <div class="card mb-2 product-wap rounded-0">
                                 <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${product.getAnh()}'/>">
                                	</div>
                                	</div>
                                </td>   
                                <td class="text-center">${product.getMaLoai()} ${product.getTenSP()}</td>
                                <td>
                                	- CPU: ${product.getcPU()} <br>
                                	- RAM: ${product.getRam()} <br>
                                	- Hardware: ${product.getHardWare()} <br>
                                	- ...
                                </td>
                                <td class="text-center">
                                	<div>${product.getGia().toPlainString()} VND </div>
                                </td>
                                <td class="text-center align-middle">
                                	<a href="/BanLaptop/home/danh-muc-san-pham/chinh-sua.htm?sp=${product.getMaLoai()}">
                                	<i class="fas fa-cog fa-spin fa-lg" style="color: #000000;"></i>
                                	</a>
                                </td>
                                <td class="text-center align-middle">
                                	<a class="btn ${product.getSanPham() == null ? '' : 'disabled'}" href="/BanLaptop/home/danh-muc-san-pham/${product.getMaLoai()}.htm?linkDelete" >
                                	<i class="fas fa-trash-alt fa-lg" style="color: #000000;"></i>
                                	</a>
                                </td>
                            </tr>
                              <c:set var="stt" value="${stt + 1}" />
                            </c:forEach>
                        </tbody>
                    </table>
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
					            <a class="${currentPage == i ? 'page-link active rounded-0 mr-3 shadow-sm border-top-0 border-left-0' : 'page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark'}" 
					            href="/BanLaptop/home/danh-muc-san-pham.htm?page=${i}" style="border-color:transparent;">${i + 1}</a>
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
    </section>
    <!-- Close Content -->

    <!-- Start Article -->
    
    <!-- End Article -->


    <!-- Start Footer -->
    <footer class="bg-dark" id="tempaltemo_footer">
        <div class="container">
            <div class="row">

                <div class="col-md-4 pt-5">
                    <h2 class="h2 text-success border-bottom pb-3 border-light logo">Zay Shop</h2>
                    <ul class="list-unstyled text-light footer-link-list">
                        <li>
                            <i class="fas fa-map-marker-alt fa-fw"></i>
                            123 Consectetur at ligula 10660
                        </li>
                        <li>
                            <i class="fa fa-phone fa-fw"></i>
                            <a class="text-decoration-none" href="tel:010-020-0340">010-020-0340</a>
                        </li>
                        <li>
                            <i class="fa fa-envelope fa-fw"></i>
                            <a class="text-decoration-none" href="mailto:info@company.com">info@company.com</a>
                        </li>
                    </ul>
                </div>

                <div class="col-md-4 pt-5">
                    <h2 class="h2 text-light border-bottom pb-3 border-light">Products</h2>
                    <ul class="list-unstyled text-light footer-link-list">
                        <li><a class="text-decoration-none" href="#">Luxury</a></li>
                        <li><a class="text-decoration-none" href="#">Sport Wear</a></li>
                        <li><a class="text-decoration-none" href="#">Men's Shoes</a></li>
                        <li><a class="text-decoration-none" href="#">Women's Shoes</a></li>
                        <li><a class="text-decoration-none" href="#">Popular Dress</a></li>
                        <li><a class="text-decoration-none" href="#">Gym Accessories</a></li>
                        <li><a class="text-decoration-none" href="#">Sport Shoes</a></li>
                    </ul>
                </div>

                <div class="col-md-4 pt-5">
                    <h2 class="h2 text-light border-bottom pb-3 border-light">Further Info</h2>
                    <ul class="list-unstyled text-light footer-link-list">
                        <li><a class="text-decoration-none" href="#">Home</a></li>
                        <li><a class="text-decoration-none" href="#">About Us</a></li>
                        <li><a class="text-decoration-none" href="#">Shop Locations</a></li>
                        <li><a class="text-decoration-none" href="#">FAQs</a></li>
                        <li><a class="text-decoration-none" href="#">Contact</a></li>
                    </ul>
                </div>

            </div>

            <div class="row text-light mb-4">
                <div class="col-12 mb-3">
                    <div class="w-100 my-3 border-top border-light"></div>
                </div>
                <div class="col-auto me-auto">
                    <ul class="list-inline text-left footer-icons">
                        <li class="list-inline-item border border-light rounded-circle text-center">
                            <a class="text-light text-decoration-none" target="_blank" href="http://facebook.com/"><i class="fab fa-facebook-f fa-lg fa-fw"></i></a>
                        </li>
                        <li class="list-inline-item border border-light rounded-circle text-center">
                            <a class="text-light text-decoration-none" target="_blank" href="https://www.instagram.com/"><i class="fab fa-instagram fa-lg fa-fw"></i></a>
                        </li>
                        <li class="list-inline-item border border-light rounded-circle text-center">
                            <a class="text-light text-decoration-none" target="_blank" href="https://twitter.com/"><i class="fab fa-twitter fa-lg fa-fw"></i></a>
                        </li>
                        <li class="list-inline-item border border-light rounded-circle text-center">
                            <a class="text-light text-decoration-none" target="_blank" href="https://www.linkedin.com/"><i class="fab fa-linkedin fa-lg fa-fw"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="col-auto">
                    <label class="sr-only" for="subscribeEmail">Email address</label>
                    <div class="input-group mb-2">
                        <input type="text" class="form-control bg-dark border-light" id="subscribeEmail" placeholder="Email address">
                        <div class="input-group-text btn-success text-light">Subscribe</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="w-100 bg-black py-3">
            <div class="container">
                <div class="row pt-2">
                    <div class="col-12">
                        <p class="text-left text-light">
                            Copyright &copy; 2021 Company Name 
                            | Designed by <a rel="sponsored" href="https://templatemo.com" target="_blank">TemplateMo</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>

    </footer>
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