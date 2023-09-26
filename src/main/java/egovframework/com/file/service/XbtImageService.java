package egovframework.com.file.service;

import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
import egovframework.com.file.vo.LearningImg;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 *      </pre>
 */
public interface XbtImageService {
	
	
	public LearningImg selectAdmAllBagImg(LearningImg params) throws Exception;
	
	public LearningImg selectSudoImgRename(LearningImg params) throws Exception;
	
	public TowdGeneration selectTowdImg(TowdGeneration params) throws Exception;
	
	public ThreedGeneration selectThreedImg(ThreedGeneration params) throws Exception;
	
}
