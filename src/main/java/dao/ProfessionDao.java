package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Profession;


@Repository("professionDao")
public interface ProfessionDao {
	
	/**
	 * ��profession���в�ѯ���е�רҵ
	 * @return
	 */
	public List<Profession> findAll();
	
	/**
	 * ����profession���p_s��
	 * ����ѧ�Ʊ�ţ����ҳ����ѧ����ص�����רҵ
	 * @param s_no
	 * @return
	 */
	public List<Profession> findProfessionByS_no(String s_no);

	public List<Profession> findProfessionByD_no(String d_no);
}
