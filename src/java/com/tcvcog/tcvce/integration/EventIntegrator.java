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
import com.tcvcog.tcvce.domain.EventExceptionDeprecated;
import com.tcvcog.tcvce.domain.EventException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.Event;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.entities.Person;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class EventIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of EventIntegrator
     */
    public EventIntegrator() {
    }
    
    public EventCategory getEventCategory(int catID) throws IntegrationException{
        
        String query = "SELECT categoryid, categorytype, title, description\n" +
            "  FROM public.ceeventcategory WHERE categoryID = ?";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        EventCategory ec = new EventCategory();
        
        try {
            
            stmt = con.prepareStatement(query);
            stmt.setInt(1, catID);
            //System.out.println("EventInteegrator.getEventCategory| sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ec.setCategoryID(rs.getInt("categoryid"));
                ec.setEventType(EventType.valueOf(rs.getString("categoryType")));
                ec.setEventCategoryTitle(rs.getString("title"));
                ec.setEventCategoryDesc(rs.getString("description"));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot get event categry", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return ec;
    }
    
    public LinkedList<EventCategory> getEventCategoryList() throws IntegrationException{
         String query = "SELECT categoryid " +
            "  FROM public.ceeventcategory;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<EventCategory> categoryList = new LinkedList();
        
        try {
            
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            System.out.println("EventIntegrator.getEventCategoryList | SQL: " + stmt.toString());
            
            while(rs.next()){
                categoryList.add(getEventCategory(rs.getInt("categoryid")));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot generate list of event categories", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return categoryList;
    }
    
    public LinkedList<EventCategory> getEventCategoryList(EventType et) throws IntegrationException{
         String query = "SELECT categoryid " +
            "  FROM public.ceeventcategory WHERE categorytype = cast (? as ceeventtype);";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<EventCategory> categoryList = new LinkedList();
        
        try {
            
            stmt = con.prepareStatement(query);
            stmt.setString(1, et.toString());
            rs = stmt.executeQuery();
            System.out.println("EventIntegrator.getEventCategoryList | SQL: " + stmt.toString());
            
            while(rs.next()){
                categoryList.add(getEventCategory(rs.getInt("categoryid")));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot generate list of event categories", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return categoryList;
    }
    
    
    public void insertEventCategory(EventCategory ec) throws IntegrationException{
        
        String query = "INSERT INTO public.ceeventcategory(\n" +
                " categoryid, categorytype, title, description)\n" +
                " VALUES (DEFAULT, CAST (? as ceeventtype), ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, ec.getEventType().name());
            stmt.setString(2, ec.getEventCategoryTitle());
            stmt.setString(3, ec.getEventCategoryDesc());
            
            System.out.println("EventInteegrator.insertEventCategory| sql: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to insert event category", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void updateEventCategory(EventCategory ec) throws IntegrationException{
        
          String query = "UPDATE public.ceeventcategory\n" +
            "   SET categorytype= CAST (? as ceeventtype), title=?, description=?\n" +
            " WHERE categoryid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, ec.getEventType().name());
            stmt.setString(2, ec.getEventCategoryTitle());
            stmt.setString(3, ec.getEventCategoryDesc());
            stmt.setInt(4, ec.getCategoryID());
            
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update event category", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void deleteEventCategory(EventCategory ec) throws IntegrationException{
         String query = "DELETE FROM public.ceeventcategory\n" +
                " WHERE categoryid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ec.getCategoryID());
           
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete event--probably because another"
                    + "part of the database has a reference to this event category. Next best: marking"
                    + "the event as inactive.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void insertEvent(Event event) throws IntegrationException{
        PersonIntegrator pi = getPersonIntegrator();
        int insertedEventID = 0;
        
        String query = "INSERT INTO public.ceevent(\n" +
            "            eventid, ceeventcategory_catid, cecase_caseid, dateofrecord, \n" +
            "            eventtimestamp, eventdescription, login_userid, disclosetomunicipality, \n" +
            "            disclosetopublic, activeevent, notes)\n" +
            "    VALUES (DEFAULT, ?, ?, ?, \n" +
            "            now(), ?, ?, ?, \n" +
            "            ?, ?, ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, event.getCategory().getCategoryID());
            stmt.setInt(2, event.getCaseID());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getDateOfRecord()));
            
            // note that the timestamp is set by a call to postgres's now()
            stmt.setString(4, event.getEventDescription());
            stmt.setInt(5, event.getEventOwnerUser().getUserID());
            stmt.setBoolean(6, event.isDiscloseToMunicipality());
            
            stmt.setBoolean(7, event.isDiscloseToPublic());
            stmt.setBoolean(8, event.isActiveEvent());
            stmt.setString(9, event.getNotes());
            
            System.out.println("EventIntegrator.insertEventCategory| sql: " + stmt.toString());
            stmt.execute();
            
            String retrievalQuery = "SELECT currval('ceevent_eventID_seq');";
            stmt = con.prepareStatement(retrievalQuery);
            
            rs = stmt.executeQuery();
            while(rs.next()){
                insertedEventID = rs.getInt(1);
                System.out.println("EventIntegrator.insertEvent | retrieved eventID: " + insertedEventID);
                
            }
            

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Event into system", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        // now connect people to event that has already been logged
        
        LinkedList<Person> ll = event.getEventPersons();
        event.setEventID(insertedEventID);
        
        if(ll != null){
            if( ll.size() > 0 && event.getEventID() != 0){
                pi.connectPersonsToEvent(event, ll);
            } 
        }
        
        
    } // close method
    
    
    
    public void updateEvent(Event event) throws IntegrationException{
        String query = "UPDATE public.ceevent\n" +
            "   SET ceeventcategory_catid=?, cecase_caseid=?, dateofrecord=?, \n" +
            "       eventtimestamp=now(), eventdescription=?, login_userid=?, disclosetomunicipality=?, \n" +
            "       disclosetopublic=?, activeevent=?, notes=?\n" +
            " WHERE eventid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, event.getCategory().getCategoryID());
            stmt.setInt(2, event.getCaseID());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getDateOfRecord()));
            
            // timestamp is updated with a call to postgres's now()
            stmt.setString(4, event.getEventDescription());
            stmt.setInt(5, event.getEventOwnerUser().getUserID());
            stmt.setBoolean(6, event.isDiscloseToMunicipality());
            
            stmt.setBoolean(7, event.isDiscloseToPublic());
            stmt.setBoolean(8, event.isActiveEvent());
            stmt.setString(9, event.getNotes());
            
            System.out.println("EventInteegrator.getEventByEventID| sql: " + stmt.toString());

           stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot retrive event", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void deleteEvent(Event event) throws IntegrationException{
        String query = "DELETE FROM public.ceevent WHERE eventid = ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, event.getCaseID());

           stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete event--probalby because one or"
                    + "more other entries reference this event. ", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    private Event generateEventFromRS(ResultSet rs) throws SQLException, IntegrationException{
        Event ev = new Event();
        UserIntegrator ui = getUserIntegrator();
        
        ev.setEventID(rs.getInt("eventid"));
        ev.setCategory(getEventCategory(rs.getInt("ceeventCategory_catID")));
        ev.setCaseID(rs.getInt("cecase_caseid"));
        LocalDateTime dt = rs.getTimestamp("dateofrecord").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        ev.setDateOfRecord(dt);
        ev.setPrettyDateOfRecord(getPrettyDate(dt));
        
        ev.setEventTimeStamp(rs.getTimestamp("eventtimestamp").toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        ev.setEventDescription(rs.getString("eventDescription"));
        ev.setEventOwnerUser(ui.getUserByUserID(rs.getInt("login_userid")));
        ev.setDiscloseToMunicipality(rs.getBoolean("disclosetomunicipality"));
        
        ev.setDiscloseToPublic(rs.getBoolean("disclosetopublic"));
        ev.setActiveEvent(rs.getBoolean("activeevent"));
        ev.setNotes(rs.getString("notes"));
        
        return ev;
    }
    
    public Event getEventByEventID(int eventID) throws IntegrationException{
        Event ev = null;
        
        String query = "SELECT eventid, ceeventcategory_catid, cecase_caseid, dateofrecord, \n" +
            "       eventtimestamp, eventdescription, login_userid, disclosetomunicipality, \n" +
            "       disclosetopublic, activeevent, notes, eventtype\n" +
            "  FROM public.ceevent WHERE eventid = ?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement(query);
            stmt.setInt(1, eventID);
            System.out.println("EventInteegrator.getEventByEventID| sql: " + stmt.toString());
            rs = stmt.executeQuery();

            while(rs.next()){
                ev = generateEventFromRS(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot retrive event", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return ev;
    }
    
    public LinkedList getEventsByCaseID(int caseID) throws IntegrationException{
        LinkedList<Event> eventList = new LinkedList();
        
        String query = "SELECT * FROM public.ceevent WHERE cecase_caseid = ?;";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, caseID);
            System.out.println("EventIntegrator.getEventsByCaseID| sql: " + stmt.toString());
            rs = stmt.executeQuery();

            while(rs.next()){
                eventList.add(generateEventFromRS(rs));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot generate case list", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return eventList;
    }
    

    
    
} // close class

//
//
// String query = "SELECT categoryid, categorytype, title, description\n" +
//            "  FROM public.ceeventcategory WHERE categoryID = ?";
//        Connection con = getPostgresCon();
//        ResultSet rs = null;
//        PreparedStatement stmt = null;
//        EventCategory ec = new EventCategory();
//        
//        try {
//            
//            stmt = con.prepareStatement(query);
//            stmt.setInt(1, catID);
//            System.out.println("EventInteegrator.getEventCategory| sql: " + stmt.toString());
//            rs = stmt.executeQuery();
//            
//            while(rs.next()){
//                ec.setCategoryID(rs.getInt("categoryid"));
//                ec.setEventType(EventType.valueOf(rs.getString("categoryType")));
//                ec.setEventTypeTitle(rs.getString("title"));
//                ec.setEventTypeDesc(rs.getString("description"));
//            }
//            
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//            throw new IntegrationException("Cannot search for person", ex);
//            
//        } finally{
//             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
//             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
//             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
//        } // close finally