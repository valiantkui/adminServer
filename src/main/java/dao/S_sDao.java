package dao;

import org.springframework.stereotype.Repository;

import entity.S_s;

@Repository("s_sDao")
public interface S_sDao {

	public void addS_s(S_s s_s);
		
	public void deleteByNextSubject(String next_subject);
	
	public void deleteBySubject(String s_no);
}
