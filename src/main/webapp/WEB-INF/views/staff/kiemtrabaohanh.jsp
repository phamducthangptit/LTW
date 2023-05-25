<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Tạo Phiếu Nhập</title>
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
    	<section class="bg-light">
    	<form  action="staff/danhsachdangbaohanh.htm" method="POST">
        <a class="btn btn-success btn-lg px-3 mt-1"href="danhsachdangbaohanh.htm?loaiBtn=troVe"><i class="fa fa-angle-double-left" aria-hidden="true"></i>Về Trang Bảo Hành</a>
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Lịch Sử Bảo Hành</h4>
       <ul >
       	<li class = "pb-3">Seri : ${SP.seri}</li>
       		<li class = "pb-3"> Loại Sản Phẩm : ${SP.maLoai.maLoai}</li>
       	<li class = "pb-3"> Tên Sản Phẩm : ${SP.maLoai.tenSP}</li>
       	<li class = "pb-3">Số Phiếu Bảo Hành ${SP.phieuBaoHanh.soPhieuBH}</li>
       	<li class = "pb-3"> Nhân Viên Lập Phiếu : ${SP.phieuBaoHanh.maNV.maNV} - ${SP.phieuBaoHanh.maNV.ho} ${SP.phieuBaoHanh.maNV.ten}</li>
       </ul>
       </div>
       </div>
       </div>
       <div class = "col-lg-9 mt-9">
       <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                    <thead>
                        <tr>
                        	<th >Nhân Viên Nhận</th>
                            <th >Ngày Nhận</th>
                             <th >Trạng Thái Nhận</th>
                              <th >Nhân Viên Trả</th>
                              <th >Ngày Trả</th>
                             <th >Trạng Thái Trả</th>
                          </tr>
                    </thead>
                    <tbody>
                     <c:if test = "${SP.phieuBaoHanh.ctBaoHanh.size() == 0}">
                    <tr><td colspan = "6">Chưa có chi tiết bảo hành !</td></tr>
                     
                     </c:if>
                   <c:if test = "${SP.phieuBaoHanh.ctBaoHanh.size() != 0}">
                    <c:forEach var="ct" items="${SP.phieuBaoHanh.ctBaoHanh}">
					<tr>
						<td>${ct.maNVNhan.maNV} - ${ct.maNVNhan.ho} ${ct.maNVNhan.ten}</td>
						<td>${ct.ngayNhan}</td>
						<td>${ct.trangThaiNhan}</td>
						<c:if test = "${ct.maNVTra == null}">
						<td colspan = "3">Chưa trả máy</td>
						</c:if>
						<c:if test = "${ct.maNVNhan != null}">
						<td>${ct.maNVTra.maNV} - ${ct.maNVTra.ho} ${ct.maNVTra.ten}</td>
						<td>${ct.ngayTra}</td>
						<td>${ct.trangThaiTra}</td>
						</c:if>
					</tr>
					</c:forEach>
					</c:if>
					
                    </tbody>
                    
       </table>
       </div>
       </div>
       
       </div>
       </form>
    </section>
    </main>
    <!-- End Contact -->
    <%@include file="footerQL.jsp" %>
</body>
</html>