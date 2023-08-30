
package egovframework.com.adm.login;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.login.service.ApiLoginService;
import egovframework.com.adm.login.vo.ApiLogin;
import egovframework.com.adm.login.vo.LoginRequest;
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
 * 
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.8.26	정진오			IncludedInfo annotation 추가
 *  2011.09.07  서준식          스프링 시큐리티 로그인 및 SSO 인증 로직을 필터로 분리
 *  2011.09.25  서준식          사용자 관리 컴포넌트 미포함에 대한 점검 로직 추가
 *  2011.09.27  서준식          인증서 로그인시 스프링 시큐리티 사용에 대한 체크 로직 추가
 *  2011.10.27  서준식          아이디 찾기 기능에서 사용자 리름 공백 제거 기능 추가
 *      </pre>
 */
@RestController
@RequestMapping("/adm")
@Api(tags = "Login Management API")
public class ApiLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLoginController.class);

    @Autowired
    private ApiLoginService apiLoginService;
    

    @PostMapping("/login.do")
    @ApiOperation(value = "Login by loginId", notes = "This function returns the token of the user.")
    public BaseResponse<TokenResponse> login(HttpServletRequest request,
            @ApiParam @RequestBody LoginRequest loginRequest) {
        // LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getLoginId())) {
            throw new CustomBaseException(BaseResponseCode.INPUT_CHECK_ERROR,
                    new String[] {"loginId", "로그인 아이디"});
        }
        if (!StringUtils.hasText(loginRequest.getLoginPw())) {
            throw new CustomBaseException(BaseResponseCode.INPUT_CHECK_ERROR,
                    new String[] {"loginPw", "로그인 암호"});
        }
        String token = apiLoginService.createToken(request, loginRequest);
        if (token == null) {
            throw new CustomBaseException(BaseResponseCode.AUTH_ERROR, "");
        }
        
        //로그인 횟수
        //loginService.updateLoginCnt(loginRequest.getLoginId());
        
        // 로그인 시간 업데이트 
        //loginService.updateLoginTime(loginRequest.getLoginId());
        
        return new BaseResponse<TokenResponse>(new TokenResponse(token, "bearer"));
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
