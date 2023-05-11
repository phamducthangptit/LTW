package ptithcm.controller;

import java.sql.Date;
import java.util.List;

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

import ptithcm.model.DotGiamGia;
import ptithcm.model.NhaCungCap;
import ptithcm.model.NhanVien;

@Controller
@Transactional
public class StaffController {
	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/taodotgiamgia")
	public String taoDotGiamGia() {
		return "staff/taodotgiamgia";
	}

	@RequestMapping(value = "/taodotgiamgia", method = RequestMethod.POST)
	public String luuDotGiamGia(HttpServletRequest request, Model model, HttpSession session) {
		String nBD = request.getParameter("ngayBD");
		Date ngayBD = Date.valueOf(nBD);
		String nKT = request.getParameter("ngayKT");
		Date ngayKT = Date.valueOf(nKT);
		String moTa = request.getParameter("moTa");
		Object user = session.getAttribute("user");
		NhanVien nhanVien = new NhanVien();
		nhanVien = (NhanVien) user;
		Session s = factory.getCurrentSession();
		String hql = "from DotGiamGia";
		Query query = s.createQuery(hql);
		List<DotGiamGia> listDGG = query.list();
		DotGiamGia dotGG = new DotGiamGia("", ngayBD, ngayKT, moTa, nhanVien);
		dotGG.setMaDot("DGG" + (listDGG.size() + 1));
		if (ngayBD.compareTo(ngayKT) < 0) { // ngay bd nho hon ngay kt -> true
			s.save(dotGG);
			model.addAttribute("ErrorGG", "");
		} else {
			model.addAttribute("ErrorGG", "Ngày kết thúc không hợp lệ, vui lòng chọn lại!");
		}
		model.addAttribute("DGG", dotGG);
		return "staff/taodotgiamgia";
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
}
