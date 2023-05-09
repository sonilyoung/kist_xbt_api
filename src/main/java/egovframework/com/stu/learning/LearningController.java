
package egovframework.com.stu.learning;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.learning.service.LearningService;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningImg;
import egovframework.com.stu.learning.vo.LearningProblem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 컨텐츠 컨트롤러 클래스
 * 
 * @author iyson
 * @since 2022.12.28
 * @version 1.0
 * @see
 *
 */
@RestController
@RequestMapping("/stu/learning")
@Api(tags = "learning Management API")
public class LearningController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearningController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private LearningService learningService;
    
    
    /**
     * 사용자학습정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectLearning.do")
    @ApiOperation(value = "사용자학습정보", notes = "사용자학습정보")
    public BaseResponse<Learning> selectLearningInfo(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			Learning learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());			
			List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			
			//등록된학습문제 체크
			int problemsCnt = learningService.selectLearningProblemsCount(lpParams);
			
			if(problemsCnt > 0) {
				learningService.updateLearningProblems(lpParams);
			}else {
				//문제등록
				learningService.insertLearningProblems(lpParams);				
			}
			
			lpParams.setEndYn("N");
			List<LearningProblem> resultList = learningService.selectLearnProblemsList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			
			moduleInfoData.setLearningProblemList(resultList);
			
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
       
    
    
    
    /**
     * 학습시작
     * 
     * @param param
     * @return Company
    
    @PostMapping("/startLearning.do")
    @ApiOperation(value = "학습시작", notes = "학습시작")
    public BaseResponse<List<LearningProblem>> startLearning(HttpServletRequest request, @RequestBody LearningProblem params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			
			//차수정보조회
			Learning learnData = new Learning();
			learnData.setUserId(login.getUserId());
			Learning baselineData = learningService.selectBaseline(learnData);
			if(baselineData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}			
			
			//문제조회
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			List<LearningProblem> problems = learningService.selectLearningProblems(params);	
			if(problems == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}			
			
			//등록된학습문제 체크
			int problemsCnt = learningService.selectLearningProblemsCount(params);
			
			if(problemsCnt > 0) {
				learningService.updateLearningProblems(params);
			}else {
				//문제등록
				learningService.insertLearningProblems(params);				
			}
			
			
			List<LearningProblem> resultList = learningService.selectLearnProblemsList(params); 
			return new BaseResponse<List<LearningProblem>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  */    
           
    
    /**
     * 학습이미지목록가져오기
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectImgList.do")
    @ApiOperation(value = "학습이미지목록가져오기", notes = "학습이미지목록가져오기")
    public BaseResponse<LearningImg> selectImgList(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		try {

			LearningImg resultList = learningService.selectLeaningImgList(params); 
			return new BaseResponse<LearningImg>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }      
    
    
    
    /**
     * 학습정답선택
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/updateLearningAnswer.do")
    @ApiOperation(value = "학습정답선택", notes = "학습정답선택")
    public BaseResponse<Integer> updateLearningAnswer(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getUserActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserActionDiv" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			Learning learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());	
			
			Learning answer = learningService.selectLearnAnswer(params);
			if("1".equals(answer.getAnswer())) {//정답
				double result = 100/(double)moduleInfoData.getQuestionCnt();
				result = Math.round(result*1000)/1000.0; 
				params.setGainScore(result);
			}else {//오답
				params.setGainScore(0);
			}
			
			int result = learningService.updateLearningAnswer(params); 
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }          
    
    
    /**
     * 학습종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endLearning.do")
    @ApiOperation(value = "학습종료", notes = "학습종료")
    public BaseResponse<Learning> endLearning(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			Learning learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			params.setStudyLvl(moduleInfoData.getStudyLvl());
			params.setPassScore(moduleInfoData.getPassScore());
			
			learningService.insertLearningResult(params);
			Learning gainScore = learningService.selectLeaningSum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(moduleInfoData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//학습종료 틀린갯수 맞은갯수 확인 
			Learning resultCnt = learningService.selectLeaningSum(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());		
			
			//문제종료처리
			params.setEndYn("Y");
			learningService.updateLearningEnd(params);
			
			//결과데이터저장
			learningService.updateLearningResult(params);
			
			//학습자평균가져오기
			
			return new BaseResponse<Learning>(params);
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }          
    	    
    
    
    
    /**
     * 사용자학습완료정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectLearningComplete.do")
    @ApiOperation(value = "사용자학습완료정보", notes = "사용자학습완료정보")
    public BaseResponse<Learning> selectLearningComplete(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			Learning learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());			
			List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			
			lpParams.setEndYn("Y");
			List<LearningProblem> resultList = learningService.selectLearnProblemsList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			
			moduleInfoData.setLearningProblemList(resultList);
			
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
           
}
