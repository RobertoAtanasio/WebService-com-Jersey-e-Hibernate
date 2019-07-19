package br.com.devmedia.webservice.jwt;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

public class KeyGenerator {

	public Key generateKey() {
        String keyString = "IkRldk1lZGlhIiBhcMOzcyBTSEEtMjU2IGUgRW5jb2RlQmFzZTY0Ow=="; //"DevMedia" após SHA-256 e EncodeBase64;
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA256");
        return key;
    }
}
