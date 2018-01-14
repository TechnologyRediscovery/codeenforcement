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
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.Person;
import com.tcvcog.tcvce.entities.PersonType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Connects Person objects to the data store
 * @author sylvia
 */
public class PersonIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of PersonIntegrator
     */
    public PersonIntegrator() {
        System.out.println("PersonIntegrator.PersonIntegrator - constructor");
    }
    
    
    /**
     * Looks up a person given a personID and creates a returns a new instance
     * of Person with all the available information loaded about that person
     * @param personId the id of the person to look up
     * @return a Person object with all of the available data loaded
     */
    public Person getPerson(int personId) throws IntegrationException{
        
        Connection con = getPostgresCon();
        
        ResultSet rs = null;
        Person person = null;
        
        try {
            String s =  "SELECT personid, personType, fName, lName, "
                    + "phoneCell, phoneHome, phoneWork, "
                    + "email, address_street, address_city, "
                    + "address_zip, notes, lastUpdated \n"
                    + "FROM public.person"
                    + "WHERE personid = "
                    + personId + ";";
            PreparedStatement stmt = con.prepareStatement(s);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                // note that rs.next() is called and the cursor
                // is advanced to the first row in the rs
                person = generatePersonFromResultSet(rs);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot search for person", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return person;
    }
    
    /**
     * Accepts a result set and creates a new Person object. Note that this
     * method does not move the cursor position on the result set passed in;
     * rather, it will create a person based on the row to which the cursor is
     * pointing when it is passed in. 
     * The client method is responsible for managing cursor position
     * 
     * Also note that the client method is responsible for closing the 
     * result set that is passed to this method with rs.close();
     * 
     * @param rs the result set from which to extract data for creating a person
     * @return the new person object created from this single row of the rs
     */
    private Person generatePersonFromResultSet(ResultSet rs) throws IntegrationException{
        // Instantiates the new person object
        Person newPerson = new Person();
            
        try {
            newPerson.setPersonid(rs.getInt("personid"));
            newPerson.setPersonType(PersonType.valueOf(rs.getString("personType")));
            newPerson.setFirstName(rs.getString("fName"));
            newPerson.setLastName(rs.getString("lName"));
            newPerson.setPhoneCell(rs.getString("phoneCell"));
            newPerson.setPhoneHome(rs.getString("phoneHome"));
            newPerson.setPhoneWork(rs.getString("phoneWork"));
            newPerson.setEmail(rs.getString("email"));
            newPerson.setAddress_street(rs.getString("address_street"));
            newPerson.setAddress_city(rs.getString("address_city"));
            newPerson.setAddress_zip(rs.getString("address_zip"));
            newPerson.setNotes(rs.getString("notes"));
            //newPerson.setLastUpdated(rs.getDate("lastUpdated").toLocalDate());
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error adding new person to DB", ex);
        }
        return newPerson;
    } // close method
    
    /**
     * Implements a basic person search by first and last name parts and
     * returns a Person object based on those results. If more than one person
     * is matched on the query, only the first one is returned since
     * the cursor is only moved to the first row when passed to the
     * createPersonFromResultSet() method
     * 
     * @param fname a first name fragment to use in the query
     * @param lname a last name fragment to use in the query
     * @return the new Person() object generated from the query
     */
    public LinkedList searchForPerson(String fname, String lname) throws IntegrationException{
        Connection con = getPostgresCon();
        LinkedList ll = new LinkedList();
        ResultSet rs = null;
        
        try {
            String s =  "SELECT personid, personType, fName, lName, "
                    + "phoneCell, phoneHome, phoneWork, "
                    + "email, address_street, address_city, "
                    + "address_zip, notes \n"
                    + "FROM public.person "
                    + "WHERE fname ILIKE '%"
                    + fname + "%' AND lname ILIKE '%"
                    + lname + "%';";
            PreparedStatement stmt = con.prepareStatement(s);
            System.out.println("PersonIntegrator.searchForPerson | sql: " + stmt.toString());
            rs = stmt.executeQuery();
            
            if(rs.next()){
                // note that rs.next() is called and the cursor
                // is advanced to the first row in the rs
                
                ll.add(generatePersonFromResultSet(rs));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot search for person", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return ll;
        
    } // close method
    
    /**
     * Accepts a Person object to store in the database.
     * 
     * @param personToStore the person to store
     * @return 
     */
    public int insertPerson(Person personToStore){
        Connection con = getPostgresCon();
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        int newPersonId = 0;
        
        query.append("INSERT INTO public.person(\n" 
                + "personid, personType, fName, lName, "
                + "phonecell, phonehome, \n" 
                + "phonework, email, address_street, \n "
                + "address_city, address_zip, \n" 
                + "notes)\n" 
                + "VALUES (DEFAULT, CAST(? AS personType), ?, ?, ?, ?, \n" 
                + "?, ?, ?, ?, ?, ?);");
        
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query.toString());
            // default ID generated by sequence in PostGres
            stmt.setString(1, personToStore.getPersonType().toString());
            stmt.setString(2, personToStore.getFirstName());
            stmt.setString(3, personToStore.getLastName());
            
            stmt.setString(4, personToStore.getPhoneCell());
            stmt.setString(5, personToStore.getPhoneHome());
            
            stmt.setString(6, personToStore.getPhoneWork());
            stmt.setString(7, personToStore.getEmail());
            stmt.setString(8, personToStore.getAddress_street());
            
            stmt.setString(9, personToStore.getAddress_city());
            stmt.setString(10, personToStore.getAddress_zip());
            
            stmt.setString(11, personToStore.getNotes());
            //stmt.setDate(12, java.sql.Date.valueOf(personToStore.getLastUpdated()));
            
            stmt.executeQuery();
            
            // now look up the person we just stored to get their ID
            // and confirm that the person is in the system and retrievable
             
            // getting the ID of the most recently inserted person
            // must be retrieved during the same session in which the INSERT
            // was executed. Safe in multithreading
            String s =  "SELECT currval('person_personIDSeq');";
            stmt = con.prepareStatement(s);
            rs = stmt.executeQuery();
            if(rs.next()){
                newPersonId = rs.getInt("currval");
            }
            
        } catch (SQLException ex) {
            // call logger on backingbeanutils
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return newPersonId;
    } // close insertPerson()
    
    /**
     * Generates a linked list of Person objects given an array of person ID
     * numbers. This is accomplished by making repeated calls to the 
     * createPersonFromResultSet() method.
     * 
     * @return a linked list of person objects that can be used for display
     * and selection
     * @param people an integer array containing Person id numbers to be
     * converted into a linkedList of Person objects for display in the view
     */
    public LinkedList<Person> getPersonList(int[] people) throws IntegrationException{
        LinkedList<Person> list = new LinkedList<>();
        
        // loop through the array of integers provided and ask
        // our getPersonByID() method for a person object associated with
        // each id
        for(int i = 0; i<people.length; i++){
            list.add(PersonIntegrator.this.getPerson(people[i]));
        }
        return list;
    } // close getPersonListe()
    
    /**
     * Updates a given record for a person in the database.
     * Will throw an error if the person does not exist. 
     * Note that this method will retrieve the Person object's ID number
     * to determine which record to update in the db
     * 
     * @param personToUpdate the Person object with the updated data
     * to be stored in the database. All old information will be overwritten
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public void updatePerson(Person personToUpdate) throws IntegrationException{
        Connection con = getPostgresCon();
        String query;
        
        query =   "UPDATE pulblic.person SET"
                + "personType = CAST(? AS personType),"
                + "fName = ?,"
                + "lName = ?,"
                + "phonecell = ?,"
                + "phonehome = ?," 
                + "phonework = ?,"
                + "email = ?,"
                + "address_street = ?,"
                + "address_city = ?,"
                + "address_zip = ?," 
                + "notes = ?"
                + "lastUpdated = ?"
                + "WHERE personId = ?;";
        
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query);
            // default ID generated by sequence in PostGres
            stmt.setString(1, personToUpdate.getPersonType().toString());
            stmt.setString(2, personToUpdate.getFirstName());
            stmt.setString(3, personToUpdate.getLastName());
            
            stmt.setString(4, personToUpdate.getPhoneCell());
            stmt.setString(5, personToUpdate.getPhoneHome());
            
            stmt.setString(6, personToUpdate.getPhoneWork());
            stmt.setString(7, personToUpdate.getEmail());
            stmt.setString(8, personToUpdate.getAddress_street());
            
            stmt.setString(9, personToUpdate.getAddress_city());
            stmt.setString(10, personToUpdate.getAddress_zip());
            
            stmt.setString(11, personToUpdate.getNotes());
            stmt.setDate(12, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.setInt(13, personToUpdate.getPersonid());
            
            stmt.executeQuery();
        
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update person");
            
        
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    } // close updatePerson
    
    public HashMap getPersonMapByPropertyID(int propertyID){
        
        return new HashMap();
    }
    
    public HashMap getPersonMapByCaseID(int caseID){
        
        return new HashMap();
    }
    
    public void deletePerson(int personToDeleteID){
        
        
    }
    
    
} // close class
