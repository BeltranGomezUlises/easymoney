/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils;

import com.ub.easymoney.entities.commons.ConfigMail;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class UtilsMail {

    public static void sendMail(String hostName, int smtpPort, String userMail, String userPass, boolean ssl, String from, String subject, String msg, String toMail) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(hostName);
            email.setSmtpPort(smtpPort);
            email.setAuthenticator(new DefaultAuthenticator(userMail, userPass));
            email.setSSL(ssl);
            email.setFrom(from);
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(toMail);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(UtilsMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendMail(ConfigMail configMail, String subject, String msg, String toMail) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(configMail.getHostName());
            email.setSmtpPort(configMail.getPort());
            email.setAuthenticator(new DefaultAuthenticator(configMail.getAuth().getMail(), configMail.getAuth().getPass()));
            email.setSSL(true);
            email.setFrom(configMail.getAuth().getMail());
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(toMail);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(UtilsMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendRecuperarContraseñaHTMLMail(String toMail, String userName, String codigoRecuperacion) throws EmailException, MalformedURLException {

        //cargar la configuracion del correo que esta asignado para recuperacion de contraseñas
        ConfigMail configMail = UtilsConfig.getResetPasswordConfigMail();                
        
        HtmlEmail email = new HtmlEmail();
        email.setHostName(configMail.getHostName());
        email.setSmtpPort(configMail.getPort());
        email.setAuthentication(configMail.getAuth().getMail(), configMail.getAuth().getPass());
        email.setSSL(configMail.isSsl());
        email.setFrom(configMail.getAuth().getMail());
        email.setSubject("Recuperar Contraseña");
        email.addTo(toMail);

        // embed the image and get the content id to see it in-line
        URL url = new URL("http://192.168.10.8:8480/pruebas/esoft-transparent.png");
        String cid = email.embed(url, "esoftLogo");

        String htmlCadena
                = "<html>\n" +
                "    <head>\n" +
                "        <title>Servicio de recuperación de contraseña</title>\n" +
                "    </head>\n" +
                "    <body style=\"color: rgba(0,0,0, 0.8); font-family: verdana; font-size: 14px;\">\n" +
                "        <div style=\"width: 100%; display: flex; justfy-content: center;\">\n" +
                "            <div style=\"width: 70%;\">\n" +
                "                <div style=\"width: 100%; margin-bottom: 10px;\">\n" +
                "                    <img src=\"cid:" + cid + "\" style=\"height: 60px; margin-left: 20px;\" />\n" +
                "                </div>\n" +
                "\n" +
                "                <div style=\"border: 1px solid rgba(128, 128, 128, 0.31); width: 100%; padding: 20px;\">\n" +
                "                    <h3 style=\"margin-top: 0;\">" +
                                    "HOLA " + userName +
                "                    </h3>\n" +
                "                    <p>Utilize el siguiente codigo para continuar con el proceso de recuperación de su contraseña</p>\n" +
                "\n" +
                "                    <div style=\"background: #009BD2; color: white; padding: 10px; width: 100px; text-align: center;\">\n" +
                                codigoRecuperacion +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "                <div style=\"width: 100%; margin-bottom: 10px;\">\n" +
                "                    <p style=\"margin-left: 20px; color: rgba(128, 128, 128, 0.5);\">"+
                "                       Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste corporis adipisci saepe error nobis quia."+
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        // set the html message           
        email.setHtmlMsg(htmlCadena);
        String defaultMsg = "Su código de recuperación es: " + codigoRecuperacion;
        // set the alternative message
        email.setTextMsg(defaultMsg);
        // send the email
        email.send();
    }

    public static void testSendMail() {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("correo@gmail.com", "pass"));
            email.setSSL(true);
            email.setFrom("Ulises Beltran");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("ubg700@gmail.com");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(UtilsMail.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void testSendHTMLMail() {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthentication("correo@gmail.com", "pass");
            email.setSSL(true);
            email.setFrom("beltrangomezulises@gmail.com", "Ulises Beltrán");
            email.setSubject("Test email with inline image");
            email.addTo("ubg700@gmail.com", "Ulises");
            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");
            // set the html message
            email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");
            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");
            // send the email
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(UtilsMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UtilsMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
