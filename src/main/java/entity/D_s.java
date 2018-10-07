package entity;

public class D_s {

	private String d_no;//方向编号
	private String s_no;//学科编号
	public String getD_no() {
		return d_no;
	}
	public void setD_no(String d_no) {
		this.d_no = d_no;
	}
	public String getS_no() {
		return s_no;
	}
	public void setS_no(String s_no) {
		this.s_no = s_no;
	}
	@Override
	public String toString() {
		return "D_s [d_no=" + d_no + ", s_no=" + s_no + "]";
	}
	
	
}
