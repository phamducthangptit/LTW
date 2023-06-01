package ptithcm.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.model.CTBaoHanh;
import ptithcm.model.CTDonDatHang;
import ptithcm.model.CTDotGiamGia;
import ptithcm.model.DoanhThuTheoNgay;
import ptithcm.model.DonDatHang;
import ptithcm.model.DotGiamGia;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.NhaCungCap;
import ptithcm.model.NhanVien;
import ptithcm.model.PhieuNhap;
import ptithcm.model.SanPham;
@Transactional
@Controller

public class StaffController {
	@Autowired
	SessionFactory factory;

	ArrayList<LoaiSanPham> listSP = new ArrayList<LoaiSanPham>();
	ArrayList<Integer> listSL = new ArrayList<Integer>();
	ArrayList<SanPham> listSPNhap = new ArrayList<SanPham>();
	 String NCChientai = "";
	 String ngayHienTai = "";
		@RequestMapping(value = "/taodotgiamgia")
		public String taoDotGiamGia(Model model) {
			String hql = "FROM LoaiSanPham";
			Session session = factory.getCurrentSession();
			Query query = session.createQuery(hql);
			model.addAttribute("maxLSP", query.list().size());
			return "staff/taodotgiamgia";
		}

		@RequestMapping(value = "/taodotgiamgia", method = RequestMethod.POST)
		public String luuDotGiamGia(HttpServletRequest request, Model model, HttpSession session,
				@RequestParam(value = "taoDGG", required = false) String taoDGG,
				@RequestParam(value = "luuDGG", required = false) String luuDGG) {
			Session s = factory.getCurrentSession();
			if (taoDGG != null) {// neu click vao button tao dgg
				String nBD = request.getParameter("ngayBD");
				Date ngayBD = Date.valueOf(nBD);
				String nKT = request.getParameter("ngayKT");
				Date ngayKT = Date.valueOf(nKT);
				String moTa = request.getParameter("moTa");
				int soLuongSPGG = Integer.parseInt(request.getParameter("slSP"));
				Object user = session.getAttribute("user");
				NhanVien nhanVien = new NhanVien();
				nhanVien = (NhanVien) user;
				String hql = "FROM DotGiamGia";
				Query query = s.createQuery(hql);
				DotGiamGia dotGG = new DotGiamGia("", ngayBD, ngayKT, moTa, nhanVien);
				dotGG.setMaDot("DGG" + (query.list().size() + 1));
				if (ngayBD.compareTo(ngayKT) <= 0) { // ngay bd nho hon ngay kt -> true
					s.save(dotGG);
					model.addAttribute("ErrorGG", "");

				} else {
					model.addAttribute("ErrorGG", "Ngày kết thúc không hợp lệ, vui lòng chọn lại!");
				}
				hql = "FROM LoaiSanPham";
				query = s.createQuery(hql);
				model.addAttribute("listLoaiSP", query.list());
				model.addAttribute("DGG", dotGG);
				model.addAttribute("slSP", soLuongSPGG);

				return "staff/taodotgiamgia";
			} else if (luuDGG != null) {
				model.addAttribute("ErrorGG", "");
				String[] listSPGG = request.getParameterValues("maLSP");
				String[] listPTGiamGia = request.getParameterValues("phamTramGiam");
				int n = listPTGiamGia.length;
				String maDot = request.getParameter("maDot");
				// check xem 1 loại sp có được giảm giá trong cùng 1 lần hay không
				Set<String> setSPGG = new HashSet<>();
				for (String string : listSPGG) {
					setSPGG.add(string);
				}
				if (setSPGG.size() != listSPGG.length) {
					model.addAttribute("ErrorDGG", "Đã trùng loại sản phẩm trong đợt giảm giá!Vui lòng chọn lại!");

					String nBD = request.getParameter("ngayBD");
					Date ngayBD = Date.valueOf(nBD);
					String nKT = request.getParameter("ngayKT");
					Date ngayKT = Date.valueOf(nKT);
					String moTa = request.getParameter("moTa");

					int soLuongSPGG = Integer.parseInt(request.getParameter("slSP"));
					Object user = session.getAttribute("user");
					NhanVien nhanVien = new NhanVien();
					nhanVien = (NhanVien) user;
					DotGiamGia dotGG = new DotGiamGia(maDot, ngayBD, ngayKT, moTa, nhanVien);

					model.addAttribute("DGG", dotGG);
					model.addAttribute("slSP", soLuongSPGG);
					String hql = "FROM LoaiSanPham";
					Query query = s.createQuery(hql);
					model.addAttribute("listLoaiSP", query.list());
					return "staff/taodotgiamgia";
				} else {
					for (int i = 0; i < n; i++) {
						DotGiamGia dgg = (DotGiamGia) s.get(DotGiamGia.class, maDot);
						LoaiSanPham lsp = (LoaiSanPham) s.get(LoaiSanPham.class, listSPGG[i]);
						CTDotGiamGia ctDGG = new CTDotGiamGia(Integer.parseInt(listPTGiamGia[i]), lsp, dgg);
						s.save(ctDGG);
					}
				}
			}
			String hql = "from DotGiamGia";
			Query query = s.createQuery(hql);
			List<DotGiamGia> listDGG = query.list();
			model.addAttribute("listDGG", listDGG);
			return "staff/danhsachdotgiamgia";
		}

		@RequestMapping(value = "/danhsachdotgiamgia")
		public String danhSachDotGG(Model model) {
			Session session = factory.getCurrentSession();
			String hql = "from DotGiamGia";
			Query query = session.createQuery(hql);
			List<DotGiamGia> listDGG = query.list();
			model.addAttribute("listDGG", listDGG);
			return "staff/danhsachdotgiamgia";
		}

		@RequestMapping(value = "/ctdotgg")
		public String chiTietDotGG(HttpServletRequest request, Model model) {

			String maDot = request.getParameter("madot");
			Session session = factory.getCurrentSession();

			String hql = "FROM CTDotGiamGia CTDGG WHERE CTDGG.maDot.maDot = :maDot";
			Query query = session.createQuery(hql);
			query.setParameter("maDot", maDot);
			List<CTDotGiamGia> listCTDGG = query.list();

			DotGiamGia dotGiamGia = (DotGiamGia) session.get(DotGiamGia.class, maDot);

			model.addAttribute("listCTDGG", listCTDGG);
			model.addAttribute("DGG", dotGiamGia);

			return "staff/ctdotgiamgia";
		}

