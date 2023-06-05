package ptithcm.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.compress.utils.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import ptit.bean.UploadFile;
import ptithcm.model.BinhLuan;
import ptithcm.model.CTDotGiamGia;
import ptithcm.model.Cart;
import ptithcm.model.ChinhSuaGia;
import ptithcm.model.DotGiamGia;
import ptithcm.model.GioHang;
import ptithcm.model.HangSanXuat;
import ptithcm.model.HoaDon;
import ptithcm.model.KhachHang;
import ptithcm.model.LoaiSanPham;
import ptithcm.model.NhanVien;
import ptithcm.model.SanPham;
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
	
	
	// Xử lý danh muc sản phẩm
	String inputSearch = "";
	
	@RequestMapping(value={ "/", "/index" },method = RequestMethod.GET)
	public String showHome(ModelMap model, HttpSession session) {
		if (session.getAttribute("user1") != null) {
			return "redirect:/homenv.htm";
		} else if(session.getAttribute("user2") != null) {
			return "redirect:/donhangchuagiao.htm";
		}
		KhachHang nguoi = (KhachHang) session.getAttribute("user");;
		GioHang gh = null;
		if(nguoi != null) {
			gh = getGioHang(nguoi.getEmail()); 
		}
		List<LoaiSanPham> list1 = getLoaiSanPham_HOME();
		if (list1==null) {
			model.addAttribute("SLsanPham", "0");
			model.addAttribute("listProducts", null);
			model.addAttribute("product1",null);
			model.addAttribute("listLoaiSanPham", null);
			return "sp/home";
		}
		sort(list1);
		ArrayList<LoaiSanPham> list2 = new ArrayList<>();
		ArrayList<LoaiSanPham> list3 = new ArrayList<>();
		int n = list1.size() >= 5 ? 5:list1.size();
		int m = list1.size() >= 3 ? 3:list1.size();
		Hibernate.initialize(list1.get(0).getMaTheLoai());
		Hibernate.initialize(list1.get(0).getMaHang());

		for(int i=1 ; i < n; i ++ ) {
			Hibernate.initialize(list1.get(i).getMaTheLoai());
			Hibernate.initialize(list1.get(i).getMaHang());
			list2.add(list1.get(i));
		}
		for(int i=0 ; i < m; i ++ ) {
			Hibernate.initialize(list1.get(i).getMaTheLoai());
			Hibernate.initialize(list1.get(i).getMaHang());
			list1.get(i).setBinhLuan(getBinhLuanLSP(list1.get(i).getMaLoai()));
			list3.add(list1.get(i));
		}
		if (gh == null) {
			model.addAttribute("SLsanPham", "0");
		} else {
			model.addAttribute("SLsanPham", getSLSanPhamCuaGH(gh.getIdGH()));
		}
		model.addAttribute("listProducts", list2);
		model.addAttribute("product1",list1.get(0));
		model.addAttribute("listLoaiSanPham", list3);
		return "sp/home";
	}
	private void sort(List<LoaiSanPham> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
            	int a = list.get(j).getSanPham()== null ? 0 : list.get(j).getSanPham().size();
            	int b = list.get(j+1).getSanPham()== null ? 0 : list.get(j+1).getSanPham().size();
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
		if (list.size() == 0) return null;
		DotGiamGia dgg = getDotGiamGia();
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
		if(list.size() ==0) return null;
		return list;
	}
	@RequestMapping(value="about",method = RequestMethod.GET)
	public String showAbout() {
		return "sp/about";
	}
	@RequestMapping(value="danh-muc-san-pham", method = RequestMethod.GET)
	public String showDanhMucSanPham(ModelMap model,
			HttpSession session,
			@RequestParam(defaultValue = "0") int page
			) {
		if(session.getAttribute("user1")==null) {
			return "redirect:/dangnhap.htm";
		}
		int pageSize = 9;
		int totalLoaiSanPham = getSoLuongLoaiSanPham();
		int totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
		int startPage = Math.max(0, page - 1);
		int endPage = Math.min(totalPages - 1, page + 1);
		if(totalPages == 0) {
			startPage = endPage = 0;
		}
		String url ="/BanLaptop/home/danh-muc-san-pham.htm";
		List<LoaiSanPham> listLoaiSanPham = getLoaiSanPhamDMSP(page,pageSize);
		for (LoaiSanPham sp: listLoaiSanPham) {
			BigDecimal strippedValue = sp.getGia().stripTrailingZeros();
			sp.setGia(strippedValue);
		}
		//System.out.println(page);
		model.addAttribute("stt", page*pageSize + 1);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		model.addAttribute("url",url);
		
		return "sp/DanhMucSanPham";
	}
	
	@RequestMapping(value="danh-muc-san-pham/chinh-sua", method = RequestMethod.GET)
	public String chinhSuaSanPham(ModelMap model,
			HttpSession session,
			@RequestParam String sp
			) {
		if(session.getAttribute("user1")==null) {
			return "redirect:/dangnhap.htm";
		}
		LoaiSanPham sanPham = tim1LoaiSanPham(sp);
		Hibernate.initialize(sanPham.getMaTheLoai());
		Hibernate.initialize(sanPham.getMaHang());
		BigDecimal strippedValue = sanPham.getGia().stripTrailingZeros();
		sanPham.setGia(strippedValue);
		strippedValue = sanPham.getGiaNhap().stripTrailingZeros();
		sanPham.setGiaNhap(strippedValue);
		model.addAttribute("product",sanPham);
		List<TheLoai> theLoai = getALLTheLoai();
		List<HangSanXuat> hangSanXuat = getALLHangSanxuat();
		model.addAttribute("listTheLoai", theLoai);
		model.addAttribute("listHang", hangSanXuat);
		return "sp/chinh-sua";
	}
	@RequestMapping(value="danh-muc-san-pham/chinh-sua", method = RequestMethod.POST) 
	public String updateLoaiSanPham(ModelMap model,
				HttpSession session,
			  	HttpServletRequest request,
			  	@RequestParam("photo") MultipartFile photo
	 ) {
		int check = 0;
		int check1 = 0;
		String maLoai = request.getParameter("maLoai");
		LoaiSanPham sanPham = tim1LoaiSanPham(maLoai);
		Hibernate.initialize(sanPham.getMaTheLoai());
		Hibernate.initialize(sanPham.getMaHang());
		NhanVien nv = (NhanVien) session.getAttribute("user1");
		if(nv==null) {
			return "redirect:/dangnhap.htm";
		}
		String tenSP = request.getParameter("ten");
		if (!sanPham.getTenSP().equals(tenSP)) 
			{	check++;
				sanPham.setTenSP(tenSP);
			}
		String gia = request.getParameter("gia");
		String cpu = request.getParameter("cpu");
		if (!sanPham.getcPU().equals(cpu))
		{	check++;
			sanPham.setcPU(cpu);
		}
		String ram = request.getParameter("ram");
		if (!sanPham.getRam().equals(ram)) {
			sanPham.setRam(ram);
			check++;
		}
		String hardware = request.getParameter("hardware");
		if (!sanPham.getHardWare().equals(hardware)) {
			sanPham.setHardWare(hardware);
			check++;
		}
		String card = request.getParameter("card");
		if (!sanPham.getCard().equals(card)) 
			{
				sanPham.setCard(card);
				check++;
			}
		String screen = request.getParameter("screen");
		if (!sanPham.getScreen().equals(screen)) {
			sanPham.setScreen(screen);
			check++;
		}
		String os = request.getParameter("os");
		if (!sanPham.getOs().equals(os)) {
			sanPham.setOs(os);
			check++;
		}
		String moTa = request.getParameter("moTa");
		if (!sanPham.getMoTa().equals(moTa)) {
			sanPham.setMoTa(moTa);
			check++;
		}
		//System.out.println(moTa);
		String theLoai = request.getParameter("theLoai");
		if (!sanPham.getMaTheLoai().getMaTheLoai().equals(theLoai)) check++;
		String hang = request.getParameter("hangSanXuat");
		if (!sanPham.getMaHang().getMaHang().equals(hang)) check++;
		BigDecimal gia1 = new BigDecimal(gia);
		if (sanPham.getGia().compareTo(gia1) != 0) {
			sanPham.setGia(gia1);
			check1++;
		}
		String giaNhap = request.getParameter("giaNhap");
		BigDecimal giaNhap1 = new BigDecimal(giaNhap);
		if (sanPham.getGiaNhap().compareTo(giaNhap1) != 0) {
			sanPham.setGiaNhap(giaNhap1);
			check++;
		}
		if (!photo.getOriginalFilename().equals("")) {
		String fileName = saveImage(photo);
		if(fileName!=null) {
			sanPham.setAnh(fileName);
			check++;
		} }
		String s = "";
		if(check!=0) {
			s = updateLoaiSanPham(sanPham, theLoai, hang, nv);
		}
		if (check1 != 0) {
			s = updateLoaiSanPham(sanPham, theLoai, hang, nv);
			Session session1 = factory.openSession();
			Transaction t = session1.beginTransaction();
			LocalDate localDate = LocalDate.now();
			Date currentDate = Date.valueOf(localDate);
			ChinhSuaGia csg = new ChinhSuaGia(currentDate,sanPham.getGia(), nv, sanPham);
			try {
				session1.save(csg);
				t.commit();
			} catch(Exception e) {
				t.rollback();
				model.addAttribute("message", "Chỉnh sửa giá thất bại");
			} finally {
				session1.close();
			}
		}
		
		BigDecimal strippedValue = sanPham.getGia().stripTrailingZeros();
		sanPham.setGia(strippedValue);
		strippedValue = sanPham.getGiaNhap().stripTrailingZeros();
		sanPham.setGiaNhap(strippedValue);
		model.addAttribute("product",sanPham);
		List<TheLoai> theLoai1 = getALLTheLoai();
		List<HangSanXuat> hangSanXuat1 = getALLHangSanxuat();
		model.addAttribute("listTheLoai", theLoai1);
		model.addAttribute("listHang", hangSanXuat1);
		if(check == 0 && check1 ==0) {
			model.addAttribute("message", "Bạn chưa thay đổi gì cả ?");
		} else {
			model.addAttribute("message", s);
		}
		return "sp/chinh-sua";
	}
	@RequestMapping(value="danh-muc-san-pham/chinh-sua.htm", params="btnBack")
	public String backDMSP(HttpSession session) {
		if(session.getAttribute("user1")==null) {
			return "redirect:/dangnhap.htm";
		}
		return "redirect:/home/danh-muc-san-pham.htm";
	}
	@RequestMapping(value="danh-muc-san-pham/{sp}.htm", params = "linkDelete") 
	public String deleteLoaiSanPham(ModelMap model, 
			HttpSession ss,
			@PathVariable("sp") String maLoai) {

		LoaiSanPham sanPham = tim1LoaiSanPham(maLoai);
		
		List<ChinhSuaGia> csg = getChinhSuaGia(maLoai);
		for (ChinhSuaGia c: csg) {
			Integer i = xoaChinhSuaGia(c);
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		LoaiSanPham sp = (LoaiSanPham) session.merge(sanPham);
		try {
			session.delete(sp);
			t.commit();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
		return "redirect:/home/danh-muc-san-pham.htm";
	}
	private List<ChinhSuaGia> getChinhSuaGia(String maLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChinhSuaGia WHERE maLoai.maLoai = :maLoai";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai", maLoai);
		List<ChinhSuaGia> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	private Integer xoaChinhSuaGia(ChinhSuaGia x) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		ChinhSuaGia y = (ChinhSuaGia) session.merge(x);
		try {
			session.delete(y);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	private String updateLoaiSanPham(LoaiSanPham x,
			String theLoai,
			String hangSanXuat, NhanVien nv) {
			
			TheLoai tl = getTheLoai(theLoai);
			HangSanXuat hsx = getHangSanxuat(hangSanXuat);
			
			if ( tl == null) {
				//System.out.println(tl.getMaTheLoai());
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				int sizeTheLoai = getSizeTheLoai();
				String maTheLoai = "" + String.valueOf(theLoai.charAt(0)) + 
				String.valueOf(theLoai.charAt(theLoai.length()-1))
				+ Integer.toString(sizeTheLoai);
				tl= new TheLoai(maTheLoai, theLoai);
				try {
				session.save(tl);
				t.commit();
				} catch(Exception e) {
					t.rollback();
					//session.close();
					return "Thêm thể loại thất bại";
					}
				finally {
					session.close();
				}
			} 
			if (hsx==null) {
				//System.out.println(hsx.getMaHang());
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				int sizeHangSanXuat = getSizeHangSanXuat();
				String maHangSanXuat = "" + String.valueOf(hangSanXuat.charAt(0)) + 
				String.valueOf(hangSanXuat.charAt(hangSanXuat.length()-1))
				+ Integer.toString(sizeHangSanXuat);
				hsx = new HangSanXuat(maHangSanXuat, hangSanXuat);
				try {
					session.save(hsx);
					t.commit();
				} catch(Exception e) {
					t.rollback();
					//session.close();
					return "Thêm hãng sản xuất thất bại";
				}
				finally {
					session.close();
				}
			}
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			x.setMaTheLoai(tl);
			x.setMaHang(hsx);
			LoaiSanPham sanPham = (LoaiSanPham) session.merge(x);
			
			try {
				
				session.update(sanPham);
				//session.save(csg);
				t.commit();
			} catch(Exception e) {
				System.out.println(e.getMessage());
				t.rollback();
				//session.close();
				return "Thay đổi sản phẩm thất bại";
			} finally {
				session.close();
			}
			return "Thay đổi sản phẩm thành công";
		}
	
	// Xử lý danh muc sản phẩm <<<<<<<

	@RequestMapping(value="shop", method = RequestMethod.GET)
	public String showShop(ModelMap model,
			HttpSession session,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam (defaultValue = "") String sp
			) {
		if (session.getAttribute("user1") != null) {
			return "redirect:/homenv.htm";
		} else if(session.getAttribute("user2") != null) {
			return "redirect:/donhangchuagiao.htm";
		}
		//gioHang.setSanPham(new ArrayList<>());
		int pageSize = 9;
		int totalLoaiSanPham ; 
		int totalPages ;
		
		int startPage ;
		int endPage ;
		String url = "/BanLaptop/home/shop.htm";
		List<LoaiSanPham> listLoaiSanPham ;
		KhachHang nguoi = (KhachHang) session.getAttribute("user");;
		GioHang gh = null;
		if(nguoi != null) {
			gh = getGioHang(nguoi.getEmail()); 
		}
		
		if(!sp.equals("")) {
			if (session.getAttribute("user") == null) {
				return "redirect:/dangnhap.htm";
			}
			 
			if (gh == null) {
				gh = new GioHang(0,null,null,null,nguoi,null);
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				try {
					ss.save(gh);
					t.commit();
				}catch(Exception e) {
					t.rollback();
				} finally {
					ss.close();
				}
			}
			List<SanPham> list = getSanPham(sp);
			if (list != null) {
				SanPham SPThemVaoGH = list.get(0);
				SPThemVaoGH.setIdGH(gh);
				//gh.setSanPham(getSanPhamGH(gh.getIdGH()));
				//System.out.println(gh.getSanPham());
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				SanPham x = (SanPham) ss.merge(SPThemVaoGH);
				try {
					//ss.save(gh);
					ss.update(x);
					t.commit();
				}catch(Exception e) {
					t.rollback();
				} finally {
					ss.close();
				}
			}
			sp = "";
		}
		

			totalLoaiSanPham = searchSLProduct1(inputSearch);
			totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
			if (totalPages ==0) totalPages = 1;
			startPage = Math.max(0, page - 1);
			endPage = Math.min(totalPages - 1, page + 1);
			listLoaiSanPham = this.searchProduct1(page, pageSize, inputSearch);
			List<LoaiSanPham> listLoaiSanPham1 = getLoaiSanPham(page,pageSize);
			model.addAttribute("listLoais", listLoaiSanPham1);

		for (LoaiSanPham x:listLoaiSanPham) {
			BigDecimal strippedValue = x.getGia().stripTrailingZeros();
			x.setGia(strippedValue);
		}
		List<HangSanXuat> listHang = getHang();
		List<TheLoai> listTheLoai = getTheLoai();
		model.addAttribute("listTheLoai", listTheLoai);
		model.addAttribute("listHang", listHang);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		for (LoaiSanPham l :  listLoaiSanPham) {
			System.out.println(l.getMaLoai());
			System.out.println(l.getCtDotGiamGia());
		}
		if (gh == null) {
			model.addAttribute("SLsanPham", "0");
		} else {
			model.addAttribute("SLsanPham", getSLSanPhamCuaGH(gh.getIdGH()));
		}
		model.addAttribute("url", url);
		return "sp/shop";
	}


	@RequestMapping(value="shop/search", method = RequestMethod.GET)
	public String searchProduct(ModelMap model,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") int page
			) {
		if (session.getAttribute("user1") != null) {
			return "redirect:/homenv.htm";
		} else if(session.getAttribute("user2") != null) {
			return "redirect:/donhangchuagiao.htm";
		}
		inputSearch = request.getParameter("searchInput");
		if (inputSearch.equals("")) {
			return "redirect:/home/shop/search.htm";
		}
		int pageSize = 9;
		int totalLoaiSanPham = searchSLProduct1(inputSearch);
		int totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
		if (totalPages ==0) totalPages = 1;
		int startPage = Math.max(0, page - 1);
		int endPage = Math.min(totalPages - 1, page + 1);
		String url = "/BanLaptop/home/shop.htm";
		List<LoaiSanPham> listLoaiSanPham = this.searchProduct1(page, pageSize, inputSearch);
		List<LoaiSanPham> listLoaiSanPham1 = getLoaiSanPham(page,pageSize);
		List<HangSanXuat> listHang = getHang();
		List<TheLoai> listTheLoai = getTheLoai();
		for (LoaiSanPham x:listLoaiSanPham) {
			BigDecimal strippedValue = x.getGia().stripTrailingZeros();
			x.setGia(strippedValue);
		}
		model.addAttribute("listTheLoai", listTheLoai);
		model.addAttribute("listHang", listHang);
		model.addAttribute("searchInput", inputSearch);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		model.addAttribute("listLoais", listLoaiSanPham1);
		model.addAttribute("url", url);
		//System.out.println(inputSearch);
		//System.out.println(totalLoaiSanPham);
		return "sp/shop";
	}
	@RequestMapping(value="shop-single", method = RequestMethod.POST) 
	public String themSanPhamVaoGH(ModelMap model,
			HttpSession session,
			HttpServletRequest request) {
		String maLoai = request.getParameter("maLoai");
		int soLuong = Integer.parseInt(request.getParameter("soLuong"));
		if (session.getAttribute("user") == null) {
			return "redirect:/dangnhap.htm";
		}
		KhachHang nguoi = (KhachHang) session.getAttribute("user");;
		GioHang gh = null;
		if(nguoi != null) {
			gh = getGioHang(nguoi.getEmail()); 
		}
		
		if (gh == null) {
			gh = new GioHang(0,null,null,null,nguoi,null);
			Session ss = factory.openSession();
			Transaction t = ss.beginTransaction();
			try {
				ss.save(gh);
				t.commit();
			}catch(Exception e) {
				t.rollback();
			} finally {
					ss.close();
			}
		}
		List<SanPham> list = getSanPham(maLoai);
		if (list != null) {
			for(int i=0 ; i < soLuong;i++) {
				SanPham SPThemVaoGH = list.get(i);
				SPThemVaoGH.setIdGH(gh);
					//gh.setSanPham(getSanPhamGH(gh.getIdGH()));
					//System.out.println(gh.getSanPham());
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				SanPham x = (SanPham) ss.merge(SPThemVaoGH);
				try {
					ss.update(x);
					t.commit();
					model.addAttribute("message", "Thêm vào giỏ hàng thành công!");
				}catch(Exception e) {
					t.rollback();
					model.addAttribute("message", "Thêm vào giỏ hàng thất bại");
					break;
				} finally {
					ss.close();
				}
			}
		}
		LoaiSanPham x = tim1LoaiSanPham(maLoai);
		BigDecimal strippedValue = x.getGia().stripTrailingZeros();
		x.setGia(strippedValue);
		model.addAttribute("product",x);
		return "sp/shop-single";
	}
	@RequestMapping(value="shop-single",params="btnBuy", method = RequestMethod.POST )
	public String muaNgay(ModelMap model,
			HttpSession session,
			HttpServletRequest request) {
		String maLoai = request.getParameter("maLoai");
		int soLuong = Integer.parseInt(request.getParameter("soLuong"));
		if (session.getAttribute("user") == null) {
			return "redirect:/dangnhap.htm";
		}
		KhachHang nguoi = (KhachHang) session.getAttribute("user");;
		GioHang gh = null;
		if(nguoi != null) {
			gh = getGioHang(nguoi.getEmail()); 
		}
		
		if (gh == null) {
			gh = new GioHang(0,null,null,null,nguoi,null);
			Session ss = factory.openSession();
			Transaction t = ss.beginTransaction();
			try {
				ss.save(gh);
				t.commit();
			}catch(Exception e) {
				t.rollback();
			} finally {
					ss.close();
			}
		}
		List<SanPham> list = getSanPham(maLoai);
		if (list != null) {
			for(int i=0 ; i < soLuong;i++) {
				SanPham SPThemVaoGH = list.get(i);
				SPThemVaoGH.setIdGH(gh);
					//gh.setSanPham(getSanPhamGH(gh.getIdGH()));
					//System.out.println(gh.getSanPham());
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				SanPham x = (SanPham) ss.merge(SPThemVaoGH);
				try {
					ss.update(x);
					t.commit();
				}catch(Exception e) {
					t.rollback();
					break;
				} finally {
					ss.close();
				}
			}
		}
		Session ss = factory.getCurrentSession();
		String hql = "SELECT DISTINCT maLoai.maLoai FROM SanPham WHERE idGH.idGH = :id AND daBan = 0 ";
		Query query = ss.createQuery(hql);
		query.setParameter("id", gh.getIdGH());
		List<String> list1 = query.list();
		cart.clear();
		for(int i =list1.size()-1 ; i>=0 ;i--) {
			Cart tmp = new Cart();
			
			LoaiSanPham sp1 = tim1LoaiSanPham(list1.get(i));
			BigDecimal strippedValue = sp1.getGia().stripTrailingZeros();
			sp1.setGia(strippedValue);
			tmp.setLsp(sp1);
			tmp.setIdGH(gh.getIdGH());
			tmp.setSoLuong((int) getSLSanPhamCua1Loai(gh.getIdGH(), list1.get(i)).longValue());
			int soLuongCon = (getSanPham(list1.get(i)) == null) ? 0: getSanPham(list1.get(i)).size();
			
			tmp.setSoLuongMax(tmp.getSoLuong()+soLuongCon);
			tmp.setCheck(0);
			cart.add(tmp);
		}
		model.addAttribute("lsp",maLoai);
		model.addAttribute("sl",-1);
		model.addAttribute("thaotac","chon");
		return "redirect:/home/gio-hang.htm";
	}
	@RequestMapping(value="shop/search/{input}.htm",  params = "linkSearch", method = RequestMethod.GET)
	public String searchProductLink(ModelMap model, HttpSession session,@PathVariable("input") String input) {
		if (session.getAttribute("user1") != null) {
			return "redirect:/homenv.htm";
		} else if(session.getAttribute("user2") != null) {
			return "redirect:/donhangchuagiao.htm";
		}
		System.out.println(input);
		model.addAttribute("searchInput", input);
		return "redirect:/home/shop/search.htm";
	}
	@RequestMapping(value="shop-single", method = RequestMethod.GET)
	public String show1LoaiSanPham(ModelMap model,
			HttpServletRequest request,
			@RequestParam(defaultValue = "") String lsp) {
		if (lsp.equals("")) return "redirect:/home/shop.htm";
		LoaiSanPham x = tim1LoaiSanPham(lsp);
		BigDecimal strippedValue = x.getGia().stripTrailingZeros();
		x.setGia(strippedValue);
		model.addAttribute("product",x);
		model.addAttribute("message","");
		return "sp/shop-single";
		
	}
	private List<TheLoai> getTheLoai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai";
		Query query = session.createQuery(hql);
		List<TheLoai> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	
	private List<HangSanXuat> getHang() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat";
		Query query = session.createQuery(hql);
		List<HangSanXuat> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	
	// Xử lí giỏ hàng >>>>>
	
	ArrayList<Cart> cart = new ArrayList<>();
	ArrayList<Cart> cart1 = new ArrayList<>();
	@RequestMapping(value="gio-hang", method=RequestMethod.GET)
	public String showGH(ModelMap model, HttpSession session,
			HttpServletRequest request,
			@RequestParam(defaultValue = "") String lsp,
			@RequestParam (defaultValue = "-1") int sl,
			@RequestParam (defaultValue = "") String thaotac
			) {
		if(lsp.equals("")) {
		
		KhachHang nguoi = (KhachHang) session.getAttribute("user");;
		
		
		if(nguoi == null) {
			return "redirect:/dangnhap.htm";
		}
		GioHang gh = null;
		gh = getGioHang(nguoi.getEmail());  
		if (gh == null) {
				gh = new GioHang(0,null,null,null,nguoi,null);
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				try {
					ss.save(gh);
					t.commit();
				}catch(Exception e) {
					t.rollback();
				} finally {
					ss.close();
				}
		}
		//System.out.println(gh.getIdGH());
		Session ss = factory.getCurrentSession();
		String hql = "SELECT DISTINCT maLoai.maLoai FROM SanPham WHERE idGH.idGH = :id AND daBan = 0 ";
		Query query = ss.createQuery(hql);
		query.setParameter("id", gh.getIdGH());
		List<String> list = query.list();
		cart.clear();
		cart1.clear();
		for(int i =list.size()-1 ; i>=0 ;i--) {
			Cart tmp = new Cart();
			
			LoaiSanPham sp1 = tim1LoaiSanPham(list.get(i));
			BigDecimal strippedValue = sp1.getGia().stripTrailingZeros();
			sp1.setGia(strippedValue);
			tmp.setLsp(sp1);
			tmp.setIdGH(gh.getIdGH());
			tmp.setSoLuong((int) getSLSanPhamCua1Loai(gh.getIdGH(), list.get(i)).longValue());
			int soLuongCon = (getSanPham(list.get(i)) == null) ? 0: getSanPham(list.get(i)).size();
			
			tmp.setSoLuongMax(tmp.getSoLuong()+soLuongCon);
			tmp.setCheck(0);
			cart.add(tmp);
		}
		model.addAttribute("sum",new BigDecimal(0));
		model.addAttribute("SLVP",cart1.size());
		model.addAttribute("cart", cart);
		} else {
			BigDecimal sum = ((BigDecimal) session.getAttribute("sumGH"));
			if(sl == -1) {
				
				if(thaotac.equals("chon")) {
					for(int i=0; i < cart.size(); i++) {
						if(cart.get(i).getLsp().getMaLoai().equals(lsp)) {
							
							if(cart.get(i).getCheck() == 0) {
							cart.get(i).setCheck(1);
							cart1.add(cart.get(i));
							if (cart.get(i).getLsp().getCtDotGiamGia()== null) {
								sum =sum.add(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
							} else {
								BigDecimal x = cart.get(i).getLsp().getGia().multiply(new BigDecimal(100 -cart.get(i).getLsp().getCtDotGiamGia().get(0).getTiLeGiam()));
								x = x.divide(new BigDecimal(100));
								x = x.multiply(new BigDecimal(cart.get(i).getSoLuong()));
								sum =sum.add(x);
							}
							session.setAttribute("sumGH", sum);
							} 
							model.addAttribute("SLVP", cart1.size());
							model.addAttribute("sum",sum);
							model.addAttribute("cart", cart);
							return "sp/gio-hang";
							}
					}
				} else if(thaotac.equals("bochon")) {
					for(int i=0; i < cart.size(); i++) {
						if(cart.get(i).getLsp().getMaLoai().equals(lsp)) {
							if(cart.get(i).getCheck() == 1) {
							cart.get(i).setCheck(0);
							
							if (cart.get(i).getLsp().getCtDotGiamGia()== null) {
								sum =sum.subtract(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
							} else {
								BigDecimal x = cart.get(i).getLsp().getGia().multiply(new BigDecimal(100 -cart.get(i).getLsp().getCtDotGiamGia().get(0).getTiLeGiam()));
								x = x.divide(new BigDecimal(100));
								x = x.multiply(new BigDecimal(cart.get(i).getSoLuong()));
								sum =sum.subtract(x);
							}
							session.setAttribute("sumGH", sum);
							model.addAttribute("sum",sum);
							break;
							} else {
								model.addAttribute("sum",sum);
								model.addAttribute("SLVP",cart1.size());
								model.addAttribute("cart", cart);
								return "sp/gio-hang";
							}
							}	
					}
					for(int i=0; i < cart1.size(); i++) {
						if(cart1.get(i).getLsp().getMaLoai().equals(lsp)) {
							cart1.remove(i);
							break;
						}
					}
					model.addAttribute("sum",sum);
					model.addAttribute("SLVP",cart1.size());
					model.addAttribute("cart", cart);
					return "sp/gio-hang";
				}
					
			}
			if(sl == 0) {
				List<SanPham> listSP1 = getSanPhamCuaLoaiGH(lsp, cart.get(0).getIdGH());
				for (SanPham sp1 :listSP1) {
					sp1.setIdGH(null);
					Session ss = factory.openSession();
					Transaction t = ss.beginTransaction();
					SanPham sp2 = (SanPham) ss.merge(sp1);
					try {
						ss.update(sp2);
						t.commit();
					}catch(Exception e) {
						t.rollback();
					} finally {
						ss.close();
					}
				}
				for(int i=0; i < cart.size(); i++) {
					if(cart.get(i).getLsp().getMaLoai().equals(lsp)) {
						if(cart.get(i).getCheck() == 1) {
							if (cart.get(i).getLsp().getCtDotGiamGia()== null) {
								sum =sum.subtract(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
							} else {
								BigDecimal x = cart.get(i).getLsp().getGia().multiply(new BigDecimal(100 -cart.get(i).getLsp().getCtDotGiamGia().get(0).getTiLeGiam()));
								x = x.divide(new BigDecimal(100));
								x = x.multiply(new BigDecimal(cart.get(i).getSoLuong()));
								sum =sum.subtract(x);
							}
							session.setAttribute("sumGH", sum);
							}
						cart.remove(i);
						break;
					}
				}
				for(int i=0; i < cart1.size(); i++) {
					if(cart1.get(i).getLsp().getMaLoai().equals(lsp)) {
						cart1.remove(i);
						break;
					}
				}
				model.addAttribute("sum",sum);
				model.addAttribute("SLVP",cart1.size());
				model.addAttribute("cart", cart);
				return "sp/gio-hang";
			}
			for (Cart c:cart) {
				if(c.getLsp().getMaLoai().equals(lsp)) {
					if(c.getSoLuong() > sl) {
						SanPham sp1 = getSanPhamCua1LoaiGH(lsp, c.getIdGH());
						sp1.setIdGH(null);
						Session ss = factory.openSession();
						Transaction t = ss.beginTransaction();
						SanPham sp2 = (SanPham) ss.merge(sp1);
						try {
							ss.update(sp2);
							t.commit();
						}catch(Exception e) {
							t.rollback();
						} finally {
							ss.close();
						}
						c.setSoLuong(sl);
						if (c.getLsp().getCtDotGiamGia()== null) {
							sum =sum.subtract(c.getLsp().getGia().multiply(new BigDecimal(1)));
						} else {
							BigDecimal x = c.getLsp().getGia().multiply(new BigDecimal(100 -c.getLsp().getCtDotGiamGia().get(0).getTiLeGiam()));
							x = x.divide(new BigDecimal(100));
							x = x.multiply(new BigDecimal(1));
							sum =sum.subtract(x);
						}
						model.addAttribute("sum",new BigDecimal(0));
						model.addAttribute("SLVP",cart1.size());
						model.addAttribute("cart", cart);
						
					}
					else {
						SanPham sp1 = getSanPham(lsp).get(0);
						sp1.setIdGH(getGHid(c.getIdGH()));
						Session ss = factory.openSession();
						Transaction t = ss.beginTransaction();
						SanPham sp2 = (SanPham) ss.merge(sp1);
						try {
							ss.update(sp2);
							t.commit();
						}catch(Exception e) {
							t.rollback();
						} finally {
							ss.close();
						}
						c.setSoLuong(sl);
						if (c.getLsp().getCtDotGiamGia()== null) {
							sum =sum.add(c.getLsp().getGia().multiply(new BigDecimal(1)));
						} else {
							BigDecimal x = c.getLsp().getGia().multiply(new BigDecimal(100 -c.getLsp().getCtDotGiamGia().get(0).getTiLeGiam()));
							x = x.divide(new BigDecimal(100));
							x = x.multiply(new BigDecimal(1));
							sum =sum.add(x);
						}
						model.addAttribute("sum",new BigDecimal(0));
						model.addAttribute("SLVP",cart1.size());
						model.addAttribute("cart", cart);
						
					}
					if(c.getCheck() == 1) {
						session.setAttribute("sumGH", sum);
						model.addAttribute("sum",sum);
						for (Cart c1:cart1) {
							if(c1.getLsp().getMaLoai().equals(lsp)) {
								c1.setSoLuong(sl);
								break;
							}
						}
					}
					return "sp/gio-hang";
				}
			}
		}
		
		
		return "sp/gio-hang";
	}
	@RequestMapping(value="gio-hang", method=RequestMethod.POST)
	public String datGH(ModelMap model, HttpSession session) {
		KhachHang nguoi = (KhachHang) session.getAttribute("user");
		BigDecimal sum = ((BigDecimal) session.getAttribute("sumGH"));
		model.addAttribute("nguoi", nguoi);
		model.addAttribute("stt",1);
		model.addAttribute("sum",sum);
		model.addAttribute("SLVP",cart1.size());
		model.addAttribute("cart", cart1);
		return "sp/thanh-toan";
	}
	@RequestMapping(value="thanh-toan", method=RequestMethod.POST) 
	public String xacNhanThanhToan(ModelMap model, 
			HttpSession session,
			HttpServletRequest request) {
		KhachHang nguoi = (KhachHang) session.getAttribute("user");
		String diaChi = request.getParameter("diaChi");
		if(!nguoi.getDiaChi().equals(diaChi)) {
			Session ss = factory.openSession();
			Transaction t = ss.beginTransaction();
			nguoi.setDiaChi(diaChi);
			
			KhachHang x = (KhachHang) ss.merge(nguoi);
			try {
				ss.update(x);
				t.commit();
			} catch (Exception e) {
				t.rollback();
			} finally {
				ss.close();
			}
		}
		
		GioHang gh = getGHid_1(nguoi.getEmail());
		if (gh == null) {
			LocalDate localDate = LocalDate.now();
			Date currentDate = Date.valueOf(localDate);
			gh = new GioHang(1,currentDate,null,null,nguoi,null);
			Session ss = factory.openSession();
			Transaction t = ss.beginTransaction();
			try {
				ss.save(gh);
				t.commit();
			}catch(Exception e) {
				t.rollback();
			} finally {
				ss.close();
			}
		}
		for(int i = 0 ; i< cart1.size() ; i++) {
			String maLoai = cart1.get(i).getLsp().getMaLoai();
			for (int j = 0 ; j< cart1.get(i).getSoLuong();j++) {
				SanPham sp = getSanPhamCua1LoaiGH(maLoai, cart.get(i).getIdGH());
				sp.setDaBan(1);
				
				sp.setIdGH(gh);
				Session ss = factory.openSession();
				Transaction t = ss.beginTransaction();
				SanPham sp1 = (SanPham) ss.merge(sp);
				try {
					ss.update(sp);
					t.commit();
				}catch(Exception e) {
					t.rollback();
				} finally {
					ss.close();
				}
			}
		}
		
		
		return "redirect:/home/dang-giao.htm";
	}
	
	@RequestMapping(value="dang-giao", method=RequestMethod.GET) 
	public String showDangGiao(ModelMap model, 
			HttpSession session,
			HttpServletRequest request) {
		KhachHang nguoi = (KhachHang) session.getAttribute("user");
		if(nguoi == null) {
			return "redirect:/dangnhap.htm";
		}
		List<GioHang> gh = getGHid_DangGiao(nguoi.getEmail());
		if (gh == null) {
			return "sp/dang-giao";
		}
		
		ArrayList<Cart> cartDangGiao = new ArrayList<>();
		for(int i =gh.size()-1 ; i>=0 ;i--) {
			List<String> listMaLoai = getMaLoai_DangGiao(gh.get(i).getIdGH());
			for (int j =listMaLoai.size()-1 ; j>=0 ;j--) {
				Cart tmp = new Cart();
				LoaiSanPham sp1 = tim1LoaiSanPham(listMaLoai.get(j));
				BigDecimal strippedValue = sp1.getGia().stripTrailingZeros();
				sp1.setGia(strippedValue);
				tmp.setLsp(sp1);
				tmp.setIdGH(gh.get(i).getIdGH());
				tmp.setSoLuong((int) getSLSanPhamCua1Loai_DangGiao(gh.get(i).getIdGH(), listMaLoai.get(j)).longValue());
				tmp.setSoLuongMax(0);
				tmp.setCheck(gh.get(i).getTrangThai());
				cartDangGiao.add(tmp);
			}
		}
		model.addAttribute("cart",cartDangGiao);
		return "sp/dang-giao";
	}
	private Long getSLSanPhamCua1Loai_DangGiao(int idGH, String maLoai) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(seri) FROM SanPham WHERE  idGH.idGH = :id AND maLoai.maLoai = :maLoai AND daBan = 1";
		Query query = session.createQuery(hql);
		query.setParameter("id", idGH);
		query.setParameter("maLoai", maLoai);
		Long count = (Long) query.uniqueResult();
		return count;
	}
	private List<String> getMaLoai_DangGiao(int idGH){
		Session ss = factory.getCurrentSession();
		String hql = "SELECT DISTINCT maLoai.maLoai FROM SanPham WHERE idGH.idGH = :id AND daBan = 1 ";
		Query query = ss.createQuery(hql);
		query.setParameter("id", idGH);
		List<String> list = query.list();
		return list;
	}
	private List<GioHang> getGHid_DangGiao(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM GioHang WHERE email.email = :email  AND (trangThai = 1 OR trangThai = 2 OR trangThai = 3)";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<GioHang> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	private GioHang getGHid_1(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM GioHang WHERE email.email = :email  AND trangThai = 1";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<GioHang> list = query.list();
		if(list.size() ==0) return null;
		return list.get(0);
	}
	
	private GioHang getGHid(int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM GioHang WHERE idGH.idGH = :idGH";
		Query query = session.createQuery(hql);
		query.setParameter("idGH", idGH);
		List<GioHang> list = query.list();
		if(list.size() ==0) return null;
		return list.get(0);
	}
	private List<SanPham> getSanPhamCuaLoaiGH(String maLoai, int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE maLoai.maLoai =:maLoai1 AND idGH.idGH = :idGH  AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", maLoai);
		query.setParameter("idGH", idGH);
		List<SanPham> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	private SanPham getSanPhamCua1LoaiGH(String maLoai, int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE maLoai.maLoai =:maLoai1 AND idGH.idGH = :idGH  AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", maLoai);
		query.setParameter("idGH", idGH);
		List<SanPham> list = query.list();
		if(list.size() ==0) return null;
		return list.get(0);
	}

	private GioHang getGioHang(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM GioHang WHERE email.email =:email1 AND trangThai = 0";
		Query query = session.createQuery(hql);
		query.setParameter("email1", email);
		List<GioHang> list = query.list();
		if(list.size() != 0)
			return list.get(0);
		else return null;
		
	}
	private List<SanPham> getSanPhamGH(int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE idGH.idGH=:id AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("id", idGH);
		List<SanPham> list = query.list();
		return list;
	}
	
	private List<LoaiSanPham> getLoaiSanPham(int page, int pageSize) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);
		int offset = page * pageSize;
		List<LoaiSanPham> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		DotGiamGia dgg = getDotGiamGia();
		for (LoaiSanPham loaiSanPham : list) {
		    loaiSanPham.setSanPham(getSanPham(loaiSanPham.getMaLoai()));
		    if(dgg == null) {
		    	loaiSanPham.setCtDotGiamGia(null);
		    }
		    else {
		    	loaiSanPham.setCtDotGiamGia(getCTDotGiamGia(dgg.getMaDot(), loaiSanPham.getMaLoai()));
		    }
		}
		
		
		return list;
	}
	private DotGiamGia getDotGiamGia() {
		LocalDate localDate = LocalDate.now();
		Date currentDate = Date.valueOf(localDate);
		Session session = factory.getCurrentSession();
		String hql = "FROM DotGiamGia WHERE ngayBatDau <= :ngay AND ngayKetThuc>=:ngay";
		Query query = session.createQuery(hql);
		query.setParameter("ngay", currentDate);
		if(query.list().size() ==0) return null;
		DotGiamGia x = (DotGiamGia) query.list().get(0);
		return x;
	}
	private List<CTDotGiamGia> getCTDotGiamGia(String maDot, String maLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTDotGiamGia WHERE  maDot.maDot =:maDot AND maLoai.maLoai=:maLoai ";
		Query query = session.createQuery(hql);
		query.setParameter("maDot", maDot);
		query.setParameter("maLoai", maLoai);
		List<CTDotGiamGia> result = query.list(); 
		if(query.list().size() ==0) return null;
		//result.get(0).setTiLeGiam(100 - result.get(0).getTiLeGiam());
		
		return result ;
	}
	private List<LoaiSanPham> getLoaiSanPhamDMSP(int page, int pageSize) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);
		int offset = page * pageSize;
		List<LoaiSanPham> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		for (LoaiSanPham loaiSanPham : list) {
		    loaiSanPham.setSanPham(getSanPhamDMSP(loaiSanPham.getMaLoai()));
		}
		return list;
	}
	private List<SanPham> getSanPhamDMSP(String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE maLoai.maLoai =:maLoai1";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", s);
		List<SanPham> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	private List<SanPham> getSanPham(String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM SanPham WHERE maLoai.maLoai =:maLoai1 AND idGH.idGH = null  AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("maLoai1", s);
		List<SanPham> list = query.list();
		if(list.size() ==0) return null;
		return list;
	}
	private Long getSLSanPhamCua1Loai(int idGH, String maLoai) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(seri) FROM SanPham WHERE  idGH.idGH = :id AND maLoai.maLoai = :maLoai AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("id", idGH);
		query.setParameter("maLoai", maLoai);
		Long count = (Long) query.uniqueResult();
		return count;
	}
	private Long getSLSanPhamCuaGH(int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(DISTINCT maLoai) FROM SanPham WHERE  idGH.idGH = :id AND daBan = 0";
		Query query = session.createQuery(hql);
		query.setParameter("id", idGH);
		Long count = (Long) query.uniqueResult();
		return count;
	}
	
	private List<LoaiSanPham> searchProduct1(int page, int pageSize, String s) {
		Session session = factory.getCurrentSession();
		ArrayList<String> tmp = catChuoi(s);
		String hql = "FROM LoaiSanPham WHERE maLoai LIKE :product_name"
				+" OR  tenSP LIKE :product_name1"
				+" OR  maTheLoai.tenTL LIKE :product_name"
				+" OR  maHang.tenHang LIKE :product_name";
		Query query = session.createQuery(hql);
		query.setParameter("product_name","%" +tmp.get(0) + "%");
		query.setParameter("product_name1","%" + tmp.get(1)+ "%" );
		int offset = page * pageSize;
		List<LoaiSanPham> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		DotGiamGia dgg = getDotGiamGia();
		for (LoaiSanPham loaiSanPham : list) {
		    loaiSanPham.setSanPham(getSanPham(loaiSanPham.getMaLoai()));
		    if(dgg == null) {
		    	loaiSanPham.setCtDotGiamGia(null);
		    }
		    else {
		    	loaiSanPham.setCtDotGiamGia(getCTDotGiamGia(dgg.getMaDot(), loaiSanPham.getMaLoai()));
		    }
		}
		
		
		
		return list;
	}
	
	private int searchSLProduct1( String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham WHERE maLoai LIKE :product_name"
				+" OR  tenSP LIKE :product_name1"
				+" OR  maTheLoai.tenTL LIKE :product_name"
				+" OR  maHang.tenHang LIKE :product_name";
		ArrayList<String> tmp = catChuoi(s);
		Query query = session.createQuery(hql);
		query.setParameter("product_name","%" +tmp.get(0) + "%");
		query.setParameter("product_name1","%" + tmp.get(1)+ "%" );
		List<LoaiSanPham> list = query.list();
		return list.size();
	}
	
	private int getSoLuongLoaiSanPham() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham";
		Query query = session.createQuery(hql);
		List<LoaiSanPham> list = query.list();
		return list.size();
	}
	private LoaiSanPham tim1LoaiSanPham(String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham WHERE maLoai =:s";
		Query query = session.createQuery(hql);
		query.setParameter("s", s);
		List<LoaiSanPham> list = query.list();
		if (list.size() == 0) return null;
		LoaiSanPham x = list.get(0);
		x.setBinhLuan(getBinhLuanLSP(s));
		x.setSanPham(getSanPham(s));
		DotGiamGia dgg = getDotGiamGia();
		
		if(dgg == null) {
		    	x.setCtDotGiamGia(null);
		    }
		    else {
		    	x.setCtDotGiamGia(getCTDotGiamGia(dgg.getMaDot(), x.getMaLoai()));
		    }
		return x;
	}
	private List<BinhLuan> getBinhLuanLSP(String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BinhLuan WHERE maLoai.maLoai =:s";
		Query query = session.createQuery(hql);
		query.setParameter("s", s);
		List<BinhLuan> list = query.list();
		for(BinhLuan bl:list) {
			Hibernate.initialize(bl.getEmail());
		}
		if (list.size() == 0) return null;
		return list;
	}
	private ArrayList<String> catChuoi(String s) {
		int vt = 0;
		for (int i =0 ; i < s.length() ; i++) {
			if (s.charAt(i) == '-') {
				vt = i;
				break;
			}
		}
		
		ArrayList<String> result = new ArrayList<>();
		if(vt != 0) {
			String s1 = s.substring(0, vt - 1);
			String s2 = s.substring(vt+1, s.length());
			result.add(s1);
			result.add(s2);
		} else {
			result.add(s);
			result.add(s);
		}
		return result;
	}
	// Xử lý thêm sản phẩm 
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String form(ModelMap model, HttpSession session) {
		if(session.getAttribute("user1")==null) {
			return "redirect:/dangnhap.htm";
		}
		String hinhAnh = "sp.png";
		model.addAttribute("hinhanh", hinhAnh);
		List<TheLoai> theLoai = getALLTheLoai();
		List<HangSanXuat> hangSanXuat = getALLHangSanxuat();
		model.addAttribute("listTheLoai", theLoai);
		model.addAttribute("listHang", hangSanXuat);
		//System.out.println(baseUploadFile.getBasePath());
		return "sp/themsp";
	}
	
	
	@RequestMapping(value="add", method = RequestMethod.POST) public String
	  themSanPham(ModelMap model,
				HttpSession session,
			  @RequestParam("photo") MultipartFile photo,
			  	HttpServletRequest request
	 ) {
		String maLoai = request.getParameter("maLoai");
		if (getMaLoai(maLoai)) {
			model.addAttribute("message", "Mã loại bị trùng");
			return "sp/themsp";
		}
		NhanVien nv = (NhanVien) session.getAttribute("user1");
		if(nv==null) {
			return "redirect:/dangnhap.htm";
		}
		String tenSP = request.getParameter("ten");
		String gia = request.getParameter("gia");
		String giaNhap = request.getParameter("giaNhap");
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
		BigDecimal gia2 = new BigDecimal(giaNhap);
		String fileName = saveImage(photo);
		if(fileName == null) {
			fileName = "sp.png";
		}
		LoaiSanPham x = new LoaiSanPham(maLoai, tenSP, gia1, fileName, moTa, cpu, 
				ram, hardware, card, screen, os,gia2, null, null);
		String s = saveLoaiSanPham(x, theLoai, hang, nv);
		if (s.equals("TC")) {
			return "redirect:/home/danh-muc-san-pham.htm";
		}
		model.addAttribute("message", s);
	  return "sp/themsp";
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
	private String saveLoaiSanPham(LoaiSanPham x,
			String theLoai,
			String hangSanXuat, NhanVien nv) {
			
			//System.out.println(theLoai);
			TheLoai tl = getTheLoai(theLoai);
			HangSanXuat hsx = getHangSanxuat(hangSanXuat);
			//System.out.println(hangSanXuat);
			LocalDate localDate = LocalDate.now();
			Date currentDate = Date.valueOf(localDate);
			ChinhSuaGia csg = new ChinhSuaGia(currentDate,x.getGia(), nv, x);
			if ( tl == null) {
				int sizeTheLoai = getSizeTheLoai();
				String maTheLoai = "" + String.valueOf(theLoai.charAt(0)) + 
				String.valueOf(theLoai.charAt(theLoai.length()-1))
				+ Integer.toString(sizeTheLoai);
				tl= new TheLoai(maTheLoai, theLoai);
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
				session.save(tl);
				t.commit();
				} catch(Exception e) {
					t.rollback();
					//session.close();
					return "Thêm thể loại thất bại";
					}
				finally {
					session.close();
				}
			} 
			if (hsx==null) {
				int sizeHangSanXuat = getSizeHangSanXuat();
				String maHangSanXuat = "" + String.valueOf(hangSanXuat.charAt(0)) + 
				String.valueOf(hangSanXuat.charAt(hangSanXuat.length()-1))
				+ Integer.toString(sizeHangSanXuat);
				hsx = new HangSanXuat(maHangSanXuat, hangSanXuat);
				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					session.save(hsx);
					t.commit();
				} catch(Exception e) {
					t.rollback();
					//session.close();
					return "Thêm hãng sản xuất thất bại";
				}finally {
					session.close();
				}
			}
			x.setMaTheLoai(tl);
			x.setMaHang(hsx);
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(x);
				session.save(csg);
				t.commit();
			} catch(Exception e) {
				t.rollback();
//				System.out.println(e.getMessage());
//				System.out.println(x.getMaLoai());
				session.close();
				return "Thêm sản phẩm thất bại";
			} finally {
				session.close();
			}
			
			return "TC";
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
	
	
	private TheLoai getTheLoai(String theLoai) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai WHERE  maTheLoai=:maTheLoai OR tenTL = :maTheLoai";
		Query query = session.createQuery(hql);
		query.setParameter("maTheLoai", theLoai);
		List<TheLoai> list = query.list();
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	private int getSizeTheLoai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheLoai";
		Query query = session.createQuery(hql);
		
		List<TheLoai> list = query.list();
		return list.size();
	}
	
	private HangSanXuat getHangSanxuat(String hangSanXuat) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat WHERE  maHang=:maHang1 OR tenHang = :maHang1";
		Query query = session.createQuery(hql);
		query.setParameter("maHang1", hangSanXuat);
		List<HangSanXuat> list = query.list();
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	private int getSizeHangSanXuat() {
		Session session = factory.getCurrentSession();
		String hql = "FROM HangSanXuat";
		Query query = session.createQuery(hql);
		List<HangSanXuat> list = query.list();
		return list.size();
	}
}
