<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Tạo đợt giảm giá</title>
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

<!-- Load fonts style after rendering the layout styles -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="stylesheet"
	href="<c:url value='/resource/assets/css/fontawesome.min.css'/>">
<style>
.button-container {
	display: flex;
	justify-content: center;
}
</style>
<%@include file="headerNV.jsp"%>
</head>

<body>
	<section class="bg-light">
	<div class="container">
		<h2 style="text-align: center;">Tạo đợt giảm giá</h2>
		<form action="taodotgiamgia.htm" method="POST">
			<div class="row justify-content-center">
				<div class="col-sm-6">
					<div class="mb-3">
						<input type="text" value="${DGG.maDot }" name="maDot" hidden="hidden" /> 
						<label for="ngayBD" class="form-label">Ngày bắt đầu</label> 
						<input type="date" class="form-control" name="ngayBD" required="required" value="${DGG.ngayBatDau}">
					</div>

					<div class="mb-3">
						<h6 style="color: red;">${ErrorGG }</h6>
						<label for="ngayKT" class="form-label">Ngày kết thúc</label> <input
							type="date" class="form-control" name="ngayKT"
							required="required" value="${DGG.ngayKetThuc}">
					</div>

					<div class="mb-3">
						<label for="ten" class="form-label">Mô tả</label>
						<textarea name="moTa" rows="5" cols="70">${DGG.moTa}</textarea>
					</div>
					<div class="mb-3">
						<label for="ten" class="form-label">Số lượng loại sản phẩm
							được giảm giá:</label> <input type="number" class="form-control"
							name="slSP" required="required" min="1" max="${maxLSP }"
							value="${slSP }" />
					</div>
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4"
							style="width: 170px;" ${ErrorGG != '' ? '' : 'hidden'}
							name="taoDGG" value="taoDGG">Tạo đợt giảm giá</button>
					</div>
					<div class="mb-3" ${ErrorGG != '' ? 'hidden' : ''}>
						<h6 style="color: red;">${ErrorDGG }</h6>
						<table style="text-align: center;">
							<thead>
								<tr>
									<th scope="col">STT</th>
									<th scope="col">Loại sản phẩm</th>
									<th scope="col">Phần trăm giảm</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="1" end="${slSP }">
									<tr class="body">
										<td>${i }</td>
										<td><select name="maLSP" class="form-select"
											required="required">
												<c:forEach items="${listLoaiSP}" var="LSP">
													<option value="${LSP.maLoai}">${LSP.toString()}</option>
												</c:forEach>
										</select></td>
										<td><input type="number" class="form-control"
											name="phamTramGiam"  min="0" max="100" ${ErrorGG == '' ? 'required' : ''} />
										</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>

					</div>
					<div class="button-container">
						<button type="submit" class="btn btn-success mb-4"
							style="width: 170px;" ${ErrorGG != '' ? 'hidden' : ''}
							name="luuDGG" value="luuDGG">Lưu đợt giảm giá</button>
					</div>
				</div>

			</div>
		</form>
	</div>
</section>
	<%@include file="footerQL.jsp" %>
</body>
</html>