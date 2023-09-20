package egovframework.com.api.edc.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.file.vo.LearningImg;
import egovframework.com.global.common.GlobalsProperties;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service("sudoImgServiceImpl")
public class SudoImgServiceImpl implements SudoImgService {
	
    /*카이스트api*/
    public static final String url = GlobalsProperties.getProperty("xbp.api");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SudoImgServiceImpl.class);

	@Autowired
	private FileStorageService fileStorageService;    
	
    /*kaist xray 저장경로*/
    public static final String KAIST_XRAY_RESULT_IMG_PATH = GlobalsProperties.getProperty("kaist.xray.result.img.path");	
	

	@Override
	public String transImages(LearningImg oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("=========sudoImg start=========");
		
		//long testTime = System.currentTimeMillis();
		
		OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.HOURS).build();
		
		// 이미지 파일을 읽고, Base64로 인코딩하여 JSON 데이터에 포함시킴
		File imageFile1 = new File(KAIST_XRAY_RESULT_IMG_PATH+File.separator+oj.getBagScanId()+File.separator+af1.getSaveFileName());
		byte[] imageData1 = Files.readAllBytes(imageFile1.toPath());
		
		File imageFile2 = new File(KAIST_XRAY_RESULT_IMG_PATH+File.separator+oj.getBagScanId()+File.separator+af2.getSaveFileName());
		byte[] imageData2 = Files.readAllBytes(imageFile2.toPath());		
		//String encodedImageData = Base64.getEncoder().encodeToString(imageData);

		// JSON 데이터 생성
		JSONObject json = new JSONObject();
		json.put("bagScanId", oj.getBagScanId());
		json.put("imgFront", imageData1);
		json.put("imgFrontName", af1.getOriginalFileName());
		
		json.put("imgSide", imageData2);
		
		json.put("imgSideName", af2.getOriginalFileName());		
		//json.put("imageData", encodedImageData);

		// JSON 데이터를 문자열로 변환
		String jsonString = json.toString();

		String tgtUrl = url+File.separator+"api"+File.separator+"transImages.do";
		
		// 요청 생성
		Request request = new Request.Builder()
				.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(RequestBody.create(jsonString, MediaType.parse("application/json")))
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		
        // 수신한 JSON 데이터 읽기
        String jsonData = response.body().string();
		LOGGER.info("=========sudoImg end=========");
		System.out.println("response: " + response);
		System.out.println("Received JSON data: " + jsonData);		
		LOGGER.info("=========sudoImg end=========");
		
		return jsonData;
	}


}
