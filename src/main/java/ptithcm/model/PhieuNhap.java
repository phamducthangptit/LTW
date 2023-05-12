package ptithcm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="PHIEU_NHAP")
public class PhieuNhap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOPHIEUNHAP")
	private int soPhieuNhap;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYLAPPN")
	private Date ngayLapPN;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@OneToOne
	@JoinColumn(name="MADDH")
	private DonDatHang maDDH;
	
	@OneToMany(mappedBy = "soPhieuNhap", fetch = FetchType.LAZY)
	private List<SanPham> sanPham;

	public PhieuNhap() {
	}

	
	public PhieuNhap(int soPhieuNhap, Date ngayLapPN, NhanVien maNV, DonDatHang maDDH, List<SanPham> sanPham) {
		super();
		this.soPhieuNhap = soPhieuNhap;
		this.ngayLapPN = ngayLapPN;
		this.maNV = maNV;
		this.maDDH = maDDH;
		this.sanPham = sanPham;
	}


	public int getSoPhieuNhap() {
		return soPhieuNhap;
	}

	public void setSoPhieuNhap(int soPhieuNhap) {
		this.soPhieuNhap = soPhieuNhap;
	}

	public Date getNgayLapPN() {
		return ngayLapPN;
	}

	public void setNgayLapPN(Date ngayLapPN) {
		this.ngayLapPN = ngayLapPN;
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	

	public DonDatHang getMaDDH() {
		return maDDH;
	}


	public void setMaDDH(DonDatHang maDDH) {
		this.maDDH = maDDH;
	}


	public List<SanPham> getSanPham() {
		return sanPham;
	}

	public void setSanPham(List<SanPham> sanPham) {
		this.sanPham = sanPham;
	}
	
	
}
