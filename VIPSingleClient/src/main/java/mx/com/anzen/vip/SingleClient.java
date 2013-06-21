package mx.com.anzen.vip;

import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import com.verisign.schemas.vip._2011._04.vipuserservices.AddCredentialRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.AddCredentialResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.AuthenticateUserRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.AuthenticateUserResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.CreateUserRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.CreateUserResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.CredentialDetailType;
import com.verisign.schemas.vip._2011._04.vipuserservices.CredentialTypeEnum;
import com.verisign.schemas.vip._2011._04.vipuserservices.DeleteUserRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.DeleteUserResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.EvaluateRiskRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.EvaluateRiskResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.GetServerTimeRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.GetServerTimeResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.KeyValuePairType;
import com.verisign.schemas.vip._2011._04.vipuserservices.OtpAuthDataType;
import com.verisign.schemas.vip._2011._04.vipuserservices.SetTemporaryPasswordRequestType;
import com.verisign.schemas.vip._2011._04.vipuserservices.SetTemporaryPasswordResponseType;
import com.verisign.schemas.vip._2011._04.vipuserservices.auth.AuthenticationService;
import com.verisign.schemas.vip._2011._04.vipuserservices.auth.AuthenticationServicePort;
import com.verisign.schemas.vip._2011._04.vipuserservices.mgmt.ManagementService;
import com.verisign.schemas.vip._2011._04.vipuserservices.mgmt.ManagementServicePort;
import com.verisign.schemas.vip._2011._04.vipuserservices.query.QueryService;
import com.verisign.schemas.vip._2011._04.vipuserservices.query.QueryServicePort;

public final class SingleClient {

    static final QName QUERY_SERVICE = new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/query","QueryService");
    static final QName MGMT_SERVICE = new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/mgmt","ManagementService");
    static final QName AUTH_SERVICE = new QName("http://schemas.verisign.com/vip/2011/04/vipuserservices/auth",	"AuthenticationService");
    static final String query_wsdl = "vipuserservices-query-1.1.wsdl";
    static final String mgmt_wsdl = "vipuserservices-mgmt-1.1.wsdl";
    static final String auth_wsdl = "vipuserservices-auth-1.1.wsdl";
    static String certFile = "/home/carlos/git/VipSingleClient/VIPSingleClient/src/main/resources/vip_cert.p12";
    static String password = "s3cr37o123";

    private AuthenticationService authenticationService = null;    	
    private QueryService queryService = null;    
    private ManagementService mgmtService = null;

    static {
    	try {
    	    System.setProperty("javax.net.debug", "ssl");
            System.setProperty("javax.net.ssl.keyStore", certFile);
            System.setProperty("javax.net.ssl.keyStorePassword", password);
            System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
    	} catch (Exception localException) {
    		System.out.println("Exception : " + localException);
    	}
    }
    
    public SingleClient() {
    	URL authwsdlURL = null;
    	URL mgmtwsdlURL = null;
    	URL querywsdlURL = null;
    	
    	authwsdlURL = SingleClient.class.getClassLoader().getResource(auth_wsdl);
    	mgmtwsdlURL = SingleClient.class.getClassLoader().getResource(mgmt_wsdl);
    	querywsdlURL = SingleClient.class.getClassLoader().getResource(query_wsdl);

    	
    	authenticationService = new AuthenticationService(authwsdlURL,AUTH_SERVICE);    	
	queryService = new QueryService(querywsdlURL, QUERY_SERVICE);    
    	mgmtService = new ManagementService(mgmtwsdlURL, MGMT_SERVICE);
    
    }
    
    public static void main(String[] args) throws Exception {
    	
    	new SingleClient().authenticate();
        // getTime();
    	// createUser();
    	// deleteUser();
    	// addCredential();
    	// getTemporaryPassword();
    	// authenticate();
    	// evaluateRisk();
    
    }
    
