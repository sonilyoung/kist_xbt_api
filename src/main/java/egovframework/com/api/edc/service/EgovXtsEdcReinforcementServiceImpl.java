package egovframework.com.api.edc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("egovCbtEdcReinforcementService")
public class EgovXtsEdcReinforcementServiceImpl implements EgovXtsEdcReinforcementService {
	
	//private String url = "http://127.0.0.1:8080/test/arrange_level.do";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcReinforcementServiceImpl.class);
	
	/*
	@Override
	public boolean reinforcementLearningAi() throws Exception {
		
		boolean result = false;
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url(url)//url
				  .get()//request method
				  .build();
				Response response = client.newCall(request).execute();
				
		result = response.isSuccessful();
		response.close();
		
		return result;
	}*/
	
}
