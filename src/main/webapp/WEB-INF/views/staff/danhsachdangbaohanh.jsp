<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Danh Sách Bảo Hành</title>
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
        <form  action="staff/danhsachdangbaohanh.htm" method="POST">
        <a class="btn btn-success btn-lg px-3 mt-1"href="danhsachdangbaohanh.htm?loaiBtn=troVe"><i class="fa fa-angle-double-left" aria-hidden="true"></i>Về Trang Bảo Hành</a>
        	<div class = "card-body">
            <div class = "container py-5">
                <h2>Danh Sách Sản Phẩm Đang Nhận Bảo Hành</h2>
            
              <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                  <thead>
                    <tr>
                      <th>Seri</th>
                      <th>Mã Loại</th>
                      <th>Tên Sản Phẩm</th>
                       <th>Nhân Viên Nhận</th>
                      <th>Ngày Nhận</th>
                      <th>Trạng Thái Nhận</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                 <c:if test = "${listBH.size() == 0 }">
                 <tr><td colspan="7">Hiện Không Có Sản Phẩm Nào Đang Nhận Bảo Hành</td></tr>
                 </c:if>
                 <c:if test = "${listSP.size() != 0 }">
                    <c:forEach var="BH" items="${listBH}">
					<tr>
						<td>${BH.soPhieuBH.seri.seri }</td>
						<td>${BH.soPhieuBH.seri.maLoai.maLoai }</td>
						<td>${BH.soPhieuBH.seri.maLoai.tenSP }</td> 
						<td>${BH.maNVNhan.maNV } - ${BH.maNVNhan.ho } ${BH.maNVNhan.ten }</td>
						<td>${BH.ngayNhan}</td>
						<td>${BH.trangThaiNhan }</td>
					  	
					  	 <td>
					  	 
					  	 <a class="btn btn-success btn-lg px-3 mt-1"href="danhsachdangbaohanh.htm?seri=${BH.soPhieuBH.seri.seri }&loaiBtn=traMay">Sửa Xong</a>
					  	 </td>
					</tr>
				</c:forEach>
				</c:if>
                  </tbody>
                </table> 
   				
              </div>
              </div>
               </form>
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