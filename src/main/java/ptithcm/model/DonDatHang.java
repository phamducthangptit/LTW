package ptithcm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="DON_DAT_HANG")
public class DonDatHang {
	@Id
	@Column(name="MADDH")
	private String maDDH;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYDAT")
	private Date ngayDat;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@ManyToOne
	@JoinColumn(name="MANCC")
	private NhaCungCap maNCC;
	
	@OneToOne
	@JoinColumn(name="SOPHIEUNHAP")
	private PhieuNhap soPhieuNhap;
	
	@OneToMany(mappedBy = "maDDH")
	private List<CTDonDatHang> ctDonDatHang;
	
	

	public DonDatHang() {
	}



	public DonDatHang(String maDDH, Date ngayDat, NhanVien maNV, NhaCungCap maNCC, PhieuNhap soPhieuNhap) {
		this.maDDH = maDDH;
		this.ngayDat = ngayDat;
		this.maNV = maNV;
		this.maNCC = maNCC;
		this.soPhieuNhap = soPhieuNhap;
	}



	public String getMaDDH() {
		return maDDH;
	}



	public void setMaDDH(String maDDH) {
		this.maDDH = maDDH;
	}



	public Date getNgayDat() {
		return ngayDat;
	}



	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}



	public NhanVien getMaNV() {
		return maNV;
	}



	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}



	public NhaCungCap getMaNCC() {
		return maNCC;
	}



	public void setMaNCC(NhaCungCap maNCC) {
		this.maNCC = maNCC;
	}



	public PhieuNhap getSoPhieuNhap() {
		return soPhieuNhap;
	}



	public void setSoPhieuNhap(PhieuNhap soPhieuNhap) {
		this.soPhieuNhap = soPhieuNhap;
	}



	public List<CTDonDatHang> getCtDonDatHang() {
		return ctDonDatHang;
	}



	public void setCtDonDatHang(List<CTDonDatHang> ctDonDatHang) {
		this.ctDonDatHang = ctDonDatHang;
	}
	
	
}
