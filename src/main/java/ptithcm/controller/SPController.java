package ptithcm.controller;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import ptit.bean.UploadFile;
import ptithcm.model.Cart;
import ptithcm.model.ChinhSuaGia;
import ptithcm.model.GioHang;
import ptithcm.model.HangSanXuat;
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
	
	@RequestMapping(value="danh-muc-san-pham", method = RequestMethod.GET)
	public String showDanhMucSanPham(ModelMap model,
			@RequestParam(defaultValue = "0") int page
			) {
		int pageSize = 9;
		int totalLoaiSanPham = getSoLuongLoaiSanPham();
		int totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
		int startPage = Math.max(0, page - 1);
		int endPage = Math.min(totalPages - 1, page + 1);
		String url ="/BanLaptop/home/danh-muc-san-pham.htm";
		List<LoaiSanPham> listLoaiSanPham = getLoaiSanPham(page,pageSize);
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
			@RequestParam String sp
			) {
		LoaiSanPham sanPham = tim1LoaiSanPham(sp);
		//System.out.println(sanPham);
		Hibernate.initialize(sanPham.getMaTheLoai());
		Hibernate.initialize(sanPham.getMaHang());
		model.addAttribute("hinhanh", sanPham.getAnh());
		model.addAttribute("maLoai", sanPham.getMaLoai());
		model.addAttribute("ten", sanPham.getTenSP());
		BigDecimal strippedValue = sanPham.getGia().stripTrailingZeros();
		sanPham.setGia(strippedValue);
		model.addAttribute("gia", sanPham.getGia().toPlainString());
		model.addAttribute("cpu", sanPham.getcPU());
		model.addAttribute("ram", sanPham.getRam());
		model.addAttribute("hardware", sanPham.getHardWare());
		model.addAttribute("card", sanPham.getCard());
		model.addAttribute("screen", sanPham.getScreen());
		model.addAttribute("os", sanPham.getOs());
		model.addAttribute("theLoai", sanPham.getMaTheLoai().getMaTheLoai());
		model.addAttribute("hangSanXuat", sanPham.getMaHang().getMaHang());
		model.addAttribute("moTa", sanPham.getMoTa());
		List<TheLoai> theLoai = getALLTheLoai();
		List<HangSanXuat> hangSanXuat = getALLHangSanxuat();
		model.addAttribute("listTheLoai", theLoai);
		model.addAttribute("listHang", hangSanXuat);
		return "sp/chinh-sua";
	}
	@RequestMapping(value="danh-muc-san-pham/chinh-sua", method = RequestMethod.POST) 
	public String updateLoaiSanPham(ModelMap model,
				HttpSession session,
			  	HttpServletRequest request
	 ) {
		int check = 0;
		int check1 = 0;
		String maLoai = request.getParameter("maLoai");
		LoaiSanPham sanPham = tim1LoaiSanPham(maLoai);
		Hibernate.initialize(sanPham.getMaTheLoai());
		Hibernate.initialize(sanPham.getMaHang());
		NhanVien nv = (NhanVien) session.getAttribute("user");
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
		String theLoai = request.getParameter("theLoai");
		if (!sanPham.getMaTheLoai().getMaTheLoai().equals(theLoai)) check++;
		String hang = request.getParameter("hangSanXuat");
		if (!sanPham.getMaHang().getMaHang().equals(hang)) check++;
		BigDecimal gia1 = new BigDecimal(gia);
		System.out.println(check);
		System.out.println(check1);
		if (sanPham.getGia().compareTo(gia1) != 0) {
			sanPham.setGia(gia1);
			check1++;
		}
		System.out.println(check);
		System.out.println(check1);
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
				//session1.close();
				model.addAttribute("message", "Chỉnh sửa giá thất bại");
			} finally {
				session1.close();
			}
		}
		model.addAttribute("hinhanh", sanPham.getAnh());
		model.addAttribute("maLoai", sanPham.getMaLoai());
		model.addAttribute("ten", sanPham.getTenSP());
		BigDecimal strippedValue = sanPham.getGia().stripTrailingZeros();
		sanPham.setGia(strippedValue);
		model.addAttribute("gia", sanPham.getGia().toPlainString());
		model.addAttribute("cpu", sanPham.getcPU());
		model.addAttribute("ram", sanPham.getRam());
		model.addAttribute("hardware", sanPham.getHardWare());
		model.addAttribute("card", sanPham.getCard());
		model.addAttribute("screen", sanPham.getScreen());
		model.addAttribute("os", sanPham.getOs());
		model.addAttribute("theLoai", sanPham.getMaTheLoai().getMaTheLoai());
		model.addAttribute("hangSanXuat", sanPham.getMaHang().getMaHang());
		model.addAttribute("moTa", sanPham.getMoTa());
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
	
	private String updateLoaiSanPham(LoaiSanPham x,
			String theLoai,
			String hangSanXuat, NhanVien nv) {
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			TheLoai tl = getTheLoai(theLoai);
			HangSanXuat hsx = getHangSanxuat(hangSanXuat);
			
			if ( tl == null) {
				System.out.println(tl.getMaTheLoai());
				
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
			} 
			if (hsx==null) {
				System.out.println(hsx.getMaHang());
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
			}
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
			//System.out.println(sp);
			//System.out.println(listSP.get(0).getMaLoai());
		}
		
		
		if (inputSearch.equals("")) {
			totalLoaiSanPham = getSoLuongLoaiSanPham();
			totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
			if (totalPages ==0) totalPages = 1;
			startPage = Math.max(0, page - 1);
			endPage = Math.min(totalPages - 1, page + 1);
			listLoaiSanPham = getLoaiSanPham(page,pageSize);
			model.addAttribute("listLoais", listLoaiSanPham);
		} else {
			totalLoaiSanPham = searchSLProduct1(inputSearch);
			totalPages = (int) Math.ceil((double) totalLoaiSanPham / pageSize);
			if (totalPages ==0) totalPages = 1;
			startPage = Math.max(0, page - 1);
			endPage = Math.min(totalPages - 1, page + 1);
			listLoaiSanPham = this.searchProduct1(page, pageSize, inputSearch);
			List<LoaiSanPham> listLoaiSanPham1 = getLoaiSanPham(page,pageSize);
			model.addAttribute("listLoais", listLoaiSanPham1);
		}
		//System.out.println(listLoaiSanPham.size());
		List<HangSanXuat> listHang = getHang();
		List<TheLoai> listTheLoai = getTheLoai();
		model.addAttribute("listTheLoai", listTheLoai);
		model.addAttribute("listHang", listHang);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("listLoaiSanPham", listLoaiSanPham);
		if (gh == null) {
			model.addAttribute("SLsanPham", "0");
		} else {
			model.addAttribute("SLsanPham", getSLSanPhamCuaGH(gh.getIdGH()));
		}
		model.addAttribute("url", url);
		return "sp/shop";
	}


	@RequestMapping(value="shop",  params = "btnsearch", method = RequestMethod.GET)
	public String searchProduct(ModelMap model,
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") int page
			) {
		inputSearch = request.getParameter("searchInput");
		if (inputSearch.equals("")) {
			return "redirect:/home/shop.htm";
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
	@RequestMapping(value="shop-single", method = RequestMethod.GET)
	public String show1LoaiSanPham(ModelMap model,
			HttpServletRequest request,
			@RequestParam() String lsp) {
		
		LoaiSanPham x = tim1LoaiSanPham(lsp);
		BigDecimal strippedValue = x.getGia().stripTrailingZeros();
		x.setGia(strippedValue);
		model.addAttribute("product",x);
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
							
							sum =sum.add(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
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
							
							sum =sum.subtract(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
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
							sum =sum.subtract(cart.get(i).getLsp().getGia().multiply(new BigDecimal(cart.get(i).getSoLuong())));
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
						sum =sum.subtract(c.getLsp().getGia().multiply(new BigDecimal(1)));
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
						sum =sum.add(c.getLsp().getGia().multiply(new BigDecimal(1)));
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
		BigDecimal sum = ((BigDecimal) session.getAttribute("sumGH"));
		model.addAttribute("stt",1);
		model.addAttribute("sum",sum);
		model.addAttribute("SLVP",cart1.size());
		model.addAttribute("cart", cart1);
		return "sp/thanh-toan";
	}
	
	private GioHang getGHid(int idGH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM GioHang WHERE idGH.idGH = :idGH  AND trangThai = 0";
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
		for (LoaiSanPham loaiSanPham : list) {
		    loaiSanPham.setSanPham(getSanPham(loaiSanPham.getMaLoai()));
		}
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
				+" OR  tenSP LIKE :product_name1";
		Query query = session.createQuery(hql);
		query.setParameter("product_name","%" +tmp.get(0) + "%");
		query.setParameter("product_name1","%" + tmp.get(1)+ "%" );
		int offset = page * pageSize;
		List<LoaiSanPham> list = query.setFirstResult(offset).setMaxResults(pageSize).list();
		for (LoaiSanPham loaiSanPham : list) {
		    loaiSanPham.setSanPham(getSanPham(loaiSanPham.getMaLoai()));
		}
		return list;
	}
	
	private int searchSLProduct1( String s) {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiSanPham WHERE maLoai LIKE :product_name"
				+" OR  tenSP LIKE :product_name1";
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
		return list.get(0);
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
	public String form(ModelMap model) {
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
		NhanVien nv = (NhanVien) session.getAttribute("user");
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
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			System.out.println(theLoai);
			TheLoai tl = getTheLoai(theLoai);
			HangSanXuat hsx = getHangSanxuat(hangSanXuat);
			System.out.println(hangSanXuat);
			LocalDate localDate = LocalDate.now();
			Date currentDate = Date.valueOf(localDate);
			ChinhSuaGia csg = new ChinhSuaGia(currentDate,x.getGia(), nv, x);
			if ( tl == null) {
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
			} 
			if (hsx==null) {
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
			}
			x.setMaTheLoai(tl);
			x.setMaHang(hsx);
			try {
				session.save(x);
				session.save(csg);
				t.commit();
			} catch(Exception e) {
				t.rollback();
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
