package ptithcm.model;

import java.util.Date;
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
@Table(name="PHIEU_TRA")
public class PhieuTra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOPHIEUTRA")
	private int soPhieuTra;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYTRA")
	private Date ngayTra;
	
	@OneToOne
	@JoinColumn(name="SOHOADON")
	private HoaDon soHoaDon;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;

	@OneToMany(mappedBy = "soPhieuTra")
	private List<SanPham> sanPham;
	public PhieuTra() {
	}
	public PhieuTra(int soPhieuTra, Date ngayTra, HoaDon soHoaDon, NhanVien maNV) {
		this.soPhieuTra = soPhieuTra;
		this.ngayTra = ngayTra;
		this.soHoaDon = soHoaDon;
		this.maNV = maNV;
	}
	public int getSoPhieuTra() {
		return soPhieuTra;
	}
	public void setSoPhieuTra(int soPhieuTra) {
		this.soPhieuTra = soPhieuTra;
	}
	public Date getNgayTra() {
		return ngayTra;
	}
	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}
	public HoaDon getSoHoaDon() {
		return soHoaDon;
	}
	public void setSoHoaDon(HoaDon soHoaDon) {
		this.soHoaDon = soHoaDon;
	}
	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	public List<SanPham> getSanPham() {
		return sanPham;
	}
	public void setSanPham(List<SanPham> sanPham) {
		this.sanPham = sanPham;
	}
	
}
