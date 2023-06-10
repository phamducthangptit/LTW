package ptithcm.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="GIO_HANG")
public class GioHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDGH")
	private int idGH;
	
	@Column(name="TRANGTHAI")
	private int trangThai;
	

	@Column(name="NGAYTAO")
	private Date ngayTao;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@ManyToOne
	@JoinColumn(name="MASHIPPER")
	private NhanVien maShipper;
	
	@ManyToOne
	@JoinColumn(name="EMAIL")
	private KhachHang email;
	
	@OneToOne(mappedBy = "idGH")
	private HoaDon hoaDon;
	
	@OneToMany(mappedBy = "idGH")
	private List<SanPham> sanPham;

	public GioHang() {
	}

	public GioHang(int trangThai, Date ngayTao, NhanVien maNV, NhanVien maShipper, KhachHang email,
			HoaDon hoaDon) {
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
		this.maNV = maNV;
		this.maShipper = maShipper;
		this.email = email;
		this.hoaDon = hoaDon;
	}

	public int getIdGH() {
		return idGH;
	}

	public void setIdGH(int idGH) {
		this.idGH = idGH;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	public NhanVien getMaShipper() {
		return maShipper;
	}

	public void setMaShipper(NhanVien maShipper) {
		this.maShipper = maShipper;
	}

	public KhachHang getEmail() {
		return email;
	}

	public void setEmail(KhachHang email) {
		this.email = email;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public List<SanPham> getSanPham() {
		return sanPham;
	}

	public void setSanPham(List<SanPham> sanPham) {
		this.sanPham = sanPham;
	}
	
	
	
}
