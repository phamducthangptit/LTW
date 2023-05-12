<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Zay Shop eCommerce HTML CSS Template</title>
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
<!--

-->
</head>

<body>
    <%@include file="headerNV.jsp"%>
    <!-- Modal -->
    <div class="modal fade bg-white" id="templatemo_search" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="w-100 pt-1 mb-5 text-right">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="" method="get" class="modal-content modal-body border-0 p-0">
                <div class="input-group mb-2">
                    <input type="text" class="form-control" id="inputModalSearch" name="q" placeholder="Search ...">
                    <button type="submit" class="input-group-text bg-success text-light">
                        <i class="fa fa-fw fa-search text-white"></i>
                    </button>
                </div>
            </form>
        </div>
    </div>


    <!-- Start Content Page -->
    <main>
   
          <section class = "bg-light">
        	<div class = "card-body">
    
            <div class = "container py-5">
                <h2>Đơn Đặt Hàng</h2>
               <h6 style="color: red; right: 15px ; text-align: right">${ThongBao}</h6>
            <h3><a href="taodondathang.htm" style="text-decoration: none; border: 2px solid #343a40;padding: 5px;"><i class="fa fa-plus-circle" aria-hidden="true"></i>  Tạo Đơn Đặt Hàng Mới</a></h3>
            <p>   </p>
            <form class="employee-list" action="staff/dondathang.htm" method="POST">
                <table>
                  <thead>
                    <tr>
                      <th>Mã DDH</th>
                      <th>Ngày Đặt</th>
                      <th>Nhà Cung Cấp</th>
                      <th>Nhân Viên Đặt</th>
                    
                      <th>Phiếu Nhập</th>
                      <th></th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="ddh" items="${DSDDH}">
					<tr>
						<td>${ddh.maDDH }</td>
						<td>${ddh.ngayDat }</td>
						<td>${ddh.getMaNCC().maNCC} - ${ddh.getMaNCC().tenNCC}</td>
						<td>${ddh.maNV.maNV } - ${ddh.maNV.ho } ${ddh.maNV.ten }</td>

						<c:if test = "${ddh.soPhieuNhap == null}">
						  <td><a href="#?idDDH=${ddh.maDDH}">Xuất Phiếu Nhập</a></td>
						  </c:if>
						  <c:if test = "${ddh.soPhieuNhap != null}">
					  	<td><a href="#?idDDH=${ddh.maDDH}">Xem Phiếu Nhập</a></td>
					  	</c:if>
					  	<td><a href="chitietdondathang.htm?maDDH=${ddh.maDDH}"><i class="fa fa-bars" aria-hidden="true"></i></a></td>
					  	<td><a href="detetedondathang.htm?idDDH=${ddh.maDDH}"><i class="fa fa-trash" aria-hidden="true"></i></a></td>
					</tr>
				</c:forEach>
                  </tbody>
                </table>
   				 </form>
              </div>
             </div>
             </section>
        </main>
		
	<%@include file="footerQL.jsp" %>
    <!-- Start Script -->
    <script src="assets/js/jquery-1.11.0.min.js"></script>
    <script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/js/templatemo.js"></script>
    <script src="assets/js/custom.js"></script>
    <!-- End Script -->
</body>

</html>