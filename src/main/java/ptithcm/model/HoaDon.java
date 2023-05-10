package ptithcm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="HOA_DON")
public class HoaDon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOHOADON")
	private int soHoaDon;
	
	@Column(name="MASOTHUE")
	private String maSoThue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAYLAPHD")
	private Date ngayLapHD;
	
	@OneToOne
	@JoinColumn(name="IDGH")
	private GioHang idGH;
	
	@OneToOne(mappedBy = "soHoaDon")
	private PhieuTra phieuTra;

	public HoaDon() {
	}

	public HoaDon(int soHoaDon, String maSoThue, Date ngayLapHD, GioHang idGH) {
		this.soHoaDon = soHoaDon;
		this.maSoThue = maSoThue;
		this.ngayLapHD = ngayLapHD;
		this.idGH = idGH;
	}

	public int getSoHoaDon() {
		return soHoaDon;
	}

	public void setSoHoaDon(int soHoaDon) {
		this.soHoaDon = soHoaDon;
	}

	public String getMaSoThue() {
		return maSoThue;
	}

	public void setMaSoThue(String maSoThue) {
		this.maSoThue = maSoThue;
	}

	public Date getNgayLapHD() {
		return ngayLapHD;
	}

	public void setNgayLapHD(Date ngayLapHD) {
		this.ngayLapHD = ngayLapHD;
	}

	public GioHang getIdGH() {
		return idGH;
	}

	public void setIdGH(GioHang idGH) {
		this.idGH = idGH;
	}

	public PhieuTra getPhieuTra() {
		return phieuTra;
	}

	public void setPhieuTra(PhieuTra phieuTra) {
		this.phieuTra = phieuTra;
	}
	
	
}
