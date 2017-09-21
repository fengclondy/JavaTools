package com.zhx.CFCACipher.server;

import cfca.mobile.BOCServerDecryption;


/**
 * Created by admin on 2017/9/1.
 */

public class CFCAServerDecryption{

    private static String sm2PrivateKey = "MIICLgIBATBHBgoqgRzPVQYBBAIBBgcqgRzPVQFoBDBmi2I0B0RzwZyZBY/wLzmIBDMdSUoPZpkWwTEn2rCuINp6OR0oKxa6+pGDtjwFdcgwggHeBgoqgRzPVQYBBAIBBIIBzjCCAcowggFuoAMCAQICBRAAAABDMAwGCCqBHM9VAYN1BQAwIzELMAkGA1UEBhMCQ04xFDASBgNVBAoMC0NGQ0EgT0NBODg4MB4XDTEzMDUxNjAzNDY1NloXDTE0MDMxMjAzNDY1NlowJTELMAkGA1UEBhMCQ04xFjAUBgNVBAMMDXNtMmRvdWJsZWNlcnQwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASJd/fd5HhDC6sHaFisyxPz/6nZwOu2N7xM0iM04miNdqYnl/2HVgXNDjqU0++6MTmUXJTljnPJ4akAhapGhGpio4GKMIGHMB8GA1UdIwQYMBaAFACQCuvvo4oRDRbCWCXtDieQ7RgnMDgGA1UdHwQxMC8wLaAroCmGJ2h0dHA6Ly8xOTIuMTY4LjEyMC4xMjcvY3JsL1NNMi9jcmwxLmNybDALBgNVHQ8EBAMCBDAwHQYDVR0OBBYEFJu5LVzOme1SdrKWMBrgFTb52Lt7MAwGCCqBHM9VAYN1BQADSAAwRQIgK/XTEOXLlws6vrPBBpfg55Ka2EXVsnnbIMqLEdttp4oCIQDnooBfDh/ka0yTcW83phYskvLKeHu+XMRZx4oa+irxvA==";

    private static String sm2Password = "111111";

    private static String serverRandom = "MTIzNDU2Nzg5MDk4NzY1NA==";



    /**
     * 提供对外服务
     * @param encodeData        加密数据, base64编码格式
     * @param clientRandom      加密客户端随机数, base64编码格式
     * @return
     */
    public static String getPassword(String encodeData, String clientRandom) {

        String[] args = {sm2PrivateKey, sm2Password, clientRandom, serverRandom, encodeData, "0"};
        String password = BOCServerDecryption.decrypt(args);
        System.out.println("明文数据:" + password);

        return password;
    }

    public static void main(String[] args) {
//        String sm2_data = "a2J6g+Gx00NIfLsOtgYLiw==";
//        String sm2_clientRandom = "ExP0tr0WaQmp0SVEzyHGUtCeesWt9M5aR92pY7I1q+jkt8luTv8SRA3cNdEbatLHG+JaLTdID+sTvXGrl0JdiMU6nJozsLdiFsEMnEakBcFKiY9I8kZQWcsIW1FFlVuNdHJj4i5C6bfMC8xgHa6UXw==";

        String sm2_data = args[0];
        String sm2_clientRandom = args[1];
        String password = getPassword(sm2_data, sm2_clientRandom);
        System.out.println(password);
    }

}
