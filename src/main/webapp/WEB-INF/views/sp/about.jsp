<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>

<head>
    <title>12Shop - Thông Tin</title>
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
    



    <!-- Start Banner Hero -->
    <section class="bg-success py-5">
        <div class="container">
            <div class="row align-items-center py-5">
                <div class="col-md-8 text-white">
                    <h1>Nhóm 12</h1>
                    <p>
                        Nguyễn Phúc Luân - N20DCCN036 <br>
                        Đặng Thành Tân - N20DCCN062 <br>
                        Phạm Đức Thắng - N20DCCN032
                    </p>
                </div>
                <div class="col-md-4">
                    <img src="<c:url value ='/resource/images/12.png'/>" alt="Thông tin nhóm 12" style="width:356px;height:356px;">
                </div>
            </div>
        </div>
    </section>
    <!--End Brands-->
 <section class="container py-5">
        <div class="row text-center pt-5 pb-3">
            <div class="col-lg-6 m-auto">
                <h1 class="h1">Dịch vụ của 12Shop</h1>
                <p>
                    Chúng tôi luôn đồng hành với laptop của bạn 
                </p>
            </div>
        </div>
        <div class="row">

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-truck fa-lg"></i></div>
                    <h2 class="h5 mt-4 text-center">Vận chuyển</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fas fa-exchange-alt"></i></div>
                    <h2 class="h5 mt-4 text-center">Giao hàng và đổi trả</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-percent"></i></div>
                    <h2 class="h5 mt-4 text-center">Khuyến mãi</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-user"></i></div>
                    <h2 class="h5 mt-4 text-center">Dịch vụ 24h</h2>
                </div>
            </div>
        </div>
    </section>

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