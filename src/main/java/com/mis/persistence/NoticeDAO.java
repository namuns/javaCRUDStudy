package com.mis.persistence;

import java.util.List;

import com.mis.domain.NoticeFileVO;
import com.mis.domain.NoticeVO;
import com.mis.domain.SearchCriteria;

public interface NoticeDAO {

	// 1. Notice 등록
	public int adCreate(NoticeVO vo) throws Exception;

	// 2. Notice 상세보기
	public NoticeVO read(int noticeNo) throws Exception;

	// 3. Notice 수정
	public void adUpdate(NoticeVO vo) throws Exception;

	// 4. Notice 삭제
	public void adDelete(int noticeNo) throws Exception;

	// 5. (관리자)Notice 검색 가능한 목록
	// 학생 : Notice검색가능한 항목
	public List<NoticeVO> listSearch(SearchCriteria cri) throws Exception;

	// 6. Notice 검색 가능한 목록 ---> 페이징 , 카운트
	// 학생 :: 검색 가능한 목록 ---> 페이징 , 카운트
	public int listSearchCount(SearchCriteria cri) throws Exception;

	// 7. 파일 등록(관리자)
	public void insertFile(NoticeFileVO fVo) throws Exception;

	// 8. 파일 삭제(관리자)
	public void deleteFile(int noticeNo) throws Exception;

	// 9. 파일 목록(관리자)
	public List<NoticeFileVO> fileList(int noticeNo) throws Exception;

}
