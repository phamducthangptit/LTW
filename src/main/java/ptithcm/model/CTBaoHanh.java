package ptithcm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CT_BAO_HANH")
public class CTBaoHanh {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDCTBH")
	private int idCTBH;
	
	@Column(name="TRANGTHAINHAN")
	private String trangThaiNhan;
	
	@Column(name="TRANGTHAITRA")
	private String trangThaiTra;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYNHAN")
	private Date ngayNhan;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYTRA")
	private Date ngayTra;
	
	@ManyToOne
	@JoinColumn(name="MANVNHAN")
	private NhanVien maNVNhan;
	
	@ManyToOne
	@JoinColumn(name="MANVTRA")
	private NhanVien maNVTra;
	
	@ManyToOne
	@JoinColumn(name="SOPHIEUBH")
	private PhieuBaoHanh soPhieuBH;

	public CTBaoHanh() {
	}

	public CTBaoHanh(String trangThaiNhan, String trangThaiTra, Date ngayNhan, Date ngayTra,
			NhanVien maNVNhan, NhanVien maNVTra, PhieuBaoHanh soPhieuBH) {

		this.trangThaiNhan = trangThaiNhan;
		this.trangThaiTra = trangThaiTra;
		this.ngayNhan = ngayNhan;
		this.ngayTra = ngayTra;
		this.maNVNhan = maNVNhan;
		this.maNVTra = maNVTra;
		this.soPhieuBH = soPhieuBH;
	}

	public int getIdCTBH() {
		return idCTBH;
	}

	public void setIdCTBH(int idCTBH) {
		this.idCTBH = idCTBH;
	}

	public String getTrangThaiNhan() {
		return trangThaiNhan;
	}

	public void setTrangThaiNhan(String trangThaiNhan) {
		this.trangThaiNhan = trangThaiNhan;
	}

	public String getTrangThaiTra() {
		return trangThaiTra;
	}

	public void setTrangThaiTra(String trangThaiTra) {
		this.trangThaiTra = trangThaiTra;
	}

	public Date getNgayNhan() {
		return ngayNhan;
	}

	public void setNgayNhan(Date ngayNhan) {
		this.ngayNhan = ngayNhan;
	}

	public Date getNgayTra() {
		return ngayTra;
	}

	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}

	public NhanVien getMaNVNhan() {
		return maNVNhan;
	}

	public void setMaNVNhan(NhanVien maNVNhan) {
		this.maNVNhan = maNVNhan;
	}

	public NhanVien getMaNVTra() {
		return maNVTra;
	}

	public void setMaNVTra(NhanVien maNVTra) {
		this.maNVTra = maNVTra;
	}

	public PhieuBaoHanh getSoPhieuBH() {
		return soPhieuBH;
	}

	public void setSoPhieuBH(PhieuBaoHanh soPhieuBH) {
		this.soPhieuBH = soPhieuBH;
	}
	
	
}
