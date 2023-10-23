package egovframework.com.api.edc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.api.edc.service.EgovXtsEdcApiService;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.api.edc.vo.MpoleData;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(tags = "XBT external API")
public class XbtMpoleApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtMpoleApiController.class);
	
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
     * mpole데이터 저장
     * 
     * @param param
     * @return Integer
     */	    
	@ResponseBody
	@RequestMapping(value = {"/saveMpoleData.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<MpoleData> saveMpoleData(HttpServletRequest request, HttpServletResponse response
			, @RequestBody MpoleData params) throws Exception {
		
        //ApiLogin login = apiLoginService.getLoginInfo(request);
        //LOGGER.info("login : " + login);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		try {
			
			LOGGER.info("=========mploe start=========");
			LOGGER.info("params : " + params);
			
			JSONObject jsonData = fileStorageService.createMpoleData(params);
			LOGGER.info("jsonData : " + jsonData);
			
			boolean result = fileStorageService.createMpoleFile(jsonData);
			
			LOGGER.info("=========mploe end=========");
			
			if(result) {
				return new BaseResponse<MpoleData>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), params);
			}else {
				return new BaseResponse<MpoleData>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());	
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<MpoleData>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}
	}

	
	
    /**
     * mpole데이터 가져오기
     * 
     * @param param
     * @return Integer
     */	    
	@ResponseBody
	@RequestMapping(value = {"/selectMpoleData.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<MpoleData> selectMpoleData(HttpServletRequest request, HttpServletResponse response
			, @RequestBody MpoleData params) throws Exception {
		
        //ApiLogin login = apiLoginService.getLoginInfo(request);
        //LOGGER.info("login : " + login);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		try {
			
			LOGGER.info("=========selet mploe start=========");
			//LOGGER.info("params : " + params);
			
			List<JSONObject> jsonData = fileStorageService.selectMpoleData(params);
			//LOGGER.info("jsonData : " + jsonData);
			
			LOGGER.info("=========selet mploe end=========");
			
			if(jsonData != null) {
				return new BaseResponse<MpoleData>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), params);
			}else {
				return new BaseResponse<MpoleData>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());	
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<MpoleData>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}
	}	
	
}
