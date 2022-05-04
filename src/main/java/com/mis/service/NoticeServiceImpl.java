package com.mis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mis.domain.NoticeFileVO;
import com.mis.domain.NoticeVO;
import com.mis.domain.SearchCriteria;
import com.mis.persistence.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Inject
	private NoticeDAO dao;

	@Override
	public void register(NoticeVO vo) throws Exception {

		
		
		//1. Textarea 줄바꿈 처리
		vo.setContent(vo.getContent().replace("\\r\\n", "<br>"));
		
		//2. 공지사항 기본 내용 저장 ( 첨부파일 제외) => PK인 noticeNo 받아오기
		int noticeNO = dao.adCreate(vo);
		
		//3. 첨부파일 등록
		
		//3-1) 첨부파일 존재 여부 확인
		if(vo.getFiles() != null) {
			//3-2) 다중 첨부파일 저장
			
			for (int i = 0; i < vo.getFiles().length; i++) {
				NoticeFileVO fVo = new NoticeFileVO();
				fVo.setNoticeNo(noticeNO); // 공지사항 테이블 pk (FK)
				fVo.setNoticeFileName(vo.getFiles()[i]); //업로드 첨부파일 명
				
				dao.insertFile(fVo);
			}
		}
		
		
		
	}
	

	@Override
	public NoticeVO read(int noticeNo) throws Exception {

		return dao.read(noticeNo);
	}
	

	@Override
	public void modify(NoticeVO vo) throws Exception {
		

		//1. Textarea 줄바꿈 처리
		vo.setContent(vo.getContent().replace("\\r\\n", "<br>"));
		
		
		//2. 게시글 수정
		dao.adUpdate(vo);
		
		//3. 공지사항에 소속된 첨부파일 삭제
		dao.deleteFile(vo.getNoticeNo());
		
		//4. 첨부파일 등록
		
		//3-1) 첨부파일 존재 여부 확인
		if(vo.getFiles() != null) {
			//3-2) 다중 첨부파일 저장
			
			for (int i = 0; i < vo.getFiles().length; i++) {
				NoticeFileVO fVo = new NoticeFileVO();
				fVo.setNoticeNo(vo.getNoticeNo()); // 공지사항 테이블 pk (FK)
				fVo.setNoticeFileName(vo.getFiles()[i]); //업로드 첨부파일 명
				
				dao.insertFile(fVo);
			}
		}
		
	}

	@Override
	public void remove(int noticeNo) throws Exception {
		
		//공지사랑 게시글 삭제시, 1) 첨부파일 삭제 2)게시글을 삭제 
		
		//1) 첨부파일 삭제
		dao.deleteFile(noticeNo);
		
		// 2) 공지사항게시글 삭제
		dao.adDelete(noticeNo);
		
	}
	
	@Override
	public List<NoticeVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}
	
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {

		return dao.listSearchCount(cri);
	}


	@Override
	public List<NoticeFileVO> fileList(int noticeNo) throws Exception {

		return dao.fileList(noticeNo);
	}
	
	

}