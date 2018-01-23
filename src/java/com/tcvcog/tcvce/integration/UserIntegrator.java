/*
 * Copyright (C) 2017 Turtle Creek Valley
Council of Governments, PA
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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.User;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneOffset;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class UserIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of UserIntegrator
     */
    public UserIntegrator() {
    }
    
    public void insertUser(User userToInsert) throws IntegrationException{
        Connection con = getPostgresCon();
        
        String query = "INSERT INTO public.login(\n" +
"            userid, userrole, username, password, muni_municode, fname, lname, \n" +
"            worktitle, phonecell, phonehome, phonework, email, address_street, \n" +
"            address_city, address_zip, address_state, notes, activitystartdate, \n" +
"            activitystopdate, accesspermitted)\n" +
"    VALUES (DEFAULT, CAST (? AS role) , ?, ?, ?, ?, ?, \n" +
"            ?, ?, ?, ?, ?, ?, \n" +
"            ?, ?, ?, ?, ?, \n" +
"            ?, ?);";
        
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, userToInsert.getRoleType().toString());
            stmt.setString(2, userToInsert.getUsername());
            stmt.setString(3, userToInsert.getPassword());
            stmt.setInt(4, userToInsert.getMuniCode());
            stmt.setString(5, userToInsert.getfName());
            stmt.setString(6, userToInsert.getlName());
            
            stmt.setString(7,userToInsert.getWorkTitle());
            stmt.setString(8, userToInsert.getPhoneCell());
            stmt.setString(9, userToInsert.getPhoneHome());
            stmt.setString(10, userToInsert.getPhoneWork());
            stmt.setString(11, userToInsert.getEmail());
            stmt.setString(12, userToInsert.getAddress_street());
            
            stmt.setString(13, userToInsert.getAddress_city());
            stmt.setString(14, userToInsert.getAddress_zip());
            stmt.setString(15, userToInsert.getAddress_state());
            stmt.setString(16, userToInsert.getNotes());
            stmt.setTimestamp(17, java.sql.Timestamp
                    .valueOf(userToInsert.getActivityStartDate()));
            
            stmt.setTimestamp(18, java.sql.Timestamp
                    .valueOf(userToInsert.getActivityStopDate()));
            stmt.setBoolean(19, userToInsert.isAccessPermitted());
            
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new IntegrationException("Error inserting new person", ex);
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        
    }
    
    public void updateUser(User userToUpdate){
        
    }
    
    public void deleteUser(User userToDelete){
        
        
        
    }
    
    public User getUserByUserID(int userID){
        
        return new User();
    }
    
    public LinkedList getCompleteUserList(){
        return new LinkedList();
        
    }
    
}
