package ptithcm.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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

import ptithcm.model.BinhLuan;
import ptithcm.model.CTDotGiamGia;
import ptithcm.model.CungCap;
import ptithcm.model.DoanhThuTheoNgay;
import ptithcm.model.DotGiamGia;
import ptithcm.model.KhachHang;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.NhaCungCap;
import ptithcm.model.NhanVien;
import ptithcm.model.SanPham;

@Transactional
@Controller

public class Staff1Controller {
	@Autowired
	SessionFactory factory;


	@RequestMapping(value = "/homenv")
	public String showHome(ModelMap model) {
		List<LoaiSanPham> list1 = getLoaiSanPham_HOME();
		if (list1 == null) {
			model.addAttribute("listProducts", null);
			model.addAttribute("product1", null);
			return "staff/homeNV";
		}
		sort(list1);
		ArrayList<LoaiSanPham> list2 = new ArrayList<>();
		int n = list1.size() >= 5 ? 5 : list1.size();
		Hibernate.initialize(list1.get(0).getMaTheLoai());
		Hibernate.initialize(list1.get(0).getMaHang());
		for (int i = 1; i < n; i++) {
			Hibernate.initialize(list1.get(i).getMaTheLoai());
			Hibernate.initialize(list1.get(i).getMaHang());
			list2.add(list1.get(i));
		}
		model.addAttribute("listProducts", list2);
		model.addAttribute("product1", list1.get(0));
		return "staff/homeNV";
	}

