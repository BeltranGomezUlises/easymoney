/*
 * Copyright (C) 2017 Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ub.easymoney.entities.commons;

/**
 * entidad de configuraciones generales del sistema
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class CGConfig{

    private JwtsConfig jwtConfig;
    private MailsConfig mailConfig;
    private SMSConfig smsConfig;
    private AccessConfig accessConfig;
    private BitacorasConfig bitacorasConfig;

    public BitacorasConfig getBitacorasConfig() {
        return bitacorasConfig;
    }

    public void setBitacorasConfig(BitacorasConfig bitacorasConfig) {
        this.bitacorasConfig = bitacorasConfig;
    }

    public SMSConfig getSmsConfig() {
        return smsConfig;
    }

    public void setSmsConfig(SMSConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    public JwtsConfig getJwtConfig() {
        return jwtConfig;
    }

    public void setJwtConfig(JwtsConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public MailsConfig getMailConfig() {
        return mailConfig;
    }

    public void setMailConfig(MailsConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public AccessConfig getAccessConfig() {
        return accessConfig;
    }

    public void setAccessConfig(AccessConfig accessConfig) {
        this.accessConfig = accessConfig;
    }

    /**
     * modelo de configuraciones generales
     */
    public static class MailsConfig {

        private String resetPasswordMailId;
        private String supportMailId;
        private String contactMailId;

        public String getResetPasswordMailId() {
            return resetPasswordMailId;
        }

        public void setResetPasswordMailId(String resetPasswordMailId) {
            this.resetPasswordMailId = resetPasswordMailId;
        }

        public String getSupportMailId() {
            return supportMailId;
        }

        public void setSupportMailId(String supportMailId) {
            this.supportMailId = supportMailId;
        }

        public String getContactMailId() {
            return contactMailId;
        }

        public void setContactMailId(String contactMailId) {
            this.contactMailId = contactMailId;
        }

        @Override
        public String toString() {
            return "MailsConfig{" + "resetPasswordMailId=" + resetPasswordMailId + ", supportMailId=" + supportMailId + ", contactMailId=" + contactMailId + '}';
        }

    }

    /**
     * modelo de configuraciones de json web tokens
     */
    public static class JwtsConfig {

        private int secondsSessionJwtExp;
        private int secondsRecoverJwtExp;

        public int getSecondsSessionJwtExp() {
            return secondsSessionJwtExp;
        }

        public void setSecondsSessionJwtExp(int secondsSessionJwtExp) {
            this.secondsSessionJwtExp = secondsSessionJwtExp;
        }

        public int getSecondsRecoverJwtExp() {
            return secondsRecoverJwtExp;
        }

        public void setSecondsRecoverJwtExp(int secondsRecoverJwtExp) {
            this.secondsRecoverJwtExp = secondsRecoverJwtExp;
        }

        @Override
        public String toString() {
            return "JwtsConfig{" + "secondsSessionJwtExp=" + secondsSessionJwtExp + ", secondsRecoverJwtExp=" + secondsRecoverJwtExp + '}';
        }

    }

    /**
     * modelo de configuracion de provedor de SMS
     */
    public static class SMSConfig {

        private String uri;
        private String deviceImei;
        private String usuarioId;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getDeviceImei() {
            return deviceImei;
        }

        public void setDeviceImei(String deviceImei) {
            this.deviceImei = deviceImei;
        }

        public String getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(String usuarioId) {
            this.usuarioId = usuarioId;
        }

        @Override
        public String toString() {
            return "SMSConfig{" + "uri=" + uri + ", deviceImei=" + deviceImei + ", usuarioId=" + usuarioId + '}';
        }

    }

    /**
     * modelo de configuracion de accesos del sistema
     */
    public static class AccessConfig {

        private int secondsBetweenEvents;
        private int maxNumberAttemps;
        private int secondsTermporalBlockingUser;
        private int maxPasswordRecords;

        public int getMaxPasswordRecords() {
            return maxPasswordRecords;
        }

        public void setMaxPasswordRecords(int maxPasswordRecords) {
            this.maxPasswordRecords = maxPasswordRecords;
        }

        public int getSecondsBetweenEvents() {
            return secondsBetweenEvents;
        }

        public void setSecondsBetweenEvents(int secondsBetweenEvents) {
            this.secondsBetweenEvents = secondsBetweenEvents;
        }

        public int getMaxNumberAttemps() {
            return maxNumberAttemps;
        }

        public void setMaxNumberAttemps(int maxNumberAttemps) {
            this.maxNumberAttemps = maxNumberAttemps;
        }

        public int getSecondsTermporalBlockingUser() {
            return secondsTermporalBlockingUser;
        }

        public void setSecondsTermporalBlockingUser(int secondsTermporalBlockingUser) {
            this.secondsTermporalBlockingUser = secondsTermporalBlockingUser;
        }

        @Override
        public String toString() {
            return "AccessConfig{" + "secondsBetweenEvents=" + secondsBetweenEvents + ", maxNumberAttemps=" + maxNumberAttemps + ", secondsTermporalBlockingUser=" + secondsTermporalBlockingUser + ", maxPasswordRecords=" + maxPasswordRecords + '}';
        }

    }

    public static class BitacorasConfig {

        private int mesesAPersistir;

        public int getMesesAPersistir() {
            return mesesAPersistir;
        }

        public void setMesesAPersistir(int mesesAPersistir) {
            this.mesesAPersistir = mesesAPersistir;
        }

    }

    @Override
    public String toString() {
        return "CGConfig{" + "jwtConfig=" + jwtConfig + ", mailConfig=" + mailConfig + ", smsConfig=" + smsConfig + ", loginConfig=" + accessConfig + '}';
    }

}
