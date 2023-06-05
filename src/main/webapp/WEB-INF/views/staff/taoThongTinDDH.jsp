<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Tạo Đơn Đặt Hàng</title>
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
    	<form  action="taodondathang.htm" method="POST">
    	<a class="btn btn-success btn-lg px-3 mt-1"href="dondathang.htm"><i class="fa fa-angle-double-left" aria-hidden="true"></i>Về Đơn Đặt Hàng</a>
       <div class = "container py-5">
       
       <div class = "row">
       <div class="col-lg-3 mt-9">
       <div class = "card">
       <div class = "card-body">
   
       <h4>Tạo Đơn Đặt Hàng</h4>
       <ul >
       	<li class = "pb-3"><lable for="nhanVien">Nhân Viên Đặt :
       	</lable>
       	<br>
       	<p id = "nhanVien">${nhanVien.maNV} - ${nhanVien.ho} ${nhanVien.ten}</p>
       	
       	</li>
       	<li class = "pb-3" > Mã Đơn : <input type = "text" style="width: 100px;border :none"id ="maDDH" name = "maDDH" value ="${maDDH }" readonly="readonly"></li>
       	<li class = "pb-3"> Ngày Đặt : <input type="date" style="width: 150px;border :none" id="ngayDat" name="ngayDat" value="${ngayDat}" placeholder="dd/MM/yyyy" pattern="\d{2}/\d{2}/\d{4}" readonly="readonly"></li>
       	<c:if test = "${ CHECK.equals('NOT')})">
       	<li class = "pb-3">Hiện Không Có Nhà Cung Cấp Đủ Yêu Cầu Nhập Hàng !</li>
       	</c:if>
       
       	<li class = "pb-3"> Nhà Cung Cấp : <select type="text" id="NCC" name="NCC" style="padding: 10px; border: 1px solid #ccc; background-color: #fff; color: #333; cursor: pointer;">
       		
       		<c:forEach var="NCC" items="${DSNCC}">
    				<option value="${NCC.maNCC}">${NCC.maNCC} - ${NCC.tenNCC}</option>
			</c:forEach>

       		</select>
       		
       	</li>
       	 <button type="submit" class="btn btn-success btn-lg px-3">Tạo Đơn Đặt Hàng</button>
 	
       </ul>
       </div>
       </div>
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