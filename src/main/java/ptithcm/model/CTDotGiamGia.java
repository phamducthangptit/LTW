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
@Table(name="CT_DOT_GIAM_GIA")
public class CTDotGiamGia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDDOTGIAM")
	private int idDotGiam;
	
	@Column(name="TILEGIAM")
	private int tiLeGiam;
	
	@ManyToOne
	@JoinColumn(name="MALOAI")
	private LoaiSanPham maLoai;
	
	@ManyToOne
	@JoinColumn(name="MADOT")
	private DotGiamGia maDot;

	public CTDotGiamGia() {
	}

	public CTDotGiamGia(int idDotGiam, int tiLeGiam, LoaiSanPham maLoai, DotGiamGia maDot) {
		this.idDotGiam = idDotGiam;
		this.tiLeGiam = tiLeGiam;
		this.maLoai = maLoai;
		this.maDot = maDot;
	}

	public int getIdDotGiam() {
		return idDotGiam;
	}

	public void setIdDotGiam(int idDotGiam) {
		this.idDotGiam = idDotGiam;
	}

	public int getTiLeGiam() {
		return tiLeGiam;
	}

	public void setTiLeGiam(int tiLeGiam) {
		this.tiLeGiam = tiLeGiam;
	}

	public LoaiSanPham getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(LoaiSanPham maLoai) {
		this.maLoai = maLoai;
	}

	public DotGiamGia getMaDot() {
		return maDot;
	}

	public void setMaDot(DotGiamGia maDot) {
		this.maDot = maDot;
	}
	
	
}
