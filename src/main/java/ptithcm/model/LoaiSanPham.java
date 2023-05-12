package ptithcm.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="LOAI_SAN_PHAM")
public class LoaiSanPham {
	@Id
	@Column(name="MALOAI")
	private String maLoai;
	
	@Column(name="TENSP")
	private String tenSP;
	
	@Column(name="GIA")
	private BigDecimal gia;
	
	@Column(name="ANH")
	private String anh;
	
	@Column(name="MOTA")
	private String moTa;
	
	@Column(name="CPU")
	private String cPU;
	
	@Column(name="RAM")
	private String ram;
	
	@Column(name="HARDWARE")
	private String hardWare;
	
	@Column(name="CARD")
	private String card;
	
	@Column(name="SCREEN")
	private String screen;
	
	@Column(name="OS")
	private String os;
	
	@ManyToOne
	@JoinColumn(name="MAHANG")
	private HangSanXuat maHang;
	
	@ManyToOne
	@JoinColumn(name="MATHELOAI")
	private TheLoai maTheLoai;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<CTDotGiamGia> ctDotGiamGia;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<ChinhSuaGia> chinhSuaGia;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<CungCap> cungCap;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<CTDonDatHang> ctDonDatHang;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<SanPham> sanPham;
	
	@OneToMany(mappedBy = "maLoai", fetch = FetchType.LAZY)
	private List<BinhLuan> binhLuan;

	public LoaiSanPham() {
	}

	public LoaiSanPham(String maLoai, String tenSP, BigDecimal gia, String anh, String moTa, String cPU, 
			String ram,
			String hardWare, String card, String screen, String os, HangSanXuat maHang, TheLoai maTheLoai) {
		this.maLoai = maLoai;
		this.tenSP = tenSP;
		this.gia = gia;
		this.anh = anh;
		this.moTa = moTa;
		this.cPU = cPU;
		this.ram = ram;
		this.hardWare = hardWare;
		this.card = card;
		this.screen = screen;
		this.os = os;
		this.maHang = maHang;
		this.maTheLoai = maTheLoai;
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

	public BigDecimal getGia() {
		return gia;
	}

	public void setGia(BigDecimal gia) {
		this.gia = gia;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getcPU() {
		return cPU;
	}

	public void setcPU(String cPU) {
		this.cPU = cPU;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getHardWare() {
		return hardWare;
	}

	public void setHardWare(String hardWare) {
		this.hardWare = hardWare;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public HangSanXuat getMaHang() {
		return maHang;
	}

	public void setMaHang(HangSanXuat maHang) {
		this.maHang = maHang;
	}

	public TheLoai getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(TheLoai maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public List<CTDotGiamGia> getCtDotGiamGia() {
		return ctDotGiamGia;
	}

	public void setCtDotGiamGia(List<CTDotGiamGia> ctDotGiamGia) {
		this.ctDotGiamGia = ctDotGiamGia;
	}

	public List<ChinhSuaGia> getChinhSuaGia() {
		return chinhSuaGia;
	}

	public void setChinhSuaGia(List<ChinhSuaGia> chinhSuaGia) {
		this.chinhSuaGia = chinhSuaGia;
	}

	public List<CungCap> getCungCap() {
		return cungCap;
	}

	public void setCungCap(List<CungCap> cungCap) {
		this.cungCap = cungCap;
	}

	public List<CTDonDatHang> getCtDonDatHang() {
		return ctDonDatHang;
	}

	public void setCtDonDatHang(List<CTDonDatHang> ctDonDatHang) {
		this.ctDonDatHang = ctDonDatHang;
	}

	public List<SanPham> getSanPham() {
		return sanPham;
	}

	public void setSanPham(List<SanPham> sanPham) {
		this.sanPham = sanPham;
	}

	public List<BinhLuan> getBinhLuan() {
		return binhLuan;
	}

	public void setBinhLuan(List<BinhLuan> binhLuan) {
		this.binhLuan = binhLuan;
	}

	@Override
	public String toString() {
		return "[Tên: " + tenSP + ", CPU: " + cPU + ", RAM: " + ram + ", HardWare: " + hardWare + ", Card: "
				+ card + ", Screen: " + screen + ", OS: " + os + "]";
	}
	
}
