<%@page import="ptithcm.model.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Thêm Nhân Viên Mới</title>
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
	<div class="modal fade bg-white" id="templatemo_search" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="w-100 pt-1 mb-5 text-right">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<form action="" method="get"
				class="modal-content modal-body border-0 p-0">
				<div class="input-group mb-2">
					<input type="text" class="form-control" id="inputModalSearch"
						name="q" placeholder="Search ...">
					<button type="submit"
						class="input-group-text bg-success text-light">
						<i class="fa fa-fw fa-search text-white"></i>
					</button>
				</div>
			</form>
		</div>
	</div>


	<!-- Start Content Page -->
	<main>
		<section class="bg-light">
			<div class="container pd-5">
				<div class="card">
					<div class="card-body">
						<div class="container py-5">
							<h2>Thêm Nhân Viên Mới</h2>

							<form class="employee-list" action="themnhanvien.htm"
								method="POST">
								<h6 style="color: red; margin-left: 15px">${ErrorId }</h6>
								<h6 style="color: red; margin-left: 15px">${Error}</h6>
								<!-- Start Contact -->
								<div class="container py-5" lang="vi">
									<div class="row py-5">

										<div class="row">
											<div class="form-group col-md-6 mb-3">
												<label for="inputId">Mã Nhân Viên :</label> <input
													type="text" class="form-control mt-1" id="Id" name="Id" value="${maNV }"
													 readonly="readonly">
											</div>
											<div class="form-group col-md-6 mb-3">
												<label for="selectChucVu">Chức Vụ :</label> <br> <select style="padding: 10px; border: 1px solid #ccc; background-color: #fff; color: #333; cursor: pointer;"
													type="text" id="CV" name="CV" value="${CV }">
													<option value="NVBanHang">Nhân Viên Bán Hàng</option>
													<!-- <option value="NVBaoHanh">Nhân Viên Bảo Hành</option> -->
													<option value="NVGiaoHang">Nhân Viên Giao Hàng</option>
													<option value="QL">Quản Lí</option>
												</select>
											</div>
										</div>
										<div class="row">
											<div class="form-group col-md-6 mb-3">
												<label for="inputHo">Họ :</label> <input type="text"
													class="form-control mt-1" id="Ho" name="Ho" value="${Ho }"
													placeholder="Vui Lòng Không Bỏ Trống" required>
											</div>
											<div class="form-group col-md-6 mb-3">
												<label for="inputTen">Tên :</label> <input type="text"
													class="form-control mt-1" id="Ten" name="Ten"
													value="${Ten }" placeholder="Vui Lòng Không Bỏ Trống"
													required>
											</div>
										</div>
										<div class="row">
											<div class="form-group col-md-6 mb-3">
												<label for="birthdate">Ngày sinh:</label><br> <input
													type="date" id="birthdate" name="birthdate"
													value="${birthdate }" placeholder="dd/mm/yyyy"
													pattern="\d{2}/\d{2}/\d{4}" required>
													<h6 style="color: red; margin-left: 15px">${ErrorBD }</h6>
											</div>
										
											<div class="form-group col-md-6 mb-3">
												<label for="inputSDT">Số Điện Thoại :</label> <input
													type="tel" class="form-control mt-1" id="SDT" name="SDT"
													value="${SDT }" pattern="0[0-9]{9}"
													placeholder="Vui Lòng Không Bỏ Trống"
													title="Số điện thoại phải bao gồm 10 chữ số, bắt đầu bằng số 0 !" required>
											</div>
										</div>

										<div class="mb-3">
											<label for="inputEmail">Email :</label> <input type="email"
												class="form-control mt-1" id="email" name="email"
												value="${email }" placeholder="Vui Lòng Không Bỏ Trống"
												title="Email phải có dạng : Nguyen@Luan..." required>
												<h6 style="color: red; margin-left: 15px">${ErrorEmail }</h6>
										</div>
										
										
										<div class="mb-3">
											<label for="inputDiaChi">Địa Chỉ :</label> <input type="text"
												class="form-control mt-1" id="diaChi" name="diaChi"
												value="${diaChi }" placeholder="Vui Lòng Không Bỏ Trống"
												required>
										</div>
										<div class="row">
											<div class="col text-end mt-2">
												<button type="submit" class="btn btn-success btn-lg px-2">
													Thêm <i class="fa fa-check" aria-hidden="true"></i>
												</button>
												<a href="danhsachnhanvien.htm" class="btn btn-success btn-lg px-4">Hủy</a>
												<!-- <button type="submit" class="btn btn-success btn-lg px-3">Hủy</button> -->
											</div>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
	<!-- End Contact -->
	<%@include file="footerQL.jsp"%>
</body>
</html>