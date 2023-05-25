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
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Tạo Phiếu Nhập</h4>
       <ul >
       	<li class = "pb-3"> Nhân Viên Đặt : <br>${NhanVien.maNV } - ${NhanVien.ho } ${NhanVien.ten }</li>
       		<li class = "pb-3"> Số Phiếu Nhập : ${soPhieuNhap}</li>
       	<li class = "pb-3"> Mã Đơn : ${DDH.maDDH }</li>
       	<li class = "pb-3"> Ngày Lập : ${NgayNhap}</li>
       	<li class = "pb-3"> Nhà Cung Cấp : ${DDH.maNCC.tenNCC}</li>
       </ul>
    <a class="btn btn-success btn-lg px-3"href="taophieunhap.htm?idDDH=${DDH.maDDH}&loaiBtn=luu">Lưu Phiếu Nhập</a>
    <a class="btn btn-success btn-lg px-3"href="taophieunhap.htm?idDDH=${DDH.maDDH}&loaiBtn=huy">Hủy</a>
       </div>
       </div>
       </div>
       <div class = "col-lg-9 mt-9">
       <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                    <thead>
                        <tr>
                        	<th width="200">Seri Sản Phẩm</th>
                            <th width="200">Mã Loại Sản Phẩm</th>
                            <th >Tên Sản Phẩm</th>
                          </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="sp" items="${listSPNhap}">
					<tr>
						<td>${sp.seri }</td>
						<td>${sp.maLoai.maLoai }</td>
						<td>${sp.maLoai.tenSP}</td>
					</tr>
					</c:forEach>
                    </tbody>
                    
       </table>
       </div>
       </div>
       
       </div>
    </section>
    </main>
    <!-- End Contact -->
    <%@include file="footerQL.jsp" %>
</body>
</html>