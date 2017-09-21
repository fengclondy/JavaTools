package cfca.mobile;

import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.util.EncryptUtil;
import com.zhx.CFCACipher.util.EncryptUtils;
import org.springframework.util.Base64Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Enumeration;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

final class AsymDecrypt
{
  static byte[] RSA_Decrypt(String pfxFileName, String pfxPassword, byte[] encryptedData)
  {
    KeyStore keyStore = null;
    byte[] plainText = null;
    InputStream input = null;
    try {
      input = new FileInputStream(pfxFileName);
      keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(input, pfxPassword.toCharArray());
      String alias = "";
      Enumeration aliases = keyStore.aliases();
      while (aliases.hasMoreElements()) {
        alias = (String)aliases.nextElement();
      }

      Key key = keyStore.getKey(alias, pfxPassword.toCharArray());
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key.getEncoded());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
      System.out.println("Key privateKey============:" + privateKey);
      Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      rsaCipher.init(2, privateKey);
      plainText = rsaCipher.doFinal(encryptedData);
    } catch (Exception e) {
      System.err.println("Exception:- " + e);
    } finally {
      if (input != null)
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("Close error");
        }
    }
    return plainText;
  }

  static byte[] SM2_Decrypt(String pfxFileName, String pfxPassword, byte[] encryptedData)
  {
    byte[] plainText = null;
    try {
//      plainText = EncryptUtil.decryptMessageBySM2(encryptedData, pfxFileName, pfxPassword, BOCServerDecryption.session); // 读文件
      plainText = EncryptUtils.decryptMessageBySM2(encryptedData, pfxFileName.getBytes(), pfxPassword, BOCServerDecryption.session); // 读sm2密码字符串
    } catch (PKIException e) {
      e.printStackTrace();
    }
    return plainText;
  }

}