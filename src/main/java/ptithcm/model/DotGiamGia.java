package ptithcm.model;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="DOT_GIAM_GIA")
public class DotGiamGia {
	@Id
	@Column(name="MADOT")
	private String maDot;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYBATDAU")
	private Date ngayBatDau;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYKETTHUC")
	private Date ngayKetThuc;
	
	@Column(name="MOTA")
	private String moTa;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@OneToMany(mappedBy = "maDot", fetch = FetchType.LAZY)
	private List<CTDotGiamGia> ctDotGiamGia;

	
	public DotGiamGia() {
	}

	public DotGiamGia(String maDot, Date ngayBatDau, Date ngayKetThuc, String moTa, NhanVien maNV) {
		this.maDot = maDot;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.moTa = moTa;
		this.maNV = maNV;
	}

	public String getMaDot() {
		return maDot;
	}

	public void setMaDot(String maDot) {
		this.maDot = maDot;
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

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	public List<CTDotGiamGia> getCtDotGiamGia() {
		return ctDotGiamGia;
	}

	public void setCtDotGiamGia(List<CTDotGiamGia> ctDotGiamGia) {
		this.ctDotGiamGia = ctDotGiamGia;
	}
	
	
}
