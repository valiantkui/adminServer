package dao;

import org.springframework.stereotype.Repository;

import entity.P_s;

@Repository("p_sDao")
public interface P_sDao {

	public void addP_s(P_s p_s);
	
	
	public void deleteByS_no(String s_no);
	
	
	public void deleteProfessionByS_no(String s_no);
}
