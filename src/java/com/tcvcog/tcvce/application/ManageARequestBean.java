/*
 * Copyright (C) 2017 sylvia
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
package com.tcvcog.tcvce.application;

import com.tcvcog.tcvce.entities.RequestStatus;



/**
 *
 * @author sylvia
 */

public class ManageARequestBean {
    
    private RequestStatus reqStat;
    

    /**
     * Creates a new instance of ManageARequestBean
     */
    public ManageARequestBean() {
    }
    
    public RequestStatus[] getStatuses() {
        System.out.println("ManageARequestBean.getStatuses");
        return RequestStatus.values();
    }

    /**
     * @return the reqStat
     */
    public RequestStatus getReqStat() {
        return reqStat;
    }

    /**
     * @param reqStat the reqStat to set
     */
    public void setReqStat(RequestStatus reqStat) {
        this.reqStat = reqStat;
    }
    
}
