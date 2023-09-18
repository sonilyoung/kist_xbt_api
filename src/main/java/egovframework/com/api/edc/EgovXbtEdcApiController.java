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
import egovframework.com.api.edc.vo.XrayImgContents;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.service.FileStorageService;
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
	
	@ResponseBody
	@RequestMapping(value = {"/transImages.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> transImages(HttpServletRequest request, HttpServletResponse response, @RequestBody XrayImgContents params) throws Exception {
		
        ApiLogin login = apiLoginService.getLoginInfo(request);
        LOGGER.info("login : " + login);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		LOGGER.info("params : " + params);
		
		//JsonNode json = null;
		//ObjectMapper mapper = new ObjectMapper();
		
		try {
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
			return new BaseResponse<Integer>(BaseResponseCode.UPLOAD_FAIL, BaseResponseCode.UPLOAD_FAIL.getMessage());
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
	
	
	
	
	
	
	
	
	
	
	
	
	
}
