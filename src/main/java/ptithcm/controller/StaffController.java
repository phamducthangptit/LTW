package ptithcm.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.model.CTDotGiamGia;
import ptithcm.model.DonDatHang;
import ptithcm.model.DotGiamGia;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.NhaCungCap;
import ptithcm.model.NhanVien;

@Controller
@Transactional
public class StaffController {
	@Autowired
	SessionFactory factory;

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
			String hql = "from DotGiamGia";
			Query query = s.createQuery(hql);
			DotGiamGia dotGG = new DotGiamGia("", ngayBD, ngayKT, moTa, nhanVien);
			dotGG.setMaDot("DGG" + (query.list().size() + 1));
			if (ngayBD.compareTo(ngayKT) < 0) { // ngay bd nho hon ngay kt -> true
				s.save(dotGG);
				model.addAttribute("ErrorGG", "");
			} else {
				model.addAttribute("ErrorGG", "Ngày kết thúc không hợp lệ, vui lòng chọn lại!");
			}
			model.addAttribute("DGG", dotGG);
			model.addAttribute("slSP", soLuongSPGG);
			hql = "FROM LoaiSanPham";
			query = s.createQuery(hql);
			model.addAttribute("listLoaiSP", query.list());
			return "staff/taodotgiamgia";
		} else if (luuDGG != null) {
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
//					String hql = "INSERT INTO CTDotGiamGia(maLoai, maDot, tiLeGiam) VALUES (:maLoai, :maDot, :tiLeGiam)";
//					Query query = s.createQuery(hql);
//					query.setParameter("maDot", dgg);
//					query.setParameter("maLoai", lsp);
//					query.setParameter("tiLeGiam", listPTGiamGia[i]);
					CTDotGiamGia ctDGG = new CTDotGiamGia(Integer.parseInt(listPTGiamGia[i]), lsp, dgg);
					s.save(ctDGG);
//					query.executeUpdate();
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

	@RequestMapping(value = "/deletedotgg")
	public String deleteDotGG(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		// cần check lại lần nữa khi xóa, đang demo
		Session session = factory.getCurrentSession();
		DotGiamGia dgg = (DotGiamGia) session.get(DotGiamGia.class, id);
		session.delete(dgg);
		String hql = "from DotGiamGia";
		Query query = session.createQuery(hql);
		List<DotGiamGia> listDGG = query.list();
		model.addAttribute("listDGG", listDGG);
		return "staff/danhsachdotgiamgia";
	}

	@RequestMapping(value = "/themnhacungcap")
	public String themNhaCC() {
		return "staff/themnhacungcap";
	}

	@RequestMapping(value = "/themnhacungcap", method = RequestMethod.POST)
	public String luuNhaCC(HttpServletRequest request, Model model) {
		String maNCC = request.getParameter("maNCC");
		Session session = factory.getCurrentSession();
		String tenNCC = request.getParameter("tenNCC");
		String diaChi = request.getParameter("diaChi");
		String email = request.getParameter("email");
		String sdt = request.getParameter("SDT");
		NhaCungCap nhaCC = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
		model.addAttribute("nhaCC", nhaCC);
		if (session.get(NhaCungCap.class, maNCC) == null) { // nếu mã nhà cc chưa tồn tại
			String hql = "FROM NhaCungCap NCC WHERE NCC.tenNCC = :tenNCC";
			Query query = session.createQuery(hql);
			query.setParameter("tenNCC", tenNCC);
			if (query.list().size() == 0) {
				session.save(nhaCC);
				hql = "FROM NhaCungCap";
				query = session.createQuery(hql);
				model.addAttribute("listNCC", query.list());
				return "staff/danhsachnhacungcap";
			} else {
				model.addAttribute("ErrorTenNCC", "Tên nhà cung cấp đã tồn tại!");
				return "staff/themnhacungcap";
			}
		} else {
			model.addAttribute("ErrorMaNCC", "Mã nhà cung cấp đã tồn tại!");
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
		model.addAttribute("id", id);
		return "staff/ctnhacungcap";
	}

	@RequestMapping(value = "/ctncc", method = RequestMethod.POST)
	public String luuThongTinNCC(HttpServletRequest request, Model model) {
		String maNCC = request.getParameter("maNCC");
		String tenNCC = request.getParameter("tenNCC");
		String diaChi = request.getParameter("diaChi");
		String email = request.getParameter("email");
		String sdt = request.getParameter("SDT");
		String id = request.getParameter("id");
		Session session = factory.getCurrentSession();
		NhaCungCap nhaCungCapCheck = (NhaCungCap) session.get(NhaCungCap.class, maNCC); // check xem nếu có thay đổi mã
																						// thì mã đã tồn tại chưa
		NhaCungCap nhaCC = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
		if (nhaCungCapCheck != null && !maNCC.equals(id)) {
			model.addAttribute("ErrorNCC", "Mã nhà cung cấp đã tồn tại! Vui lòng nhập mã khác!");
			model.addAttribute("nhaCC", nhaCC);
			model.addAttribute("tontaiDDH", "0");
		} else {
			NhaCungCap nhaCungCapOld = (NhaCungCap) session.get(NhaCungCap.class, id); // lay ra nha cc cu
			if (nhaCungCapOld.getDonDatHang().size() == 0) {
				session.delete(nhaCungCapOld);
				session.save(nhaCC); // insert lai nha cc
				model.addAttribute("nhaCC", nhaCC);
				model.addAttribute("tontaiDDH", "0");
			} else {
				nhaCungCapCheck.setDiaChi(diaChi);
				nhaCungCapCheck.setEmail(email);
				nhaCungCapCheck.setSdt(sdt);
				session.merge(nhaCungCapCheck);
				model.addAttribute("nhaCC", nhaCungCapCheck);
				model.addAttribute("tontaiDDH", nhaCungCapCheck.getDonDatHang().size());
			}
		}
		model.addAttribute("id", maNCC);
		return "staff/ctnhacungcap";

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
		return "staff/danhsachnhacungcap";
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
	
	
	@RequestMapping("themnhanvien")
	public String Showthemnhanvien()
	{
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
		 
		System.out.println("CHAY222");
		Session session = factory.getCurrentSession();
		
		String hql = "FROM NhanVien WHERE maNV = :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);

		if (query.list().size() == 0) {
			
			 NhanVien nhanVien = new NhanVien(maNV, ho, ten, ngaySinh, SDT,email,"12345678",diaChi,1,chucVu);
			 

			session.save(nhanVien); 
			model.addAttribute("ErrorId", "Thêm nhân viên mới thành công!");
			return "staff/themnhanvien";
		}
		else {

			model.addAttribute("ErrorId", "Vui lòng chọn mã nhân viên khác! Mã này đã được sử dụng!");
			model.addAttribute("email", email); 
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
	
	@RequestMapping(value = "dondathang")
	public String dondathang(ModelMap model) {
		org.hibernate.Session session = factory.getCurrentSession();
		String hql =  "FROM DonDatHang";
		Query query = session.createQuery(hql);
		List<DonDatHang> list =	query.list(); 
		
		model.addAttribute("DSDDH", list);
		
	 	model.addAttribute("ThongBao","  ");
		return "staff/dondathang";
	}
	
	
	@RequestMapping(value = "taodondathang")
	public String showtaodondathang(ModelMap model) {
		
		org.hibernate.Session session = factory.getCurrentSession();
		String hql =  "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		List<NhanVien> DSNCC =	query.list(); 
		hql = "FROM LoaiSanPham";
		Query query2 = session.createQuery(hql);
		List<LoaiSanPham> list2 =	query2.list(); 
		model.addAttribute("DSNCC", DSNCC);
		model.addAttribute("DSSP", list2);
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
		model.addAttribute("DDH", donDatHang.get(0));
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
	 ArrayList<LoaiSanPham> listSP = new ArrayList<LoaiSanPham>();
	 ArrayList<Integer> listSL = new ArrayList<Integer>();
	@RequestMapping("taotabledondathang")
	public String taotabledondathang(Model model, HttpServletRequest request)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham WHERE maLoai = :maLoai";
	    Query query = session.createQuery(hql);
	    query.setParameter("maLoai", request.getParameter("sanPham"));
	    LoaiSanPham SP = (LoaiSanPham) query.uniqueResult();
	    listSP.add(SP);
	    Integer SL = Integer.parseInt(request.getParameter("soLuong"));
	    listSL.add(SL);
		model.addAttribute("listSP",listSP);
		model.addAttribute("listSL",listSL);
		model.addAttribute("doLon",listSL.size()-1);
		return "staff/taodondathang";
	}
}