    public  void riskAutenticate() {
    
    	AuthenticationServicePort authenticationPort = authenticationService.getAuthenticationServicePort();
    
    	EvaluateRiskRequestType request = new EvaluateRiskRequestType();
    	// RequestId - event ID provided by your web application
    	request.setRequestId(("rqstId" + System.currentTimeMillis()));
    	// UserId - unique user ID provided by your web application"
    	request.setUserId("CodeUser1");
    	// UserAgent - user agent collected from the browser
    	String userAgent = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36";
    	request.setUserAgent(userAgent);
    	// IAAuthData - device data collected from the browser (result from
    	// IaDfp.readFingerprint() in JavaScript)
    	String fingerprint = "_v02MyUgNGgYOi88OTk0emB7ZXV9DWRkbnUZPDsgLXU8Y21jfHUUJSU5MAIwNx48IXpgZmJ7ZmN1fR4dARgZeXU5PD4wdRIwNj46fHUWPSc6ODB6Z2J7ZXtkYWBme2RkZXUGNDM0Jzx6YGZie2ZjKRk8OyAtdTxjbWNzMyUmNmhnYSlkZmNjKWJjbSlkZmNjKWJhZnMzJSYiaDk8NyUwJTM5NCY9JTk0LDAneyY6KTw7ITAnOzQ5eCcwODohPDsyeCM8MCIwJyk5PDciPDEwIzw7MDYxODQxNCUhMCd7JjopOTw3JSUyOjoyOTA7NDY5JTkgMjw7Nj0nOjgweyY6KTk8NyUxM3smOik5PDc7JTIhJTpmMTQgITolOSAyPDt7JjopOTw3OyUyOjoyOTAhNDk+eyY6KTk8NzslOmQxeyY6KTk8NzY8Ozs0ODo7eDcnOiImMCd4JTkgMjw7eyY6KTk8NzM5NCY9JTk0LDAneyY6KTw2MDEhMDQlOSAyPDt7JjopOTw3ITohMDh4Njo7MHglOSAyPDt7JjopOTw3ITohMDh4MjgleCU5IDI8O3smOik5PDchOiEwOHg4IDk5LHglOSAyPDt7JjopOTw3ITohMDh4OzQnJzoiJiU0NjB4JTkgMjw7eyY6czMlJTxoJiIzaGRke2J7YmVle2dlZik/NCM0aGR7Yntle2BlKSM5Nmhme2N7ZntlczMlIS9oeGBzMyU5O2gwO3gABikpKXMzJTYlaGRkZGRlZGRkZGRkZGRkZGRkZGRkZGRkZGRkZGRkZGRkZGRkZWRkZGRlZGRkZGRkZGRkZGRkZGRkZGRkZGRkZGRkZWRlczMlIzZoczMlIyZoZ3MzJSEmaHMzJSEyaA==";
    	request.setIAAuthData(fingerprint);
    	// IP - Internet Protocol address of the browser that sent the sign-in
    	// request
    	String ip = "148.240.64.125";
    	request.setIp(ip);
    
    	EvaluateRiskResponseType response = authenticationPort.evaluateRisk(request);
    
    	System.out.println("-----------------------------------------------------------");
    	System.out.println("ID Request" + response.getRequestId());
    	System.out.println("Status " + bytesToHex(response.getStatus()));
    	System.out.println("Is Risky " + response.isRisky());
    	System.out.println("Score " + response.getRiskScore());
    	System.out.println("Policy Version " + response.getPolicyVersion());
    	System.out.println("ID Event " + response.getEventId());
    	Iterator<KeyValuePairType> iterator = response.getKeyValuePairs().iterator();
    
    	while (iterator.hasNext()) {
    		KeyValuePairType item = iterator.next();
    		System.out.println("Key   " + item.getKey());
    		System.out.println("Value " + item.getValue());
    	}
    
    	System.out.println("-----------------------------------------------------------");
    
    }
    
    public  void getTime() {

	QueryServicePort port = queryService.getQueryServicePort();

	GetServerTimeRequestType getServerTimeRequest = new GetServerTimeRequestType();
	getServerTimeRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
	GetServerTimeResponseType response = port.getServerTime(getServerTimeRequest);
    
    	System.out.println(bytesToHex(response.getStatus()));
    	System.out.println(response.getStatusMessage());
    	System.out.println(response.getTimestamp());
    	System.out.println(response.getDetailMessage());
    }
    
    private  void authenticate() {
    
    	URL wsdlURL = null;
    	wsdlURL = QueryService.class.getClassLoader().getResource(auth_wsdl);
    
    	AuthenticationService service = new AuthenticationService(wsdlURL,AUTH_SERVICE);
    	AuthenticationServicePort authenticationPort = service.getAuthenticationServicePort();
    
    	AuthenticateUserRequestType authenticateUserRequest = new AuthenticateUserRequestType();
    	authenticateUserRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
    	authenticateUserRequest.setUserId("CodeUser1");
    	OtpAuthDataType otpAuthData = new OtpAuthDataType();
    	otpAuthData.setOtp(this.getTemporaryPassword());
    	authenticateUserRequest.setOtpAuthData(otpAuthData);
    
    	AuthenticateUserResponseType responseAuthenticate = authenticationPort.authenticateUser(authenticateUserRequest);
    	System.out.println(bytesToHex(responseAuthenticate.getStatus()));
    	System.out.println(responseAuthenticate.getStatusMessage());
    
    }
    
