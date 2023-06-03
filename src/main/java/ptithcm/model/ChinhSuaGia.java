package ptithcm.model;

import java.math.BigDecimal;
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
@Table(name="CHINH_SUA_GIA")
public class ChinhSuaGia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDCS")
	private int idCS;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(name="NGAY")
	private Date ngay;
	
	@Column(name="GIA")
	private BigDecimal gia;
	
	@ManyToOne
	@JoinColumn(name="MANV")
	private NhanVien maNV;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;

	public ChinhSuaGia() {
	}
	
	

	public ChinhSuaGia( Date ngay, BigDecimal gia, NhanVien maNV, LoaiSanPham maLoai) {
		this.ngay = ngay;
		this.gia = gia;
		this.maNV = maNV;
		this.maLoai = maLoai;
	}



	public int getIdCS() {
		return idCS;
	}

	public void setIdCS(int idCS) {
		this.idCS = idCS;
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public BigDecimal getGia() {
		return gia;
	}

	public void setGia(BigDecimal gia) {
		this.gia = gia;
	}

	public NhanVien getMaNV() {
		return maNV;
	}

	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}
	
	
}
