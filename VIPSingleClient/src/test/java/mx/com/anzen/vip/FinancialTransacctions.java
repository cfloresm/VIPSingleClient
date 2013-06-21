package mx.com.anzen.vip;

import java.net.URL;

import javax.xml.namespace.QName;

import com.verisign.schemas.vip._2011._04.vipuserservices.EvaluateNonMonetaryTransactionRiskRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.NonMonetaryTransactionType;
import com.verisign.schemas.vip._2011._04.vipuserservices.auth.AuthenticationService;
import com.verisign.schemas.vip._2011._04.vipuserservices.auth.AuthenticationServicePort;
import com.verisign.schemas.vip._2011._04.vipuserservices.query.QueryService;

public class FinancialTransacctions {
	  static final QName QUERY_SERVICE =  new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/query", "QueryService");
	  static final QName MGMT_SERVICE =  new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/mgmt", "ManagementService");
	  static final QName AUTH_SERVICE =  new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/auth", "AuthenticationService");
	  static final String query_wsdl = "vipuserservices-query-1.1.wsdl";
	  static final String mgmt_wsdl = "vipuserservices-mgmt-1.1.wsdl";
	  static final String auth_wsdl = "vipuserservices-auth-1.1.wsdl";
	  static String certFile = "/home/carlos/Workspace/VIP/vip_cert.p12";
	  static String password = "s3cr37o123";

	  static {
		    try {
			      //System.setProperty("javax.net.debug", "ssl");
			      System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
			      System.setProperty("javax.net.ssl.keyStore", certFile);
			      System.setProperty("javax.net.ssl.keyStorePassword", password);
			    } catch (Exception localException){
			      System.out.println("Exception : " + localException);
			    }
	 }
	  
	public static void main(String []args){
	    URL wsdlURL = null;
	    wsdlURL = FinancialTransacctions.class.getClassLoader().getResource(auth_wsdl);
	    
	    AuthenticationService service =  new AuthenticationService(wsdlURL, AUTH_SERVICE);
	    AuthenticationServicePort authenticationPort = service.getAuthenticationServicePort();
	    
	    EvaluateNonMonetaryTransactionRiskRequestType evaluateNonMonetaryTransactionRiskRequest =  new  EvaluateNonMonetaryTransactionRiskRequestType();
	    evaluateNonMonetaryTransactionRiskRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
	    evaluateNonMonetaryTransactionRiskRequest.setUserId("CodeUser1");
	    NonMonetaryTransactionType monetaryTransaction =  new NonMonetaryTransactionType();
		evaluateNonMonetaryTransactionRiskRequest.setNonMonetaryTransaction(monetaryTransaction );
	    
		authenticationPort.evaluateNonMonetaryTransactionRisk(evaluateNonMonetaryTransactionRiskRequest );
	    
	}
	  
	public static String bytesToHex(byte[] bytes) {
	    char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