    private String  getTemporaryPassword() {
    	URL wsdlURL = null;
    	wsdlURL = QueryService.class.getClassLoader().getResource(mgmt_wsdl);
    
    	ManagementService service = new ManagementService(wsdlURL, MGMT_SERVICE);
    	ManagementServicePort mgmtPort = service.getManagementServicePort();
    
    	SetTemporaryPasswordRequestType setTemporaryPasswordRequest = new SetTemporaryPasswordRequestType();
    	setTemporaryPasswordRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
    	setTemporaryPasswordRequest.setUserId("CodeUser1");
    	mgmtPort.setTemporaryPassword(setTemporaryPasswordRequest);
    
    	SetTemporaryPasswordResponseType response = mgmtPort.setTemporaryPassword(setTemporaryPasswordRequest);
    	System.out.println(bytesToHex(response.getStatus()));
    	System.out.println(response.getStatusMessage());
    	System.out.println(response.getTemporaryPassword());
    
    	return response.getTemporaryPassword();
    }
    
    public  void createUser() {
    	URL wsdlURL = null;
    	wsdlURL = QueryService.class.getClassLoader().getResource(mgmt_wsdl);
    
    	ManagementService mgmtService = new ManagementService(wsdlURL,MGMT_SERVICE);
    	ManagementServicePort authPort = mgmtService.getManagementServicePort();
    
    	CreateUserRequestType createUserRequest = new CreateUserRequestType();
    	createUserRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
    	createUserRequest.setUserId("CodeUser1");
    	// createUserRequest.setPin("CodeUser1");
    
    	CreateUserResponseType response = authPort.createUser(createUserRequest);
    
    	System.out.println(response.getRequestId());
    	System.out.println(bytesToHex(response.getStatus()));
    	System.out.println(response.getStatusMessage());
    
    }
    
    public static void deleteUser() {
    	URL wsdlURL = null;
    	wsdlURL = QueryService.class.getClassLoader().getResource(mgmt_wsdl);
    
    	ManagementService mgmtService = new ManagementService(wsdlURL,MGMT_SERVICE);
    	ManagementServicePort authPort = mgmtService.getManagementServicePort();
    
    	DeleteUserRequestType deleteUserRequest = new DeleteUserRequestType();
    	deleteUserRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
    	deleteUserRequest.setUserId("CodeUser1");
    	DeleteUserResponseType deleteResponse = authPort.deleteUser(deleteUserRequest);
    
    	System.out.println(deleteResponse.getStatusMessage());
    	System.out.println(bytesToHex(deleteResponse.getStatus()));
    }
    
    public static void addCredential() {
    	URL wsdlURL = null;
    	wsdlURL = QueryService.class.getClassLoader().getResource(mgmt_wsdl);
    
    	ManagementService mgmtService = new ManagementService(wsdlURL,MGMT_SERVICE);
    	ManagementServicePort authPort = mgmtService.getManagementServicePort();
    
    	AddCredentialRequestType addCredentialRequest = new AddCredentialRequestType();
    	CredentialDetailType typeCredential = new CredentialDetailType();
    	typeCredential.setCredentialId("VSST37141428");
    	typeCredential.setCredentialType(CredentialTypeEnum.STANDARD_OTP);
    	typeCredential.setFriendlyName("Credential OTP");
    	addCredentialRequest.setCredentialDetail(typeCredential);
    	addCredentialRequest.setRequestId(("rqstId" + System.currentTimeMillis()));
    	addCredentialRequest.setUserId("CodeUser1");
    
    	AddCredentialResponseType responseCredential = authPort.addCredential(addCredentialRequest);
    	System.out.println(bytesToHex(responseCredential.getStatus()));
    	System.out.println(responseCredential.getStatusMessage());
    }
    
    public static String bytesToHex(byte[] bytes) {
    	char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    			'A', 'B', 'C', 'D', 'E', 'F' };
    	char[] hexChars = new char[bytes.length * 2];
    	int v;
    	for (int j = 0; j < bytes.length; j++) {
    		v = bytes[j] & 0xFF;
    		hexChars[j * 2] = hexArray[v >>> 4];
    		hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    	}
    	return new String(hexChars);
    }

}
