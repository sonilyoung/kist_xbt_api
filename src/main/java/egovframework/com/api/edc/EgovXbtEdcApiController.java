package egovframework.com.api.edc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.api.edc.service.EgovXtsEdcApiService;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.api.edc.utils.CommandExcutor;
import egovframework.com.api.edc.vo.CommandExcute;
import egovframework.com.api.edc.vo.XrayImgContents;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.file.vo.LearningImg;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/kaist/api")
@Api(tags = "XBT external API")
public class EgovXbtEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXbtEdcApiController.class);
	
	@Autowired
	private EgovXtsEdcPseudoFilterService egovXtsEdcPseudoFilterService;
	
	@Autowired
	private EgovXtsEdcThreeDimensionService egovXtsEdcThreeDimensionService;
	
	@Autowired
	private EgovXtsEdcReinforcementService egovXtsEdcReinforcementService;
	
	@Autowired
	private EgovXtsEdcApiService egovXtsEdcApiService;	
	
	@Autowired
	private ApiLoginService apiLoginService;	
	
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private XbtImageService xbtImageService;
	
    
    /**
     * kaist 슈도이미지 업로드
     * 
     * @param param
     * @return Company
     */	    
	@ResponseBody
	@RequestMapping(value = {"/transKaistImages.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> transKaistImages(HttpServletRequest request, HttpServletResponse response, @RequestBody XrayImgContents params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("params : " + params);
		
		//JsonNode json = null;
		//ObjectMapper mapper = new ObjectMapper();
		
		try {
			LOGGER.info("=========파링생성 디렉토리의 파일삭제");
			fileStorageService.fileDeleteAll("F", "S", "target");
			
			
			LOGGER.info("=========정면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgFront())){
				fileStorageService.ByteToFile(params.getImgFront(), params.getImgFrontName());
			}
			
			LOGGER.info("=========측면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgSide())){
				fileStorageService.ByteToFile(params.getImgSide(), params.getImgSideName());
			}		
			
			return new BaseResponse<Integer>(BaseResponseCode.UPLOAD_SUCCESS, BaseResponseCode.UPLOAD_SUCCESS.getMessage());
			
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<Integer>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage());
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}

		/*
		 * 
		 * long testTime = System.currentTimeMillis(); JsonNode jsonNode = null;
		 * HashMap<String, Object> hash = new HashMap<String, Object>();//리턴 객체 생성
		 * ObjectMapper mapper = new ObjectMapper(); try {
		 * 
		 * } catch(Exception e) { e.printStackTrace(); }
		 */
		
	}	
	
    /**
     * kaist 슈도이미지 명렁어실행
     * 
     * @param param
     * @return Company
     */		
	@ResponseBody
	@RequestMapping(value = {"/commandKaistExcute.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<String> commandExcute(
	    HttpServletRequest request, HttpServletResponse response,
	    @RequestBody CommandExcute params){
	    ApiLogin login = apiLoginService.getLoginInfo(request);
	    LOGGER.info("login : " + login);
	    if (login == null) {
	        throw new BaseException(BaseResponseCode.AUTH_FAIL);
	    }
	      
	    try {
	        CommandExcutor ce = new CommandExcutor();
	        LOGGER.info("params : " + params);
	        
	        String result = "";
			if(!StringUtils.isEmpty(params.getCommand())){
				for(String c : params.getCommand()) {
					
					LOGGER.info("명령어 실행 ==========> " + c);
					result = ce.excutor(c);	
				}
			}	
			
	        return new BaseResponse<String>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result);
	    } catch(Exception e) {
	        e.printStackTrace();
	        return new BaseResponse<String>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage(), e.getMessage());
	    }
	}
		
	
    /**
     * kaist 슈도이미지 가져오기
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectKaistSudoImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<LearningImg> selectKaistSudoImg(HttpServletRequest request, HttpServletResponse response, @RequestBody LearningImg params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("params : " + params);
		
		//JsonNode json = null;
		//ObjectMapper mapper = new ObjectMapper();
		
		try {
			LearningImg li = new LearningImg();
			li.setBagScanId(params.getBagScanId());
			
			//파일리네임
			xbtImageService.selectSudoImgRename(li);
			
			//파일이미지가져오기
			LearningImg result = xbtImageService.selectAdmAllBagImg(li);
			
			fileStorageService.fileDeleteAll("F", "S", "result");
			//LearningImg result = xbtImageService.selectAdmAllBagImg(li);		
			return new BaseResponse<LearningImg>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result);
			
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<LearningImg>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage());
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}

	}	
	
	
	
	
	
	
	
	
	
	
	
}
