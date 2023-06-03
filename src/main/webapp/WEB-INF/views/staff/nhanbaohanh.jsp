<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Nhận Bảo Hành</title>
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
    	<form  action="nhanbaohanh.htm" method="POST">
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
      
       <h4>Bảo Hành</h4>
       <ul >
       
       	<li class = "pb-3"> Nhân Viên Nhận : <br>${nhanVien.maNV } - ${nhanVien.ho } ${nhanVien.ten }</li>
       
       	<li class = "pb-3">Seri Sản Phẩm Cần Tìm: <input type="text" class="form-control" id = "seri" name="seri" required="required" value="${seri }">
       	 <c:if test = "${SanPhamTim == null}">
                   
						<h6 style="color: red;">${ThongBao}</h6>                   
					
                    </c:if>
       	 <button type="submit" class="btn btn-success btn-lg px-3 mt-1" name ="timSeri" value = "timSeri">Tìm <i class="fa fa-search" aria-hidden="true"></i></button>
       	</li>
       	<li><a class="btn btn-success btn-lg px-3"href="danhsachdangbaohanh.htm">Sản Phẩm Đã Nhận</a></li>
       	<h6 style="color: red;">${ThongBao2}</h6>        
       </ul>
     	
       </div>
       </div>
       </div>

       <div class = "col-lg-9">
       <table class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
                    <thead>
                        <tr>
                        <th>Hình Ảnh</th>
                        	<th >Seri</th>
                            <th >Mã Loại</th>
                            <th >Tên Sản Phẩm</th>
                            <th>Thời Gian Bảo Hành</th>
                            <th>Chi Tiết</th>
                            <th>Đổi Trả</th>
                          </tr>
                    </thead>
                    <tbody>
                    
                    <c:if test = "${SanPhamTim != null}">
                    <tr>
                     	<td class="col-md-1">
                           <div class="card mb-2 product-wap rounded-0">
                               <div class="card rounded-0">
                                <img class="card-img rounded-0 img-fluid" src="<c:url value ='/resource/images/${SanPhamTim.maLoai.getAnh()}'/>">
                               </div>
                           </div>
                        </td> 
						<td>${SanPhamTim.seri }</td>
						<td>${SanPhamTim.maLoai.maLoai }</td>
						<td>${SanPhamTim.maLoai.tenSP}</td>
						<td>${SanPhamTim.phieuBaoHanh.ngayBatDau} đến ${SanPhamTim.phieuBaoHanh.ngayKetThuc}
						<c:if test ="${HetHan.equals('HetHan')}">
						<br>Hết Hạn Bảo Hành
						</c:if>
						</td>
						<td>
						<a  href="kiemtrabaohanh.htm?seri=${SanPhamTim.seri}"><i class="fa fa-bars" style = "color : black" aria-hidden="true"></i></a>
						</td>
						<c:if test ="${DoiTra.equals('DuocTra')}">
						<td><button type="submit" class="btn btn-success btn-lg px-3 " name ="traMay" value = "traMay">Trả Hàng</button></td>
						</c:if>
						<c:if test ="${DoiTra.equals('DaTra')}">
						<td>Đã Trả</td>
						</c:if>
						<c:if test ="${DoiTra == null}">
						<td>Hết hạn đổi trả</td>
						</c:if>
						<c:if test ="${HetHan == null}">
						<tr>
						<td colspan = "6">Trạng Thái Nhận Máy : <input type="text" class="form-control" 
						id = "trangThaiNhan" name="trangThaiNhan" 
						value="${trangThaiNhan }"  placeholder="Ví dụ : Lỗi Win,..." >
						<h6 style="color: red;">${ThongBao3}</h6>   
						</td>
						<td><button type="submit" class="btn btn-success btn-lg px-3 mt-2" name ="nhanMay" value = "nhanMay">Nhận Máy</button></td>
						</tr>

						</c:if>
						
						
						
					</tr>
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