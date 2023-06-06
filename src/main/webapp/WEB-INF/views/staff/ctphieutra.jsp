<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Chi Tiết Phiếu Trả</title>
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

</head>
<body>
 <!-- Modal -->
   

    <!-- Start Content Page -->
    <main>
    	<section class="bg-light">
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Phiếu Trả</h4>
       <ul >
       	<li class = "pb-3"> Nhân Viên Trả : <br>${sp.soPhieuTra.maNV.maNV } - ${sp.soPhieuTra.maNV.ho } ${sp.soPhieuTra.maNV.ten }</li>
       		<li class = "pb-3"> Số Phiếu Trả : ${sp.soPhieuTra.soPhieuTra}</li>
       	<li class = "pb-3"> Số Hóa Đơn : ${sp.soPhieuTra.soHoaDon.soHoaDon }</li>
       	<li class = "pb-3"> Ngày Trả : ${sp.soPhieuTra.ngayTra}</li>

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
                        	<th width="200">Seri Sản Phẩm</th>
                            <th width="200">Mã Loại Sản Phẩm</th>
                            <th >Tên Sản Phẩm</th>
                            <th>Đơn Giá Bán</th>
                          </tr>
                    </thead>
                    <tbody>
					<tr>
						 <td class="col-md-2">
                                 <div class="card mb-2 product-wap rounded-0">
                                 <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${sp.maLoai.getAnh()}'/>">
                                	</div>
                                	</div>
                          </td> 
						<td class="text-center align-middle">${sp.seri }</td>
						<td class="text-center align-middle">${sp.maLoai.maLoai }</td>
						<td class="text-center align-middle">${sp.maLoai.tenSP}</td>
						<td class="text-center align-middle">${sp.maLoai.gia.intValue()} VND</td>
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