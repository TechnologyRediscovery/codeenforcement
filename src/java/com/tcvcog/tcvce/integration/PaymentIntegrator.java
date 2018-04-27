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
import com.tcvcog.tcvce.occupancy.Payment;
import com.tcvcog.tcvce.occupancy.PaymentType;
import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Adam Gutonski
 */
public class PaymentIntegrator extends BackingBeanUtils implements Serializable {
    
    public PaymentIntegrator() {
        
    }
    
    public void updatePayment(Payment payment) throws IntegrationException {
        String query = "UPDATE public.payment\n" +
                    "   SET occinspec_inspectionid=?, paymenttype_typeid=?, \n" +
                    "       datereceived=?, datedeposited=?, amount=?, payerid=?, referencenum=?, \n" +
                    "       checkno=?, cleared=?\n" +
                    " WHERE paymentid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, payment.getOccupancyInspectionID());
            stmt.setInt(2, payment.getPaymentType().getPaymentTypeId());
            //update date received
            if(payment.getPaymentDateReceived() != null){
                stmt.setTimestamp(3, java.sql.Timestamp.valueOf(payment.getPaymentDateReceived()));
                
            } else {
                stmt.setNull(3, java.sql.Types.NULL);
            }
            //update date deposited
            if(payment.getPaymentDateDeposited() != null){
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(payment.getPaymentDateDeposited()));
                
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }
            stmt.setDouble(5, payment.getPaymentAmount());
            stmt.setInt(6, payment.getPaymentPayerID());
            stmt.setString(7, payment.getPaymentReferenceNum());
            stmt.setInt(8, payment.getCheckNum());
            stmt.setBoolean(9, payment.isCleared());
            stmt.setInt(10, payment.getPaymentID());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update payment", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public LinkedList<Payment> getPaymentList() throws IntegrationException {
        String query = "SELECT paymentid, occinspec_inspectionid, paymenttype_typeid, datereceived, \n" +
                    "       datedeposited, amount, payerid, referencenum, checkno, cleared\n" +
                    "  FROM public.payment;";
            Connection con = getPostgresCon();
            ResultSet rs = null;
            PreparedStatement stmt = null;
            LinkedList<Payment> paymentList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            System.out.println("");
            System.out.println("TRYING TO GET PAYMENT LIST");
            rs = stmt.executeQuery();
            System.out.println("PaymentIntegrator.getPaymentList | SQL: " + stmt.toString());
            while(rs.next()){
                paymentList.add(generatePayment(rs));
            }
            
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                throw new IntegrationException("Cannot get Payment List", ex);
            } finally {
                if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
                if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
                if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
            }
            return paymentList;  
    }
    
    public void insertPayment(Payment payment) throws IntegrationException {
        String query = "INSERT INTO public.payment(\n" +
                        "            paymentid, occinspec_inspectionid, paymenttype_typeid, datereceived, \n" +
                        "            datedeposited, amount, payerid, referencenum, checkno, cleared)\n" +
                        "    VALUES (DEFAULT, ?, ?, ?, \n" +
                        "            ?, ?, ?, ?, ?, DEFAULT);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, payment.getOccupancyInspectionID());
            stmt.setInt(2, payment.getPaymentType().getPaymentTypeId());
            if(payment.getPaymentDateReceived() != null){
                stmt.setTimestamp(3, java.sql.Timestamp.valueOf(payment.getPaymentDateReceived()));
                
            } else {
                stmt.setNull(3, java.sql.Types.NULL);
            }
            if(payment.getPaymentDateDeposited() != null){
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(payment.getPaymentDateDeposited()));
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }
            stmt.setDouble(5, payment.getPaymentAmount());
            stmt.setInt(6, payment.getPaymentPayerID());
            stmt.setString(7, payment.getPaymentReferenceNum());
            stmt.setInt(8, payment.getCheckNum());
            System.out.println("PaymentIntegrator.paymentIntegrator | sql: " + stmt.toString());
            System.out.println("TRYING TO EXECUTE INSERT METHOD");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert payment", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void deletePayment(Payment payment) throws IntegrationException {
         String query = "DELETE FROM public.payment\n" +
                        " WHERE paymentid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, payment.getPaymentID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete payment record--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private Payment generatePayment(ResultSet rs) throws IntegrationException {
        Payment newPayment = new Payment();
        PaymentTypeIntegrator pti = getPaymentTypeIntegrator();
        
        try {
            newPayment.setPaymentID(rs.getInt("paymentid"));
            newPayment.setOccupancyInspectionID(rs.getInt("occinspec_inspectionid"));
            newPayment.setPaymentType(pti.getPaymentTypeFromPaymentTypeID(rs.getInt("paymenttype_typeid")));
            java.sql.Timestamp dateReceived = rs.getTimestamp("datereceived");
            //for effective date
            if(dateReceived != null) {
                newPayment.setPaymentDateReceived(dateReceived.toLocalDateTime());
            } else {
                newPayment.setPaymentDateReceived(null);
            }
            java.sql.Timestamp dateDeposited = rs.getTimestamp("datedeposited");
            //for expiration date
            if(dateDeposited != null) {
                newPayment.setPaymentDateDeposited(dateDeposited.toLocalDateTime());
            } else {
                newPayment.setPaymentDateDeposited(null);
            }
            newPayment.setPaymentAmount(rs.getDouble("amount"));
            newPayment.setPaymentPayerID(rs.getInt("payerid"));
            newPayment.setPaymentReferenceNum(rs.getString("referencenum"));
            newPayment.setCheckNum(rs.getInt("checkno"));
            newPayment.setCleared(rs.getBoolean("cleared"));
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating Payment from result set", ex);
        }
        return newPayment;
    }

}
