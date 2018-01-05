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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.entities.CEActionRequest;
import java.sql.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author sylvia
 */
public class CEActionRequestIntegrator extends BackingBeanUtils implements Serializable {

    //Connection integratorConn;
    /**
     * Creates a new instance of CEActionRequestIntegrator
     */
    public CEActionRequestIntegrator() {
    }
    
    public int submitCEActionRequest(CEActionRequest request){
        System.out.println("CEActionRequestIntegrator.submitCEActionRequest: Writing Action Request Into DB");
        int controlCode = 0;
        /*
        System.out.println("DATE DEBUGGING:");
        System.out.println("Get current time stamp:");
        System.out.println(getCurrentTimeStamp().toString());
        System.out.println("Date of record:");
        Date pracDate = new Date(request.getDateOfRecord().toEpochDay());
        System.out.println(pracDate.toString());
        */
        
//        Logger.getLogger(CEActionRequestIntegrator.class.getName()).log(Level.INFO, null, "Attempting ce request submission");
        
        StringBuilder qbuilder = new StringBuilder();
        qbuilder.append("INSERT INTO public.codeenfactionrequest(\n" 
                + "requestpubliccc, muni_muniid, "
                + "issuetype_issuetypeid, actrequestor_requestorid, submittedtimestamp, "
                + "dateofrecord, addressofconcern, addressZip, "
                + "notataddress, requestdescription, isurgent, "
                + "caseid, reqstat_requeststatusid, coginternalnotes, "
                + "muniinternalnotes, publicexternalnotes)\n" +
                "    VALUES (?, ?, ?, \n" +
                "            ?, ?, ?, ?, \n" +
                "            ?, ?, ?, ?, ?, \n" +
                "            ?, ?, ?, \n" +
                "            ?);");
        
      
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(qbuilder.toString());
            
           // insertAction.setInt(1, request.getRequestID());
            controlCode = getControlCodeFromTime();
            stmt.setInt(1, controlCode);
            //stmt.setInt(2, request.getMuni_muniCode());
            
            stmt.setInt(3, request.getIssueType_issueTypeID());
            // TODO deal with requestor flow
            stmt.setInt(4, 1); 
            
            stmt.setTimestamp(5, getCurrentTimeStamp());
            
            stmt.setDate(6, java.sql.Date.valueOf(request.getDateOfRecord()));
//            insertAction.setDate(7, java.sql.Date.valueOf(request.getDateOfRecord()));
            stmt.setString(7, request.getAddressOfConcern());
            //stmt.setString(8, request.getAddressZip());
            
            stmt.setBoolean(9, request.getNotAtAddress());
            stmt.setString(10, request.getRequestDescription());
            stmt.setBoolean(11, request.isIsUrgent());
            
            //stmt.setInt(12, request.getCaseID());
            
            stmt.setInt(13, 1);
          //  insertAction.setInt(14, request.getReqStat_requestStatusID());
            stmt.setString(14, request.getCogInternalNotes());
            
            stmt.setString(15, request.getMuniInternalNotes());
            stmt.setString(16, request.getPublicExternalNotes());
            
            // INSERT REQUEST INTO DATABASE
            System.out.println("CEActionRequestIntegrator.submitCEActionRequest - inserting...");
            stmt.executeUpdate();
            
            
            
            stmt.close();
        } catch (SQLException ex) { 
            Logger.getLogger(CEActionRequestIntegrator.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CEActionRequestIntegrator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            if (con != null) {
                try {
                    System.out.println("CEActionRequestorIntegrator.submitCEActionRequest - close con");
                    con.close();
                } catch (SQLException e) { /* ignored */}
             }
        } // close finally
        return controlCode;
        
    } // close submitActionRequest
    
    private StringBuilder getSelectActionRequestSQLStatement(int controlCode){
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT requestid, requestpubliccc, muni_muniid, \n" +
"	issuetype_issuetypeid, actrequestor_requestorid, submittedtimestamp, \n" +
"	dateofrecord, addressofconcern, codeenfactionrequest.addresszip, \n" +
"	notataddress, requestdescription, isurgent, \n" +
"	caseid,reqstat_requeststatusid, coginternalnotes, \n" +
"	muniinternalnotes, publicexternalnotes,\n" +
"	actionRqstIssueType.typeName AS typename, municipality.muniName AS muniname, requestStatus.statusName AS statusname \n" +
"	FROM public.codeenfactionrequest INNER JOIN public.actionrequestor ON actrequestor_requestorid = requestorid\n" +
"		INNER JOIN actionrqstissuetype ON issuetype_issuetypeid = issuetypeid\n" +
"		INNER JOIN requestStatus ON reqStat_requestStatusID = requestStatusID\n" +
"		INNER JOIN municipality ON muni_muniID = municipalityID\n" +
"		INNER JOIN actionRequestorType ON actionRequestorType_typeID = typeID");
        sb.append(" WHERE requestpubliccc = ");
        sb.append(String.valueOf(controlCode));
        sb.append(";");
        
