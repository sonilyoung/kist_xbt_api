package egovframework.com.adm.learningMgr.dao;

import java.util.List;

import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LearningMgrDAO")
public class LearningMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.learningMgr.dao.LearningMgrDAO";
	
	public List<?> selectModuleList(EduModule params) {
		return (List<?>)selectList(Namespace + ".selectModuleList", params);
	}
	
	public List<?> selectModuleQuestion(EduModule params) {
		return (List<?>)selectList(Namespace + ".selectModuleQuestion", params);
	}
		
	
	public int insertModule(EduModule params) {
		return insert(Namespace + ".insertModule", params);
	}
	
	public int insertModuleQuestion(EduModule params) {
		return insert(Namespace + ".insertModuleQuestion", params);
	}	
	
	public int updateModule(EduModule params) {
		return update(Namespace + ".updateModule", params);
	}	
	
	public int deleteModule(EduModule params) {
		return delete(Namespace + ".deleteModule", params);
	}
	
	public int deleteModuleQuestion(EduModule params) {
		return delete(Namespace + ".deleteModuleQuestion", params);
	}
	
	public EduModule selectModule(EduModule params) {
		return selectOne(Namespace + ".selectModule", params);
	}	

	public List<?> getXrayPointList(XrayPoint params) {
		return (List<?>)selectList(Namespace + ".getXrayPointList", params);
	}
	
	public List<?> getXrayPointDetailList(XrayPointDetail params) {
		return (List<?>)selectList(Namespace + ".getXrayPointDetailList", params);
	}
	
	public List<?> getEduTypeList(EduType params) {
		return (List<?>)selectList(Namespace + ".getEduTypeList", params);
	}			
	
	public EduModule selectXrayModuleContents(EduModule params) {
		return selectOne(Namespace + ".selectXrayModuleContents", params);
	}		
	
	public List<?> selectModuleXrayPopList(EduModule params) {
		return (List<?>)selectList(Namespace + ".selectModuleXrayPopList", params);
	}	
}