		@RequestMapping(value = "/ctdotgg", method = RequestMethod.POST)
		public String luuThongTinDotGG(HttpServletRequest request, Model model) {
			Session session = factory.getCurrentSession();

			String nBD = request.getParameter("ngayBD");
			Date ngayBD = Date.valueOf(nBD);

			String nKT = request.getParameter("ngayKT");
			Date ngayKT = Date.valueOf(nKT);

			String moTa = request.getParameter("moTa");

			String[] listPTGiamGia = request.getParameterValues("phamTramGiam");
			String maDot = request.getParameter("maDot");

			DotGiamGia dotGiamGia = (DotGiamGia) session.get(DotGiamGia.class, maDot);
			dotGiamGia.setNgayBatDau(ngayBD);
			dotGiamGia.setNgayKetThuc(ngayKT);
			dotGiamGia.setMoTa(moTa);

			String hql = "FROM CTDotGiamGia CTDGG WHERE CTDGG.maDot.maDot = :maDot";
			Query query = session.createQuery(hql);
			query.setParameter("maDot", maDot);
			List<CTDotGiamGia> listCTDGG = query.list();

			if (ngayBD.compareTo(ngayKT) <= 0) { // true
				session.merge(dotGiamGia); // update lai ngay kt bat dau

				// update lai phan tram giam gia cho cac san pham
				hql = "UPDATE CTDotGiamGia CTDGG SET CTDGG.tiLeGiam = :tiLeGiam WHERE CTDGG.maDot.maDot = :maDot AND CTDGG.maLoai.maLoai = :maLoai";
				query = session.createQuery(hql);
				int i = 0;
				for (String tiLeGiam : listPTGiamGia) {
					query.setParameter("tiLeGiam", Integer.parseInt(tiLeGiam));
					listCTDGG.get(i).setTiLeGiam(Integer.parseInt(tiLeGiam));
					query.setParameter("maDot", maDot);
					query.setParameter("maLoai", listCTDGG.get(i++).getMaLoai().getMaLoai());
					query.executeUpdate();
				}
			} else {
				model.addAttribute("ErrorGG", "Ngày kết thúc không hợp lệ, vui lòng chọn lại!");
			}

			dotGiamGia = (DotGiamGia) session.get(DotGiamGia.class, maDot);

			model.addAttribute("DGG", dotGiamGia);
			model.addAttribute("listCTDGG", listCTDGG);

			return "staff/ctdotgiamgia";
		}

		@RequestMapping(value = "/timkiemdgg", method = RequestMethod.POST)
		public String timKiemDGG(HttpServletRequest request, Model model) {
			String idDGG = request.getParameter("iddgg");
			String hql = "FROM DotGiamGia DGG WHERE DGG.maDot = :iddgg";
			Session session = factory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.setParameter("iddgg", idDGG);
			model.addAttribute("listDGG", query.list());
			return "staff/danhsachdotgiamgia";
		}

		@RequestMapping(value = "/deletedotgg")
		public String deleteDotGG(HttpServletRequest request, Model model) {
			String maDot = request.getParameter("madot");

			Session session = factory.getCurrentSession();
			String hql = "DELETE FROM CTDotGiamGia WHERE maDot.maDot = :maDot";
			Query query = session.createQuery(hql);
			query.setParameter("maDot", maDot);
			query.executeUpdate();

			DotGiamGia dgg = (DotGiamGia) session.get(DotGiamGia.class, maDot);
			session.delete(dgg);

			hql = "from DotGiamGia";
			query = session.createQuery(hql);
			List<DotGiamGia> listDGG = query.list();

			model.addAttribute("listDGG", listDGG);
			return "redirect:/danhsachdotgiamgia.htm";
		}

		@RequestMapping(value = "/themnhacungcap")
		public String themNhaCC() {
			return "staff/themnhacungcap";
		}

		@RequestMapping(value = "/themnhacungcap", method = RequestMethod.POST)
		public String luuNhaCC(HttpServletRequest request, Model model) {
			Session session = factory.getCurrentSession();

			String tenNCC = request.getParameter("tenNCC");
			String diaChi = request.getParameter("diaChi");
			String email = request.getParameter("email");
			String sdt = request.getParameter("SDT");

			String hql = "FROM NhaCungCap";
			Query query = session.createQuery(hql);
			NhaCungCap nhaCC = new NhaCungCap("NCC" + query.list().size(), tenNCC, diaChi, sdt, email);

			model.addAttribute("nhaCC", nhaCC);

			hql = "FROM NhaCungCap NCC WHERE NCC.tenNCC = :tenNCC";
			query = session.createQuery(hql);
			query.setParameter("tenNCC", tenNCC);
			if (query.list().size() == 0) { // check ten ncc
				session.save(nhaCC);
				hql = "FROM NhaCungCap";
				query = session.createQuery(hql);
				model.addAttribute("listNCC", query.list());
				return "redirect:/danhsachnhacungcap.htm";
			} else {
				model.addAttribute("ErrorTenNCC", "Tên nhà cung cấp đã tồn tại!");
				return "staff/themnhacungcap";
			}
		}

		@RequestMapping(value = "/danhsachnhacungcap")
		public String danhSachNCC(Model model) {
			Session session = factory.getCurrentSession();
			String hql = "FROM NhaCungCap";
			Query query = session.createQuery(hql);
			model.addAttribute("listNCC", query.list());
			return "staff/danhsachnhacungcap";
		}

		@RequestMapping(value = "/ctncc")
		public String chiTietNCC(@RequestParam("id") String id, Model model) {
			Session session = factory.getCurrentSession();
			String hql = "FROM NhaCungCap NCC WHERE NCC.maNCC = :mancc";
			Query query = session.createQuery(hql);
			query.setParameter("mancc", id);
			NhaCungCap nhaCungCap = (NhaCungCap) query.list().get(0);
			model.addAttribute("nhaCC", nhaCungCap);
			model.addAttribute("tontaiDDH", nhaCungCap.getDonDatHang().size());
			return "staff/ctnhacungcap";
		}

