package ptithcm.model;

public class Cart {
	private int idGH;
	private LoaiSanPham lsp;
	private int soLuong;
	private int soLuongMax;
	private int check;
	public Cart() {
		
	}
	
	

	public Cart(int idGH, LoaiSanPham lsp, int soLuong, int soLuongMax, int check) {
		this.idGH = idGH;
		this.lsp = lsp;
		this.soLuong = soLuong;
		this.soLuongMax = soLuongMax;
		this.check = check;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public int getIdGH() {
		return idGH;
	}

	public void setIdGH(int idGH) {
		this.idGH = idGH;
	}

	public LoaiSanPham getLsp() {
		return lsp;
	}
	public void setLsp(LoaiSanPham lsp) {
		this.lsp = lsp;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public int getSoLuongMax() {
		return soLuongMax;
	}
	public void setSoLuongMax(int soLuongMax) {
		this.soLuongMax = soLuongMax;
	}
	
}
