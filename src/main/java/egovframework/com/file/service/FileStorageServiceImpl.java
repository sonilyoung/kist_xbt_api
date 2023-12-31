package egovframework.com.file.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.api.edc.vo.MpoleBoundingData;
import egovframework.com.api.edc.vo.MpoleData;
import egovframework.com.api.edc.vo.XrayImgContents;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.file.vo.LearningImg;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.FileReader;

/**
 * FileStorageService 구현체로 서블릿 설치 경로에 파일 저장 (※파일 Storage 결정 시까지 임시로 사용)
 * 
 * @fileName : ServletFileStorageService.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
@Service
@SuppressWarnings("unused")
public class FileStorageServiceImpl implements FileStorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);
	
    @Autowired
    private ServletContext servletContext;

	private String realPath;
    
    /*단품 저장경로*/
    public static final String UNIT_ROOT_DIR = GlobalsProperties.getProperty("xray.unitImg.path");    

    /*xray 저장경로*/
    public static final String XRAY_ROOT_DIR = GlobalsProperties.getProperty("xray.img.path");
    
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");
    
    /*파일업로드 저장경로*/
    public static final String FILE_DB_UPLOAD_PATH = GlobalsProperties.getProperty("file.db.upload.path");
    
    /*kaist xray 저장경로*/
    public static final String KAIST_SUDO_ROOT_DIR = GlobalsProperties.getProperty("kaist.sudo.img.path");    
    
    /*kist xray 결과경로*/
    public static final String KAIST_SUDO_RESULT_DIR = GlobalsProperties.getProperty("kaist.sudo.result.img.path");
    
    /*kist 2D 결과경로*/
    public static final String KAIST_TWOD_RESULT_DIR = GlobalsProperties.getProperty("kaist.twod.result.img.path");    

    /*kist 3D 생성경로*/
    public static final String KAIST_THREED_IMG_PATH = GlobalsProperties.getProperty("kaist.threed.img.path");    
    
    /*kist 3D 결과경로*/
    public static final String KAIST_THREED_RESULT_DIR = GlobalsProperties.getProperty("kaist.threed.result.img.path");
    
    /*엠폴 파일업로드 저장경로*/
    public static final String EMPOLE_FILE_UPLOAD_PATH = GlobalsProperties.getProperty("empole.file.upload.path");    
    
    @PostConstruct
    public void initialize() {
        this.realPath = servletContext.getRealPath("/");
    }
    
	@Override
	public AttachFile createUnitImageFile(String targetName, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = UNIT_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getTargetName()).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}        
    
	@Override
	public AttachFile createXrayImageFile(String targetName, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = XRAY_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getTargetName()).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}    

    @Override
    public AttachFile createFile(MultipartFile file) throws Exception {
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String filePath = FILE_UPLOAD_PATH;
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //sb.append(originalFileName.substring(0, originalFileName.indexOf(fileExtension) - 1));
        //sb.append("_").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))
                .append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(filePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(FILE_DB_UPLOAD_PATH);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
    }

    @Override
    public boolean deleteFile(AttachFile AttachFile) {
        File file = getFile(AttachFile);
        boolean bDeleted = false;
        if (file.exists()) {
            bDeleted = file.delete();
        }
        return bDeleted;
    }

    @Override
    public File getFile(AttachFile AttachFile) {
        File file = new File(AttachFile.getFilePath(), AttachFile.getSaveFileName());
        return file;
    }
    
    /**
     * 이미지정보 가져오기
     */
    @Override
    public void getImage(String imgPath, HttpServletResponse response) throws Exception{
    	
    	File file = new File(imgPath);
    	if(!file.isFile()){
    		throw new BaseException(BaseResponseCode.DATA_IS_NULL);
    	}
    	
    	FileInputStream fis = null;
    	BufferedInputStream in = null;
    	ByteArrayOutputStream bStream = null;
    	try {
    		fis = new FileInputStream(file);
    		in = new BufferedInputStream(fis);
    		bStream = new ByteArrayOutputStream();
    		int imgByte;
    		while ((imgByte = in.read()) != -1) {
    			bStream.write(imgByte);
    		}

    		String type = "";
    		String ext = FilenameUtils.getExtension(file.getName());
    		if (ext != null && !"".equals(ext)) {
    			if ("jpg".equals(ext.toLowerCase())) {
    				type = "image/jpeg";
    			} else {
    				type = "image/" + ext.toLowerCase();
    			}

    		} else {
    			LOGGER.debug("Image fileType is null.");
    		}

    		response.setHeader("Content-Type", type);
    		response.setContentLength(bStream.size());

    		bStream.writeTo(response.getOutputStream());

    		response.getOutputStream().flush();
    		response.getOutputStream().close();

    	} catch (Exception e) {
    		LOGGER.debug("{}", e);
    	} finally {
    		if (bStream != null) {
    			try {
    				bStream.close();
    			} catch (Exception est) {
    				LOGGER.debug("IGNORED: {}", est.getMessage());
    			}
    		}
    		if (in != null) {
    			try {
    				in.close();
    			} catch (Exception ei) {
    				LOGGER.debug("IGNORED: {}", ei.getMessage());
    			}
    		}
    		if (fis != null) {
    			try {
    				fis.close();
    			} catch (Exception efis) {
    				LOGGER.debug("IGNORED: {}", efis.getMessage());
    			}
    		}
    	}
    }

	@Override
	public AttachFile createXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = XRAY_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+targetName;
    	File imgDir = new File(filePath+File.separator+targetName);
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(targetName).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}

	@Override
	public AttachFile createKaistXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        //String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String fileExtension = "jpg";
        
        String filePath = KAIST_SUDO_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+targetName;
    	File imgDir = new File(filePath+File.separator+targetName);
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(targetName).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}	

	public void ByteToFile(byte[] target, String fileName, String filePath, LearningImg params) {
        String savePath = ""; // 저장할 파일 경로 및 이름
        
	    if("sudo".equals(filePath)) {
	    	savePath = KAIST_SUDO_ROOT_DIR;  // 슈도컬러생성경로
	    }else if("threed".equals(filePath)) {
	    	//savePath = KAIST_THREED_IMG_PATH + File.separator + params.getUnitId();  // 3d이미지생성경로
	    	savePath = KAIST_THREED_IMG_PATH + File.separator + params.getUnitId();  // 3d이미지생성경로
	    }        
        
        String targetFile = fileName;
        File fileDir = new File(savePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	} 

        try {
            
            File lOutFile = new File(savePath+File.separator+targetFile);
            FileOutputStream outputStream = new FileOutputStream(lOutFile);
            outputStream.write(target);
            outputStream.close();            
            LOGGER.info("파일 저장 완료");            		
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void fileDeleteAll(String target1, String target2, String filePath) {
	    String directoryPath = "";
	    
	    if("sudo".equals(filePath)) {
	    	directoryPath = KAIST_SUDO_ROOT_DIR;  // 슈도이미지 생성경로
	    }else if("sudo_result".equals(filePath)) {
	    	directoryPath = KAIST_SUDO_RESULT_DIR;  // 슈도이미지 결과경로
	    }else if("twod_result".equals(filePath)) {
	    	directoryPath = KAIST_TWOD_RESULT_DIR;  // 2d이미지 결과경로
	    }else if("threed_result".equals(filePath)) {
	    	directoryPath = KAIST_THREED_RESULT_DIR;  // 3d이미지 결과경로
	    }else if("threed".equals(filePath)) {
	    	directoryPath = KAIST_THREED_IMG_PATH;  // 3d이미지 요청경로
	    }

	    File directory = new File(directoryPath);
	    File[] files = directory.listFiles();  // 디렉토리 내의 모든 파일을 가져옵니다.

	    if (files != null) {
	        for (File file : files) {
	            if (file.isFile()) {
	            	 if("threed_result".equals(filePath) || "threed".equals(filePath) || "sudo".equals(filePath) || "sudo_result".equals(filePath)) {
			            file.delete();  // 파일 삭제
	            	 }else {
	 	            	if(file.getName().contains(target1) || file.getName().contains(target2)) {
		            		file.delete();  // 파일 삭제
		            	}	            		 
	            	 }

	            }
	        }
	    }		
	}

	@Override
	public JSONObject createMpoleData(MpoleData params) throws Exception {
		// TODO Auto-generated method stub
        // Create the JSONObject
		
		JSONObject jsonData = new JSONObject();
		try {
	        jsonData.put("unitGroupCd", params.getUnitGroupCd());
	        jsonData.put("unitName", params.getUnitName());
	        jsonData.put("empoleImg", params.getEmpoleImg());
	        jsonData.put("fileSize", params.getFileSize());
	        jsonData.put("fileExtension", params.getFileExtention());

	        // Create the JSONArray for boundingData
	        JSONArray boundingDataArray = new JSONArray();
	        for (MpoleBoundingData boundingData : params.getBoundingData()) {
	            JSONObject boundingDataJson = new JSONObject();
	            // Set properties of boundingDataJson using boundingData object
	            boundingDataJson.put("boundingX", boundingData.getBoundingX());
	            boundingDataJson.put("boundingY", boundingData.getBoundingY());
	            boundingDataJson.put("boundingWidth", boundingData.getBoundingWidth());
	            boundingDataJson.put("boundingHeight", boundingData.getBoundingHeight());
	            boundingDataJson.put("boundingClass", boundingData.getBoundingClass());
	            boundingDataJson.put("boundingScore", boundingData.getBoundingScore());
	            boundingDataArray.put(boundingDataJson);
	        }
	        jsonData.put("boundingData", boundingDataArray);

	        System.out.println(jsonData.toString());
	        jsonData.toString();		
	        return jsonData;
		}catch(Exception e) {
			e.printStackTrace();
			return jsonData;
		}
	}

	@Override
	public boolean createMpoleFile(JSONObject params) throws Exception {
		// TODO Auto-generated method stub
		boolean result = true;
		
        String filePath = EMPOLE_FILE_UPLOAD_PATH + File.separator + new SimpleDateFormat("yyyyMMdd").format(new Date());
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}   
    	
        StringBuilder sb = new StringBuilder();
        sb.append("mpole-").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(".").append("json");
        String fileName = sb.toString();		
		
        try (FileWriter file = new FileWriter(fileDir + File.separator + fileName)) {
            file.write(params.toString());
            LOGGER.info("JSON 객체가 파일에 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	
		/*
	    try {
	        // Read the JSON file as a string
	        String jsonString = new String(Files.readAllBytes(Paths.get("D:/files/output.json")));
	
	        // Convert the string to a JSON object
	        JSONObject jsonObject = new JSONObject(jsonString);
	
	        // Use the JSON object as needed
	        LOGGER.info("jsonObject:" + jsonObject);
	
	        LOGGER.info("JSON 파일을 성공적으로 읽고 JSON 객체로 변환했습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }*/		
		
		return result;
	}

	@Override
	public List<JSONObject> selectMpoleData(MpoleData params) throws Exception {
		// TODO Auto-generated method stub
		String xrayPath = EMPOLE_FILE_UPLOAD_PATH + File.separator + params.getTargetDate();
        File[] fileList = FileReader.ListFile( xrayPath );		
        LOGGER.info("selectMpoleData path: " + xrayPath);
        List<JSONObject> resultList = new ArrayList<JSONObject>();
        if(fileList==null) {
        	return null;
        }
        
        for( int i = 0; i < fileList.length; i++ ) {
    	    try {
    	        // Read the JSON file as a string
    	        String jsonString = new String(Files.readAllBytes(fileList[i].toPath()));
    	        // Convert the string to a JSON object
    	        JSONObject jsonObject = new JSONObject(jsonString);
    	        // Use the JSON object as needed
    	        //LOGGER.info("jsonObject:" + jsonObject.get);
    	        resultList.add(jsonObject);
    	        
    	        // JSON 파싱하여 이미지 데이터 추출
    	        String targetImg = jsonObject.get("empoleImg").toString();
    	        String fileExtension = jsonObject.get("fileExtension").toString();
    	        
    	        //String empoleImgStr = targetImg.substring(3, targetImg.length() - 1);

    	        // 이스케이프 문자 제거
    	        //empoleImgStr = empoleImgStr.replace("\\", "");

    	        // Base64 디코딩
    	        byte[] empoleImg = Base64.getDecoder().decode(targetImg);
    	        
    	        //입력 바이트 배열의 길이가 4의 배수가 아닌 경우 패딩 추가
    	        /*int paddingLength = 4 - (empoleImg.length % 4);
    	        if (paddingLength < 4) {
    	            byte[] paddedEmpoleImg = new byte[empoleImg.length + paddingLength];
    	            System.arraycopy(empoleImg, 0, paddedEmpoleImg, 0, empoleImg.length);
    	            empoleImg = paddedEmpoleImg;
    	        }*/
    	        
    	        String filePath = EMPOLE_FILE_UPLOAD_PATH + File.separator + params.getTargetDate();
    	        File fileDir = new File(filePath);
    	        // root directory 없으면 생성
    	    	if (!fileDir.exists()) {
    	    		fileDir.mkdirs(); //폴더 생성합니다.
    	    	}   
    	    	    	        
    	    	String fileNameWithoutExtension = FilenameUtils.removeExtension(fileList[i].getName());
                File lOutFile = new File(EMPOLE_FILE_UPLOAD_PATH + File.separator + params.getTargetDate() + File.separator + fileNameWithoutExtension + "." + fileExtension);
                FileOutputStream outputStream = new FileOutputStream(lOutFile);
                outputStream.write(empoleImg);
                outputStream.close();            
                LOGGER.info("파일 저장 완료");     	        
    	        LOGGER.info("JSON 파일을 성공적으로 읽고 JSON 객체로 변환했습니다.");
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        resultList = null;
    	    }          	
        }
        
        return resultList;
	}

}
