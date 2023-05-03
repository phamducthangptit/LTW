package ptithcm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NHAN_VIEN")
public class NhanVien {
	@Id
	@Column(name = "MANV")
	private String maNV;

	@Column(name = "HO")
	private String ho;

	@Column(name = "TEN")
	private String ten;

	@Column(name = "NGAYSINH")
	private Date ngaySinh;

	@Column(name = "SDT")
	private String sDT;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "DIACHI")
	private String diaChi;

	@Column(name = "TRANGTHAI")
	private int trangThai;

	@Column(name = "ROLE")
	private String role;

	public NhanVien() {
	}

	public NhanVien(String maNV, String ho, String ten, Date ngaySinh, String sDT, String email, String password,
			String diaChi, int trangThai, String role) {
		this.maNV = maNV;
		this.ho = ho;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.sDT = sDT;
		this.email = email;
		this.password = password;
		this.diaChi = diaChi;
		this.trangThai = trangThai;
		this.role = role;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
