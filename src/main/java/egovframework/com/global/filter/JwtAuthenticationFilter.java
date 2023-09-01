package egovframework.com.global.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.global.exception.CustomBaseException;
import egovframework.com.global.http.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 사용자 서비스
     */
    @Autowired
    protected ApiLoginService apiLoginService;

	@Autowired
	protected Environment env;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomBaseException {
    	
    	ApiLogin login = apiLoginService.getLoginInfo(request);
		if (login == null) {
			throw new CustomBaseException(BaseResponseCode.AUTH_ERROR);
		}	
        filterChain.doFilter(request, response);
    }

}