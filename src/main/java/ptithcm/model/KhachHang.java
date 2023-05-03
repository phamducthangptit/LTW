package ptithcm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KHACH_HANG")
public class KhachHang {
	@Id
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "HO")
	private String ho;

	@Column(name = "TEN")
	private String ten;

	@Column(name = "DIACHI")
	private String diaChi;

	@Column(name = "NGAYSINH")
	private Date ngaySinh;

	@Column(name = "SDT")
	private String sdt;

	@Column(name = "PASSWORD")
	private String pass;

	@Column(name = "TRANGTHAI")
	private int trangThai;

	public KhachHang() {
	}

	public KhachHang(String email, String ho, String ten, String diaChi, Date ngaySinh, String sdt, String pass,
			int trangThai) {
		this.email = email;
		this.ho = ho;
		this.ten = ten;
		this.diaChi = diaChi;
		this.ngaySinh = ngaySinh;
		this.sdt = sdt;
		this.pass = pass;
		this.trangThai = trangThai;
	}
	

	public KhachHang(String email, String ho, String ten, String diaChi, Date ngaySinh, String sdt) {
		this.email = email;
		this.ho = ho;
		this.ten = ten;
		this.diaChi = diaChi;
		this.ngaySinh = ngaySinh;
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

}
