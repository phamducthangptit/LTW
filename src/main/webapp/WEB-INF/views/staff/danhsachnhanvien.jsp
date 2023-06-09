<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Danh Sách Nhân Viên</title>
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

<%@include file="headerNV.jsp"%>
</head>

<body>
    
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
                <h2>Danh sách nhân viên</h2>
                <h6 style="color: red; right: 15px ; text-align: right">${Error}</h6>
            <h3><a href="themnhanvien.htm" class="btn btn-success btn-lg px-3"><i class="fa fa-user-plus" aria-hidden="true"></i>  Thêm Nhân Viên Mới</a></h3>
            <p>   </p>
            <form class="employee-list" action="staff/danhsachnhanvien.htm" method="POST">
                <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                  <thead>
                    <tr>
                      <th>Mã NV</th>
                      <th>Họ</th>
                      <th>Tên</th>
                      <th>Ngày sinh</th>
                      <th>Số Điện Thoại</th>
                      <th>Email</th>
                      <th>Địa chỉ</th>
                      <th>Trạng thái tài khoản</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="nv" items="${DSNhanVien}">
					<tr>
						<td>${nv.maNV }</td>
						<td>${nv.ho }</td>
						<td>${nv.ten }</td>
						<td>${nv.ngaySinh }</td>
						<td>${nv.sDT }</td>
						<td>${nv.email }</td>
						<td>${nv.diaChi }</td>
						<c:if test = "${nv.trangThai == 1}">
						  <td> <a href="trangthainhanvien.htm?idnv=${nv.maNV}&TT=1"><i class="fa fa-toggle-on" style = "color : black" aria-hidden="true"></i></a></td>
						  </c:if>
						  <c:if test = "${nv.trangThai == 0}">
					  	<td><a href="trangthainhanvien.htm?idnv=${nv.maNV}&TT=0"><i class="fa fa-toggle-off" style = "color : black" aria-hidden="true"></i></a></td>
					  	</c:if>
					  	
					  	 <td>
					  	 
					  	 <a href="suathongtinnhanvien.htm?idnv=${nv.maNV}"><i class="fa fa-pencil-alt" style = "color : black" aria-hidden="true"></i></a>
					  	 </td>
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