package ptithcm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="HANG_SAN_XUAT")
public class HangSanXuat {
	@Id
	@Column(name="MAHANG")
	private String maHang;
	
	@Column(name="TENHANG")
	private String tenHang;
	
	@OneToMany(mappedBy = "maHang", fetch = FetchType.LAZY)
	private List<LoaiSanPham> loaiSanPham;

	
	public HangSanXuat() {
	}

	public HangSanXuat(String maHang, String tenHang) {
		this.maHang = maHang;
		this.tenHang = tenHang;
	}

	public String getMaHang() {
		return maHang;
	}

	public void setMaHang(String maHang) {
		this.maHang = maHang;
	}

	public String getTenHang() {
		return tenHang;
	}

	public void setTenHang(String tenHang) {
		this.tenHang = tenHang;
	}

	public List<LoaiSanPham> getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(List<LoaiSanPham> loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}
	
	
}
