/*
 * Copyright (C) 2017 cedba
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
package com.tcvcog.tcvce.resultsets;

import javax.inject.Named;
import javax.faces.bean.*;
import java.sql.*;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import java.util.LinkedList;
import javax.faces.component.UISelectItem;
import java.util.HashMap;
import com.tcvcog.tcvce.util.Constants;
import java.io.Serializable;


/**
 *
 * @author cedba
 */
public class RequestorTypesMap implements Serializable{
    
    private HashMap requestTypes;
    
    public RequestorTypesMap(){
        System.out.println("constructing RequestorTypesMap");
    
    }

    /**
     * @return the requestorTypeHM
     */
    public HashMap getRequestTypes() {
        
        HashMap<String, Integer> typeMap = new HashMap<>();
        
        Connection con = null;
        
        System.out.println("Connecting to database");
        try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
        
        // setup database parameters
        Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
        source.setServerName(Constants.SERVER_NAME);
        source.setDatabaseName(Constants.DB_NAME);
        source.setUser(Constants.DB_USERNAME);
        source.setPassword(Constants.DB_PASS);
        source.setMaxConnections(Constants.MAX_CONNECTIONS);
        // make connection
        try {
            con = source.getConnection();
            System.out.println("connection to db open");
        } catch (SQLException ex) {
            ex.toString();
        }
        
        String query = "SELECT typeID, typeName FROM public.actionRequestorType;";
        ResultSet rs = null;
 
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                typeMap.put(rs.getString("typeName"), rs.getInt("typeID"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } // end try/catch
        
        setRequestTypes(typeMap);
        return requestTypes;
    }

    /**
     * @param requestTypes the requestTypes to set
     */
    public void setRequestTypes(HashMap requestTypes) {
        this.requestTypes = requestTypes;
    }


   
    
}
