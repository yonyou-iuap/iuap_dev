package formulatest;


public class testRest {
	public static void main(String[] args) {
//	String param = "{\"formula\":\""
//				+ "f->charat(a,2);f1->charat(a,4)"
//				+ "\",\"a\":\"12dwsd\"}";
//	
	
	String param ="{\"formulas\":[\"f1->d1*d2*i1\"],\"variables\":{\"d1\":3,\"d2\":4,\"i1\":6}}";
		RestHttpRequestUtil.doPost(param); 
	// result	{"f1":[72]}
	
		// get test	
	//http://localhost:8080/iuap-formula-service/formula/execute4get?formula={"variables":{"d1":3,"d2":4,"i1":6},"formulas":["f1->d1*d2*i1"]}
   //	http://localhost:8080/iuap-formula-service/formula/execute4get?formula={"variables":{"d1":[3,4,5,6],"d2":[4,7,10,78],"i1":[6,7,8,9]},"formulas":["f1->d1*d2*i1"]}
	}

}