        return sb;
        
    } // close method
    
    private CEActionRequest generateActionRequestFromRS(ResultSet rs) throws SQLException{
            
            // create the action request object
            CEActionRequest actionRequest = new CEActionRequest();
            
           actionRequest.setRequestID(rs.getInt("requestid"));
           actionRequest.setRequestPublicCC(rs.getInt("requestPubliccc"));
           //actionRequest.setMuni_muniCode(rs.getInt("muni_muniid"));
           
           actionRequest.setIssueType_issueTypeID(rs.getInt("issuetype_issuetypeid"));
           // TODO integrate requestorID
           actionRequest.setSubmittedTimeStamp(rs.getTimestamp("submittedtimestamp").toLocalDateTime());
           
           actionRequest.setDateOfRecord(rs.getDate("dateofrecord").toLocalDate());
           actionRequest.setAddressOfConcern(rs.getString("addressofconcern"));
           //actionRequest.setAddressZip(rs.getString("addresszip"));
           
           actionRequest.setNotAtAddress(rs.getBoolean("notataddress"));
           actionRequest.setRequestDescription(rs.getString("requestDescription"));
           actionRequest.setIsUrgent(rs.getBoolean("isurgent"));
           
           //actionRequest.setCaseID(rs.getInt("caseid"));
           //actionRequest.setReqStat_requestStatusID(rs.getInt("reqStat_requestStatusID"));
           actionRequest.setCogInternalNotes(rs.getString("coginternalnotes"));
           
           actionRequest.setMuniInternalNotes(rs.getString("muniinternalnotes"));
           actionRequest.setPublicExternalNotes(rs.getString("publicexternalnotes"));
           
           // pulling from auxillary tables' columns from the SQL joins
           actionRequest.setIssueTypeString(rs.getString("typeName"));
//           actionRequest.setMuniNameString(rs.getString("muniName"));
//           actionRequest.setRequestStatusString(rs.getString("statusname"));
           return actionRequest;
    }
    
    
    public CEActionRequest getActionRequest(int controlCode){
        System.out.println("CEActionRequestorIntegrator.getActionRequest- start");
        CEActionRequest request = null;
        
        StringBuilder sb = getSelectActionRequestSQLStatement(controlCode);
        
        // for degugging
        // System.out.println("Select Statement: ");
        // System.out.println(sb.toString());
        Connection con = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            con = getPostgresCon();
            stmt = con.createStatement();
            
            // Retrieve action data from postgres
           rs = stmt.executeQuery(sb.toString());
           
           // loop through the result set and reat an action request from each
           while(rs.next()){
               request = generateActionRequestFromRS(rs);
         
           }
        } catch (SQLException ex) {
            Logger.getLogger(CEActionRequestIntegrator.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
             if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* ignored */}
            }
         
            if (con != null) {
                try {
                    System.out.println("CEActionRequestorIntegrator.getActionRequest- Close con");
                    con.close();
                } catch (SQLException e) { /* ignored */}
             }
        } // close finally
        return request;
    } // close getActionRequest
    
    public LinkedList getCEActionRequestList(){
        LinkedList<CEActionRequest> requestList = new LinkedList();
        String query = "SELECT requestpubliccc FROM public.codeenfactionrequest"; 
        ResultSet rs = null;
         
         int controlCode;
         
         System.out.println("CEActionRequestorIntegrator.getCEActionRequestList - Accessing dbcon bean through faces context");
         //FacesContext context = getFacesContext();
         //DBConnection dbCon = context.getApplication().evaluateExpressionGet(context, "#{dBConnection}", DBConnection.class);
         
        Connection con = getPostgresCon();
        
         try {
           Statement stmt = con.createStatement();
            
            // Retrieve action data from postgres
           rs = stmt.executeQuery(query);
           while(rs.next()){
               controlCode = rs.getInt("requestpubliccc");
               requestList.add(getActionRequest(controlCode));
           } // close while
           con.close();
               
         } catch (SQLException ex) {
                Logger.getLogger(CEActionRequestIntegrator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    System.out.println("CEActionRequestorIntegrator.getCEActionRequestList - Close ResultSet");
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
         
            if (con != null) {
                try {
                    System.out.println("CEActionRequestorIntegrator.getCEActionRequestList - Close con");
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
             
        }// close try/catch
         
         return requestList;
    } // close method
} // close class
