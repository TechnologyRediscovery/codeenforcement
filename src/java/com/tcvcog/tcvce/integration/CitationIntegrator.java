/*
 * Copyright (C) 2018 Adam Gutonski
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
import com.tcvcog.tcvce.entities.Citation;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Adam Gutonski
 */
public class CitationIntegrator extends BackingBeanUtils implements Serializable {
    
    public CitationIntegrator() {
        
    }
    
    public void updateCitation(Citation citation) throws IntegrationException {
        String query = "UPDATE public.citation\n" +
                    "   SET citationno=?, origin_courtentity_entityid=?, cecase_caseid=?, \n" +
                    "       login_userid=?, dateofrecord=?, transtimestamp=?, isactive=?, \n" +
                    "       notes=?\n" +
                    " WHERE citationid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setString(1, citation.getCitationNo());
            stmt.setInt(2, citation.getCourtEntityOriginID());
            stmt.setInt(3, citation.getCaseID());
            stmt.setInt(4, citation.getUserID());
            //update date of record
            if(citation.getDateOfRecord() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(citation.getDateOfRecord()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            //update trans time stamp
            if(citation.getTransTimeStamp() != null){
                stmt.setTimestamp(6, java.sql.Timestamp.valueOf(citation.getTransTimeStamp()));
                
            } else {
                stmt.setNull(6, java.sql.Types.NULL);
            }
            stmt.setBoolean(7, citation.isIsActive());
            stmt.setString(8, citation.getNotes());
            stmt.setInt(9, citation.getCitationID());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update citation record", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public LinkedList<Citation> getCitationList() throws IntegrationException {
            String query = "SELECT citationid, citationno, origin_courtentity_entityid, cecase_caseid, \n" +
                        "       login_userid, dateofrecord, transtimestamp, isactive, notes\n" +
                        "  FROM public.citation;";
            Connection con = getPostgresCon();
            ResultSet rs = null;
            PreparedStatement stmt = null;
            LinkedList<Citation> citationList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            System.out.println("");
            rs = stmt.executeQuery();
            System.out.println("CitationIntegrator.getCitationList | SQL: " + stmt.toString());
            while(rs.next()){
                citationList.add(generateCitation(rs));
            }
            
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                throw new IntegrationException("Cannot get Citation List", ex);
            } finally {
                if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
                if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
                if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
            }
            return citationList;   
    }
    
    public void insertCitation(Citation citation) throws IntegrationException {
        String query = "INSERT INTO public.citation(\n" +
                "            citationid, citationno, origin_courtentity_entityid, cecase_caseid, \n" +
                "            login_userid, dateofrecord, transtimestamp, isactive, notes)\n" +
                "    VALUES (DEFAULT, ?, ?, ?, \n" +
                "            ?, ?, ?, DEFAULT, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, citation.getCitationNo());
            stmt.setInt(2, citation.getCourtEntityOriginID());
            stmt.setInt(3, citation.getCaseID());
            stmt.setInt(4, citation.getUserID());
            if(citation.getDateOfRecord()!= null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(citation.getDateOfRecord()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            if(citation.getTransTimeStamp() != null){
                stmt.setTimestamp(6, java.sql.Timestamp.valueOf(citation.getTransTimeStamp()));
            } else {
                stmt.setNull(6, java.sql.Types.NULL);
            }
            stmt.setString(7, citation.getNotes());
            System.out.println("CitationIntegraot.citationIntegrator | sql: " + stmt.toString());
            System.out.println("TRYING TO EXECUTE INSERT METHOD");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Citation Record", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void deleteCitation(Citation citation) throws IntegrationException{
         String query = "DELETE FROM public.citation\n" +
                        " WHERE citationid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, citation.getCitationID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete citation record--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private Citation generateCitation(ResultSet rs) throws IntegrationException {
        Citation citation = new Citation();
        
    
        try {
            citation.setCitationID(rs.getInt("citationid"));
            citation.setCitationNo((rs.getString("citationno")));
            citation.setCourtEntityOriginID(rs.getInt("origin_courtentity_entityid"));
            citation.setCaseID(rs.getInt("cecase_caseid"));
            citation.setUserID(rs.getInt("login_userid"));
            java.sql.Timestamp dor = rs.getTimestamp("dateofrecord");
            //for date of record
            if(dor != null) {
                citation.setDateOfRecord(dor.toLocalDateTime());
            } else {
                citation.setDateOfRecord(null);
            }
            java.sql.Timestamp tts = rs.getTimestamp("transtimestamp");
            //for trans time stampe
            if(tts != null) {
                citation.setTransTimeStamp(tts.toLocalDateTime());
            } else {
                citation.setTransTimeStamp(null);
            }
            citation.setIsActive(rs.getBoolean("isactive"));
            citation.setNotes((rs.getString("notes")));
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating Citation from result set", ex);
        }
        
        return citation;
    }
    
}
