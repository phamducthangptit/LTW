package ptithcm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="NHA_CUNG_CAP")
public class NhaCungCap {
	@Id
	@Column(name="MANCC")
	private String maNCC;
	
	@Column(name="TENNCC")
	private String tenNCC;
	
	@Column(name="DIACHI")
	private String diaChi;
	
	@Column(name="SDT")
	private String sdt;
	
	@Column(name="EMAIL")
	private String email;
	
	@OneToMany(mappedBy = "maNCC", fetch = FetchType.LAZY)
	private List<CungCap> cungCap;
	
	@OneToMany(mappedBy = "maNCC", fetch = FetchType.LAZY)
	private List<DonDatHang> donDatHang;

	public NhaCungCap() {
	}

	public NhaCungCap(String maNCC, String tenNCC, String diaChi, String sdt, String email) {
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.email = email;
	}

	public String getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CungCap> getCungCap() {
		return cungCap;
	}

	public void setCungCap(List<CungCap> cungCap) {
		this.cungCap = cungCap;
	}
	
}
