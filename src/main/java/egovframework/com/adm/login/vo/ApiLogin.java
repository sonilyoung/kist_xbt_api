package egovframework.com.adm.login.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ApiLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;                     //사용자ID        
    private String regDt;                      //등록일        
}
