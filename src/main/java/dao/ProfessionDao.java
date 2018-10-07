package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Profession;


@Repository("professionDao")
public interface ProfessionDao {
	
	/**
	 * 从profession表中查询所有的专业
	 * @return
	 */
	public List<Profession> findAll();
	
	/**
	 * 关联profession表和p_s表，
	 * 根据学科编号，查找出与该学科相关的所有专业
	 * @param s_no
	 * @return
	 */
	public List<Profession> findProfessionByS_no(String s_no);

	public List<Profession> findProfessionByD_no(String d_no);
}
