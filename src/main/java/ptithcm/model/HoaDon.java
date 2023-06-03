package ptithcm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="HOA_DON")
public class HoaDon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SOHOADON")
	private int soHoaDon;
	
	@Column(name="MASOTHUE")
	private String maSoThue;
	
	
	@OneToOne
	@JoinColumn(name="IDGH")
	private GioHang idGH;
	
	@OneToOne(mappedBy = "soHoaDon")
	private PhieuTra phieuTra;

	public HoaDon() {
	}

	public HoaDon( String maSoThue, GioHang idGH) {

		this.maSoThue = maSoThue;

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
