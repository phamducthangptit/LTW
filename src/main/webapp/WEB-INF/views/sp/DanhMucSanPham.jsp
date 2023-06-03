<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Danh mục sản phẩm</title>
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
    <link rel="stylesheet" type="text/css" href="<c:url value='/resource/assets/css/slick.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value ='/resource/assets/css/slick-theme.css'/>">
    <base href="${pageContext.servletContext.contextPath}/">
    <%@include file="../staff/headerNV.jsp"%>
<!--
    
TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

-->
</head>

<body>
    <!-- Start Top Nav -->

    <!-- Close Top Nav -->

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
    <%@include file="../staff/footerQL.jsp"%>
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