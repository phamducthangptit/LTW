<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Thêm sản phẩm mới</title>
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
  

    <!-- Open Content -->
    <section class="bg-light">
    <form action="home/add.htm" method="post" enctype="multipart/form-data">
        <div class="container pb-5">
            <div class="row">
                <div class="col-lg-5 mt-5">
                    <div class="card mb-3">
                        <img  id="imagePreview" class="card-img img-fluid" src="<c:url value ='/resource/images/${hinhanh}'/>" alt="Card image cap" id="product-detail">
                    </div>

	                    <div class = "row">
	                    	<ul class="list-inline">
	                                <li class="list-inline-item">
	                                    <h1 class="h2">Hình ảnh</h1>
	                                </li>
	                                <li class="list-inline-item">
	                                    <input type="file" name="photo" id="imageFile" onchange="previewImage(this)" required="required" >
	                                </li>
	                            </ul>	          
	                    </div>

                </div>
                <!-- col end -->
                <div class="col-lg-7 mt-5">
                    <div class="card">
                        <div class="card-body">
                            <h1 class="h2">Nhập thông tin sản phẩm:</h1>
                        	<div class="row g-3">
							  <div class="col-md-4">
							    <label for="productType">Mã loại sản phẩm:</label>
							    <input type="text" class="form-control" name="maLoai" placeholder="Vostro 15 3520" maxlength="15" required="required">
							  </div>
							  <div class="col-md-8">
							    <label for="productName">Tên sản phẩm:</label>
							    <input type="text" class="form-control"  name="ten" placeholder="Laptop DELL" maxlength="50" required="required">
							  </div>
							  <div class="col-md-6">
							    <label for="productPrice">Giá bán:</label>
							    <input type="number" class="form-control" name="gia" required="required">
							  </div>
							  <div class="col-md-6">
							    <label for="productPrice">Giá nhập:</label>
							    <input type="number" class="form-control" name="giaNhap" required="required" >
							  </div>
							  <div class="col-md-6">
							    <label for="productCPU">CPU:</label>
							    <input type="text" class="form-control"  name="cpu" maxlength="50" placeholder="Nhập thông tin CPU" required="required" >
							  </div>
							  <div class="col-md-6">
							    <label for="productRAM">RAM:</label>
							    <input type="text" class="form-control" name="ram" maxlength="50" placeholder="Nhập thông tin RAM" required="required">
							  </div>
							  <div class="col-md-6">
							    <label for="productHardware">Hardware:</label>
							    <input type="text" class="form-control"  name="hardware" maxlength="50" placeholder="Nhập thông tin phần cứng" required="required">
							  </div>
							  <div class="col-md-6">
							    <label for="productCard">Card màn hình:</label>
							    <input type="text" class="form-control"  name="card"  maxlength="50" placeholder="Nhập thông tin card màn hình" required="required">
							  </div>
							  <div class="col-md-12">
							    <label for="productScreen">Màn hình:</label>
							    <input type="text" class="form-control"  name="screen" maxlength="50"  placeholder="Nhập thông tin màn hình" required="required">
							  </div>
							  <div class="col-md-12">
							    <label for="productOS">Hệ điều hành:</label>
							    <input type="text" class="form-control"  name="os" maxlength="50" placeholder="Nhập thông tin hệ điều hành" required="required">
							  </div>
							  <div class="col-md-6">
							  	<label for="theLoai" class="form-label">Thể loại:</label>
							  	<input type="text" list="theLoais" name="theLoai" class="form-control" required="required">
							  	<datalist id="theLoais">
							      <c:forEach items="${listTheLoai}" var="product">
							        <option value="${product.getMaTheLoai()}">${product.getTenTL()}</option>
							      </c:forEach>
							    </datalist>  	
							  </div>
							  <div class="col-md-6">
							  	<label for="moTa" class="form-label">Hãng sản xuất:</label>
							  	<input type="text" list="listHang" name="hangSanXuat" class="form-control" required="required">
							  	<datalist id="listHang">
							      <c:forEach items="${listHang}" var="product">
							        <option value="${product.getMaHang()}">${product.getTenHang()}</option>
							      </c:forEach>
							    </datalist>  	
							  </div>
								<div class="col-md-12">
								<label for="moTa" class="form-label">Mô tả:</label>
	  							<textarea class="form-control" name="moTa"></textarea>
	  							</div>
	  						<div class="row pb-3" style="margin-top: 10px;">
	  							<div class="col d-grid">
							  	<button type="submit" class="btn btn-primary" style="display: none;">Ẩn</button>
							  	</div>
	  							<div class="col d-grid">
							  	<button type="submit" class="btn btn-success btn-lg">Thêm sản phẩm</button>
							  	${message}
							  	</div>
							  </div>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
        </form>
    </section>
    <!-- Close Content -->

    <!-- Start Article -->
    
    <!-- End Article -->


    <%@include file="../staff/footerQL.jsp"%>
    <!-- End Footer -->
<script type="text/javascript">
function previewImage(input) {
    var reader = new FileReader();
    reader.onload = function(e) {
        document.getElementById('imagePreview').setAttribute('src', e.target.result);
    };
    reader.readAsDataURL(input.files[0]);
}
</script>
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