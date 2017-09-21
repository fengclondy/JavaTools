package cfca.mobile;

import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.util.EncryptUtil;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class SymDecrypt
{
  static byte[] DES3_CBC_Decrypt(byte[] ivData, byte[] keyData, byte[] cipherText)
  {
    SecretKeySpec key3Des = new SecretKeySpec(keyData, "DESede");
    IvParameterSpec IvSpec = new IvParameterSpec(ivData);
    byte[] plainText = null;
    try {
      Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      cipher.init(2, key3Des, IvSpec);
      plainText = cipher.doFinal(cipherText);
    } catch (Exception e) {
      System.err.println("DecryptCipher Exception:- " + e);
    }
    return plainText;
  }

  static byte[] SM4_CBC_Decrypt(byte[] ivData, byte[] keyData, byte[] cipherText) {
    try {
      return EncryptUtil.decryptMessageBySM4(cipherText, ivData, keyData);
    } catch (PKIException e) {
      System.err.println("DecryptCipher Exception:- " + e);
    }
    return null;
  }
}