	private void sort(List<LoaiSanPham> list) {
		int n = list.size();
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				int a = list.get(j).getSanPham() == null ? 0 : list.get(j).getSanPham().size();
				int b = list.get(j + 1).getSanPham() == null ? 0 : list.get(j + 1).getSanPham().size();
				if (a < b) {
					LoaiSanPham temp = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, temp);
				}
			}
		}
	}

	private List<LoaiSanPham> getLoaiSanPham_HOME() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);

		List<LoaiSanPham> list = query.list();
		if(list.size() == 0) return null;
		for (LoaiSanPham loaiSanPham : list) {
			loaiSanPham.setSanPham(getSanPhamBestSell(loaiSanPham.getMaLoai()));
		}
		return list;
	}

	private List<SanPham> getSanPhamBestSell(String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE maLoai.maLoai =:maLoai1 AND daBan = 1";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", s);
		List<SanPham> list = query.list();
		if (list.size() == 0)
			return null;
		return list;
	}

	@RequestMapping(value = "/thongtincanhannv")
	public String thongTinCaNhan(Model model, HttpSession session) {
		Object user = session.getAttribute("user");
		NhanVien nhanVien = new NhanVien();
		nhanVien = (NhanVien) user;
		model.addAttribute("nhanVien", nhanVien);
		return "staff/thongtincanhan";
	}

	@RequestMapping(value = "/doimatkhaunv")
	public String showFormDoiMkUser() {
		return "staff/doimatkhau";
	}

	@RequestMapping(value = "/doimatkhaunv", method = RequestMethod.POST)
	public String doiMkUser(HttpServletRequest request, Model model) {
		HttpSession s = request.getSession();

		Object user = s.getAttribute("user");
		NhanVien nhanVien = new NhanVien();
		nhanVien = (NhanVien) user;

		String mkCu = request.getParameter("mkCu");
		String mkMoi1 = request.getParameter("mkMoi1");
		String mkMoi2 = request.getParameter("mkMoi2");
		if (!nhanVien.getPassword().equals(mkCu)) {
			model.addAttribute("ErrorMkCu", "Mật khẩu cũ bạn nhập chưa chính xác!");
			return "staff/doimatkhau";
		} else {
			if (!mkMoi1.equals(mkMoi2)) {
				model.addAttribute("ErrorMkMoi", "Mật khẩu mới bạn nhập chưa khớp nhau!");
				return "staff/doimatkhau";
			} else {
				Session session = factory.getCurrentSession();
				String hql = "UPDATE NhanVien NV SET NV.password = :pass WHERE NV.maNV = :maNV";
				Query query = session.createQuery(hql);
				query.setParameter("pass", mkMoi1);
				query.setParameter("maNV", nhanVien.getMaNV());
				query.executeUpdate();
			}
		}

		return "redirect:/thongtincanhannv.htm";
	}

	@RequestMapping(value = "/luuthongtinnv", method = RequestMethod.POST)
	public String luuThongTinNV(HttpServletRequest request, Model model) {
		String maNV = request.getParameter("maNV");
		Session session = factory.getCurrentSession();
		NhanVien nhanVien = (NhanVien) session.get(NhanVien.class, maNV);
		nhanVien.setHo(request.getParameter("ho"));
		nhanVien.setTen(request.getParameter("ten"));
		nhanVien.setNgaySinh(Date.valueOf(request.getParameter("ngaySinh")));
		nhanVien.setDiaChi(request.getParameter("diaChi"));
		nhanVien.setsDT(request.getParameter("sdt"));
		nhanVien.setEmail(request.getParameter("email"));

		session.merge(nhanVien);
		model.addAttribute("nhanVien", nhanVien);
		return "staff/thongtincanhan";
	}

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
	public String themNhaCC(Model model) {
		Session session = factory.getCurrentSession();
		String hql = "From LoaiSanPham";
		Query query = session.createQuery(hql);
		List<LoaiSanPham> listLSP = query.list();
		model.addAttribute("listSP", listLSP);
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
		String[] listLSP = request.getParameterValues("selectedSP");
		hql = "From LoaiSanPham";
		query = session.createQuery(hql);
		List<LoaiSanPham> LSP = query.list();
		if(listLSP == null) {
			
			model.addAttribute("listSP", listLSP);
			model.addAttribute("ErrorLSP", "Chưa chọn loại sản phẩm cho nhà cung cấp này");
			model.addAttribute("nhaCC", nhaCC);
			model.addAttribute("listSP", LSP);
			return "staff/themnhacungcap";
		} else {
			model.addAttribute("nhaCC", nhaCC);

			hql = "FROM NhaCungCap NCC WHERE NCC.tenNCC = :tenNCC";
			query = session.createQuery(hql);
			query.setParameter("tenNCC", tenNCC);
			if (query.list().size() == 0) { // check ten ncc
				session.save(nhaCC);
				hql = "FROM NhaCungCap";
				for (String s : listLSP) {
					System.out.println(s);
					LoaiSanPham lsp = (LoaiSanPham) session.get(LoaiSanPham.class, s);
					CungCap cungCap = new CungCap(lsp, nhaCC);
					session.save(cungCap);
				}
				query = session.createQuery(hql);
				model.addAttribute("listNCC", query.list());
				return "redirect:/danhsachnhacungcap.htm";
			} else {
				model.addAttribute("ErrorTenNCC", "Tên nhà cung cấp đã tồn tại!");
				model.addAttribute("nhaCC", nhaCC);
				model.addAttribute("listSP", LSP);
				return "staff/themnhacungcap";
			}
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
	// xu li phan binh luan
	
		@RequestMapping(value = "/home/chinhsuabinhluan", method = RequestMethod.GET)
		public String showChinhSuaBL(Model model,
				@RequestParam("id") String id, @RequestParam("maLoai") String maLoai) {
			model.addAttribute("idBL", id);
			return "redirect:/home/shop-single.htm?lsp=" + maLoai;
		}
		
		@RequestMapping(value = "/home/updatebinhluan", method = RequestMethod.POST)
		public String updateBinhLuan(HttpServletRequest request, Model model) {
			String noiDung = request.getParameter("binhLuan");
			String id = request.getParameter("id");
			String maLoai = request.getParameter("maLoai");
			Session session = factory.getCurrentSession();
			BinhLuan binhLuan = (BinhLuan)session.get(BinhLuan.class, Integer.parseInt(id));
			binhLuan.setMoTa(noiDung.trim());
			session.merge(binhLuan);
			model.addAttribute("idBL", 0); // để readonly bình luận
			return "redirect:/home/shop-single.htm?lsp=" + maLoai;
		}
		
		@RequestMapping(value = "/home/thembinhluan", method = RequestMethod.POST)
		public String themBinhLuan(HttpServletRequest request) {
			Session session = factory.getCurrentSession();
			String maLoai = request.getParameter("maLoai");
			LoaiSanPham loaiSP = (LoaiSanPham) session.get(LoaiSanPham.class, maLoai);
			String khachHangEmail = request.getParameter("email");
			KhachHang khachHang = (KhachHang) session.get(KhachHang.class, khachHangEmail);
			String noiDung = request.getParameter("binhLuan");
			Date ngayBL = new Date(System.currentTimeMillis()) ;
			String diem = request.getParameter("star");
			BinhLuan binhLuan = new BinhLuan(Integer.parseInt(diem), noiDung, ngayBL, khachHang, loaiSP);
			session.save(binhLuan);
			return "redirect:/home/shop-single.htm?lsp=" + maLoai;
		}
		

}
