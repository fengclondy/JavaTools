package cfca.mobile;

import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.lib.crypto.JCrypto;
import cfca.sadk.lib.crypto.Session;
import java.io.PrintStream;

public class BOCServerDecryption
{
  public static int SM2 = 0;
  public static int RSA = 1;
  static Session session;

  private static void initSession()
    throws PKIException
  {
    String deviceType = "JSOFT_LIB";
    JCrypto.getInstance().initialize("JSOFT_LIB", null);
    session = JCrypto.getInstance().openSession("JSOFT_LIB");
  }

  private static void usage() {
    System.out.println("java -jar SIP.jar <PRI_PATH> <PRI_PASSWORD> <ENCRYPTED_RC> <RS> <ENCRYPTED_DATA> <TYPE>");
    System.out.println("\t<PRI_PATH> \t\t私钥证书文件, 后缀一般为pfx,sm2");
    System.out.println("\t<PRI_PASSWORD> \t\t私钥证书文件密码");
    System.out.println("\t<ENCRYPTED_RC> \t\t加密客户端随机数, base64编码格式");
    System.out.println("\t<RS> \t\t\t服务器端随机数, base64编码格式");
    System.out.println("\t<ENCRYPTED_DATA> \t加密数据, base64编码格式");
    System.out.println("\t<TYPE> \t\t\t加密结果类型, 0表示国密SM2, 1表示RSA");
  }

  public static String getVersion() {
    return "3.0.1.1";
  }

  public static String decrypt(String[] args) {
    String plainPWD;
    byte[] rcData;
    byte[] plainPWDBinary;
    int nArgumentsNumber = args.length;
    if (6 != nArgumentsNumber) {
      usage();
      throw new IllegalArgumentException("Parameters number is illegal");
    }

    String pfxFileName = args[0];
    String pfxPassword = args[1];
    String rcEncryptedDataBase64 = args[2];
    String RSBase64 = args[3];
    String pwdEncryptedDataBase64 = args[4];
    int cipherType = Integer.parseInt(args[5]);

    byte[] rsData = Base64.decode(RSBase64);
    if (rsData.length != 16)
      throw new IllegalArgumentException("Server random binary data length not equal 16");

    if (cipherType == RSA) {
      rcData = AsymDecrypt.RSA_Decrypt(pfxFileName, pfxPassword, Base64.decode(rcEncryptedDataBase64));   // 读文件
      if (rcData == null)
        throw new RuntimeException("RSA Decrypt failed");

      if (rcData.length != 16)
        throw new IllegalArgumentException("Client random binary data length not equal 16");

      byte[] keyData = new byte[24];
      byte[] ivData = new byte[8];
      System.arraycopy(rsData, 0, keyData, 0, 12);
      System.arraycopy(rcData, 0, keyData, 12, 12);
      System.arraycopy(rsData, 12, ivData, 0, 4);
      System.arraycopy(rcData, 12, ivData, 4, 4);

      plainPWDBinary = SymDecrypt.DES3_CBC_Decrypt(ivData, keyData, Base64.decode(pwdEncryptedDataBase64));
      if (plainPWDBinary == null)
        throw new RuntimeException("DES3_CBC Decrypt failed");

      plainPWD = new String(plainPWDBinary);
    } else {
      rcData = AsymDecrypt.SM2_Decrypt(pfxFileName, pfxPassword, rcEncryptedDataBase64.getBytes());
      if (rcData == null)
        throw new RuntimeException("SM2 Decrypt failed");

      if (rcData.length != 16)
        throw new IllegalArgumentException("Client random binary data length not equal 16");

      byte[] ivData = new byte[16];
      byte[] keyData = new byte[16];
      System.arraycopy(rsData, 0, keyData, 0, 8);
      System.arraycopy(rcData, 0, keyData, 8, 8);
      System.arraycopy(rsData, 8, ivData, 0, 8);
      System.arraycopy(rcData, 8, ivData, 8, 8);
      plainPWDBinary = SymDecrypt.SM4_CBC_Decrypt(ivData, keyData, pwdEncryptedDataBase64.getBytes());
      if (plainPWDBinary == null)
        throw new RuntimeException("SM4_CBC Decrypt failed");

      plainPWD = new String(plainPWDBinary);
    }
    return plainPWD;
  }

  public static void main(String[] args)
  {
    System.out.println("Password:" + decrypt(args));
  }

  static
  {
    try
    {
      initSession();
    } catch (PKIException e) {
      throw new IllegalStateException("PKIException: " + e.getMessage());
    }
  }
}