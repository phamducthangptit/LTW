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
   		
    	<section class="bg-light">
    	<form  action="taotabledondathang.htm" method="POST">
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
       	<li class = "pb-3"> Ngày Đặt : <input type="date" id="ngayDat" name="ngayDat" value="${ngayDat}" placeholder="dd/MM/yyyy" pattern="\d{2}/\d{2}/\d{4}" required></li>
       	<li class = "pb-3"> Nhà Cung Cấp : <select type="text" id="NCC" name="NCC">
       		<c:forEach var="NCC" items="${DSNCC}">
    				<option value="${NCC.maNCC}" ${NCC.maNCC eq NCChientai ? 'selected' : ''}>${NCC.maNCC} - ${NCC.tenNCC}</option>
			</c:forEach>

       		</select>
       	</li>
       	 <button type="submit" class="btn btn-success btn-lg px-3" name ="luuDDH" value = "luuDDH">Lưu Đơn Đặt Hàng</button>
 
       </ul>
           	 <h6 style="color: red; margin-left: 15px">${ThongBao }</h6>
       </div>
       </div>
       </div>
       
       <div class = "col-lg-9">
      
       <table  class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
      	 <thead>
                        <tr>
                            <th >Sản Phẩm</th>
                            <th >Số lượng</th>
                            <th ></th>
                         </tr>
         
        </thead>
        		<tbody>
                    
					<tr>
						<td><select type="text" id="sanPham" name="sanPham">
       				<c:forEach var="sp" items="${DSSP}">
       				 <option value="${sp.maLoai}">${sp.tenSP}</option>
       				</c:forEach>
       				</select>
       				</td>
       				
						<td> <input type="number" size="4" name="soLuong" min="1" max="100" id="soLuong" value="1"   style="text-align: center;"></td>
						<!-- <td><ul class="list-inline pb-3">
                            <li class="list-inline-item text-right">
                            <input type="hidden" name="product-quanity" id="product-quanity" value="1">
                            </li>
                            <li class="list-inline-item"><span class="btn btn-success" id="btn-minus">-</span></li>
                            <li class="list-inline-item"><span class="badge bg-secondary" id="var-value">1</span></li>
                             <li class="list-inline-item"><span class="btn btn-success" id="btn-plus">+</span></li>
                             </ul>
                          </td> -->
						<td> <button type="submit" class="btn btn-success btn-lg px-3" name ="themDDH" value = "themDDH">Thêm</button></td>
					</tr>
		
                    </tbody>
                
                  
                   
       </table>
       <br>
    <table  class="table table-light table-striped table-hover bordered-dark"
				style="text-align: center;">
    		 <thead>
                        <tr>
                            <th>Sản Phẩm</th>
                            <th>Số lượng</th>
                            <th></th>
                          </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty listSP}">
                    <c:forEach var="i"  begin="0" end ="${doLon}">
					<tr>
						<td>${listSP.get(i).maLoai} - ${listSP.get(i).tenSP}</td>
						
						<td>${listSL.get(i)}</td>
						<td><a href="XoaSPtabledondathang.htm?maLoai=${listSP.get(i).maLoai}"><i class="fa fa-trash" aria-hidden="true"></i></a></td>
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