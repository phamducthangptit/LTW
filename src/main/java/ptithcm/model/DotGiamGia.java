package ptithcm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DOT_GIAM_GIA")
public class DotGiamGia {
	
	@Id
	@Column(name = "MADOT")
	private String maDot;
	
	@Column(name = "NGAYBATDAU")
	private Date ngayBD;
	
	@Column(name = "NGAYKETTHUC")
	private Date ngayKT;
	
	@Column(name = "MOTA")
	private String moTa;
	
	@ManyToOne
	@JoinColumn(name = "maNV")
	private NhanVien nhanVien;

	
	public DotGiamGia() {
	}

	public DotGiamGia(String maDot, Date ngayBD, Date ngayKT, String moTa, NhanVien nhanVien) {
		this.maDot = maDot;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.moTa = moTa;
		this.nhanVien = nhanVien;
	}

	public String getMaDot() {
		return maDot;
	}

	public void setMaDot(String maDot) {
		this.maDot = maDot;
	}

	public Date getNgayBD() {
		return ngayBD;
	}

	public void setNgayBD(Date ngayBD) {
		this.ngayBD = ngayBD;
	}

	public Date getNgayKT() {
		return ngayKT;
	}

	public void setNgayKT(Date ngayKT) {
		this.ngayKT = ngayKT;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	
	
}
