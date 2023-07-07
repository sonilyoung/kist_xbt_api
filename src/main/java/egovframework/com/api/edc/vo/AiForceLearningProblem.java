package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class AiForceLearningProblem{
	private Long progressNo;
	private Long moduleId;
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String userId;
	private String bagScanId;
	private String unitId;
	private int actionDiv;
	private String userActionDiv;
	private String answerYn;
	private int gainScore;
	private String endYn;
	private String insertDate;
	private String eduType;
	private String studyLvl;
	private String actionDivName;
	private String userActionDivName;
	private String languageCode;
	private String unitName;
	private String openYn;
	private String passYn;
	private int trySeq;
	private int questionCnt;
	//private byte[] imgReal;//실사
	//private byte[] imgFront;//정면
	//private byte[] imgSide;//측면	
}
