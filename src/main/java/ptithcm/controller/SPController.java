package ptithcm.controller;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import ptit.bean.UploadFile;
import ptithcm.model.HangSanXuat;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.TheLoai;
@Transactional
@Controller
@RequestMapping("/home/")
public class SPController {
	
	@Autowired
	SessionFactory factory;
	@Autowired
	@Qualifier("uploadfile")
	UploadFile baseUploadFile;
	
	@RequestMapping(value="danh-muc-san-pham", method = RequestMethod.GET)
	public String showDanhMucSanPham(ModelMap model,
			@RequestParam(defaultValue = "0") int page
			) {
		int pageSize = 9;
		int totalLoaiSanPham = getSoLuongLoaiSanPham();
		int totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
		int startPage = Math.max(0, page - 1);
		int endPage = Math.min(totalPages - 1, page + 1);
		
		List<LoaiSanPham> listLoaiSanPham = getLoaiSanPham(page,pageSize);
		System.out.println(page);
		model.addAttribute("stt", page*pageSize + 1);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		
		return "sp/DanhMucSanPham";
	}
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String form(ModelMap model) {
		String hinhAnh = "sp.png";
		model.addAttribute("hinhanh", hinhAnh);
		List<TheLoai> theLoai = getALLTheLoai();
		List<HangSanXuat> hangSanXuat = getALLHangSanxuat();
		model.addAttribute("listTheLoai", theLoai);
		model.addAttribute("listHang", hangSanXuat);
		System.out.println(baseUploadFile.getBasePath());
		return "sp/themsp";
	}
	@RequestMapping(value="add", method = RequestMethod.POST) public String
	  themSanPham(ModelMap model,
			  @RequestParam("photo") MultipartFile photo,
			  	HttpServletRequest request
	 ) {
		String maLoai = request.getParameter("maLoai");
		if (getMaLoai(maLoai)) {
			model.addAttribute("message", "Mã loại bị trùng");
			return "sp/themsp";
		}
		String tenSP = request.getParameter("ten");
		String gia = request.getParameter("gia");
		String cpu = request.getParameter("cpu");
		String ram = request.getParameter("ram");
		String hardware = request.getParameter("hardware");
		String card = request.getParameter("card");
		String screen = request.getParameter("screen");
		String os = request.getParameter("os");
		String moTa = request.getParameter("moTa");
		String theLoai = request.getParameter("theLoai");
		String hang = request.getParameter("hangSanXuat");
		BigDecimal gia1 = new BigDecimal(gia);
		
		String fileName = saveImage(photo);
		if(fileName == null) {
			fileName = "sp.png";
		}
		LoaiSanPham x = new LoaiSanPham(maLoai, tenSP, gia1, fileName, moTa, cpu, 
				ram, hardware, card, screen, os, null, null);
		String s = saveLoaiSanPham(x, theLoai, hang);
		model.addAttribute("message", s);
	  return "sp/themsp";
	}
	@RequestMapping(value="shop", method = RequestMethod.GET)
	public String showShop(ModelMap model,
			@RequestParam(defaultValue = "0") int page
			) {
		int pageSize = 9;
		int totalLoaiSanPham = getSoLuongLoaiSanPham();
		int totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
		int startPage = Math.max(0, page - 1);
		int endPage = Math.min(totalPages - 1, page + 1);
		
		List<LoaiSanPham> listLoaiSanPham = getLoaiSanPham(page,pageSize);
		//System.out.println(listLoaiSanPham.size());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		return "sp/shop";
	}
	
	private List<LoaiSanPham> getLoaiSanPham(int page, int pageSize) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);
		int offset = page * pageSize;
		List<LoaiSanPham> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		return list;
	}
	
	private int getSoLuongLoaiSanPham() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);
		List<LoaiSanPham> list = query.list();
		return list.size();
}
	
	private String saveImage(MultipartFile multipartFile) {
		try {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			String fileName = date + multipartFile.getOriginalFilename();
			String photoPath = baseUploadFile.getBasePath() +File.separator + fileName;
			//System.out.println(photoPath);
			multipartFile.transferTo(new File(photoPath));
			Thread.sleep(3500);
			return fileName;
 		} catch (Exception e) {
 			return null;
 		}
	}
	
	private List<TheLoai> getALLTheLoai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai";
		Query query = session.createQuery(hql);
		List<TheLoai> list = query.list();
		return list;
	}
	
	private Boolean getMaLoai(String maLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham WHERE maLoai=:maLoai1";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", maLoai);
		List<LoaiSanPham> list = query.list();
		if (list.size() == 0) return false;
		return true;
	}
	private List<HangSanXuat> getALLHangSanxuat() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat";
		Query query = session.createQuery(hql);
		List<HangSanXuat> list = query.list();
		return list;
	}
	
	private String saveLoaiSanPham(LoaiSanPham x,
									String theLoai,
									String hangSanXuat) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		TheLoai tl = getTheLoai(theLoai);
		HangSanXuat hsx = getHangSanxuat(hangSanXuat);
		if ( tl == null) {
			int sizeTheLoai = getSizeTheLoai(theLoai);
			String maTheLoai = "" + String.valueOf(theLoai.charAt(0)) + 
					String.valueOf(theLoai.charAt(theLoai.length()-1))
					+ Integer.toString(sizeTheLoai);
			 tl= new TheLoai(maTheLoai, theLoai);
			try {
				session.save(tl);
				t.commit();
			} catch(Exception e) {
				t.rollback();
				session.close();
				return "Thêm thể loại thất bại";
			}
		} 
		if (hsx==null) {
			int sizeHangSanXuat = getSizeHangSanXuat(hangSanXuat);
			String maHangSanXuat = "" + String.valueOf(hangSanXuat.charAt(0)) + 
					String.valueOf(hangSanXuat.charAt(hangSanXuat.length()-1))
					+ Integer.toString(sizeHangSanXuat);
			 hsx = new HangSanXuat(maHangSanXuat, hangSanXuat);
			try {
				session.save(hsx);
				t.commit();
			} catch(Exception e) {
				t.rollback();
				session.close();
				return "Thêm hãng sản xuất thất bại";
			}
		}
		x.setMaTheLoai(tl);
		x.setMaHang(hsx);
		try {
			session.save(x);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			session.close();
			return "Thêm sản phẩm thất bại";
		} finally {
			session.close();
		}
		return "Thêm sản phẩm thành công";
	}
	
	private TheLoai getTheLoai(String theLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai WHERE  tenTL=:tenTheLoai";
		Query query = session.createQuery(hql);
		query.setParameter("tenTheLoai", theLoai);
		List<TheLoai> list = query.list();
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	private int getSizeTheLoai(String theLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai WHERE  tenTL=:tenTheLoai";
		Query query = session.createQuery(hql);
		query.setParameter("tenTheLoai", theLoai);
		List<TheLoai> list = query.list();
		return list.size();
	}
	
	private HangSanXuat getHangSanxuat(String hangSanXuat) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat WHERE  tenHang=:tenHang1";
		Query query = session.createQuery(hql);
		query.setParameter("tenHang1", hangSanXuat);
		List<HangSanXuat> list = query.list();
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	private int getSizeHangSanXuat(String hangSanXuat) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat WHERE  tenHang=:tenHang1";
		Query query = session.createQuery(hql);
		query.setParameter("tenHang1", hangSanXuat);
		List<HangSanXuat> list = query.list();
		return list.size();
	}
}
