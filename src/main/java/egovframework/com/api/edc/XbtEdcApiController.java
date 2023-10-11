package egovframework.com.api.edc;

import java.io.File;

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
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
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
public class XbtEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtEdcApiController.class);
	
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
	public BaseResponse<Integer> transKaistImages(HttpServletRequest request, HttpServletResponse response
			, @RequestBody LearningImg params) throws Exception {
		
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
			fileStorageService.fileDeleteAll("", "", "sudo");
			
			
			LOGGER.info("=========정면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgFront())){
				fileStorageService.ByteToFile(params.getImgFront(), params.getImgFrontName(), "sudo", params);
			}
			
			LOGGER.info("=========측면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgSide())){
				fileStorageService.ByteToFile(params.getImgSide(), params.getImgSideName(), "sudo", params);
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
	    @RequestBody LearningImg params){
	    ApiLogin login = apiLoginService.getLoginInfo(request);
	    LOGGER.info("login : " + login);
	    if (login == null) {
	        throw new BaseException(BaseResponseCode.AUTH_FAIL);
	    }
	      
	    try {
	        CommandExcutor ce = new CommandExcutor();
	        LOGGER.info("params : " + params);
	        
	        String result = "";
			if(!StringUtils.isEmpty(params.getKaistCommand())){
				for(String c : params.getKaistCommand()) {
					
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
			
			//sudo컬러 파일이 생성됬는지 확인
			//File fileYn = new File("/home/jun/project/gwansae-unified/color2multi/color/fileY.txt");
			//if (fileYn.isFile()) {
				LearningImg li = new LearningImg();
				li.setBagScanId(params.getBagScanId());
				
				//파일리네임
				xbtImageService.selectSudoImgRename(li);
				
				//파일이미지가져오기
				LOGGER.info("params : " + params);
				LearningImg result = xbtImageService.selectAdmAllBagImg(li);
				
				//파일삭제
				fileStorageService.fileDeleteAll("", "", "sudo_result");
				return new BaseResponse<LearningImg>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result);					
			//}else {
				//return new BaseResponse<LearningImg>(BaseResponseCode.FILE_CREATE, BaseResponseCode.FILE_CREATE.getMessage());
			//}
			
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<LearningImg>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage());
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}

	}	
	
	
	
    /**
     * kaist 2d이미지합성 가져오기
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectKaistTowdImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<TowdGeneration> selectKaistTowdImg(HttpServletRequest request, HttpServletResponse response
			, @RequestBody TowdGeneration params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("kaist 2d이미지합성 가져오기 : " + params);
		
		try {
			
			//파일이미지가져오기
			LOGGER.info("params : " + params);
			TowdGeneration result = xbtImageService.selectTowdImg(params);
			
			//파일삭제
			fileStorageService.fileDeleteAll("sample", "", "twod_result");
			return new BaseResponse<TowdGeneration>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result);
			
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<TowdGeneration>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage());
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}

	}		
	
	
    /**
     * kaist 3D 이미지 업로드
     * 
     * @param param
     * @return Company
     */	    
	@ResponseBody
	@RequestMapping(value = {"/transKaistThreedImages.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> transKaistThreedImages(HttpServletRequest request, HttpServletResponse response
			, @RequestBody LearningImg params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("kaist 3D 이미지 업로드 시작");
		
		//JsonNode json = null;
		//ObjectMapper mapper = new ObjectMapper();
		
		try {
			//LOGGER.info("=========파링생성 디렉토리의 파일삭제");
			fileStorageService.fileDeleteAll("", "", "threed");
			fileStorageService.fileDeleteAll("", "", "threed_result");
			
			
			LOGGER.info("=========정면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgFront())){
				fileStorageService.ByteToFile(params.getImgFront(), params.getImgFrontName(), "threed", params);
			}
			
			LOGGER.info("=========측면이미지 업로드");
			if(!StringUtils.isEmpty(params.getImgSide())){
				fileStorageService.ByteToFile(params.getImgSide(), params.getImgSideName(), "threed", params);
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
     * kaist 3d이미지합성 가져오기
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectKaistThreedImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<ThreedGeneration> selectKaistThreedImg(HttpServletRequest request, HttpServletResponse response
			, @RequestBody ThreedGeneration params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("kaist 3d이미지합성 가져오기 : " + params);
		
		try {
			
			//파일이미지가져오기
			ThreedGeneration result = xbtImageService.selectThreedImg(params);
			
			//파일삭제
			//fileStorageService.fileDeleteAll("input", "output", "threed_result");
			return new BaseResponse<ThreedGeneration>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result);
			
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<ThreedGeneration>(BaseResponseCode.UNKONWN_KAIST_ERROR, BaseResponseCode.UNKONWN_KAIST_ERROR.getMessage());
			//json = mapper.convertValue(result, JsonNode.class);
			//return new BaseResponse<JsonNode>(json);			
		}

	}		
		
	
}
