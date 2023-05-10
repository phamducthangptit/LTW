package ptithcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SAN_PHAM")
public class SanPham {
	@Id
	@Column(name="SERI")
	private String seri;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;
	
	@ManyToOne
	@JoinColumn(name="SOPHIEUNHAP")
	private PhieuNhap soPhieuNhap;
	
	@ManyToOne
	@JoinColumn(name="IDGH")
	private GioHang idGH;
	
	@ManyToOne
	@JoinColumn(name="SOPHIEUTRA")
	private PhieuTra soPhieuTra;
	
	@OneToOne(mappedBy = "seri")
	private PhieuBaoHanh phieuBaoHanh;

	public SanPham() {
	}

	public SanPham(String seri, LoaiSanPham maLoai, PhieuNhap soPhieuNhap, GioHang idGH, PhieuTra soPhieuTra,
			PhieuBaoHanh phieuBaoHanh) {
		this.seri = seri;
		this.maLoai = maLoai;
		this.soPhieuNhap = soPhieuNhap;
		this.idGH = idGH;
		this.soPhieuTra = soPhieuTra;
		this.phieuBaoHanh = phieuBaoHanh;
	}

	public String getSeri() {
		return seri;
	}

	public void setSeri(String seri) {
		this.seri = seri;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}

	public PhieuNhap getSoPhieuNhap() {
		return soPhieuNhap;
	}

	public void setSoPhieuNhap(PhieuNhap soPhieuNhap) {
		this.soPhieuNhap = soPhieuNhap;
	}

	public GioHang getIdGH() {
		return idGH;
	}

	public void setIdGH(GioHang idGH) {
		this.idGH = idGH;
	}

	public PhieuTra getSoPhieuTra() {
		return soPhieuTra;
	}

	public void setSoPhieuTra(PhieuTra soPhieuTra) {
		this.soPhieuTra = soPhieuTra;
	}

	public PhieuBaoHanh getPhieuBaoHanh() {
		return phieuBaoHanh;
	}

	public void setPhieuBaoHanh(PhieuBaoHanh phieuBaoHanh) {
		this.phieuBaoHanh = phieuBaoHanh;
	}
	

}
