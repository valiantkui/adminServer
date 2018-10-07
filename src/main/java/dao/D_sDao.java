package dao;

import org.springframework.stereotype.Repository;

import entity.D_s;

@Repository("d_sDao")
public interface D_sDao {
	
	public void addD_s(D_s d_s);
	
	public void deleteByD_no(String d_no);

}
