/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsSecurity {

    /**
     * SYMETRIC ENCRYPTION
     */
    private static final String SECRET_MD5_KEY = "secrect ultra private string key";
    private static final String ENCRYPT_ALGORITHM = "DESede";

    /**
     * ASYMETRIC ENCRYPTION
     */
    private static KeyPairGenerator keyGen;
    private static KeyPair clavesRSA;
    private static PrivateKey clavePrivada;
    private static PublicKey clavePublica;

    //inicializar las llaver para esta instancia
    static {
        try {
            //Creación y obtención del par de claves
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);//tamaño de la clave            
            clavesRSA = keyGen.generateKeyPair();

            //Clave privada
            clavePrivada = clavesRSA.getPrivate();

            //Clave pública
            clavePublica = clavesRSA.getPublic();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UtilsSecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String cifrarMD5(String texto) {
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(SECRET_MD5_KEY.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
        }
        return base64EncryptedString;
    }

    public static String decifrarMD5(String textoEncriptado) throws Exception {
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(SECRET_MD5_KEY.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, ENCRYPT_ALGORITHM);

            Cipher decipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw ex;
        }
        return base64EncryptedString;
    }

    public static String getPublicKey() {
        return Base64.encodeBase64String(clavePublica.getEncoded());
    }

    private static String getPrivateKey() {
        return Base64.encodeBase64String(clavePrivada.getEncoded());
    }

    public static String decryptBase64ByPrivateKey(String bufferCifrado) throws Exception {
        try {
            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
            //Obtener texto descifrado            
            byte[] bufferClaro = cifrador.doFinal(Base64.decodeBase64(bufferCifrado));
            return new String(bufferClaro, StandardCharsets.UTF_8);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            throw ex;
        }
    }

    public static void testDeCrifradoYDecifrado() {
        try {
            //Se pueden mostrar las claves para ver cuáles son, aunque esto no es aconsejable
            System.out.println("clavePublica: " + getPublicKey());
            System.out.println("clavePrivada: " + getPrivateKey());

            //Texto plano
            String textoACifrar = "sms";
            byte[] bufferClaro = textoACifrar.getBytes();

            //Ciframos con clave pública el texto plano utilizando RSA
            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
            System.out.println("Cifrar con clave pública el Texto:");
            System.out.write(bufferClaro);

            //Realización del cifrado del texto plano
            byte[] bufferCifrado = cifrador.doFinal(bufferClaro);
            System.out.println("Texto CIFRADO");
            System.out.write(bufferCifrado);
            System.out.println("\n_______________________________");

            //Desencriptación utilizando la clave privada
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
            System.out.println("Descifrar con clave privada");

            //Obtener y mostrar texto descifrado
            bufferClaro = cifrador.doFinal(bufferCifrado);
            System.out.println("Texto DESCIFRADO");
            System.out.write(bufferClaro);
            System.out.println("\n_______________________________");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(UtilsSecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
