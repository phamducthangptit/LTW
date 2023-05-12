package ptithcm.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CT_DON_DAT_HANG")
public class CTDonDatHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDDON")
	private int idDon;
	
	@Column(name="SOLUONG")
	private int soLuong;
	
	@Column(name="DONGIA")
	private BigDecimal donGia;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;
	
	@ManyToOne
	@JoinColumn(name="MADDH")
	private DonDatHang maDDH;

	public CTDonDatHang() {
	}

	public CTDonDatHang(int idDon, int soLuong, BigDecimal donGia, LoaiSanPham maLoai, DonDatHang maDDH) {
		this.idDon = idDon;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.maLoai = maLoai;
		this.maDDH = maDDH;
	}

	public int getIdDon() {
		return idDon;
	}

	public void setIdDon(int idDon) {
		this.idDon = idDon;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public BigDecimal getDonGia() {
		return donGia;
	}

	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}

	public DonDatHang getMaDDH() {
		return maDDH;
	}

	public void setMaDDH(DonDatHang maDDH) {
		this.maDDH = maDDH;
	}
	
	
}
