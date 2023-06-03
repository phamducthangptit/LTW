<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Giỏ Hàng Chưa Duyệt</title>
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
    	<a class="btn btn-success btn-lg px-3 mt-1"href="duyetgiohang.htm"><i class="fa fa-angle-double-left" aria-hidden="true"></i>Về Danh Sách Giỏ Hàng</a>
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Chi Tiết Giỏ Hàng</h4>
       <ul >
       	<li class = "pb-3"> Nhân Viên Duyệt : <br>${nhanVien.maNV } - ${nhanVien.ho } ${nhanVien.ten }</li>
       		<li class = "pb-3"> ID Giỏ Hàng : ${gioHang.idGH}</li>
       	<li class = "pb-3"> Ngày Tạo : ${gioHang.ngayTao }</li>
       	<li class = "pb-3"> Email Khách Hàng: ${gioHang.email.email}</li>
       </ul>
       <c:if test = "${gioHang.trangThai == 1}">
    <a class="btn btn-success btn-lg px-3"href="duyet.htm?idGH=${gioHang.idGH}">Duyệt</a>
    </c:if>
    <c:if test = "${gioHang.trangThai ==2}">
  <h6 style="color: red;">Giỏ Hàng Đã Duyệt</h6>   
    </c:if>
       </div>
       </div>
       </div>
       <div class = "col-lg-9 mt-9">
       <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                    <thead>
                        <tr>
                       		<th>Hình Ảnh</th>
                            <th width="200">Mã Loại Sản Phẩm</th>
                            <th >Tên Sản Phẩm</th>
                            <th>Số Lượng </th>
                          </tr>
                    </thead>
                    <tbody>
               <c:if test="${not empty listSP}">
                    <c:forEach var="i"  begin="0" end ="${size}">
					<tr>
						 <td class="col-md-1">
                            <div class="card mb-2 product-wap rounded-0">
                              <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${listSP.get(i).maLoai.getAnh()}'/>">
                              </div>
                            </div>
                        </td> 
						<td>${listSP.get(i).maLoai.maLoai} </td>
						<td>${listSP.get(i).maLoai.tenSP}</td>
						<td>${listSL.get(i)}</td>
					</tr>
					</c:forEach>
					</c:if>
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