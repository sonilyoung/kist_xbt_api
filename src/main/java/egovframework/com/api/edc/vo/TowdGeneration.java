package egovframework.com.api.edc.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TowdGeneration implements Serializable{
	private static final long serialVersionUID = 2800411363195049766L;
	private String category;
	private String categoryCnt;
	private String fileName;
	private String[] kaistCommand;
	private List<byte[]> towdGenList;
	private List<String> fileNameList;
}
