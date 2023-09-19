package egovframework.com.api.edc.service;

import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.file.vo.LearningImg;

public interface SudoImgService {
	
	public String transImages(LearningImg oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception;
	
}
