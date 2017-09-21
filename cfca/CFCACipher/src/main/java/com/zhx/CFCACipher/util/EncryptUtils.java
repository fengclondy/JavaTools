package com.zhx.CFCACipher.util;

import cfca.sadk.algorithm.common.Mechanism;
import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.algorithm.sm2.SM2PrivateKey;
import cfca.sadk.lib.crypto.Session;
import cfca.sadk.util.Base64;
import cfca.sadk.util.EncryptUtil;
import cfca.sadk.util.KeyUtil;

import java.security.Key;

/**
 * Created by admin on 2017/9/4.
 */
public class EncryptUtils extends EncryptUtil {

    /**
     * @deprecated
     */
    public static byte[] decryptMessageBySM2(byte[] encryptedData, byte[] sm2FileData, String sm2FilePwd, Session session) throws PKIException {
        SM2PrivateKey sm2PrivKey = KeyUtil.getPrivateKeyFromSM2(sm2FileData, sm2FilePwd);
        Mechanism mechanism = new Mechanism("SM2");
        return decrypt(mechanism, sm2PrivKey, encryptedData, session);
    }

    public static byte[] decrypt(Mechanism encryptAlg, Key key, byte[] encryptData, Session session) throws PKIException {
        try {
            return session.decrypt(encryptAlg, key, Base64.decode(encryptData));
        } catch (PKIException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new PKIException("Encrypt Failure", var6);
        }
    }
}
