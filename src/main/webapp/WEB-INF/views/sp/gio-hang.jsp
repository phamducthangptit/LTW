<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Giỏ hàng</title>
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
     <%@include file="headerKH.jsp"%>
    
<!--
    
TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

-->
</head>

<body>
    

    <!-- Open Content -->
    <section class="bg-light">
        <div class="container pb-5">
           
                
                <!-- col end -->
               
          <div class="card">
                    
                    
                     
            <div class="card-body">
	              <div class="row">
	                        <div class="col-lg-8 mt-5">
	                    	<h4>Giỏ hàng:</h4>
	                    	</div>
	                    	
	                    </div>
	                   <form action="home/gio-hang.htm" method="post">
                    <div class="row">
                        <table class=" table table-light table-striped table-hover bordered-dark">
                        <thead>
                            <tr>
                                <th scope="col" class="text-center align-middle">Chọn</th>
                                <th scope="col" class="text-center align-middle">Hình ảnh</th>
                                <th scope="col" class="text-center align-middle">Mã <br> Tên sản phẩm</th>
                                <th scope="col" class="text-center align-middle">Đơn giá</th>
                                <th scope="col" class="text-center align-middle">Số lượng</th>
                                <th scope="col" class="text-center align-middle">Số tiền</th>
                                <th scope="col" class="text-center align-middle">Xoá</th>
             						
                            </tr>
                        </thead>
                        <tbody>
                         	
                        	<c:forEach items="${cart}" var="product">
                        	
                            <tr>
                                <td class="col-md-1 text-center align-middle">
                                
                                	<div class="checkbox-link">
  									<c:if test="${product.getCheck() == 0}">
  									 
                                		<a class="nav-link" href="/BanLaptop/home/gio-hang.htm?lsp=${product.getLsp().getMaLoai()}&thaotac=chon">
                                		<i class="far fa-square fa-lg" style="color: #000000;"></i>
                                		</a>
                         
                                	</c:if>
                                	<c:if test="${product.getCheck() == 1}">
                                	<a class="nav-link" href="/BanLaptop/home/gio-hang.htm?lsp=${product.getLsp().getMaLoai()}&thaotac=bochon">
                                	<i class="far fa-check-square fa-lg" style="color: #000000;"></i>
                                	</a>
                                	</c:if>
                                	</div>
                                </td>
                                <td class="col-md-2">
                                 <div class="card mb-2 product-wap rounded-0">
                                 <div class="card rounded-0">
                                 <a href="/BanLaptop/home/shop-single.htm?lsp=${product.getLsp().getMaLoai()}">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${product.getLsp().getAnh()}'/>">
                                </a>
                                	</div>
                                	</div>
                                </td>  
                                <td class="">
                                ${product.getLsp().getMaLoai()} ${product.getLsp().getTenSP()}<br>
                                - CPU: ${product.getLsp().getcPU()} <br>
                                - RAM: ${product.getLsp().getRam()} <br>
                                - Hardware: ${product.getLsp().getHardWare()} <br>
                                - ...
                                </td>
                                <td class="text-center align-middle">
                                	<div>
                                	<c:if test="${product.getLsp().getCtDotGiamGia() == null}">
                                	<p class="text-center mb-0">${product.getLsp().getGia().toPlainString()} VND </p>
                                	<c:set var="soTien" value ="${product.getLsp().getGia()*product.getSoLuong()}"/>
                                </c:if>
                                <c:if test="${product.getLsp().getCtDotGiamGia() != null}">
                                <c:set var="giam" value ="${100-product.getLsp().getCtDotGiamGia().get(0).getTiLeGiam()}"/>
                                <c:set var="giaGiam" value ="${product.getLsp().getGia().multiply(giam).divide(100)}"/>
                                	<p class="text-center mb-0"><s>${product.getLsp().getGia().toPlainString()} VND</s></p>
                                	<h6 class="text-center mb-0" style="color:red;">${giaGiam.toPlainString()} VND</h6>
                                	<c:set var="soTien" value ="${giaGiam*product.getSoLuong()}"/>
                                </c:if>
                                	 </div>
                                </td>
                                <td class="col-md-2 text-center align-middle">
                                	<div class="col-auto">
                                        <ul class="list-inline pb-3">
                                            <li class="list-inline-item"><a class="btn btn-success ${product.getSoLuong() == 1 ? 'disabled' : ''}" id="btn-minus"
                                            href="/BanLaptop/home/gio-hang.htm?lsp=${product.getLsp().getMaLoai()}&sl=${product.getSoLuong()-1}">-</a></li>
                                            <li class="list-inline-item"><span class="badge bg-secondary disabled" id="var-value">${product.getSoLuong()}</span></li>
                                            <li class="list-inline-item"><a class="btn btn-success ${product.getSoLuong() == product.getSoLuongMax() ? 'disabled' : ''}" id="btn-plus"
                                            href="/BanLaptop/home/gio-hang.htm?lsp=${product.getLsp().getMaLoai()}&sl=${product.getSoLuong()+1}">+</a></li>
                                        </ul>
                                    </div>
                                	</td>
                                <td class="text-center align-middle">
                              				${soTien.toPlainString()} VND
                                </td>
                                
                                <td class="text-center align-middle">
                                	<a href="/BanLaptop/home/gio-hang.htm?lsp=${product.getLsp().getMaLoai()}&sl=0">
                                	<i class="fas fa-backspace fa-lg" style="color: #000000;"></i>
                                	</a>
                                </td>
                            </tr>
                              
                            </c:forEach>
                            <tr>
                            <td>
                            </td>
                            <td class="text-center align-middle">
                            	Tổng:
                            </td>
                            <td class="align-middle">
                            	${SLVP}	loại sản phẩm.
                            </td>
                            <td>
                            </td>
                            <td class="text-center align-middle">
                            	Tổng tiền: 
                            </td>
                            <td class="text-center align-middle">
                            	${sum.toPlainString()} VND
                            </td>
                            <td class="col-md-1 text-center align-middle">
                            	
							  	<button type="submit" class="btn btn-success btn-xs" ${SLVP == 0 ?'disabled':'' }>Mua hàng</button>
							
                            </td>
                            </tr>
                        </tbody>
                    </table>
                           </div>
                           </form>
                           
                    </div>
            </div>
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