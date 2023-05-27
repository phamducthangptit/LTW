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
@Table(name="PHIEU_BAO_HANH")
public class PhieuBaoHanh {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOPHIEUBH")
	private int soPhieuBH;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYBATDAU")
	private Date ngayBatDau;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYKETTHUC")
	private Date ngayKetThuc;
	
	@OneToOne
	@JoinColumn(name="SERI")
	private SanPham seri;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@OneToMany(mappedBy = "soPhieuBH")
	private List<CTBaoHanh> ctBaoHanh;

	public PhieuBaoHanh() {
	}

	public PhieuBaoHanh( Date ngayBatDau, Date ngayKetThuc, SanPham seri, NhanVien maNV) {
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.seri = seri;
		this.maNV = maNV;
	}

	public int getSoPhieuBH() {
		return soPhieuBH;
	}

	public void setSoPhieuBH(int soPhieuBH) {
		this.soPhieuBH = soPhieuBH;
	}

	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public SanPham getSeri() {
		return seri;
	}

	public void setSeri(SanPham seri) {
		this.seri = seri;
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	public List<CTBaoHanh> getCtBaoHanh() {
		return ctBaoHanh;
	}

	public void setCtBaoHanh(List<CTBaoHanh> ctBaoHanh) {
		this.ctBaoHanh = ctBaoHanh;
	}
	
	
}
