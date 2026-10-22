package com.msnegi.aes256encryption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import kotlin.text.Charsets;

public class AES256Fragment extends Fragment {

    CallBackInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("256 Encryption/Decryption");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_a_e_s256, container, false);

        String originalString = "This text is to be encrypted";
        String userPassword = "mypassword";                        //key better to be used as fcm_token as password of two users could be same coincidently

        SecureRandom random = new SecureRandom();
        byte [] salt = new byte[256];
        random.nextBytes(salt);

        String encryptedString = encrypt(originalString, userPassword, salt) ;
        String decryptedString = decrypt(encryptedString, userPassword, salt) ;

        String str = "Original String : " + originalString + "\n\nAES256 Eencrypted String : " + encryptedString + "\n\nDecrypted String : " + decryptedString;
        ((TextView) view.findViewById(R.id.aesEncryptiontxt)).setText(str);

        return view;
    }

    @SuppressLint("NewApi")
    public static String encrypt(String strToEncrypt, String secKey, byte[] salt)
    {
        try
        {
            byte[] iv = new byte[16];
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secKey.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec userPassword = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, userPassword, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String decrypt(String strToDecrypt, String secKey, byte[] salt) {
        try
        {
            byte[] iv = new byte[16];
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secKey.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec userPassword = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, userPassword, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    //-- password incryption
    //-- Tools().encryptToBase64(password.text.toString())

    public String encryptToBase64(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(text.getBytes(Charsets.UTF_8), 0, text.length());
        return android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT).trim();
    }

}