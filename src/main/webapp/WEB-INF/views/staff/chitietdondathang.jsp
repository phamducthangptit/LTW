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
    	<section class="bg-light">
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Chi Tiết Đơn Đặt Hàng</h4>
       <ul >
       	<li class = "pb-3"> Nhân Viên Đặt : <br>${DDH.maNV.maNV } - ${DDH.maNV.ho } ${DDH.maNV.ten }</li>
       	<li class = "pb-3"> Mã Đơn : ${DDH.maDDH }</li>
       	<li class = "pb-3"> Ngày Đặt : ${DDH.ngayDat }</li>
       	<li class = "pb-3"> Nhà Cung Cấp : ${DDH.maNCC.tenNCC}</li>
       </ul>
     
       </div>
       </div>
       </div>
       <div class = "col-lg-9">
       <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                    <thead>
                        <tr>
                        	<th class="col-md-2">Hình Ảnh</th>
                            <th width="200">Mã Loại Sản Phẩm</th>
                            <th >Tên Sản Phẩm</th>
                            <th width="150">Số lượng</th>
                            <th width="200">Đơn giá </th>
                          </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ct" items="${DDH.getCtDonDatHang()}">
					<tr>
						 <td class="col-md-2">
                           <div class="card mb-2 product-wap rounded-0">
                               <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${ct.maLoai.getAnh()}'/>">
                               </div>
                           </div>
                        </td> 
						<td>${ct.maLoai.maLoai }</td>
						<td>${ct.maLoai.tenSP }</td>
						<td>${ct.soLuong }</td>
						<td>${ct.donGia.intValue() } VNĐ</td>
	
					</tr>
					</c:forEach>
					<tr>
					<td colspan = "3"></td>
					<td> Tổng cộng :</td>
					<td>${Tong} VNĐ</td>
					</tr>
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