		@RequestMapping(value = "/ctncc", method = RequestMethod.POST)
		public String luuThongTinNCC(HttpServletRequest request, Model model) {
			String maNCC = request.getParameter("maNCC");
			String tenNCC = request.getParameter("tenNCC");
			String diaChi = request.getParameter("diaChi");
			String email = request.getParameter("email");
			String sdt = request.getParameter("SDT");
			Session session = factory.getCurrentSession();
			NhaCungCap nhaCungCap = (NhaCungCap) session.get(NhaCungCap.class, maNCC);
			String hql = "FROM NhaCungCap NCC WHERE NCC.tenNCC = :tenNCC AND NCC.maNCC <> :maNCC";
			Query query = session.createQuery(hql);
			query.setParameter("tenNCC", tenNCC);
			query.setParameter("maNCC", maNCC);
			if (query.list().size() != 0) {
				model.addAttribute("ErrorTenNCC", "Tên nhà cung cấp đã tồn tại!");
			} else {
				nhaCungCap.setTenNCC(tenNCC);
				nhaCungCap.setDiaChi(diaChi);
				nhaCungCap.setEmail(email);
				nhaCungCap.setSdt(sdt);
			}
			model.addAttribute("tontaiDDH", nhaCungCap.getDonDatHang().size());
			model.addAttribute("nhaCC", nhaCungCap);
			return "staff/ctnhacungcap";

		}

		@RequestMapping(value = "/timkiemncc", method = RequestMethod.POST)
		public String timKiemNCC(HttpServletRequest request, Model model) {
			String idNCC = request.getParameter("idncc");
			String hql = "FROM NhaCungCap NCC WHERE NCC.maNCC = :idncc";
			Session session = factory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.setParameter("idncc", idNCC);
			model.addAttribute("listNCC", query.list());
			return "staff/danhsachnhacungcap";
		}

		@RequestMapping(value = "/deletencc")
		public String deleteNCC(@RequestParam("id") String id, Model model) {
			Session session = factory.getCurrentSession();
			NhaCungCap nhaCungCapCheck = (NhaCungCap) session.get(NhaCungCap.class, id);
			if (nhaCungCapCheck.getDonDatHang().size() == 0) {
				session.delete(nhaCungCapCheck);
			}
			String hql = "FROM NhaCungCap";
			Query query = session.createQuery(hql);
			model.addAttribute("listNCC", query.list());
			return "redirect:/danhsachnhacungcap.htm";
		}

		@RequestMapping(value = "/doanhthutheongay")
		public String viewThongKeTheoNgay(Model model) {
			model.addAttribute("XF", 0);
			return "staff/doanhthutheongay";
		}

