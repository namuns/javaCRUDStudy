package com.mis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mis.domain.ProductVO;
import com.mis.domain.SearchCriteria;
import com.mis.persistence.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService{
	

	@Inject
	private ProductDAO dao;
	
	@Override
	public void register(ProductVO vo) throws Exception {
		dao.create(vo);
		
	}
	
	@Override
	public ProductVO read(int pno) throws Exception {
		//1) 조회수 업뎃

		dao.updateViewCount(pno);

		//2) 상세게시물 리턴
		return dao.read(pno);
	}
	

	@Override
	public void modify(ProductVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
		
	}

	@Override
	public void remove(int pno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(pno);
		
	}
	
	@Override
	public List<ProductVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}
	
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {

		return dao.listSearchCount(cri);
	}
	
	

	
	
}
