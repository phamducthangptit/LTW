package ptithcm.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.model.CTBaoHanh;
import ptithcm.model.CTDonDatHang;
import ptithcm.model.CungCap;
import ptithcm.model.DonDatHang;
import ptithcm.model.GioHang;
import ptithcm.model.HoaDon;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.NhaCungCap;
import ptithcm.model.NhanVien;
import ptithcm.model.PhieuBaoHanh;
import ptithcm.model.PhieuNhap;
import ptithcm.model.PhieuTra;
import ptithcm.model.SanPham;
@Transactional
@Controller

public class StaffController {
	@Autowired
	SessionFactory factory;

	ArrayList<LoaiSanPham> listSP = new ArrayList<LoaiSanPham>();
	ArrayList<Integer> listSL = new ArrayList<Integer>();
	ArrayList<SanPham> listSPNhap = new ArrayList<SanPham>();
	 String ngayHienTai = "";
		
	@RequestMapping(value = "danhsachnhanvien")
	public String danhsachnhanvien(ModelMap model,HttpSession s) {
		
		
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
	public String Showthemnhanvien(Model model , HttpServletRequest request)
	{
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien WHERE maNV = :maNV";
	    Query query = session.createQuery(hql);
	    query.setParameter("maNV", request.getParameter("Id"));
	    List<NhanVien> listNhanVien = query.list();
	    NhanVien nhanVien = listNhanVien.get(0);
	    
	    hql = "FROM NhanVien WHERE email = :email";
	    query = session.createQuery(hql);
	    query.setParameter("email", request.getParameter("email"));
	    NhanVien nvTemp = (NhanVien) query.uniqueResult();
	    if (!nvTemp.getMaNV().equals(listNhanVien.get(0).getMaNV()))
	    {
	    	model.addAttribute("ThongBaoEmail","Email này đã được sử dụng vui lòng nhập email khác !");
	    	model.addAttribute("nv", nhanVien);
			return "staff/suathongtinnhanvien";
	    }
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
	public String dondathang(ModelMap model,HttpSession s) {
		
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		String nCCsl =request.getParameter("NCC");
		
		Object user = s.getAttribute("user");
		NhanVien nhanvien = new NhanVien();
		nhanvien = (NhanVien) user;
		Session session = factory.getCurrentSession();
		String hql =  "FROM NhaCungCap where maNCC = :NCC";
		Query query = session.createQuery(hql);
		query.setParameter("NCC", nCCsl);
		NhaCungCap NCC =	(NhaCungCap) query.uniqueResult(); 
		hql = "FROM CungCap where maNCC.maNCC = :NCC";
		query = session.createQuery(hql);
		query.setParameter("NCC",nCCsl);
		List<CungCap> list2 =	query.list(); 
		
		hql = "from DonDatHang";
		query= session.createQuery(hql);
		List<DonDatHang> ddh =	query.list(); 
		String maDon = "DDH" + String.format("%03d", (ddh.size()+1));
		model.addAttribute("NCC", NCC);
		model.addAttribute("DSCC", list2);
		model.addAttribute("nhanVien",nhanvien);
		model.addAttribute("maDDH",maDon);
		model.addAttribute("ngayDat",request.getParameter("ngayDat"));
		return "staff/taodondathang";
	}
	@RequestMapping(value = "taoThongTinDDH")
	public String taoThongTinDDH(ModelMap model,HttpServletRequest request) {
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		Calendar calendar = Calendar.getInstance();
		Date currentDate = new Date(calendar.getTime().getTime());
		model.addAttribute("ngayDat",currentDate);
		return "staff/taoThongTinDDH";
	}

	@RequestMapping("chitietdondathang")
	public String showchitietdondathang(Model model, HttpServletRequest request)
	{
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
			HttpSession s = request.getSession();
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			Session session = factory.getCurrentSession();
			String hql = "FROM DonDatHang WHERE maDDH = :maDDH";
		    Query query = session.createQuery(hql);
		    query.setParameter("maDDH", request.getParameter("idDDH"));
		    DonDatHang donDat = (DonDatHang) query.uniqueResult();
		    if (donDat.getSoPhieuNhap() == null)
		    {
		    	hql = "From CTDonDatHang where maDDH.maDDH = :maDDH";
		    	query = session.createQuery(hql);
		    	query.setParameter("maDDH", request.getParameter("idDDH"));
		    	List<CTDonDatHang> ctDon = query.list();
		    	for (CTDonDatHang ctDonDatHang : ctDon)
		    	{
		    		session.delete(ctDonDatHang);
		    	}
				
				session.delete(donDat);
				 
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
			@RequestParam(value = "luuDDH", required = false) String luuDDH,
			@RequestParam(value = "huy", required = false) String huy)
	{
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		Object user = s.getAttribute("user");
		NhanVien nhanvien = new NhanVien();
		nhanvien = (NhanVien) user;
		Session session = factory.getCurrentSession();
		if (huy != null)
		{
			listSL.clear();
			listSP.clear();
			return "redirect:taoThongTinDDH.htm";
		}
		if (themDDH != null)
		{
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
		String hql = "FROM NhaCungCap WHERE maNCC = :NCC";
		Query query= session.createQuery(hql);
		query.setParameter("NCC", request.getParameter("NCC"));
		NhaCungCap nhaCungCap = (NhaCungCap) query.uniqueResult();
		hql = "FROM CungCap where maNCC.maNCC = :NCC";
		query = session.createQuery(hql);
		query.setParameter("NCC",request.getParameter("NCC"));
		List<CungCap> list2 =	query.list(); 
		model.addAttribute("DSCC",list2);
		model.addAttribute("listSP",listSP);
		model.addAttribute("listSL",listSL);
		model.addAttribute("doLon",listSL.size()-1);
		model.addAttribute("NCC",nhaCungCap);
		model.addAttribute("ngayDat",request.getParameter("ngayDat"));
		model.addAttribute("maDDH",request.getParameter("maDDH"));
		model.addAttribute("nhanVien",nhanvien);
		return "staff/taodondathang";
	}
	
	@RequestMapping("XoaSPtabledondathang")
	public String XoaSPtabledondathang(Model model, HttpServletRequest request)
	{
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		Session session = factory.getCurrentSession();
		
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
		String hql = "FROM NhaCungCap WHERE maNCC = :NCC";
		Query query= session.createQuery(hql);
		query.setParameter("NCC", request.getParameter("maNCC"));
		NhaCungCap nhaCungCap = (NhaCungCap) query.uniqueResult();
		
		hql = "FROM CungCap where maNCC.maNCC = :NCC";
		query = session.createQuery(hql);
		query.setParameter("NCC",request.getParameter("maNCC"));
		List<CungCap> list2 =query.list(); 
		
		model.addAttribute("DSCC",list2);
		model.addAttribute("listSP",listSP);
		model.addAttribute("listSL",listSL);
		model.addAttribute("doLon",listSL.size()-1);
		model.addAttribute("NCC",nhaCungCap);
		model.addAttribute("ngayDat",request.getParameter("ngayDat"));
		model.addAttribute("maDDH",request.getParameter("maDDH"));
		model.addAttribute("nhanVien",nhanvien);
		return "staff/taodondathang";
	}
	
// Phiếu Nhập
	@RequestMapping("xemphieunhap")
	public String xemPhieuNhap(Model model, HttpServletRequest request)
	{
		HttpSession s = request.getSession();
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
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
				
					session.save(sp);
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
				@RequestParam(value = "nhanMay", required = false) String nhanMay,
				@RequestParam(value = "traMay", required = false) String traMay)
		{
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
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
				else if (spNhan.size() != 0 && spNhan.get(0).getPhieuBaoHanh() == null)
				{
					model.addAttribute("ThongBao","Sản Phẩm Này Chưa Bán ! Vui lòng nhập Seri khác!");
				}
				else 
				{
					 
				        calendar.setTime(spNhan.get(0).getPhieuBaoHanh().getNgayBatDau());
				        calendar.add(Calendar.DAY_OF_MONTH, 30);
				        Date dayreturns = new Date(calendar.getTime().getTime());
					model.addAttribute("SanPhamTim",spNhan.get(0));
					model.addAttribute("seri",request.getParameter("seri"));
					 model.addAttribute("NgayHientai",currentDate);
					 model.addAttribute("ThongBao"," ");
					 System.out.println(dayreturns);
					if(currentDate.compareTo(spNhan.get(0).getPhieuBaoHanh().getNgayKetThuc()) > 0 )
					{
						model.addAttribute("HetHan","HetHan");
					}
					 if (spNhan.get(0).getPhieuBaoHanh().getNgayBatDau().compareTo(currentDate) <= 0 && currentDate.compareTo(dayreturns) <= 0 &&spNhan.get(0).getSoPhieuTra() == null)
					 { 
						 model.addAttribute("DoiTra", "DuocTra");
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
					 model.addAttribute("DoiTra",("DuocTra"));
					 model.addAttribute("ThongBao3", "Vui lòng điền trạng thái của máy trước khi nhận!");
				}
			}
			
			if (traMay != null)
			{
				hql = "From SanPham Where seri = :seri";
				query = session.createQuery(hql);
				query.setParameter("seri", request.getParameter("seri") );
				SanPham spNhan = (SanPham) query.uniqueResult();
				spNhan.setDaBan(2);
				PhieuTra pt = new PhieuTra(currentDate,spNhan.getIdGH().getHoaDon(),nhanvien);
				session.save(pt);
				
				spNhan.setSoPhieuTra(pt);
				session.save(spNhan);
				model.addAttribute("SanPhamTim",spNhan);
				model.addAttribute("seri",request.getParameter("seri"));
				 model.addAttribute("NgayHientai",currentDate);
				 model.addAttribute("ThongBao"," ");
				 model.addAttribute("DoiTra",("DaTra"));
				 model.addAttribute("HetHan","TraHang");
			}
			
			 model.addAttribute("nhanVien", nhanvien);
			
			 return "staff/nhanbaohanh";
		}
		
	@RequestMapping("danhsachdangbaohanh")
	public String danhSachSPDangBaoHanh(Model model,HttpServletRequest request,
			@RequestParam(defaultValue = "") String loaiBtn,
			HttpSession s)
	{
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
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
	
	@RequestMapping("duyetgiohang")
	public String danhSachGioHangChuaDuyet(Model model,HttpSession s)
	{
		
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		Session session = factory.getCurrentSession();
		String hql ;
		Query query ;
		hql = "FROM GioHang Where trangThai = 1";
		query = session.createQuery(hql);
		List<GioHang> listGH = query.list();
		model.addAttribute("listGH",listGH);
		return "staff/duyetgiohang";
	}
	@RequestMapping("chitietGHchuaduyet")
	public String chiTietGioHangChuaDuyet(Model model,HttpServletRequest request ,HttpSession s)
	{
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		NhanVien nhanvien = (NhanVien) s.getAttribute("user");
		Session session = factory.getCurrentSession();
		String hql;
		Query query;
		hql = "from SanPham where idGH.idGH = :idGH";
		query = session.createQuery(hql);
		query.setParameter("idGH", Integer.parseInt(request.getParameter("idGH")) );
		List<SanPham> listSPduyet = query.list();
		List<SanPham> listSPtemp = new ArrayList<>();
		List<Integer> listSLtemp = new ArrayList<>();

		for (SanPham sp : listSPduyet) {
			if (listSPtemp.size()== 0)
			{
				listSPtemp.add(sp);
				listSLtemp.add(1);
			}
			else {
				int check = 0;
				 for (SanPham temp : listSPtemp)
				 {
					 if(temp.getMaLoai().getMaLoai().equals(sp.getMaLoai().getMaLoai()))
					 {
						 int index = listSPtemp.indexOf(temp);
						 int SL = listSLtemp.get(index);
						 listSLtemp.set(index, SL +1);
						 check = 1;
					 }
				 }
				 if (check ==0 )
				 {
					 listSPtemp.add(sp);
					listSLtemp.add(1);
				 }
			}
		   
		}
		model.addAttribute("listSP",listSPtemp);
		model.addAttribute("listSL",listSLtemp);
		model.addAttribute("size" , listSLtemp.size()-1);
		model.addAttribute("nhanVien", nhanvien);
		hql = "FROM GioHang Where idGH = :idGH";
		query = session.createQuery(hql);
		query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
		GioHang gioHang = (GioHang) query.uniqueResult();
		model.addAttribute("gioHang" , gioHang);
		
		return "staff/chitietGHchuaduyet";
	}
<<<<<<< HEAD
=======
	@RequestMapping("duyet")
	public String duyet(Model model , HttpServletRequest request ,HttpSession s) {
		if ( s.getAttribute("user") == null)
		{
			return "redirect:dangnhap.htm";
		}
		NhanVien nhanvien = (NhanVien) s.getAttribute("user");
		Session session = factory.getCurrentSession();
		String hql  = "FROM GioHang Where idGH = :idGH";
		Query query = session.createQuery(hql);
		query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
		GioHang gioHang = (GioHang) query.uniqueResult();
		gioHang.setMaNV(nhanvien);
		gioHang.setTrangThai(2);
		session.save(gioHang);
		hql = "from SanPham where idGH.idGH = :idGH";
		query = session.createQuery(hql);
		query.setParameter("idGH", Integer.parseInt(request.getParameter("idGH")) );
		List<SanPham> listSPduyet = query.list();
		for (SanPham sPham : listSPduyet)
		{
			
			Calendar calendar = Calendar.getInstance();
			Date currentDate = new Date(calendar.getTime().getTime());
			calendar.setTime(currentDate);
			calendar.add(Calendar.YEAR, 2);
			java.util.Date futureDate = calendar.getTime();
	        Date dateBH = new Date(futureDate.getTime());
	        PhieuBaoHanh bHanh = new PhieuBaoHanh(currentDate,dateBH,sPham,nhanvien);
			session.save(bHanh);
			sPham.setPhieuBaoHanh(bHanh);
			session.update(sPham);
		}
		HoaDon hd = new HoaDon(sinhMaSoThue(), gioHang);
		Integer check = saveHoaDon(hd,gioHang);

		return "redirect:duyetgiohang.htm";
	}
	private Integer saveHoaDon(HoaDon x, GioHang gh) {
			Session ss = factory.openSession();
			Transaction t = ss.beginTransaction();
			try {
				ss.save(x);
				t.commit();
			}catch(Exception e) {
				t.rollback();
				return 1;
			} finally {
				ss.close();
			}
			gh.setHoaDon(x);
			ss = factory.openSession();
			t = ss.beginTransaction();
			GioHang gh1 = (GioHang) ss.merge(gh);
			try {
				ss.update(gh1);
				t.commit();
			}catch(Exception e) {
				t.rollback();
				return 1;
			} finally {
				ss.close();
			}
			return 0;
		}
		private String sinhMaSoThue() {
			Session session = factory.getCurrentSession();
			String hql = "FROM HoaDon";
			Query query = session.createQuery(hql);
			List<HoaDon> list = query.list();
			int n = list.size() + 1;
			String ma = Integer.toString(n);
			String result ="MST";
			for (int i=ma.length() ; i < 11;i++ ) {
				result += "0";
			}
			result += ma;
			return result;
		}

		@RequestMapping("donhangchuagiao")
		public String danhSachgiohangchuagiao(Model model,HttpSession s)
		{
			
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			Session session = factory.getCurrentSession();
			String hql ;
			Query query ;
			hql = "FROM GioHang Where trangThai = 2 and maShipper is null";
			query = session.createQuery(hql);
			List<GioHang> listGH = query.list();
			model.addAttribute("listGH",listGH);
			return "staff/donhangchuagiao";
		}
		
		@RequestMapping("chitietGHchuagiao")
		public String chiTietGioHangChuaGiao(Model model,HttpServletRequest request ,HttpSession s)
		{
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql;
			Query query;
			hql = "from SanPham where idGH.idGH = :idGH";
			query = session.createQuery(hql);
			query.setParameter("idGH", Integer.parseInt(request.getParameter("idGH")) );
			List<SanPham> listSPduyet = query.list();
			List<SanPham> listSPtemp = new ArrayList<>();
			List<Integer> listSLtemp = new ArrayList<>();

			for (SanPham sp : listSPduyet) {
				if (listSPtemp.size()== 0)
				{
					listSPtemp.add(sp);
					listSLtemp.add(1);
				}
				else {
					int check = 0;
					 for (SanPham temp : listSPtemp)
					 {
						 if(temp.getMaLoai().getMaLoai().equals(sp.getMaLoai().getMaLoai()))
						 {
							 int index = listSPtemp.indexOf(temp);
							 int SL = listSLtemp.get(index);
							 listSLtemp.set(index, SL +1);
							 check = 1;
						 }
					 }
					 if (check ==0 )
					 {
						 listSPtemp.add(sp);
						listSLtemp.add(1);
					 }
				}
			   
			}
			model.addAttribute("listSP",listSPtemp);
			model.addAttribute("listSL",listSLtemp);
			model.addAttribute("size" , listSLtemp.size()-1);
			model.addAttribute("nhanVien", nhanvien);
			hql = "FROM GioHang Where idGH = :idGH";
			query = session.createQuery(hql);
			query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
			GioHang gioHang = (GioHang) query.uniqueResult();
			model.addAttribute("gioHang" , gioHang);
			
			return "staff/chitietGHchuagiao";
		}
		
		@RequestMapping("nhangiaohang")
		public String nhangiaohang(Model model , HttpServletRequest request ,HttpSession s) {
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql  = "FROM GioHang Where idGH = :idGH";
			Query query = session.createQuery(hql);
			query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
			GioHang gioHang = (GioHang) query.uniqueResult();
			gioHang.setMaShipper(nhanvien);
			gioHang.setTrangThai(2);
			session.save(gioHang);

			return "redirect:donhangdangnhan.htm";
		}
		@RequestMapping("donhangdangnhan")
		public String danhSachgiohangdangnhan(Model model, HttpServletRequest request,HttpSession s)
		{
			
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql ;
			Query query ;
			hql = "FROM GioHang Where trangThai = 2 and maShipper.maNV = :maNV";
			query = session.createQuery(hql);
			query.setParameter("maNV", nhanvien.getMaNV());
			List<GioHang> listGH = query.list();
			model.addAttribute("listGH",listGH);
			return "staff/donhangdangnhan";
		}
		@RequestMapping("chitietGHdangnhan")
		public String chiTietGioHangDangNhan(Model model,HttpServletRequest request ,HttpSession s)
		{
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			NhanVien nhanvien = (NhanVien) s.getAttribute("user");
			Session session = factory.getCurrentSession();
			String hql;
			Query query;
			hql = "from SanPham where idGH.idGH = :idGH";
			query = session.createQuery(hql);
			query.setParameter("idGH", Integer.parseInt(request.getParameter("idGH")) );
			List<SanPham> listSPduyet = query.list();
			List<SanPham> listSPtemp = new ArrayList<>();
			List<Integer> listSLtemp = new ArrayList<>();

			for (SanPham sp : listSPduyet) {
				if (listSPtemp.size()== 0)
				{
					listSPtemp.add(sp);
					listSLtemp.add(1);
				}
				else {
					int check = 0;
					 for (SanPham temp : listSPtemp)
					 {
						 if(temp.getMaLoai().getMaLoai().equals(sp.getMaLoai().getMaLoai()))
						 {
							 int index = listSPtemp.indexOf(temp);
							 int SL = listSLtemp.get(index);
							 listSLtemp.set(index, SL +1);
							 check = 1;
						 }
					 }
					 if (check ==0 )
					 {
						 listSPtemp.add(sp);
						listSLtemp.add(1);
					 }
				}
			   
			}
			model.addAttribute("listSP",listSPtemp);
			model.addAttribute("listSL",listSLtemp);
			model.addAttribute("size" , listSLtemp.size()-1);
			model.addAttribute("nhanVien", nhanvien);
			hql = "FROM GioHang Where idGH = :idGH";
			query = session.createQuery(hql);
			query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
			GioHang gioHang = (GioHang) query.uniqueResult();
			model.addAttribute("gioHang" , gioHang);
			
			return "staff/chitietGHdangnhan";
		}
		@RequestMapping("giaohang")
		public String giaohang(Model model , HttpServletRequest request ,HttpSession s) {
			if ( s.getAttribute("user") == null)
			{
				return "redirect:dangnhap.htm";
			}
			Session session = factory.getCurrentSession();
			String hql  = "FROM GioHang Where idGH = :idGH";
			Query query = session.createQuery(hql);
			query.setParameter("idGH",Integer.parseInt(request.getParameter("idGH"))  );
			GioHang gioHang = (GioHang) query.uniqueResult();
			gioHang.setTrangThai(3);
			session.save(gioHang);

			return "redirect:donhangdangnhan.htm";
		}
>>>>>>> main
}

