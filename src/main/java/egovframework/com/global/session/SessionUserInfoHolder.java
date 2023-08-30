package egovframework.com.global.session;

import egovframework.com.adm.login.vo.ApiLogin;

/**
 * 현재 실행중인 Thread 에 인증된 사용자 정보를 담아 사용하기 위한 클래스
 * 
 * @fileName : SessionUserInfoHolder.java
 * @author : YeongJun Lee
 * @date : 2022.06.09
 */
public class SessionUserInfoHolder {
    private static final ThreadLocal<ApiLogin> userInfo = new ThreadLocal<ApiLogin>();

    public static void set(ApiLogin login) {
        userInfo.set(login);
    }

    public static ApiLogin get() {
        return userInfo.get();
    }

    public static void remove() {
        userInfo.remove();
    }
}
