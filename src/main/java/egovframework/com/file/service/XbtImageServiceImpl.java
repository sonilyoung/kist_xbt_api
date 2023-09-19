package egovframework.com.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import egovframework.com.file.vo.LearningImg;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.util.FileReader;
import lombok.extern.log4j.Log4j2;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *		
 *      </pre>
 */
@Log4j2
@Service("xbtImageService")
public class XbtImageServiceImpl implements XbtImageService {

	/*
	 * 				//실사이미지 403
					//정면컬러 101
					//정면무기물 102
					//정면유기물 103
					//정면반전 104
					//정면채도 105
					//정면채도 106
					//정면채도 107
					//정면채도 108
					//정면채도 109
					//정면채도 110

					//정면흑백 111
					//정면흑백무기물 112
					//정면흑백유기물 113
					//정면흑백반전 114
					//정면흑백채도 115
					//정면흑백채도 116
					//정면흑백채도 117
					// 정면흑백채도118
					//정면흑백채도 119
					//정면흑백채도 120

					//측면컬러 201
					//측면무기물 202
					//측면유기물 203
					//측면반전 204
					//측면채도 205
					//측면채도206
					//측면채도207
					//측면채도208
					//측면채도209
					//측면채도210

					//측면흑백211
					//측면흑백무기물212
					//측면흑백유기물213
					//측면흑백반전214
					//측면흑백채도215
					//측면흑백채도216
					//측면흑백채도217
					//측면흑백채도218
					//측면흑백채도219
					//측면흑백채도220
	*/
	



	@Override
	public LearningImg selectAdmAllBagImg(LearningImg params) {
		// TODO Auto-generated method stub
    	String xrayPath = GlobalsProperties.getProperty("kaist.xray.result.img.path");
		String scanId = params.getBagScanId();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		if(fileList[i].getName().contains("101")) {//정면
        			params.setImgFront(fileByte);
        			params.setImgFrontColor(fileByte);
        		}else if(fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);
        		}else if(fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);
        		}else if(fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);
        		}else if(fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);
        		}else if(fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);
        		}else if(fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);
        		}else if(fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);
        		}else if(fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("201")) {//측면
        			params.setImgSide(fileByte);
        			params.setImgSideColor(fileByte);
        		}else if(fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);
        		}else if(fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);
        		}else if(fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);
        		}else if(fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);
        		}else if(fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);
        		}else if(fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);
        		}else if(fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);
        		}else if(fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("403")) {//실사이미지
        			params.setImgReal(fileByte);
        		}else if(fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);
        		}else if(fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		return params;
	}



	@Override
	public LearningImg selectSudoImgRename(LearningImg params) {
		// TODO Auto-generated method stub
    	String xrayPath = GlobalsProperties.getProperty("kaist.xray.result.img.path");
		String scanId = params.getBagScanId();	
        String strDirPath = xrayPath;
        String fileExtention = ".png";
        String fileTargetName = ""; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) {
            String newFileName = "";
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		Path file = Paths.get(fileList[i].toPath().toString());
        		
        		if(fileList[i].getName().contains("101")) {//정면
        			fileTargetName = "101";
        		}else if(fileList[i].getName().contains("102")) {
        			fileTargetName = "102";
        		}else if(fileList[i].getName().contains("103")) {
        			fileTargetName = "103";
        		}else if(fileList[i].getName().contains("104")) {
        			fileTargetName = "104";
        		}else if(fileList[i].getName().contains("105")) {
        			fileTargetName = "105";
        		}else if(fileList[i].getName().contains("106")) {
        			fileTargetName = "106";
        		}else if(fileList[i].getName().contains("107")) {
        			fileTargetName = "107";
        		}else if(fileList[i].getName().contains("108")) {
        			fileTargetName = "108";
        		}else if(fileList[i].getName().contains("109")) {
        			fileTargetName = "109";
        		}else if(fileList[i].getName().contains("110")) {
        			fileTargetName = "110";
        		}else if(fileList[i].getName().contains("111")) {
        			fileTargetName = "111";
        		}else if(fileList[i].getName().contains("112")) {
        			fileTargetName = "112";
        		}else if(fileList[i].getName().contains("113")) {
        			fileTargetName = "113";
        		}else if(fileList[i].getName().contains("114")) {
        			fileTargetName = "114";
        		}else if(fileList[i].getName().contains("115")) {
        			fileTargetName = "115";
        		}else if(fileList[i].getName().contains("116")) {
        			fileTargetName = "116";
        		}else if(fileList[i].getName().contains("117")) {
        			fileTargetName = "117";
        		}else if(fileList[i].getName().contains("118")) {
        			fileTargetName = "118";
        		}else if(fileList[i].getName().contains("119")) {
        			fileTargetName = "119";
        		}else if(fileList[i].getName().contains("120")) {
        			fileTargetName = "120";
        		}else if(fileList[i].getName().contains("201")) {//측면
        			fileTargetName = "201";
        		}else if(fileList[i].getName().contains("202")) {
        			fileTargetName = "202";
        		}else if(fileList[i].getName().contains("203")) {
        			fileTargetName = "203";
        		}else if(fileList[i].getName().contains("204")) {
        			fileTargetName = "204";
        		}else if(fileList[i].getName().contains("205")) {
        			fileTargetName = "205";
        		}else if(fileList[i].getName().contains("206")) {
        			fileTargetName = "206";
        		}else if(fileList[i].getName().contains("207")) {
        			fileTargetName = "207";
        		}else if(fileList[i].getName().contains("208")) {
        			fileTargetName = "208";
        		}else if(fileList[i].getName().contains("209")) {
        			fileTargetName = "209";
        		}else if(fileList[i].getName().contains("210")) {
        			fileTargetName = "210";
        		}else if(fileList[i].getName().contains("211")) {
        			fileTargetName = "211";
        		}else if(fileList[i].getName().contains("212")) {
        			fileTargetName = "212";
        		}else if(fileList[i].getName().contains("213")) {
        			fileTargetName = "213";
        		}else if(fileList[i].getName().contains("214")) {
        			fileTargetName = "214";
        		}else if(fileList[i].getName().contains("215")) {
        			fileTargetName = "215";
        		}else if(fileList[i].getName().contains("216")) {
        			fileTargetName = "216";
        		}else if(fileList[i].getName().contains("217")) {
        			fileTargetName = "217";
        		}else if(fileList[i].getName().contains("218")) {
        			fileTargetName = "218";
        		}else if(fileList[i].getName().contains("219")) {
        			fileTargetName = "219";
        		}else if(fileList[i].getName().contains("220")) {
        			fileTargetName = "220";
        		}else if(fileList[i].getName().contains("403")) {//실사이미지
        			fileTargetName = "403";
        		}else if(fileList[i].getName().contains("401")) {//정면위험물품
        			fileTargetName = "401";
        		}else if(fileList[i].getName().contains("402")) {//측면위험물품
        			fileTargetName = "402";
        		}
        		
                // 파일명 변경
    			newFileName = scanId + "-" + fileTargetName + fileExtention;
                Path renamedFile = file.resolveSibling(newFileName);
                // 변경된 파일 저장
                Files.write(renamedFile, fileByte);        		
        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		return params;
	}	
		
	

}
