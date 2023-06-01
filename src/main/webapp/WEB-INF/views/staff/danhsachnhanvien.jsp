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
                <h2>Danh sách nhân viên</h2>
                
            <h3><a href="themnhanvien.htm" style="text-decoration: none; border: 2px solid #343a40;padding: 5px;"><i class="fa fa-user-plus" aria-hidden="true"></i>  Thêm Nhân Viên Mới</a></h3>
            <p>   </p>
            <form class="employee-list" action="staff/danhsachnhanvien.htm" method="POST">
                <table class="table">
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
						  <td>Hoạt Động</td>
						  </c:if>
						  <c:if test = "${nv.trangThai == 0}">
					  	<td>Ngừng Hoạt Động</td>
					  	</c:if>
					  	
					  	 <td>
					  	 
					  	 <a href="suathongtinnhanvien.htm?idnv=${nv.maNV}"><i class="fa fa-pencil-alt" aria-hidden="true"></i></a>
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