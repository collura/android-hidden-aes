package com.code.collura.decrypter.crypto;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.code.collura.decrypter._base.MyApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;



public class CriptografiaService {
    private Builder mBuilder;
    private static String key;
    private static String texto;
    Toast t =  MyApplication.longToast;

    public CriptografiaService() {}

    private CriptografiaService( Builder builder ) {
        mBuilder = builder;
    }

    public static CriptografiaService getDefault( RequestCriptografia model ) {
        key = model.getKey();
        texto = model.getTexto();
        byte[] iv = new byte[16];
        try {
            return Builder.getDefaultBuilder().build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RetornoCriptografia encrypt() {
        try {
            SecretKey secretKey = getSecretKey(hashTheKey( mBuilder.getKey() ));
            byte[] dataBytes = texto.getBytes( mBuilder.getCharsetName() );
            Cipher cipher = Cipher.getInstance( mBuilder.getAlgorithm());
            cipher.init( Cipher.ENCRYPT_MODE, secretKey, mBuilder.getIvParameterSpec(), mBuilder.getSecureRandom() );

            return  new RetornoCriptografia( Base64.encodeToString(cipher.doFinal( dataBytes ), mBuilder.getBase64Mode()));
        } catch ( Exception e ) {
            return new RetornoCriptografia(e.getClass().getSimpleName(), true);
        }
    }

    public RetornoCriptografia decrypt() {
        try {
            byte[] dataBytes = Base64.decode(texto, mBuilder.getBase64Mode());
            SecretKey secretKey = getSecretKey(hashTheKey(mBuilder.getKey()));
            Cipher cipher = Cipher.getInstance(mBuilder.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, mBuilder.getIvParameterSpec(), mBuilder.getSecureRandom());
            byte[] dataBytesDecrypted = (cipher.doFinal(dataBytes));
            return new RetornoCriptografia(new String(dataBytesDecrypted));
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(BadPaddingException.class.getSimpleName())) {
                t.setText("A Chave de Segurança informada está incorreta.");
            } else if( e.getClass().getSimpleName().equals(IllegalBlockSizeException.class.getSimpleName()))
                t.setText("O texto informado não foi reconhecido como um texto criptografado.");
            else if(e.getClass().getSimpleName().equals(IllegalArgumentException.class.getSimpleName()))
                t.setText("Foi detectada uma tentativa de alteração da criptografia informada. Operação abortada.");
            else
                t.setText(e.getClass().getSimpleName());
            t.show();
            return new RetornoCriptografia(e.getClass().getSimpleName(), true);
        }
    }

    private SecretKey getSecretKey( char[] key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance( mBuilder.getSecretKeyType() );
        KeySpec spec = new PBEKeySpec( key, mBuilder.getSalt().getBytes( mBuilder.getCharsetName() ), mBuilder.getIterationCount(), mBuilder.getKeyLength());
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec( tmp.getEncoded(), mBuilder.getKeyAlgorithm() );
    }

    private char[] hashTheKey( String key ) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance( mBuilder.getDigestAlgorithm() );
        messageDigest.update( key.getBytes( mBuilder.getCharsetName() ));
        return Base64.encodeToString (messageDigest.digest(), Base64.NO_PADDING ).toCharArray();
    }

    public interface Callback {
        void onSuccess( String result );
        void onError( Exception exception );
    }

    private static class Builder {
        private byte[] mIv;
        private int mKeyLength;
        private int mBase64Mode;
        private int mIterationCount;
        private String mSalt;
        private String mKey;
        private String mAlgorithm;
        private String mKeyAlgorithm;
        private String mCharsetName;
        private String mSecretKeyType;
        private String mDigestAlgorithm;
        private String mSecureRandomAlgorithm;
        private SecureRandom mSecureRandom;
        private IvParameterSpec mIvParameterSpec;

        public static Builder getDefaultBuilder( ) {
            return new Builder()
                    .setIv ( new byte [] { 29 , 88 , - 79 , - 101 , - 108 , - 38 , - 126 , 90 , 52 , 101 , - 35 , 114 , 12 , - 48 , - 66 , - 30 })
                    .setKey ( " mor € Z € cr € tKYss " )
                    .setSalt("202174NC3135734pp")
                    .setKeyLength(128)
                    .setKeyAlgorithm("AES")
                    .setCharsetName("UTF8")
                    .setIterationCount ( 1 )
                    .setDigestAlgorithm("SHA1")
                    .setBase64Mode(Base64.DEFAULT)
                    .setAlgorithm("AES/CBC/PKCS5Padding")
                    .setSecureRandomAlgorithm("SHA1PRNG")
                    .setSecretKeyType("PBKDF2WithHmacSHA1");
        }

        private CriptografiaService build() throws NoSuchAlgorithmException {
            setSecureRandom( SecureRandom.getInstance(getSecureRandomAlgorithm() ));
            setIvParameterSpec( new IvParameterSpec( getIv() ));
            return new CriptografiaService(this);
        }

        private String getCharsetName() {
            return mCharsetName;
        }

        private Builder setCharsetName( String charsetName ) {
            mCharsetName = charsetName;
            return this;
        }

        private String getAlgorithm() {
            return mAlgorithm;
        }

        private Builder setAlgorithm( String algorithm ) {
            mAlgorithm = algorithm;
            return this;
        }

        private String getKeyAlgorithm() {
            return mKeyAlgorithm;
        }

        private Builder setKeyAlgorithm(String keyAlgorithm) {
            mKeyAlgorithm = keyAlgorithm;
            return this;
        }

        private int getBase64Mode() {
            return mBase64Mode;
        }

        private Builder setBase64Mode( int base64Mode ) {
            mBase64Mode = base64Mode;
            return this;
        }

        private String getSecretKeyType() {
            return mSecretKeyType;
        }

        private Builder setSecretKeyType( String secretKeyType ) {
            mSecretKeyType = secretKeyType;
            return this;
        }

        private String getSalt() {
            return mSalt;
        }

        private Builder setSalt( String salt ) {
            mSalt = salt;
            return this;
        }

        private String getKey() {
            return mKey;
        }

        private Builder setKey( String key ) {
            mKey = key;
            return this;
        }

        private int getKeyLength() {
            return mKeyLength;
        }

        public Builder setKeyLength(int keyLength) {
            mKeyLength = keyLength;
            return this;
        }

        private int getIterationCount() {
            return mIterationCount;
        }

        public Builder setIterationCount(int iterationCount) {
            mIterationCount = iterationCount;
            return this;
        }

        private String getSecureRandomAlgorithm() {
            return mSecureRandomAlgorithm;
        }

        public Builder setSecureRandomAlgorithm(String secureRandomAlgorithm) {
            mSecureRandomAlgorithm = secureRandomAlgorithm;
            return this;
        }

        private byte[] getIv() {
            return mIv;
        }

        public Builder setIv(byte[] iv) {
            mIv = iv;
            return this;
        }

        private SecureRandom getSecureRandom() {
            return mSecureRandom;
        }

        public Builder setSecureRandom(SecureRandom secureRandom) {
            mSecureRandom = secureRandom;
            return this;
        }

        private IvParameterSpec getIvParameterSpec() {
            return mIvParameterSpec;
        }

        public Builder setIvParameterSpec(IvParameterSpec ivParameterSpec) {
            mIvParameterSpec = ivParameterSpec;
            return this;
        }

        private String getDigestAlgorithm() {
            return mDigestAlgorithm;
        }

        public Builder setDigestAlgorithm(String digestAlgorithm) {
            mDigestAlgorithm = digestAlgorithm;
            return this;
        }
    }
}