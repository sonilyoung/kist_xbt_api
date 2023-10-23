package egovframework.com.file.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.api.edc.vo.MpoleData;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.XrayImgContents;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.file.vo.LearningImg;


/**
 * 파일 Storage 관련 Service
 * 
 * @fileName : FileStorageService.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
public interface FileStorageService {
	
	AttachFile createUnitImageFile(String targetName, AttachFile params ,MultipartFile file) throws Exception;

	AttachFile createXrayImageFile(String targetName, AttachFile params ,MultipartFile file) throws Exception;
	
	AttachFile createXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params,MultipartFile file) throws Exception;
	
	AttachFile createKaistXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params, MultipartFile file) throws Exception;
		
    File getFile(AttachFile AttachFile);

    AttachFile createFile(MultipartFile file) throws Exception;

    boolean deleteFile(AttachFile AttachFile);
    
    void getImage(String filePath, HttpServletResponse response) throws Exception;
    
    void ByteToFile(byte[] target, String fileName, String filePath, LearningImg params)throws Exception;
    
    public void fileDeleteAll(String target1, String target2, String option)throws Exception;
    
    JSONObject createMpoleData(MpoleData params) throws Exception;
    
    boolean createMpoleFile(JSONObject params) throws Exception;
    
    public List<JSONObject> selectMpoleData(MpoleData params) throws Exception;
    

}
