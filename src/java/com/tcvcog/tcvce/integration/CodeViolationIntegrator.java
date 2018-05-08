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
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.CodeViolation;
import com.tcvcog.tcvce.entities.NoticeOfViolation;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sylvia
 */
public class CodeViolationIntegrator extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of ViolationIntegrator
     */
    public CodeViolationIntegrator() {
    }
    
    public void insertCodeViolation(CodeViolation v) throws IntegrationException{
        
        String query = "INSERT INTO public.codeviolation(\n" +
            "            violationid, codesetelement_elementid, cecase_caseid, \n" +
            "            dateofrecord, entrytimestamp, stipulatedcompliancedate, \n" +
            "            actualcompliancdate, penalty, description, notes)\n" +
            "    VALUES (DEFAULT, ?, ?, \n" +
            "             ?, now(), ?, \n" +
            "            NULL, ?, ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, v.getViolationID());
            stmt.setInt(1, v.getViolatedEnfElement().getCodeSetElementID());
            stmt.setInt(2, v.getAttachedCase().getCaseID());
            //stmt.setString(3, v.getCitationID());
            
            //stmt.setTimestamp(4, java.sql.Timestamp.valueOf(v.getDateOfCitation()));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(v.getDateOfRecord()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(v.getStipulatedComplianceDate()));
            
            //stmt.setTimestamp(7, java.sql.Timestamp.valueOf(v.getActualComplianceDate()));
            stmt.setDouble(5, v.getPenalty());
            stmt.setString(6, v.getDescription());
            stmt.setString(7, v.getNotes());
            
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void insertViolationLetter(CECase c, NoticeOfViolation notice) throws IntegrationException{
        
        String query = "INSERT INTO public.noticeofviolation(\n" +
            " noticeid, caseid, lettertext, insertiontimestamp, dateofrecord, \n" +
            " requesttosend, lettersenddate, letterReturnedDate)\n" +
            " VALUES (DEFAULT, ?, ?, now(), ?, \n" +
            " ?, ?, ?);";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, c.getCaseID() );
            stmt.setString(2, notice.getNoticeText());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(notice.getDateOfRecord()));
            stmt.setBoolean(4, notice.isRequestToSend());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(notice.getLetterSentDate()));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(notice.getLetterReturnedDate()));
            
            System.out.println("CodeViolationIntegrator.insertViolationletter | sql: " + stmt.toString());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert notice of violation letter at this time, sorry.", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             
        } // close finally
        
    } // close method
    
    public void updateViolationLetter(NoticeOfViolation notice) throws IntegrationException{
         String query = "UPDATE public.noticeofviolation\n" +
                "   SET lettertext=?, dateofrecord=?, \n" +
                "   requesttosend=?, lettersenddate=?, letterreturneddate=?\n" +
                " WHERE noticeid=?;";
                // note that original time stamp is not altered on an update
         
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(2, notice.getNoticeText());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(notice.getDateOfRecord()));
            stmt.setBoolean(4, notice.isRequestToSend());
            if(notice.getLetterSentDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(notice.getLetterSentDate()));
            }
            
             if(notice.getLetterReturnedDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(notice.getLetterReturnedDate()));
            }
             
             
            System.out.println("CodeViolationIntegrator.updateNoticeOfViolation| sql: " + stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot update notice of violation letter at this time, sorry.", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             
        } // close finally
        
    }
    
    
    public void deleteViolationLetter(NoticeOfViolation notice) throws IntegrationException{
         String query = "DELETE FROM public.noticeofviolation\n" +
                "  WHERE noticeid=?;";
                // note that original time stamp is not altered on an update
         
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, notice.getNoticeID());
            
            System.out.println("CodeViolationIntegrator.updateNoticeOfViolation| sql: " + stmt.toString());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot update notice of violation letter at this time, sorry.", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             
        } // close finally
        
    }
    
    public NoticeOfViolation getNoticeOfViolation(int violationID) throws IntegrationException{
        String query = "SELECT noticeid, caseid, lettertext, insertiontimestamp, dateofrecord, \n" +
            "       requesttosend, lettersenddate, letterreturneddate\n" +
            "  FROM public.noticeofviolation;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        NoticeOfViolation notice = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, violationID);
            System.out.println("Code.getEventCategory| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                notice = generateNoticeOfViolation(rs);
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        
        return notice;
    }
    
     public LinkedList<NoticeOfViolation> getNoticeOfViolationList(CECase ceCase) throws IntegrationException{
        String query = "SELECT noticeid, caseid, lettertext, insertiontimestamp, dateofrecord, \n" +
            "       requesttosend, lettersenddate, letterreturneddate \n" +
            "  FROM public.noticeofviolation WHERE caseid=?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<NoticeOfViolation> ll = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ceCase.getCaseID());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ll.add(generateNoticeOfViolation(rs));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return ll;
    }
    
    
    public NoticeOfViolation generateNoticeOfViolation(ResultSet rs) throws SQLException{
        
        NoticeOfViolation notice = new NoticeOfViolation();
        
        notice.setNoticeID(rs.getInt("noticeid"));
        
        notice.setNoticeText(rs.getString("noticetext"));
        
        notice.setInsertionTimeStamp(rs.getTimestamp("insertiontimestamp").toLocalDateTime());
        notice.setInsertionTimeStampPretty(getPrettyDate(rs.getTimestamp("insertiontimestamp").toLocalDateTime()));
        
        notice.setDateOfRecord(rs.getTimestamp("dateofrecord").toLocalDateTime());
        notice.setDateOfRecordPretty(getPrettyDate(rs.getTimestamp("dateofrecord").toLocalDateTime()));
        
        notice.setRequestToSend(rs.getBoolean("requesttosend"));
        
        notice.setLetterSentDate(rs.getTimestamp("lettersenddate").toLocalDateTime());
        notice.setLetterSentDatePretty(getPrettyDate(rs.getTimestamp("lettersenddate").toLocalDateTime()));
        
        
        notice.setLetterReturnedDate(rs.getTimestamp("letterreturneddate").toLocalDateTime());
        notice.setLetterSentDatePretty(getPrettyDate(rs.getTimestamp("letterreturneddate").toLocalDateTime()));
        
        return notice;
        
    }
    
    
    
    
    public void updateCodeViolation(CodeViolation v) throws IntegrationException{
        String query = "UPDATE public.codeviolation\n" +
            "   SET codesetelement_elementid=?, cecase_caseid=?, \n" +
            "       dateofrecord=?, entrytimestamp=now(), stipulatedcompliancedate=?, \n" +
            "       actualcompliancdate=?, penalty=?, description=?, notes=?\n" +
            " WHERE violationid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, v.getViolatedEnfElement().getCodeSetElementID());
            stmt.setInt(2, v.getCeCaseID());
            
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(v.getDateOfRecord()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(v.getStipulatedComplianceDate()));
            
            if(v.getActualComplianceDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(v.getActualComplianceDate()));
            } else {
                stmt.setNull(6, java.sql.Types.NULL);
            }
            
            stmt.setDouble(7, v.getPenalty());
            stmt.setString((8), v.getDescription());
            stmt.setString(9, v.getNotes());
            
            System.out.println("CodeViolationIntegrator.updateViolation | stmt: " + stmt.toString());
            
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    
    public void deleteCodeViolation(CodeViolation violationToDelete) throws IntegrationException{
        String query = "DELETE FROM public.codeviolation\n" +
            " WHERE violationid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1,violationToDelete.getViolationID());
            
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete code violation--probably because"
                    + "other enetities in the system refer to it. Sorry!", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private CodeViolation generateCodeViolationFromRS(ResultSet rs) throws SQLException, IntegrationException{
        
        CodeViolation v = new CodeViolation();
        CodeIntegrator ci = getCodeIntegrator();
        System.out.println("CodeViolationIntegreator.generateCodeViolationFromRS | Current RS entry: " + rs.getString("description"));
        
        v.setViolationID(rs.getInt("violationid"));
        v.setViolatedEnfElement(ci.getEnforcableCodeElement(rs.getInt("codesetelement_elementid")));
        v.setCeCaseID(rs.getInt("cecase_caseid"));
        
       
        v.setDateOfRecord(rs.getTimestamp("dateofrecord").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        
        v.setEntryTimeStamp(rs.getTimestamp("entrytimestamp").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        
        v.setStipulatedComplianceDate(rs.getTimestamp("stipulatedcompliancedate").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        
        if(!(rs.getTimestamp("actualcompliancdate") == null)){
            v.setActualComplianceDate(rs.getTimestamp("actualcompliancdate").toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime());
            
        }
       
        
        v.setPenalty(rs.getDouble("penalty"));
        v.setDescription(rs.getString("description"));
        v.setNotes(rs.getString("notes"));
        return v;
    }
    
    
    public CodeViolation getCodeViolationByViolationID(int violationID) throws IntegrationException{
        String query = "SELECT violationid, codesetelement_elementid, cecase_caseid, dateofrecord, \n" +
"       entrytimestamp, stipulatedcompliancedate, actualcompliancdate, \n" +
"       penalty, description, notes\n" +
            "  FROM public.codeviolation WHERE violationid = ?";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        CodeViolation cv = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, violationID);
            System.out.println("Code.getEventCategory| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                cv = generateCodeViolationFromRS(rs);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return cv;
    }
    
    public LinkedList<CodeViolation> getCodeViolations(int caseID) throws IntegrationException{
        CECase tinyCase = new CECase();
        tinyCase.setCaseID(caseID);
        return CodeViolationIntegrator.this.getCodeViolations(tinyCase);
    }
    
    
    public LinkedList<CodeViolation> getCodeViolations(CECase c) throws IntegrationException{
        String query = "SELECT violationid, codesetelement_elementid, cecase_caseid, dateofrecord, \n" +
"       entrytimestamp, stipulatedcompliancedate, actualcompliancdate, \n" +
"       penalty, description, notes from public.codeviolation WHERE cecase_caseid = ?";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<CodeViolation> cvList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, c.getCaseID());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                cvList.add(generateCodeViolationFromRS(rs));
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot fetch code violation by ID, sorry.", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return cvList;
    }
    
} // close integrator
