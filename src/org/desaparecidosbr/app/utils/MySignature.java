
package org.desaparecidosbr.app.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

public class MySignature {

   // private final static String publicKey = "3441df0babc2a2dda551d7cd39fb235bc4e09cd1e4556bf261bb49188f548348";
    private final static String privateKey = MyConfig.PRIVATE_KEY;
    private final static String username = MyConfig.USER;
    private final static String password = MyConfig.PASS;
    
    public static Map<String, String> createBasicAuthHeader()
            throws InvalidKeyException, NoSuchAlgorithmException {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        String base64EncodedCredentials =
                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);
        //headerMap.put("X-Public", publicKey);
        headerMap.put("X-Hash", Utility.hmacDigest("", privateKey));
        return headerMap;
    }

}
