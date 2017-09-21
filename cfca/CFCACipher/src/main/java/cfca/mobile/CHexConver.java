package cfca.mobile;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class CHexConver
{
  private static String hexString = "0123456789ABCDEF";

  public static String str2HexStr(String str)
  {
    char[] chars = hexString.toCharArray();
    StringBuilder sb = new StringBuilder("");
    byte[] bs = str.getBytes();

    for (int i = 0; i < bs.length; ++i)
    {
      int bit = (bs[i] & 0xF0) >> 4;
      sb.append(chars[bit]);
      bit = bs[i] & 0xF;
      sb.append(chars[bit]);
      sb.append(' ');
    }
    return sb.toString().trim();
  }

  public static byte[] hexStr2ByteArray(String hexStr)
  {
    hexStr = hexStr.toUpperCase(Locale.getDefault());
    char[] hexs = hexStr.toCharArray();
    byte[] bytes = new byte[hexStr.length() / 2];

    for (int i = 0; i < bytes.length; ++i)
    {
      int n = hexString.indexOf(hexs[(2 * i)]) * 16;
      n += hexString.indexOf(hexs[(2 * i + 1)]);
      bytes[i] = (byte)(n & 0xFF);
    }
    return bytes;
  }

  public static String byte2HexStr(byte[] b)
  {
    String stmp = "";
    StringBuilder sb = new StringBuilder("");
    for (int n = 0; n < b.length; ++n)
    {
      stmp = Integer.toHexString(b[n] & 0xFF);
      sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
      sb.append(" ");
    }
    return sb.toString().trim();
  }

  public static byte[] hexStr2Bytes(String src)
  {
    int m = 0; int n = 0;
    int l = src.length() / 2;
    System.out.println(l);
    byte[] ret = new byte[l];
    for (int i = 0; i < l; ++i)
    {
      m = i * 2 + 1;
      n = m + 1;
      ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)).byteValue();
    }
    return ret;
  }

  public static String strToUnicode(String strText)
    throws Exception
  {
    StringBuilder str = new StringBuilder();

    for (int i = 0; i < strText.length(); ++i)
    {
      char c = strText.charAt(i);
      int intAsc = c;
      String strHex = Integer.toHexString(intAsc);
      if (intAsc > 128)
        str.append("\\u" + strHex);
      else
        str.append("\\u00" + strHex);
    }
    return str.toString();
  }

  public static String unicodeToString(String hex)
  {
    int t = hex.length() / 6;
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < t; ++i)
    {
      String s = hex.substring(i * 6, (i + 1) * 6);

      String s1 = s.substring(2, 4) + "00";

      String s2 = s.substring(4);

      int n = Integer.valueOf(s1, 16).intValue() + Integer.valueOf(s2, 16).intValue();

      char[] chars = Character.toChars(n);
      str.append(new String(chars));
    }
    return str.toString();
  }

  public static String encode(String str)
  {
    byte[] bytes = str.getBytes();
    StringBuilder sb = new StringBuilder(bytes.length * 2);

    for (int i = 0; i < bytes.length; ++i)
    {
      sb.append(hexString.charAt((bytes[i] & 0xF0) >> 4));
      sb.append(hexString.charAt((bytes[i] & 0xF) >> 0));
    }
    return sb.toString();
  }

  public static String decode(String bytes)
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);

    for (int i = 0; i < bytes.length(); i += 2)
      baos.write(hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)));
    return new String(baos.toByteArray());
  }
}