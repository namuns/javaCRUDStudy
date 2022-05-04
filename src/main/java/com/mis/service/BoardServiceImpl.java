package com.mis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mis.domain.BoardVO;
import com.mis.domain.Criteria;
import com.mis.domain.SearchCriteria;
import com.mis.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO dao;
	
	
	@Override
	public void register(BoardVO vo) throws Exception {
		dao.create(vo);
		
	}

	@Override
	public BoardVO read(int bno) throws Exception {


		dao.updateViewCount(bno);

		return dao.read(bno);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
		
	}

	@Override
	public void remove(int bno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bno);
		
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll();
	}
	
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(cri);
	}
	
	@Override
	public int listCountCriteria(Criteria cri) throws Exception {

		return dao.listCountCriteria(cri);
	}
	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearchCriteria(cri);
	}
	
	@Override
	public int listSearchCountCriteria(SearchCriteria cri) throws Exception {

		return dao.listSearchCountCriteria(cri);
	}
	
}