		@RequestMapping(value = "/doanhthutheongay", method = RequestMethod.POST)
		public String hienThiCTDoanhThu(HttpServletRequest request, Model model,
				@RequestParam(value = "XuatExcel", required = false) String xuatExcel)
				throws FileNotFoundException, IOException {
			String ngay = request.getParameter("ngayTK");
			Session session = factory.getCurrentSession();
			String D = ngay.substring(8, 10);
			String M = ngay.substring(5, 7);
			String Y = ngay.substring(0, 4);
			List<DoanhThuTheoNgay> resultKQ = new ArrayList<>();
			try {

				Query query = ((SQLQuery) session.createSQLQuery("EXEC SP_THONGKEDOANHTHUTHEONGAY :d, :m, :y")
						.setParameter("d", Integer.parseInt(D), StandardBasicTypes.INTEGER)
						.setParameter("m", Integer.parseInt(M), StandardBasicTypes.INTEGER)
						.setParameter("y", Integer.parseInt(Y), StandardBasicTypes.INTEGER))
						.addEntity(DoanhThuTheoNgay.class);
				resultKQ = query.list();
				model.addAttribute("doanhThuNgay", resultKQ);
				model.addAttribute("ngayTK", ngay);
				model.addAttribute("XF", resultKQ.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (xuatExcel != null) { // click vao xuat excel
				// tạo workbook mới
				Workbook workbook = new XSSFWorkbook();

				// tạo trang mới
				Sheet sheet = workbook.createSheet("Doanh thu ngày " + D + "-" + M + "-" + Y);

				// Tạo một hàng mới và đặt dữ liệu vào các ô(header)
				Row headerRow = sheet.createRow(0);
				headerRow.createCell(0).setCellValue("STT");
				headerRow.createCell(1).setCellValue("Mã loại");
				headerRow.createCell(2).setCellValue("Tên sản phẩm");
				headerRow.createCell(3).setCellValue("Giá bán (VND)");
				headerRow.createCell(4).setCellValue("Khuyến mãi (%)");
				headerRow.createCell(5).setCellValue("Giá bán ra (VND)");

				// đặt dữ liệu vào ô
				int i = 1;
				for (DoanhThuTheoNgay dt : resultKQ) {
					Row dataRow = sheet.createRow(i);
					dataRow.createCell(0).setCellValue(dt.getId());
					dataRow.createCell(1).setCellValue(dt.getMaLoai());
					dataRow.createCell(2).setCellValue(dt.getTenSP());
					dataRow.createCell(3).setCellValue(dt.getGiaBan());
					dataRow.createCell(4).setCellValue(dt.getGiamGia());
					dataRow.createCell(5).setCellValue(dt.getGiaBan());
					i++;
				}
				Row dataRow = sheet.createRow(i);
				dataRow.createCell(0).setCellValue("Tổng:");
				dataRow.createCell(1).setCellValue(resultKQ.size());

				int tongTien = 0;
				for (DoanhThuTheoNgay dt : resultKQ) {
					tongTien += dt.getGiaBanRa();
				}
				dataRow = sheet.createRow(++i);
				dataRow.createCell(0).setCellValue("Tổng tiền:");
				dataRow.createCell(5).setCellValue(tongTien);
				String desktopPath = "E:\\" + "Doanh thu ngày " + D + "-" + M + "-" + Y + ".xlsx";
				try (FileOutputStream outputStream = new FileOutputStream(desktopPath)) {
					workbook.write(outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Đóng workbook
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return "staff/doanhthutheongay";
		}

		@RequestMapping(value = "/doanhthutheothang")
		public String viewThongKeTheoThang(Model model) {
			model.addAttribute("XF", 0);
			return "staff/doanhthutheothang";
		}

		@RequestMapping(value = "/doanhthutheothang", method = RequestMethod.POST)
		public String hienThiCTDoanhThuTheoThang(HttpServletRequest request, Model model,
				@RequestParam(value = "XuatExcel", required = false) String xuatExcel) {
			String thang = request.getParameter("thang");
			String nam = request.getParameter("nam");
			Session session = factory.getCurrentSession();
			String hql = "SELECT DAY(ngayTao) FROM GioHang GH WHERE MONTH(GH.ngayTao) = :m AND YEAR(GH.ngayTao) = :y "
					+ "AND GH.trangThai = 3";
			Query q = session.createQuery(hql);
			q.setParameter("m", Integer.parseInt(thang));
			q.setParameter("y", Integer.parseInt(nam));
			List<Integer> ngayCoDoanhThu = q.list();
			List<DoanhThuTheoNgay> resultKQ = new ArrayList<>();
			for (Integer day : ngayCoDoanhThu) {
				Session s = factory.openSession();
				Query query = ((SQLQuery) s.createSQLQuery("EXEC SP_THONGKEDOANHTHUTHEONGAY :d, :m, :y")
						.setParameter("d", day, StandardBasicTypes.INTEGER)
						.setParameter("m", Integer.parseInt(thang), StandardBasicTypes.INTEGER)
						.setParameter("y", Integer.parseInt(nam), StandardBasicTypes.INTEGER))
						.addEntity(DoanhThuTheoNgay.class);
				resultKQ.addAll(query.list());
				s.close();
			}

			model.addAttribute("doanhThuTungNgay", resultKQ);
			model.addAttribute("thang", thang);
			model.addAttribute("nam", nam);
			model.addAttribute("XF", resultKQ.size());
			if (xuatExcel != null) { // click vao xuat excel
				// tạo workbook mới
				Workbook workbook = new XSSFWorkbook();

				// tạo trang mới
				Sheet sheet = workbook.createSheet("Doanh thu tháng " + thang + "-" + nam);

				// Tạo một hàng mới và đặt dữ liệu vào các ô(header)
				Row headerRow = sheet.createRow(0);
				headerRow.createCell(0).setCellValue("Mã loại");
				headerRow.createCell(1).setCellValue("Tên sản phẩm");
				headerRow.createCell(2).setCellValue("Giá bán (VND)");
				headerRow.createCell(3).setCellValue("Khuyến mãi (%)");
				headerRow.createCell(4).setCellValue("Giá bán ra (VND)");

				// đặt dữ liệu vào ô
				int i = 1;
				for (DoanhThuTheoNgay dt : resultKQ) {
					Row dataRow = sheet.createRow(i);
					dataRow.createCell(0).setCellValue(dt.getMaLoai());
					dataRow.createCell(1).setCellValue(dt.getTenSP());
					dataRow.createCell(2).setCellValue(dt.getGiaBan());
					dataRow.createCell(3).setCellValue(dt.getGiamGia());
					dataRow.createCell(4).setCellValue(dt.getGiaBan());
					i++;
				}
				Row dataRow = sheet.createRow(i);
				dataRow.createCell(0).setCellValue("Tổng:");
				dataRow.createCell(1).setCellValue(resultKQ.size());

				int tongTien = 0;
				for (DoanhThuTheoNgay dt : resultKQ) {
					tongTien += dt.getGiaBanRa();
				}
				dataRow = sheet.createRow(++i);
				dataRow.createCell(0).setCellValue("Tổng tiền:");
				dataRow.createCell(4).setCellValue(tongTien);
				String desktopPath = "E:\\" + "Doanh thu tháng " + thang + "-" + nam + ".xlsx";
				try (FileOutputStream outputStream = new FileOutputStream(desktopPath)) {
					workbook.write(outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Đóng workbook
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return "staff/doanhthutheothang";
		}
	
	@RequestMapping(value = "danhsachnhanvien")
	public String danhsachnhanvien(ModelMap model) {
		org.hibernate.Session session = factory.getCurrentSession();
		String hql =  "FROM NhanVien";
		Query query = session.createQuery(hql);
		List<NhanVien> list =	query.list(); 
		model.addAttribute("DSNhanVien", list);
		return "staff/danhsachnhanvien";
	}
	
	@RequestMapping(value = "trangthainhanvien")
	public String trangthainhanvien(ModelMap model , HttpServletRequest request)
	{
		Session session = factory.getCurrentSession();
		String hql =  "FROM NhanVien where maNV = :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", request.getParameter("idnv"));
		NhanVien nv = (NhanVien) query.uniqueResult();
		if (request.getParameter("TT").equals("1"))
		{
			nv.setTrangThai(0);
			session.save(nv);
		}
		else 
		{
			nv.setTrangThai(1);
			session.save(nv);
		}
		
		return "redirect:danhsachnhanvien.htm";
	}
	
	@RequestMapping("themnhanvien")
	public String Showthemnhanvien(Model model)
	{
		Session session = factory.getCurrentSession();
		String hql =  "FROM NhanVien";
		Query query = session.createQuery(hql);
		List<NhanVien> listNV =	query.list(); 
		String maNV = "NV" + String.format("%03d", (listNV.size()+1));
		model.addAttribute("maNV",maNV);
		model.addAttribute("Error", " ");
		model.addAttribute("ErrorEmail", " ");
		return "staff/themnhanvien";
	}
	@RequestMapping(value = "/themnhanvien", method = RequestMethod.POST)
	public String themnhanvien(HttpServletRequest request, Model model)
	{
		String maNV = request.getParameter("Id");
		  String ho = request.getParameter("Ho"); 
		  String ten = request.getParameter("Ten"); String chucVu = request.getParameter("CV");
		  String email = request.getParameter("email");
		  String SDT = request.getParameter("SDT"); 
		  String Bdate= request.getParameter("birthdate");
		  Date ngaySinh = Date.valueOf(Bdate); 
		  String diaChi =request.getParameter("diaChi");
		 
	
		Session session = factory.getCurrentSession();
		
		String hql = "FROM NhanVien WHERE email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);

		if (query.list().size() == 0) {
			
			 NhanVien nhanVien = new NhanVien(maNV, ho, ten, ngaySinh, SDT,email,"12345678",diaChi,1,chucVu);
			 

			session.save(nhanVien); 
			model.addAttribute("Error", "Thêm nhân viên mới thành công!");
			return "staff/themnhanvien";
		}
		else {

			model.addAttribute("ErrorEmail", "*Vui lòng nhập email khác! Email này đã được sử dụng!");
			model.addAttribute("maNV", maNV); 
			model.addAttribute("Ho", ho);
			model.addAttribute("Ten", ten); 
			model.addAttribute("birthdate", ngaySinh);
			model.addAttribute("SDT", SDT); 
			model.addAttribute("diaChi", diaChi);
			model.addAttribute("CV", chucVu);
			 
			return "staff/themnhanvien";
		}
		
		
	}
	@RequestMapping("suathongtinnhanvien")
	public String Showsuathongtinnhanvien(Model model, HttpServletRequest request)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien WHERE maNV = :maNV";
	    Query query = session.createQuery(hql);
	    query.setParameter("maNV", request.getParameter("idnv"));
	    List<NhanVien> nhanVien = query.list();
		model.addAttribute("nv", nhanVien.get(0));
		return "staff/suathongtinnhanvien";
	}
	@RequestMapping(value = "suathongtinnhanvien",  method = RequestMethod.POST)
	public String suathongtinnhanvien(HttpServletRequest request, Model model)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien WHERE maNV = :maNV";
	    Query query = session.createQuery(hql);
	    query.setParameter("maNV", request.getParameter("Id"));
	    List<NhanVien> listNhanVien = query.list();
	    NhanVien nhanVien = listNhanVien.get(0);
	    nhanVien.setMaNV(request.getParameter("Id"));
	    nhanVien.setHo(request.getParameter("Ho"));
	    nhanVien.setTen(request.getParameter("Ten"));
	    String Bdate= request.getParameter("birthdate");
		 Date ngaySinh = Date.valueOf(Bdate); 
		nhanVien.setNgaySinh(ngaySinh);
		nhanVien.setDiaChi(request.getParameter("diaChi"));
		nhanVien.setsDT(request.getParameter("SDT"));
		nhanVien.setEmail(request.getParameter("email"));
		nhanVien.setRole(request.getParameter("CV"));
		model.addAttribute("nv", nhanVien);
		model.addAttribute("ThongBao","Cập nhật thông tin thành công !");
		session.update(nhanVien);
		return "staff/suathongtinnhanvien";
	}
	
	/*
	 * //tạo seri public static String taoSeriSanPham() { Random random = new
	 * Random(); StringBuilder sb = new StringBuilder(); int firstDigit =
	 * random.nextInt(9) + 1; sb.append(firstDigit); for (int i = 0; i < 10; i++) {
	 * int digit = random.nextInt(10); sb.append(digit); }
	 * 
	 * return sb.toString(); }
	 */
	@RequestMapping(value = "dondathang")
	public String dondathang(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql =  "FROM DonDatHang";
		Query query = session.createQuery(hql);
		List<DonDatHang> list =	query.list(); 
		listSPNhap.clear();
		listSL.clear();
		listSP.clear();
		listSPNhap.clear();
		model.addAttribute("DSDDH", list);
		
	 	model.addAttribute("ThongBao","  ");
		return "staff/dondathang";
	}
	
	
	@RequestMapping(value = "taodondathang")
	public String showtaodondathang(ModelMap model,HttpServletRequest request) {
		HttpSession s = request.getSession();
		Object user = s.getAttribute("user");
		NhanVien nhanvien = new NhanVien();
		nhanvien = (NhanVien) user;
		org.hibernate.Session session = factory.getCurrentSession();
		String hql =  "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		List<NhanVien> DSNCC =	query.list(); 
		hql = "FROM LoaiSanPham";
		Query query2 = session.createQuery(hql);
		List<LoaiSanPham> list2 =	query2.list(); 
		
		hql = "from DonDatHang";
		Query query1 = session.createQuery(hql);
		List<NhanVien> ddh =	query1.list(); 
		String maDon = "DDH" + String.format("%03d", (ddh.size()+1));
		model.addAttribute("DSNCC", DSNCC);
		model.addAttribute("DSSP", list2);
		model.addAttribute("nhanVien",nhanvien);
		model.addAttribute("maDDH",maDon);
		return "staff/taodondathang";
	}

	@RequestMapping("chitietdondathang")
	public String showchitietdondathang(Model model, HttpServletRequest request)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM DonDatHang WHERE maDDH = :maDDH";
	    Query query = session.createQuery(hql);
	    query.setParameter("maDDH", request.getParameter("maDDH"));
	    List<DonDatHang> donDatHang = query.list();
        Hibernate.initialize(donDatHang.get(0).getCtDonDatHang());
        int sum = 0;
        for(CTDonDatHang ct : donDatHang.get(0).getCtDonDatHang())
        {
        	sum = sum + ct.getSoLuong()*ct.getDonGia().intValue();
        }
		model.addAttribute("DDH", donDatHang.get(0));
		model.addAttribute("Tong" , sum);
		return "staff/chitietdondathang";
	}
	@RequestMapping("detetedondathang")
		public String dondatHangDeleted(Model model, HttpServletRequest request)
		{
		
			Session session = factory.getCurrentSession();
			String hql = "FROM DonDatHang WHERE maDDH = :maDDH";
		    Query query = session.createQuery(hql);
		    query.setParameter("maDDH", request.getParameter("idDDH"));
		    DonDatHang donDat = (DonDatHang) query.uniqueResult();
		    if (donDat.getSoPhieuNhap() == null)
		    {
		    	String hql1 = "DELETE FROM DonDatHang WHERE maDDH = :maDDH";
		    	Query query1 = session.createQuery(hql1);
		    	query1.setParameter("maDDH", request.getParameter("idDDH"));
		    	int rowsAffected = query1.executeUpdate();
		    	model.addAttribute("ThongBao","Xóa đơn đặt hàng thành công !");
		    	
		    }
		    else 
		    {
		    	model.addAttribute("ThongBao","Đơn hàng đã có phiếu nhập không thể xóa !");
		  
		    }
		    
		    String hql2 =  "FROM DonDatHang";
			Query query2 = session.createQuery(hql2);
			List<DonDatHang> list =	query2.list(); 
			model.addAttribute("DSDDH", list);
		    
			return "staff/dondathang";
		}
	 
	@RequestMapping("taotabledondathang")
	public String taotabledondathang(Model model, HttpServletRequest request ,@RequestParam(value = "themDDH", required = false) String themDDH,
			@RequestParam(value = "luuDDH", required = false) String luuDDH)
	{
		HttpSession s = request.getSession();
		Object user = s.getAttribute("user");
		NhanVien nhanvien = new NhanVien();
		nhanvien = (NhanVien) user;
		Session session = factory.getCurrentSession();
		if (themDDH != null)
		{
			if (!NCChientai.equals(request.getParameter("NCC")) ||  !ngayHienTai.equals(request.getParameter("ngayDat")))
			{
				listSL.clear();
				listSL.clear();
			}
			String hql = "FROM LoaiSanPham WHERE maLoai = :maLoai";
			int index =-1;
			
			
				for (LoaiSanPham loaiSanPham : listSP)
				{
					if (loaiSanPham.getMaLoai().equalsIgnoreCase(request.getParameter("sanPham")))
					{
						index = listSP.indexOf(loaiSanPham);
					}
				}
				if(index != -1)
				{
					int valueSL = listSL.get(index) + Integer.parseInt(request.getParameter("soLuong"));
					listSL.set(index, valueSL);
				}
			else 
				{
				Query query = session.createQuery(hql);
			    query.setParameter("maLoai", request.getParameter("sanPham"));
			    LoaiSanPham SP = (LoaiSanPham) query.uniqueResult();
			    listSP.add(SP);
			    Integer SL = Integer.parseInt(request.getParameter("soLuong"));
			    listSL.add(SL);
			}
			
		}
		else 
		{
			String ngayD = request.getParameter("ngayDat");
			Date ngayDat = Date.valueOf(ngayD);
			String maDDH = request.getParameter("maDDH");
			String hql = "FROM NhaCungCap WHERE maNCC = :NCC";
			Query query = session.createQuery(hql);
			query.setParameter("NCC", request.getParameter("NCC"));
			NhaCungCap nhaCungCap = (NhaCungCap) query.uniqueResult();
			DonDatHang newDDH =  new DonDatHang(maDDH,ngayDat,nhaCungCap,nhanvien, null);
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new Date(calendar.getTime().getTime());
			if (ngayDat.compareTo(currentDate) < 0) { // ngay bd nho hon ngay kt -> true
				
				if (listSP.size() == 0)
				{
					model.addAttribute("ThongBao", "Hiện chưa có sản phẩm nào, vui lòng thêm sản phẩm!");
				}
				else {
					session.save(newDDH);
					for (LoaiSanPham loaiSanPham : listSP)
					{
						int soLuong = listSL.get(listSP.indexOf(loaiSanPham));
						CTDonDatHang ct = new CTDonDatHang(soLuong,loaiSanPham.getGiaNhap(),loaiSanPham,newDDH);
						session.save(ct);
					}
					listSP.clear();
					listSL.clear();
					return "redirect:dondathang.htm";
				}
				
			}
			
			else {
				model.addAttribute("ThongBao", "Ngày đặt không được lớn hơn ngày hiện tại, vui lòng chọn lại!");
			}
			
			
		}
		String hql =  "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		List<NhanVien> DSNCC =	query.list(); 
		hql = "FROM LoaiSanPham";
		Query query2 = session.createQuery(hql);
		List<LoaiSanPham> list2 =	query2.list(); 
		model.addAttribute("DSNCC", DSNCC);
		model.addAttribute("DSSP", list2);
	    NCChientai = request.getParameter("NCC");
	    ngayHienTai =request.getParameter("ngayDat");
		model.addAttribute("listSP",listSP);
		model.addAttribute("listSL",listSL);
		model.addAttribute("doLon",listSL.size()-1);
		model.addAttribute("NCChientai",request.getParameter("NCC"));
		model.addAttribute("ngayDat",request.getParameter("ngayDat"));
		model.addAttribute("maDDH",request.getParameter("maDDH"));
		model.addAttribute("nhanVien",nhanvien);
		return "staff/taodondathang";
	}
	
	@RequestMapping("XoaSPtabledondathang")
	public String XoaSPtabledondathang(Model model, HttpServletRequest request)
	{
		HttpSession s = request.getSession();
		Object user = s.getAttribute("user");
		NhanVien nhanvien = new NhanVien();
		nhanvien = (NhanVien) user;
		int index =-1;
		for (LoaiSanPham lsp : listSP)
		{
			if (lsp.getMaLoai().equalsIgnoreCase(request.getParameter("maLoai")))
			{
				index = listSP.indexOf(lsp);
			}
		}
		listSP.remove(index);
		listSL.remove(index);
		Session session = factory.getCurrentSession();
		String hql =  "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		List<NhanVien> DSNCC =	query.list(); 
		hql = "FROM LoaiSanPham";
		Query query2 = session.createQuery(hql);
		List<LoaiSanPham> list2 =	query2.list(); 
		model.addAttribute("DSNCC", DSNCC);
		model.addAttribute("DSSP", list2);
		model.addAttribute("listSP",listSP);
		model.addAttribute("listSL",listSL);
		model.addAttribute("doLon",listSL.size()-1);
		model.addAttribute("NCChientai",NCChientai);
		model.addAttribute("ngayDat",ngayHienTai);
		model.addAttribute("maDDH",request.getParameter("maDDH"));
		model.addAttribute("nhanVien",nhanvien);
		return "staff/taodondathang";
	}
	
// Phiếu Nhập
	@RequestMapping("xemphieunhap")
	public String xemPhieuNhap(Model model, HttpServletRequest request)
	{
	
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhap WHERE soPhieuNhap = :soPhieuNhap";
	    Query query = session.createQuery(hql);
	    query.setParameter("soPhieuNhap", Integer.parseInt(request.getParameter("idPN")) );
	    
		PhieuNhap pNhap =(PhieuNhap) query.uniqueResult();	
		Hibernate.initialize(pNhap.getSanPham());		
		model.addAttribute("PhieuNhap",pNhap);
		return "staff/xemphieunhap";
	}
	
	 public static String TaoSeri(String chuoi) {
	        String[] tu = chuoi.split(" "); // Tách chuỗi thành các từ riêng lẻ bằng dấu cách
	        StringBuilder ketQua = new StringBuilder();
	        
	        for (String t : tu) {
	            if (!t.isEmpty()) { // Kiểm tra nếu từ không rỗng
	                ketQua.append(t.charAt(0)); // Lấy kí tự đầu tiên của từ và thêm vào chuỗi kết quả
	            }
	        }
	        return ketQua.toString();
	    }
	
		@RequestMapping("taophieunhap")
		public String taoPhieuNhapTest(Model model , HttpServletRequest request,
				@RequestParam(defaultValue = "") String loaiBtn,
				HttpSession s
				)
		{
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql = "From DonDatHang where maDDH = :maDDH";
			Query query = session.createQuery(hql);
			 query.setParameter("maDDH", request.getParameter("idDDH") );
			DonDatHang donDatHang =(DonDatHang) query.list().get(0);
			hql = "From SanPham";
			query = session.createQuery(hql);
			List<SanPham> listSPGoc = query.list();
			int sizeSP = listSPGoc.size();
			 hql = "From PhieuNhap";
		  query = session.createQuery(hql);
				List<PhieuNhap> listPN = query.list(); 
			int soPhieuNhap = listPN.size() +1 ;

			PhieuNhap phieuNhap = new PhieuNhap();
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new Date(calendar.getTime().getTime());
			phieuNhap.setNgayLapPN(currentDate);
			phieuNhap.setMaDDH(donDatHang);
			phieuNhap.setMaNV(nhanvien);
			if (loaiBtn.equals("luu"))
			{
				session.save(phieuNhap);
				for (SanPham sp : listSPNhap)
				{
					sp.setSoPhieuNhap(phieuNhap);
					//SanPham sp1 = (SanPham) session1.merge(sp);
				}
				listSPNhap.clear();
				return "redirect:dondathang.htm";
			}
			if (loaiBtn.equals("huy"))
			{
				listSPNhap.clear();
				return "redirect:dondathang.htm";
			}
			
			for (CTDonDatHang ct : donDatHang.getCtDonDatHang())
			{
				for (int i= 0 ;i< ct.getSoLuong();i++)
				{
					sizeSP++;
					String temp = Integer.toString(sizeSP);
					 
					String seri =  TaoSeri(ct.getMaLoai().getTenSP()) + temp ;
					SanPham newSP = new SanPham(seri,0,ct.getMaLoai(),null,null,null,null);
					listSPNhap.add(newSP);
				}
			}

				model.addAttribute("soPhieuNhap" ,soPhieuNhap);
				 model.addAttribute("DDH",donDatHang);
				 model.addAttribute("NgayNhap",currentDate);
				 model.addAttribute("listSPNhap",listSPNhap);
				 model.addAttribute("NhanVien", nhanvien);
				 return "staff/taophieunhap";
		}
	 
	 //Bảo Hành
		@RequestMapping("nhanbaohanh")
		public String nhanBaoHanh(Model model ,HttpServletRequest request ,@RequestParam(value = "timSeri", required = false) String timSeri,
				HttpSession s,
				@RequestParam(value = "nhanMay", required = false) String nhanMay)
		{
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql ;
			Query query ;
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new Date(calendar.getTime().getTime());
			if(timSeri != null )
			{
				

				hql = "From SanPham Where seri = :seri";
				query = session.createQuery(hql);
				query.setParameter("seri", request.getParameter("seri") );
				List<SanPham> spNhan = query.list();
				if (spNhan.size() == 0)
				{
					model.addAttribute("ThongBao","Không có sản phẩm có Seri này! Vui lòng nhập Seri khác!");
				}
				else 
				{
					
					model.addAttribute("SanPhamTim",spNhan.get(0));
					model.addAttribute("seri",request.getParameter("seri"));
					 model.addAttribute("NgayHientai",currentDate);
					 model.addAttribute("ThongBao"," ");
					if(currentDate.compareTo(spNhan.get(0).getPhieuBaoHanh().getNgayKetThuc()) > 0 )
					{
						model.addAttribute("HetHan","Hết Hạn Bảo Hành");
					}
				}
			}
			if (nhanMay != null && request.getParameter("trangThaiNhan") != "")
			{
				hql = "FROM CTBaoHanh Where ngayTra IS NULL and soPhieuBH.seri.seri = :seri";
				query = session.createQuery(hql);
				query.setParameter("seri", request.getParameter("seri") );
				List<CTBaoHanh> CT = query.list();
				if(CT.size() != 0)
				{
					model.addAttribute("ThongBao2","Sản phẩm trên đã nhận trước đó! Vui lòng kiểm tra lại 'Sản Phẩm Đã Nhận'!");
				}
				else {
					hql = "From SanPham Where seri = :seri";
					query = session.createQuery(hql);
					query.setParameter("seri", request.getParameter("seri") );
					SanPham sp = (SanPham) query.uniqueResult();
					CTBaoHanh newCT = new CTBaoHanh(request.getParameter("trangThaiNhan"),null,currentDate,null,nhanvien,null,
							sp.getPhieuBaoHanh());
					session.save(newCT);
					model.addAttribute("ThongBao2", "Sản phẩm đã thêm vào danh sách 'Sản Phẩm Đã Nhận'!");
				}
				
			}
			if (nhanMay != null && request.getParameter("trangThaiNhan") == "")
			{
				hql = "From SanPham Where seri = :seri";
				query = session.createQuery(hql);
				query.setParameter("seri", request.getParameter("seri") );
				List<SanPham> spNhan = query.list();
				{
					
					model.addAttribute("SanPhamTim",spNhan.get(0));
					model.addAttribute("seri",request.getParameter("seri"));
					 model.addAttribute("NgayHientai",currentDate);
					 model.addAttribute("ThongBao"," ");
					 model.addAttribute("ThongBao3", "Vui lòng điền trạng thái của máy trước khi nhận!");
				}
			}
			 model.addAttribute("nhanVien", nhanvien);
			
			 return "staff/nhanbaohanh";
		}
		
	@RequestMapping("danhsachdangbaohanh")
	public String danhSachSPDangBaoHanh(Model model,HttpServletRequest request,
			@RequestParam(defaultValue = "") String loaiBtn,
			HttpSession s)
	{
		NhanVien nhanvien = (NhanVien) s.getAttribute("user");
		Session session = factory.getCurrentSession();
		String hql ;
		Query query ;
		if(loaiBtn.equals("troVe"))
		{
			return "redirect:nhanbaohanh.htm";
		}
		if (loaiBtn.equals("traMay"))
		{
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new Date(calendar.getTime().getTime());
			hql = "FROM CTBaoHanh Where ngayTra IS NULL and soPhieuBH.seri.seri = :seri";
			query = session.createQuery(hql);
			query.setParameter("seri", request.getParameter("seri") );
			CTBaoHanh ct = (CTBaoHanh) query.uniqueResult();
			ct.setMaNVTra(nhanvien);
			ct.setNgayTra(currentDate);
			ct.setTrangThaiTra("Hết Lỗi");
			session.save(ct);
		}
		hql = "FROM CTBaoHanh WHERE ngayTra IS NULL";
		query = session.createQuery(hql);
		List<CTBaoHanh> listBH = query.list();
		model.addAttribute("listBH",listBH);
		
		return "staff/danhsachdangbaohanh";
	}
	@RequestMapping("kiemtrabaohanh")
	public String kiemtrabaohanh(Model model , HttpServletRequest request,
			@RequestParam(defaultValue = "") String loaiBtn,
			HttpSession s)
	{
		Session session = factory.getCurrentSession();
		String hql ;
		Query query ;
		hql = "FROM SanPham Where seri = :seri";
		query = session.createQuery(hql);
		query.setParameter("seri", request.getParameter("seri") );
		SanPham sp = (SanPham) query.uniqueResult();
		model.addAttribute("SP",sp);
		 Hibernate.initialize(sp.getPhieuBaoHanh().getCtBaoHanh());
		return "staff/kiemtrabaohanh";
	}
//	@RequestMapping("taophieunhap")
//	public String taoPhieuNhap(Model model , HttpServletRequest request,
//			@RequestParam(defaultValue = "") String loaiBtn,
//			HttpSession s)
//			
//	{
//		
//		
//		NhanVien nhanvien = (NhanVien) s.getAttribute("user");
//
//		
//		DonDatHang donDatHang =getDDHtest(request.getParameter("idDDH"));
//		
//		int soPhieuNhap = getSiseListSPN() +1;
//		Calendar calendar = Calendar.getInstance();
//		Date currentDate = new Date(calendar.getTime().getTime());
//		 
//		int sizeSP = getSiseListSP();
//		
//		PhieuNhap phieuNhap = new PhieuNhap();
//		phieuNhap.setNgayLapPN(currentDate);
//		phieuNhap.setMaDDH(donDatHang);
//		phieuNhap.setMaNV(nhanvien);
//		
//		if (loaiBtn.equals("luu"))
//		{
//			int test = SaveTest(phieuNhap);
//			System.out.println(test);
//			
//			return "redirect:dondathang.htm";
//		}
//		
//		
//		if (loaiBtn.equals("huy"))
//		{
//			listSPNhap.clear();
//			return "redirect:dondathang.htm";
//		}
//		
//		for (CTDonDatHang ct : donDatHang.getCtDonDatHang())
//		{
//			for (int i= 0 ;i< ct.getSoLuong();i++)
//			{
//				sizeSP++;
//				String temp = Integer.toString(sizeSP);
//				 
//				String seri = temp + TaoSeri(ct.getMaLoai().getTenSP());
//				SanPham newSP = new SanPham(seri,ct.getMaLoai(),null,null,null,null);
//				listSPNhap.add(newSP);
//			}
//		}
//		System.out.println(listSPNhap.size());
//			model.addAttribute("soPhieuNhap" ,soPhieuNhap);
//			 model.addAttribute("DDH",donDatHang);
//			 model.addAttribute("NgayNhap",currentDate);
//			 model.addAttribute("listSPNhap",listSPNhap);
//			 model.addAttribute("NhanVien", nhanvien);
//			 return "staff/taophieunhap";
//		
//	}	
//	private DonDatHang getDDHtest(String maDHH) {
//		Session session = factory.getCurrentSession();
//		String hql = "From DonDatHang where maDDH = :maDDH";
//		Query query = session.createQuery(hql);
//		 query.setParameter("maDDH", maDHH );
//		DonDatHang donDatHang =(DonDatHang) query.list().get(0);
//		return donDatHang;
//	}
//	private int getSiseListSP() {
//		Session session = factory.getCurrentSession();
//		String hql = "From SanPham";
//		Query  query = session.createQuery(hql);
//		List<SanPham> listPN = query.list(); 
//		return listPN.size();
//	}
//	private int getSiseListSPN() {
//		Session session = factory.getCurrentSession();
//		String hql = "From PhieuNhap";
//		Query  query = session.createQuery(hql);
//		List<PhieuNhap> listPN = query.list(); 
//		return listPN.size();
//	}
//	private int SaveTest(PhieuNhap pn) {
//		Session session1 = factory.openSession();
//		Transaction t = session1.beginTransaction();
//		try {
//			session1.save(pn);
//			t.commit();	
//		}
//		catch (Exception e) {
//			t.rollback();
//			System.out.println("try 1" + e.getMessage());
//			return 1;
//			
//		}finally {		
//			session1.close();
//			}
//		int test = saveSP(pn);
//		System.out.println(test);
//		return 0;
//		
//	}
//	private int saveSP(PhieuNhap pn) {
//		
//		for (SanPham sp : listSPNhap)
//		{
//			Session session1 = factory.openSession();
//			Transaction t = session1.beginTransaction();
//		try {
//			
//				sp.setSoPhieuNhap(pn);
//				System.out.println(sp);
//				//SanPham sp1 = (SanPham) session1.merge(sp);
//				session1.save(sp);
//				t.commit();
//			
//			//listSPNhap.clear();
//			
//			
//		}
//		catch (Exception e) {
//			t.rollback();
//			System.out.println(e.getMessage());
//			return 1;
//		}
//		
//		finally {
//			
//			session1.close();
//		}
//		}
//		
//		return 0;
//	}
}

