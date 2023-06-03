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
@Table(name="BINH_LUAN")
public class BinhLuan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDBL")
	private int idBL;
	
	@Column(name="DIEM")
	private int diem;
	
	@Column(name="MOTA")
	private String moTa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAY")
	private Date ngay;
	
	@ManyToOne
	@JoinColumn(name="EMAIL")
	private KhachHang email;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;

	public BinhLuan() {
	}

	public BinhLuan(int idBL, int diem, String moTa, Date ngay, KhachHang email, LoaiSanPham maLoai) {
		this.idBL = idBL;
		this.diem = diem;
		this.moTa = moTa;
		this.ngay = ngay;
		this.email = email;
		this.maLoai = maLoai;
	}

	public int getIdBL() {
		return idBL;
	}

	public void setIdBL(int idBL) {
		this.idBL = idBL;
	}

	public int getDiem() {
		return diem;
	}

	public void setDiem(int diem) {
		this.diem = diem;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public KhachHang getEmail() {
		return email;
	}

	public void setEmail(KhachHang email) {
		this.email = email;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}
	
}
