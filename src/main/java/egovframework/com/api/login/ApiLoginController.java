
package egovframework.com.api.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.cmm.vo.TokenResponse;
import egovframework.com.global.exception.CustomBaseException;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 컨트롤러 클래스
 */

@RestController
@RequestMapping("/kist/api")
@Api(tags = "Login Management API")
public class ApiLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLoginController.class);

    @Autowired
    private ApiLoginService apiLoginService;
    

    @PostMapping("/login.do")
    @ApiOperation(value = "Login by loginId", notes = "This function returns the token of the user.")
    public BaseResponse<TokenResponse> login(HttpServletRequest request,
            @ApiParam @RequestBody ApiLogin loginRequest) {
        // LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getLoginId())) {
            throw new CustomBaseException(BaseResponseCode.INPUT_CHECK_ERROR,
                    new String[] {"loginId", "로그인 아이디"});
        }
        ApiLogin login = apiLoginService.createToken(request);
        if (login.getAccessToken() == null) {
            throw new CustomBaseException(BaseResponseCode.AUTH_ERROR, "");
        }
        
        //로그인 횟수
        //loginService.updateLoginCnt(loginRequest.getLoginId());
        
        // 로그인 시간 업데이트 
        //loginService.updateLoginTime(loginRequest.getLoginId());
        
        return new BaseResponse<TokenResponse>(new TokenResponse(login.getAccessToken(), "bearer"));
    }
    
    
    /**
     * 로그인 정보 조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getLoginInfo.do")
    @ApiOperation(value = "getLoginInfo information", notes = "get login information")
    public BaseResponse<ApiLogin> getLoginInfo(HttpServletRequest request) {
    	
		try {
			ApiLogin login = apiLoginService.getLoginInfo(request);
	        return new BaseResponse<ApiLogin>(login);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
        	throw new BaseException(BaseResponseCode.AUTH_FAIL);
        }
    }      

    
}
