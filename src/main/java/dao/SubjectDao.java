package dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import entity.Subject;


@Repository("subjectDao")
public interface SubjectDao {
	
	/**
	 * ����רҵ��Ų��ҳ����и�רҵ��ѧ��
	 * @param p_no רҵ���
	 * @return ѧ�Ƶļ���
	 */
	public List<Subject> findSubjectByP_no(String p_no);
	
	public List<Subject> findSubjectByD_no(String d_no);
	
	/**
	 * ��ѯ���е�ѧ��
	 * @return ѧ�Ƶļ���
	 */
	public List<Subject> findAllSubject();
	
	/**
	 * ����ѧ�Ʊ�Ų��ҳ���ѧ�Ƶ������Ϣ
	 * @param s_no
	 * @return
	 */
	public Subject findSubjectByS_no(String s_no);
	
	/**
	 * ���ݺ��ѧ�Ʊ�Ų��ҳ�ǰ��ѧ�Ƶ�ѧ����Ϣ
	 * @param s_no ѧ�Ʊ��
	 * @return	ѧ����Ϣ�ļ���
	 */
	public List<Subject> findPreSubjectByS_no(String s_no);
	
	/**
	 * ���һ��ѧ��
	 * @param subject	Ҫ��ӵ�ѧ��
	 */
	public void addSubject(Subject subject);
	
	
	
	public void updateSubjectNotImage(Subject subject);
	
	public void updateSubject(Subject subject);
	
	
	public void deleteSubjectByS_no(String s_no);
}
