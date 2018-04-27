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
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Citation;
import com.tcvcog.tcvce.entities.Property;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class CitationIntegrator extends BackingBeanUtils implements Serializable{
    
    
    public void insertCitation(Citation citation) throws IntegrationException{
        
        String query =  "INSERT INTO public.citation(\n" +
                        "            citationid, citationno, origin_courtentity_entityid, cecase_caseid, \n" +
                        "            login_userid, dateofrecord, transtimestamp, isactive, notes)\n" +
                        "    VALUES (?, ?, ?, ?, \n" +
                        "            ?, ?, now(), ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, citation.getCitationID());
            stmt.setString(2, citation.getCitationNo());
            stmt.setInt(3, citation.getOrigin_courtentity_entityID());
            stmt.setInt(4, citation.getCeCase().getCaseID());
            stmt.setInt(5, citation.getUserOwner().getUserID());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(citation.getDateOfRecord()));
            stmt.setBoolean(7, citation.isIsActive());
            stmt.setString(8, citation.getNotes());
            
            System.out.println("Code.getEventCategory| sql: " + stmt.toString());
            stmt.execute();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to insert citation into database, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        
        
    }
    
    public Citation getCitationByID(int id) throws IntegrationException{

        String query = "SELECT * FROM public.citation WHERE citationid=?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Citation c = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            System.out.println("Code.getEventCategory| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                c = generateCitationFromRS(rs);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return c;
    }
    
    public LinkedList<Citation> getCitationsByProperty(Property prop) throws IntegrationException{
        
        String query =  "SELECT citationid, citationno, origin_courtentity_entityid, cecase_caseid, \n" +
                        "       citation.login_userid, dateofrecord, transtimestamp, isactive, citation.notes\n" +
                        "  FROM public.citation 	INNER JOIN public.cecase ON cecase.caseid = citation.cecase_caseid\n" +
                        "			INNER JOIN public.property ON cecase.property_propertyID = property.propertyID\n" +
                        "  WHERE propertyID=?; ";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<Citation> citationList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, prop.getPropertyID());
            System.out.println("CitationIntegrator.getCitationsByProperty| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                citationList.add(generateCitationFromRS(rs));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return citationList;
    }
    
    public LinkedList<Citation> getCitationsByCase(CECase ceCase) throws IntegrationException{
            
        String query =  "SELECT citationid, citationno, origin_courtentity_entityid, cecase_caseid, \n" +
                        "       citation.login_userid, dateofrecord, transtimestamp, isactive, citation.notes\n" +
                        "  FROM public.citation 	INNER JOIN public.cecase ON cecase.caseid = citation.cecase_caseid\n" +
                        "  WHERE cecase.caseID=?; ";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<Citation> citationList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ceCase.getCaseID());
            System.out.println("CitationIntegrator.getCitationsByCase| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                citationList.add(generateCitationFromRS(rs));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return citationList;
    }
    
    private Citation generateCitationFromRS(ResultSet rs) throws IntegrationException{
        CaseIntegrator ci = getCaseIntegrator();
        UserIntegrator ui = getUserIntegrator();
        Citation c = new Citation();
        try {
            c.setCitationID(rs.getInt("citationID"));
            c.setCitationNo(rs.getString("citationNo"));
            c.setOrigin_courtentity_entityID(rs.getInt("origin_courtentity_entityID"));
            c.setCeCase(ci.getCECase(rs.getInt("cecase_caseID")));
            c.setUserOwner(ui.getUserByUserID(rs.getInt("login_userID")));
            c.setDateOfRecord(rs.getTimestamp("dateOfRecord").toLocalDateTime());
            c.setTimeStamp(rs.getTimestamp("transTimeStamp").toLocalDateTime());
            c.setIsActive(rs.getBoolean("isActive"));
            c.setNotes(rs.getString("notes"));
        } catch (SQLException | IntegrationException ex) {
            System.out.println(ex);
            throw new IntegrationException("Unable to build citation from RS", ex);
        }
        return c;
    }
    
    public void updateCitation(Citation citation) throws IntegrationException{
        String query =  "UPDATE public.citation\n" +
                            "   SET citationno=?, origin_courtentity_entityid=?, cecase_caseid=?, \n" +
                            "       login_userid=?, dateofrecord=?, isactive=?, \n" +
                            "       notes=?\n" +
                            " WHERE citationid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, citation.getCitationNo());
            stmt.setInt(2, citation.getOrigin_courtentity_entityID());
            stmt.setInt(3, citation.getCeCase().getCaseID());
            stmt.setInt(4, citation.getUserOwner().getUserID());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(citation.getDateOfRecord()));
            stmt.setBoolean(6, citation.isIsActive());
            stmt.setString(7, citation.getNotes());
            
            System.out.println("CitationIntegrator.updateCitation | sql: " + stmt.toString());
            stmt.execute();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update citation in the database, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void deleteCitation(Citation citation) throws IntegrationException{
        String query =  "DELETE FROM public.citation\n" +
                        " WHERE citationid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, citation.getCitationID());
            
            System.out.println("CitationIntegrator.updateCitation | sql: " + stmt.toString());
            stmt.execute();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to delete this citation in the database, sorry. "
                    + "Most likely reason: some other record in the system references this citation somehow, "
                    + "like a court case. As a result, this citation cannot be deleted.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
}
