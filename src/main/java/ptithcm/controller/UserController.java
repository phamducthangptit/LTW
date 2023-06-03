package ptithcm.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.email.SendEmail;
import ptithcm.model.KhachHang;
import ptithcm.model.NhanVien;

@Transactional
@Controller
public class UserController {
	@Autowired
	SessionFactory factory;
	@Autowired
	private SendEmail sendEmail;
	
	private String maXacThuc;
	private String email;
	private String maLayMk;

	@RequestMapping(value = { "/", "/home" })
	public String index() {
		return "redirect:/home/index.htm";
	}

	@RequestMapping("/dangnhap")
	public String showFormDN() {
		return "user/login";
	}

	@RequestMapping(value = "/dangnhap", method = RequestMethod.POST)
	public String dangNhap(HttpServletRequest request, Model model) {
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Session session = factory.getCurrentSession();
		if (Pattern.matches(EMAIL_PATTERN, username)) { // là email thì là người dùng
			String hql = "FROM KhachHang KH WHERE KH.email = :email AND KH.pass = :pass";
			Query query = session.createQuery(hql);
			query.setParameter("email", username);
			query.setParameter("pass", pass);
			List<KhachHang> list = query.list();
			KhachHang khachHang = new KhachHang();
			if (list.size() != 0) {
				khachHang = list.get(0);
				if (khachHang.getTrangThai() != 0) {
					HttpSession s = request.getSession();
					s.setAttribute("user", khachHang);
					BigDecimal sumTongTien = new BigDecimal(0);
					s.setAttribute("sumGH", sumTongTien);
					return "redirect:/home/index.htm";
				} else {
					model.addAttribute("ErrorLogin", "Tài khoản chưa được kích hoạt!");
					return "user/login";
				}

			} else {
				model.addAttribute("ErrorLogin", "Tên đăng nhập hoặc mật khẩu không đúng");
				model.addAttribute("username", username);
				return "user/login";
			}

		} else { // đây là nhân viên của công ty
			String hql = "FROM NhanVien NV WHERE NV.maNV = :maNV AND NV.password = :pass";
			Query query = session.createQuery(hql);
			query.setParameter("maNV", username);
			query.setParameter("pass", pass);
			List<NhanVien> list = query.list();
			NhanVien nhanVien = new NhanVien();
			if (list.size() != 0) {
				nhanVien = list.get(0);
				if (nhanVien.getTrangThai() != 0) {
					HttpSession s = request.getSession();
					s.setAttribute("user", nhanVien);
					return "staff/homeNV";
				} else {
					model.addAttribute("ErrorLogin", "Nhân viên không thể đăng nhập vào hệ thống!");
					return "user/login";
				}

			} else {
				model.addAttribute("ErrorLogin", "Tên đăng nhập hoặc mật khẩu không đúng");
				model.addAttribute("username", username);
				return "user/login";
			}
		}

	}

	@RequestMapping("/dangkitaikhoan")
	public String showFormDangKiTK() {
		return "user/dangkitaikhoan";
	}

	@RequestMapping(value = "/dangkitaikhoan", method = RequestMethod.POST)
	public String dangKiTK(HttpServletRequest request, Model model) {

		email = request.getParameter("email");
		String ho = request.getParameter("ho");
		String ten = request.getParameter("ten");
		String NS = request.getParameter("ngaySinh");
		Date ngaySinh = Date.valueOf(NS);
		String sdt = request.getParameter("SDT");
		String pass = request.getParameter("pass");

		Session session = factory.getCurrentSession();

		String hql = "FROM KhachHang KH WHERE KH.email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);

		if (query.list().size() == 0) { // email này chưa đăng kí tài khoản nào nên được phép đăng kí
			KhachHang khachHang = new KhachHang(email, ho, ten, "", ngaySinh, sdt, pass, 0);
			Random random = new Random(); // random mã để gửi mail
			int s1 = random.nextInt(10);
			int s2 = random.nextInt(10);
			int s3 = random.nextInt(10);
			int s4 = random.nextInt(10);
			int s5 = random.nextInt(10);
			int s6 = random.nextInt(10);
			maXacThuc = Integer.toString(s1) + Integer.toString(s2) + Integer.toString(s3) + Integer.toString(s4)
					+ Integer.toString(s5) + Integer.toString(s6);
			session.save(khachHang);
			String from = "banlaptop12ptit@gmail.com";
			String subject = "Thư xác nhận đăng kí tài khoản";
			String body = "Mã xác thực tài khoản của bạn là: " + maXacThuc;
			sendEmail.send(from, email, subject, body);
			return "user/xacthuctaikhoan";
		} else { // email đã đăng kí tài khoản, không được phép đăng kí nữa
			model.addAttribute("ErrorAccount", "Vui lòng chọn email khác! Email này đã được tạo tài khoản!");
			model.addAttribute("email", email);
			model.addAttribute("ho", ho);
			model.addAttribute("ten", ten);
			model.addAttribute("ngaySinh", ngaySinh);
			model.addAttribute("SDT", sdt);
			return "user/dangkitaikhoan";
		}
	}

