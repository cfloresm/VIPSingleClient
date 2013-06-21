package mx.com.anzen.vip;

import java.net.URL;

import javax.xml.namespace.QName;

import com.verisign.schemas.vip._2011._04.vipuserservices.SetTemporaryPasswordRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.SetTemporaryPasswordResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.mgmt.ManagementService;
import com.verisign.schemas.vip._2011._04.vipuserservices.mgmt.ManagementServicePort;
import com.verisign.schemas.vip._2011._04.vipuserservices.query.QueryService;

public class TemporaryOTP {

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
	  
	  public static void main(String[] args) {
			    URL wsdlURL = null;
			    wsdlURL = QueryService.class.getClassLoader().getResource(mgmt_wsdl);
			    
			    ManagementService service =  new ManagementService(wsdlURL, MGMT_SERVICE);
			    ManagementServicePort mgmtPort = service.getManagementServicePort();
			    
			    SetTemporaryPasswordRequestType setTemporaryPasswordRequest =  new SetTemporaryPasswordRequestType();
			    setTemporaryPasswordRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
			    setTemporaryPasswordRequest.setUserId("CodeUser1");
				mgmtPort.setTemporaryPassword(setTemporaryPasswordRequest );
			    
				SetTemporaryPasswordResponseType response = mgmtPort.setTemporaryPassword(setTemporaryPasswordRequest);
				System.out.println(bytesToHex(response.getStatus()));
				System.out.println(response.getStatusMessage());
				System.out.println(response.getTemporaryPassword());
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
