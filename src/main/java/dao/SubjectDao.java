package dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import entity.Subject;


@Repository("subjectDao")
public interface SubjectDao {
	
	/**
	 * 根据专业编号查找出所有该专业的学科
	 * @param p_no 专业编号
	 * @return 学科的集合
	 */
	public List<Subject> findSubjectByP_no(String p_no);
	
	public List<Subject> findSubjectByD_no(String d_no);
	
	/**
	 * 查询所有的学科
	 * @return 学科的集合
	 */
	public List<Subject> findAllSubject();
	
	/**
	 * 根据学科编号查找出该学科的相关信息
	 * @param s_no
	 * @return
	 */
	public Subject findSubjectByS_no(String s_no);
	
	/**
	 * 根据后继学科编号查找出前继学科的学科信息
	 * @param s_no 学科编号
	 * @return	学科信息的集合
	 */
	public List<Subject> findPreSubjectByS_no(String s_no);
	
	/**
	 * 添加一个学科
	 * @param subject	要添加的学科
	 */
	public void addSubject(Subject subject);
	
	
	
	public void updateSubjectNotImage(Subject subject);
	
	public void updateSubject(Subject subject);
	
	
	public void deleteSubjectByS_no(String s_no);
}
