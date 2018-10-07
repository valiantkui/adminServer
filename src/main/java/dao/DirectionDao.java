package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Direction;


@Repository("directionDao")
public interface DirectionDao {
	public List<Direction> findAllMasterDirection();
	public List<Direction> findAllJobDirection();
	
	
	
	public List<Direction> findMasterDirectionByP_no(String p_no);
	public List<Direction> findJobDirectionByP_no(String p_no);
	
	public Direction findMasterDirectionByD_no(String d_no);
	public Direction findJobDirectionByD_no(String d_no);
	
	public void addDirection(Direction direction);
	
	public void updateDirection(Direction direction);
	
	public void deleteByD_no(String d_no);
	
	public List<Direction> findDirectionByS_no(String s_no);
}
