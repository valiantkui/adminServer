package dao;

import org.springframework.stereotype.Repository;

import entity.P_d;

@Repository("p_dDao")
public interface P_dDao {
	
	public void addP_d(P_d p_d);
	
	public void deleteByD_no(String d_no);

}
