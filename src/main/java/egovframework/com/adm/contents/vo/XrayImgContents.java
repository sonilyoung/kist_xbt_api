package egovframework.com.adm.contents.vo;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.common.vo.LearningImg;
import lombok.Data;

@Data
public class XrayImgContents{
	private Long bagScanNo;
	private String bagScanId;
	private int studyLvl;
	private byte[] imgReal;
	private byte[] imgSide;
	private byte[] imgFront;
	private byte[] imgFrontCollar;
	private byte[] imgFrontOrganism;
	private byte[] imgFrontCollarOutline;
	private byte[] imgFrontCollarReversal;
	private byte[] imgFrontCollarBwRate1;
	private byte[] imgFrontCollarBwRate2;
	private byte[] imgFrontCollarBwRate3;
	private byte[] imgFrontCollarBwRate4;
	private byte[] imgFrontCollarBwRate5;
	private byte[] imgFrontCollarBwRate6;
	private byte[] imgFrontBw;
	private byte[] imgFrontMinerals;
	private byte[] imgFrontBwOutline;
	private byte[] imgFrontBwReversal;
	private byte[] imgFrontBwBwRate1;
	private byte[] imgFrontBwBwRate2;
	private byte[] imgFrontBwBwRate3;
	private byte[] imgFrontBwBwRate4;
	private byte[] imgFrontBwBwRate5;
	private byte[] imgFrontBwBwRate6;
	private MultipartFile mimgReal;
	private MultipartFile mimgSide;
	private MultipartFile mimgFront;
	private MultipartFile mimgFrontCollar;
	private MultipartFile mimgFrontOrganism;
	private MultipartFile mimgFrontCollarOutline;
	private MultipartFile mimgFrontCollarReversal;
	private MultipartFile mimgFrontCollarBwRate1;
	private MultipartFile mimgFrontCollarBwRate2;
	private MultipartFile mimgFrontCollarBwRate3;
	private MultipartFile mimgFrontCollarBwRate4;
	private MultipartFile mimgFrontCollarBwRate5;
	private MultipartFile mimgFrontCollarBwRate6;
	private MultipartFile mimgFrontBw;
	private MultipartFile mimgFrontMinerals;
	private MultipartFile mimgFrontBwOutline;
	private MultipartFile mimgFrontBwReversal;
	private MultipartFile mimgFrontBwBwRate1;
	private MultipartFile mimgFrontBwBwRate2;
	private MultipartFile mimgFrontBwBwRate3;
	private MultipartFile mimgFrontBwBwRate4;
	private MultipartFile mimgFrontBwBwRate5;
	private MultipartFile mimgFrontBwBwRate6;
	
	private byte[] imgSideCollar;
	private byte[] imgSideOrganism;
	private byte[] imgSideCollarOutline;
	private byte[] imgSideCollarReversal;
	private byte[] imgSideCollarBwRate1;
	private byte[] imgSideCollarBwRate2;
	private byte[] imgSideCollarBwRate3;
	private byte[] imgSideCollarBwRate4;
	private byte[] imgSideCollarBwRate5;
	private byte[] imgSideCollarBwRate6;
	private byte[] imgSideBw;
	private byte[] imgSideMinerals;
	private byte[] imgSideBwOutline;
	private byte[] imgSideBwReversal;
	private byte[] imgSideBwBwRate1;
	private byte[] imgSideBwBwRate2;
	private byte[] imgSideBwBwRate3;
	private byte[] imgSideBwBwRate4;
	private byte[] imgSideBwBwRate5;
	private byte[] imgSideBwBwRate6;
	private MultipartFile mimgSideCollar;
	private MultipartFile mimgSideOrganism;
	private MultipartFile mimgSideCollarOutline;
	private MultipartFile mimgSideCollarReversal;
	private MultipartFile mimgSideCollarBwRate1;
	private MultipartFile mimgSideCollarBwRate2;
	private MultipartFile mimgSideCollarBwRate3;
	private MultipartFile mimgSideCollarBwRate4;
	private MultipartFile mimgSideCollarBwRate5;
	private MultipartFile mimgSideCollarBwRate6;
	private MultipartFile mimgSideBw;
	private MultipartFile mimgSideMinerals;
	private MultipartFile mimgSideBwOutline;
	private MultipartFile mimgSideBwReversal;
	private MultipartFile mimgSideBwBwRate1;
	private MultipartFile mimgSideBwBwRate2;
	private MultipartFile mimgSideBwBwRate3;
	private MultipartFile mimgSideBwBwRate4;
	private MultipartFile mimgSideBwBwRate5;
	private MultipartFile mimgSideBwBwRate6;	
	
	private String insertId;
	private String insertDate;
	private String updateId;	
	private String updateDate;
	
	private LearningImg resultImg;
}
