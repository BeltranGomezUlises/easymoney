/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils;

import com.ub.easymoney.entities.commons.CGConfig;
import com.ub.easymoney.entities.commons.ConfigMail;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public final class UtilsConfig {       

    static ConfigMail getResetPasswordConfigMail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static CGConfig.SMSConfig getSMSConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static int getSecondsSessionJwtExp() {
        return 600; //segundos
    }
}