
package egovframework.com.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.test.service.TestService;
import io.swagger.annotations.ApiOperation;

/**
 * 테스트
 */
@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");
    
    public static final int XBT_TARGET_NAME = 5500;

    @Autowired
    private TestService testService;
    
    
    public static final String targetUnit = "";
    
	@GetMapping("/index.do")
	@ApiOperation(value = "test", notes = "test")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> index(HttpServletRequest request, @RequestParam(required = false) String params) {

		return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
	}

	@RequestMapping(value = "/selectPdf.do", method = RequestMethod.GET)
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public byte[] selectPdf(
			HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		BufferedOutputStream bos = null;

		 String pdfFileName = "D:/files/pdffile.pdf";
		 File pdfFile = new File(pdfFileName);
		 byte[] fileByte = Files.readAllBytes(pdfFile.toPath());
		 
		 //클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
		 response.setContentType("application/pdf");

		 //★ 이 구문이 있으면 [다운로드], 이 구문이 없다면 바로 target 지정된 곳에 view 해줍니다.
		 response.addHeader("Content-Disposition", "attachment; filename="+pdfFile.getName()+".pdf");

		 return fileByte;
	}	
	
	
}
