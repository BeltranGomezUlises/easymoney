/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos;

import com.ub.easymoney.entities.RenovadosLog;
import com.ub.easymoney.entities.RenovadosLogPK;
import com.ub.easymoney.utils.UtilsDB;
import com.ub.easymoney.utils.commons.DaoSQLFacade;

/**
 *
 * @author ulises
 */
public class DaoRenovadosLog extends DaoSQLFacade<RenovadosLog, RenovadosLogPK>{
    
    public DaoRenovadosLog() {
        super(UtilsDB.getEMFactoryCG(), RenovadosLog.class, RenovadosLogPK.class);
    }
    
}
