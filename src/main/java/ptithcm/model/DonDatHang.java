package ptithcm.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DON_DAT_HANG")
public class DonDatHang {
	@Id
	@Column(name = "MADDH")
	private String maDDH;
	@Column(name = "NGAYDAT")
	private Date ngayDat;
	@ManyToOne
	@JoinColumn(name = "MANCC")
	private NhaCungCap maNCC;
	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien maNV;

	@OneToOne(mappedBy = "maDDH")
	private PhieuNhap soPhieuNhap;

	@OneToMany(mappedBy = "maDDH")
	private List<CTDonDatHang> ctDonDatHang;

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

	public NhaCungCap getNcc() {
		return maNCC;
	}

	public void setNcc(NhaCungCap ncc) {
		this.maNCC = ncc;
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

	public DonDatHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	public DonDatHang(String maDDH, Date ngayDat, NhaCungCap ncc, NhanVien maNV, PhieuNhap soPhieuNhap) {
		super();
		this.maDDH = maDDH;
		this.ngayDat = ngayDat;
		this.maNCC = ncc;
		this.maNV = maNV;
		this.soPhieuNhap = soPhieuNhap;
	}

}