	@RequestMapping(value = "/xacthuctaikhoan", method = RequestMethod.POST)
	public String xacThucTK(HttpServletRequest request, Model model) {
		String maXT = request.getParameter("maXacThuc");
		if (maXT.equals(maXacThuc)) {
			Session session = factory.getCurrentSession();
			String hql = "UPDATE KhachHang KH SET KH.trangThai = 1 WHERE KH.email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			query.executeUpdate();
			return "user/login";
		} else {
			model.addAttribute("ErrorAccount", "Mã không đúng! Vui lòng nhập lại mã xác thực!");
			return "user/xacthuctaikhoan";
		}

	}

	@RequestMapping(value = "/dangxuat")
	public String dangXuat(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate(); // ngat phien lam viec
		return "user/home";
	}
	
	@RequestMapping(value = "/thongtincanhanuser")
	public String thongTinCaNhan(Model model, HttpSession session) {
		Object user = session.getAttribute("user");
		KhachHang kh = new KhachHang();
		kh = (KhachHang) user;
		model.addAttribute("khachHang", kh);
		return "user/thongtincanhan";
	}
	
	@RequestMapping(value = "/luuthongtinuser", method = RequestMethod.POST)
	public String luuThongTinUser(Model model, HttpServletRequest request) {
		HttpSession s = request.getSession();

		Object user = s.getAttribute("user");
		KhachHang khachHang = new KhachHang();
		khachHang = (KhachHang) user;
		String ho = request.getParameter("ho");
		String ten = request.getParameter("ten");
		String ns = request.getParameter("ngaySinh");
		Date ngaySinh = Date.valueOf(ns);
		String diaChi = request.getParameter("diaChi");
		String sdt = request.getParameter("sdt");
		String pass = request.getParameter("pass");
		
		khachHang.setHo(ho);
		khachHang.setTen(ten);
		khachHang.setNgaySinh(ngaySinh);
		khachHang.setDiaChi(diaChi);
		khachHang.setSdt(sdt);
		khachHang.setPass(pass);

		model.addAttribute("khachHang", khachHang);
		Session session = factory.getCurrentSession();
		session.update(khachHang);
		return "user/thongtincanhan";
	}
	
	
	@RequestMapping(value = "/quenmatkhau")
	public String quenMk() {
		return "user/quenmatkhau";
	}
	
	@RequestMapping(value = "/quenmatkhau", method = RequestMethod.POST)
	public String layLaiMk(HttpServletRequest request, Model model,
			@RequestParam(value = "guiMa", required = false) String guiMa,
			@RequestParam(value = "xacNhanMa", required = false) String xacNhanMa,
			@RequestParam(value = "xacNhanMk", required = false) String xacNhanMk) {
		String email = request.getParameter("email");
		String maXN = request.getParameter("maXN");
		if(guiMa != null) {//neu click vao gui ma
			Random random = new Random(); // random mã để gửi mail
			int s1 = random.nextInt(10);
			int s2 = random.nextInt(10);
			int s3 = random.nextInt(10);
			int s4 = random.nextInt(10);
			int s5 = random.nextInt(10);
			int s6 = random.nextInt(10);
			maLayMk = Integer.toString(s1) + Integer.toString(s2) + Integer.toString(s3) + Integer.toString(s4)
					+ Integer.toString(s5) + Integer.toString(s6);
			String from = "banlaptop12ptit@gmail.com";
			String subject = "Thư đặt lại mật khẩu";
			String body = "Vui lòng nhập mã sau để đặt lại mật khẩu: " + maLayMk;
			sendEmail.send(from, email, subject, body);
			model.addAttribute("email", email);
			return "user/quenmatkhau";
		} 
		model.addAttribute("email", email);
		if(xacNhanMa != null) {
			if(maXN.equals(maLayMk)) {
				model.addAttribute("maXN", maXN);
			} else {
				model.addAttribute("ErrorMa", "Mã vừa nhập không chính xác, vui lòng nhập lại!");
			}
			return "user/quenmatkhau";
		}
		if(xacNhanMk != null) {
			String pass = request.getParameter("passN");
			String hql = "UPDATE KhachHang KH SET KH.pass = :pass WHERE KH.email = :email";
			Session session = factory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			query.setParameter("pass", pass);
			query.executeUpdate();
		}
		return "user/login";
	}
	@RequestMapping("/giohang")
	public String gioHang() {
		return "user/giohang";
	}
}
