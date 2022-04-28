package com.mis.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mis.domain.BoardVO;
import com.mis.domain.Criteria;
import com.mis.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDAOListTest {

	@Inject
	private BoardDAO dao;
	
	@Test
	public void createTest() throws Exception {
		
		BoardVO vo = new BoardVO();
		vo.setBno(0);
		vo.setTitle("테스트입니다.");
		vo.setContent("테스트 COntent");
		vo.setWriter("남문수");
		
		dao.create(vo);
	}
	
	
	@Test
	public void listCriteriaTest() throws Exception{

		
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(10);

		List<BoardVO> list = dao.listCriteria(cri);
		for(BoardVO vo : list) {
			System.out.println(vo);
		}

	}
	
	
	
	
}
