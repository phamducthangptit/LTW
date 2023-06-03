<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Đơn Đặt Hàng</title>
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
            <h3><a href="taoThongTinDDH.htm" class="btn btn-success btn-lg px-3"><i class="fa fa-plus-circle" aria-hidden="true"></i>  Tạo Đơn Đặt Hàng Mới</a>
             <a href="danhsachnhacungcap.htm" class="btn btn-success btn-lg px-3"><i class="fa fa-building" aria-hidden="true"></i> Danh Sách Nhà Cung Cấp</a>
            </h3>
            
            <p>   </p>
            <form class="employee-list" action="staff/dondathang.htm" method="POST">
               <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
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
                 <c:if test = "${DSDDH.size() == 0}">
                  <tr>
                  <td colspan = "5">Hiện Chưa Có Đơn Đặt Hàng Nào !</td>
                  </tr>
                  </c:if>
                    <c:forEach var="ddh" items="${DSDDH}">
                    
					<tr>
					 
						<td>${ddh.maDDH }</td>
						<td>${ddh.ngayDat }</td>
						<td>${ddh.getMaNCC().maNCC} - ${ddh.getMaNCC().tenNCC}</td>
						<td>${ddh.maNV.maNV } - ${ddh.maNV.ho } ${ddh.maNV.ten }</td>
						
						<c:if test = "${ddh.soPhieuNhap == null}">
						  <td class="text-center align-middle"><div  id="templatemo_main_nav">
						  <a class = "nav-link" href="taophieunhap.htm?idDDH=${ddh.maDDH}">Tạo Phiếu Nhập</a>
						  </div>
						  </td>
						  </c:if>
						  <c:if test = "${ddh.soPhieuNhap != null}">
					  	<td class="text-center align-middle"><div id="templatemo_main_nav">
					  	<a class = "nav-link" href="xemphieunhap.htm?idPN=${ddh.soPhieuNhap.soPhieuNhap}">Xem Phiếu Nhập</a>
					  	</div>
					  	</td>
					  	</c:if>
					  	
					  	<td>
					  	<a class = "nav-link"  href="chitietdondathang.htm?maDDH=${ddh.maDDH}"><i class="fa fa-bars" style = "color : black" aria-hidden="true"></i></a>
					  	
					  	</td>
					  		
					  	<td><a class = "nav-link" href="detetedondathang.htm?idDDH=${ddh.maDDH}"><i class="fa fa-trash" style = "color : black" aria-hidden="true"></i></a></td>
					
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