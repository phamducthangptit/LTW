package ptithcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class DoanhThuTheoNgay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "MALOAI")
	private String maLoai;
	
	@Column(name = "TENSP")
	private String tenSP;
	
	@Column(name = "GIABAN")
	private int giaBan;
	
	@Column(name = "GIAMGIA")
	private int giamGia;

	public DoanhThuTheoNgay() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public int getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(int giaBan) {
		this.giaBan = giaBan;
	}

	public int getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(int giamGia) {
		this.giamGia = giamGia;
	}
	
	public int getGiaBanRa() {
		double giaBanRa = Math.round(giaBan - ((this.giamGia * 1.0) / 100 ) * giaBan);
		return (int)giaBanRa;
	}

	@Override
	public String toString() {
		return "DoanhThuTheoNgay [id=" + id + ", maLoai=" + maLoai + ", tenSP=" + tenSP + ", giaBan=" + giaBan
				+ ", giamGia=" + giamGia + "]";
	}
	
	
}
