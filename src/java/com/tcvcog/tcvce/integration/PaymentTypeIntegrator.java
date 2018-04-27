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
import com.tcvcog.tcvce.entities.Municipality;
import com.tcvcog.tcvce.occupancy.PaymentType;
import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Adam Gutonski
 */
public class PaymentTypeIntegrator extends BackingBeanUtils implements Serializable {
    
    private HashMap paymentTypeMap;
    
    public PaymentTypeIntegrator() {
        
    }
    
    public PaymentType getPaymentTypeFromPaymentTypeID(int paymentTypeID) throws IntegrationException {
        PaymentType paymentType = new PaymentType();
        PreparedStatement stmt = null;
        Connection con = null;
        // note that paymentTypeID is not returned in this query since it is specified in the WHERE
        String query = "SELECT typeid, pmttypetitle \n" +
                    "  FROM public.paymenttype" +
                    " WHERE typeid = ?;";
        ResultSet rs = null;
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, paymentTypeID);
            //System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | query: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                paymentType.setPaymentTypeId(rs.getInt("typeid"));
                paymentType.setPaymentTypeTitle(rs.getString("pmttypetitle"));
                          
            }
        } catch (SQLException ex) {
            System.out.println("PaymentTypeIntegrator.getPaymentTypeFromPaymentTypeID | " + ex.toString());
            throw new IntegrationException("Exception in PaymentTypeIntegrator.getPaymentTypeFromPaymentTypeID", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return paymentType;
        
    }
    
    public void updatePaymentType(PaymentType paymentType) throws IntegrationException {
        String query = "UPDATE public.paymenttype\n" +
                        "   SET pmttypetitle=?\n" +
                        "   WHERE typeid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setString(1, paymentType.getPaymentTypeTitle());
            stmt.setInt(2, paymentType.getPaymentTypeId());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update payment type", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public LinkedList<PaymentType> getPaymentTypeList() throws IntegrationException {
            String query = "SELECT typeid, pmttypetitle\n" +
                            "  FROM public.paymenttype;";
            Connection con = getPostgresCon();
            ResultSet rs = null;
            PreparedStatement stmt = null;
            LinkedList<PaymentType> paymentTypeList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            System.out.println("");
            System.out.println("TRYING TO GET PAYMENT TYPE LIST");
            rs = stmt.executeQuery();
            System.out.println("PaymentTypeIntegrator.getPaymentTypeList | SQL: " + stmt.toString());
            while(rs.next()){
                paymentTypeList.add(generatePaymentType(rs));
            }
            
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                throw new IntegrationException("Cannot get payment type List", ex);
            } finally {
                if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
                if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
                if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
            }
            return paymentTypeList;   
    }
    
    public void insertPaymentType(PaymentType paymentType) throws IntegrationException {
        String query = "INSERT INTO public.paymenttype(\n" +
                    "    typeid, pmttypetitle)\n" +
                    "    VALUES (DEFAULT, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, paymentType.getPaymentTypeId());
            stmt.setString(1, paymentType.getPaymentTypeTitle());
            System.out.println("PaymentTypeIntegrator.paymentTypeIntegrator | sql: " + stmt.toString());
            System.out.println("TRYING TO EXECUTE INSERT METHOD");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Payment Type", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    
    public void deletePaymentType(PaymentType pt) throws IntegrationException{
         String query = "DELETE FROM public.paymenttype\n" +
                        " WHERE typeid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, pt.getPaymentTypeId());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete payment type--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private PaymentType generatePaymentType(ResultSet rs) throws IntegrationException {
        PaymentType newPtype = new PaymentType();
        try {
            newPtype.setPaymentTypeId(rs.getInt("typeid"));
            newPtype.setPaymentTypeTitle(rs.getString("pmttypetitle"));
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating Payment Type from result set", ex);
        }
        return newPtype;
    }
    
    public LinkedList<PaymentType> getPaymentTypeTitleList() throws IntegrationException{
        LinkedList<PaymentType> payTypeList = new LinkedList<>();
        
        Connection con = getPostgresCon();
        String query = "SELECT typeid FROM paymenttype;";
        ResultSet rs = null;
        Statement stmt = null;
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                payTypeList.add(getPaymentTypeFromPaymentTypeID(rs.getInt("typeid")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Exception in PaymentTypeIntegrator.getPaymentTypeTitleList", ex);

        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return payTypeList;
    }
    
    /* I think I built this all by accident while trying to de-bug my converter...
        public void generatePaymentTypeTitleIDMap() throws IntegrationException{
        HashMap<String, Integer> payMap = new HashMap<>();
        
        Connection con = getPostgresCon();
        String query = "SELECT typeid, pmttypetitle FROM paymenttype;";
        ResultSet rs = null;
        Statement stmt = null;
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                payMap.put(rs.getString("pmttypetitle"), rs.getInt("typeid"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Exception in PaymentTypeIntegrator.generatePaymentTypeTitleIDMap", ex);

        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored * } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored * } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored *} }
        } // close finally
        
        paymentTypeMap = payMap;
    }

    /**
     * @return the paymentTypeMap
     *
    public HashMap getPaymentTypeMap() throws IntegrationException {
        generatePaymentTypeTitleIDMap();
        return paymentTypeMap;
    }

    /**
     * @param paymentTypeMap the paymentTypeMap to set
     *
    public void setPaymentTypeMap(HashMap paymentTypeMap) {
        this.paymentTypeMap = paymentTypeMap;
    }
    */
    

}
