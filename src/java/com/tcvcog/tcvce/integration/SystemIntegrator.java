/*
 * Copyright (C) 2018 Turtle Creek Valley
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
import com.tcvcog.tcvce.entities.ImprovementSuggestion;
import com.tcvcog.tcvce.entities.ListChangeRequest;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.occupancy.entities.OccupancyPermit;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sylvia
 */
public class SystemIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of SystemIntegrator
     */
    public SystemIntegrator() {
    }
    
    public void insertImprovementSuggestion(ImprovementSuggestion is) throws IntegrationException{
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        String s =      " INSERT INTO public.improvementsuggestion(\n" +
                        "            improvementid, improvementtypeid, improvementsuggestiontext, \n" +
                        "            improvementreply, statusid, submitterid, submissiontimestamp)\n" +
                        "    VALUES (DEFAULT, ?, ?, \n" +
                        "            NULL, ?, ?, now());";

        try {
            stmt = con.prepareStatement(s);
            stmt.setInt(1, is.getImprovementTypeID());
            stmt.setString(2, is.getSuggestionText());
            // hard-coded status for expediency
            stmt.setInt(3, 1);
            stmt.setInt(4, is.getSubmitter().getUserID());
            System.out.println("PersonIntegrator.getPersonListByPropertyID | sql: " + stmt.toString());

            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to insert improvement suggestion, sorry", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
        
    
    public void insertListChangeRequest(ListChangeRequest lcr) throws IntegrationException{
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        String s =  " INSERT INTO public.listchangerequest(\n" +
                    " changeid, changetext)\n" +
                    " VALUES (DEFAULT, ?);";

        try {
            stmt = con.prepareStatement(s);
            stmt.setString(1, lcr.getChangeRequestText());
            System.out.println("PersonIntegrator.getPersonListByPropertyID | sql: " + stmt.toString());

            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("PersonIntegrator.getPerson | Unable to retrieve person", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public HashMap<String, Integer> getSuggestionTypeMap() throws IntegrationException{
        HashMap<String, Integer> hm = new HashMap<>();
        
        String query =  "SELECT typeid, typetitle, typedescription\n" +
                        "  FROM public.improvementtype;";
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
 
        try {
            
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                hm.put(rs.getString("typetitle"), rs.getInt("typeid"));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to build property unit list due to an DB integration error", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return hm;
    }
}
