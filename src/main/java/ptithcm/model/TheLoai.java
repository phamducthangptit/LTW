package ptithcm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="THE_LOAI")
public class TheLoai {
	@Id
	@Column(name="MATHELOAI")
	private String maTheLoai;
	
	@Column(name="TENTL")
	private String tenTL;
	
	@OneToMany(mappedBy = "maTheLoai", fetch = FetchType.LAZY)
	private List<LoaiSanPham> loaiSanPham;

	
	public TheLoai() {
	}

	public TheLoai(String maTheLoai, String tenTL) {
		this.maTheLoai = maTheLoai;
		this.tenTL = tenTL;
	}

	public String getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(String maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public String getTenTL() {
		return tenTL;
	}

	public void setTenTL(String tenTL) {
		this.tenTL = tenTL;
	}

	public List<LoaiSanPham> getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(List<LoaiSanPham> loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}
	
	
}
