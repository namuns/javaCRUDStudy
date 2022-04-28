package com.mis.persistence;

import java.util.List;

import com.mis.domain.BoardVO;
import com.mis.domain.Criteria;

public interface BoardDAO {
	
	public void create (BoardVO vo) throws Exception;
	
	public BoardVO read(int bno) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public void delete(int bno)throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	//페이징 기능 추가
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	public int listCountCriteria(Criteria cri) throws Exception;
	

}
