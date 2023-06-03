package ptithcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CUNG_CAP")
public class CungCap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDCC")
	private int idCC;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;
	
	@ManyToOne
	@JoinColumn(name="MANCC")
	private NhaCungCap maNCC;

	public CungCap() {
	}

	public CungCap( LoaiSanPham maLoai, NhaCungCap maNCC) {
		this.maLoai = maLoai;
		this.maNCC = maNCC;
	}

	public int getIdCC() {
		return idCC;
	}

	public void setIdCC(int idCC) {
		this.idCC = idCC;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}

	public NhaCungCap getMaNCC() {
		return maNCC;
	}

	public void setMaNCC(NhaCungCap maNCC) {
		this.maNCC = maNCC;
	}
	
	
}
