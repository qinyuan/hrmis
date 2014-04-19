package qinyuan.hrmis.action.att.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AttListAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession()){
			return SUCCESS;
		}
		changeDept();
		changeDefUser();
		changeStartDate();
		changeEndDate();
		
		String specType=null;
		List<String> hidList=new ArrayList<String>();
		Set<String> set=getParameterNames();
		for(String str:set){
			if(str.charAt(0)=='L' || str.charAt(0)=='A'){
				specType=str;
			}else if(str.startsWith("hid")){
				hidList.add(str);
			}
		}
		if(specType!=null && hidList.size()>0){
			attManager.addSpecByAttList(specType,hidList);
		}
		
		return SUCCESS;
	}
}
