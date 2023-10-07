package egovframework.com.api.edc.vo;

import java.util.List;

import lombok.Data;

@Data
public class MpoleData {
	private String unitGroupCd;//물품그룹코드 (카테고리)
	private String unitName;//물품명
	private String empoleImg;//바이너리이미지
	private String fileSize;//파일사이즈
	private String fileExtention;//파일확장자
	private List<MpoleBoundingData> boundingData; //물품바운딩데이터
}
