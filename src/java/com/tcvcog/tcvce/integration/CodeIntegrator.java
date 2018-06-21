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
import java.io.Serializable;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.CodeElementGuideEntry;
import com.tcvcog.tcvce.entities.CodeSet;
import com.tcvcog.tcvce.entities.CodeElementEnforcable;
import com.tcvcog.tcvce.entities.Municipality;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.ActionSource;

/**
 *
 * @author Eric C. Darsow
 */
public class CodeIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of CodeIntegrator
     */
    public CodeIntegrator() {
        System.out.println("CodeIntegrator.CodeIntegrator");
        
    }

    /**
     * Utility method for use by methods in this Integrator which allows 
     * easy filling of code-related objects with fully-baked CodeSource objects
     * Note that client methods must manage cursor positins on the ResultSet 
     * passed in.
     *     
     * 
     * @param rs
     * 
     * @param source Clients are responsible for instantiating an emtpy CodeSource
     * and passing a reference into this method
     * 
     * @return a fully-baked CodeSource from the database
     * 
     * @throws SQLException 
     */
    private CodeSource populateCodeSourceFromRS(ResultSet rs, CodeSource source) throws SQLException{
        
        
        source.setSourceID(rs.getInt(1));
        source.setSourceName(rs.getString(2));
        source.setSourceYear(rs.getInt(3));
        source.setSourceDescription(rs.getString(4));
        source.setIsActive(rs.getBoolean(5));
        source.setURL(rs.getString(6));
        source.setSourceNotes(rs.getString(7));
        return source;
        
    }
    
    /**
     * Retrieves code source information given a sourceID
     * @param sourceID the unique ID number of the code source in the DB
     * @return the CodeSource object built from the DB read
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public CodeSource getCodeSource(int sourceID) throws IntegrationException{
        CodeSource source = new CodeSource();

        String query = "SELECT sourceid, name, year, "
                + "description, isactive, url, "
                + "notes\n" 
                + " FROM public.codesource WHERE sourceid = ?;";
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, sourceID);
            rs = stmt.executeQuery();
            while(rs.next()){
                populateCodeSourceFromRS(rs, source);

            }
            
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Exception in CodeIntegrator", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return source;
    }
    
    /**
     * Takes in a CodeSource object and inserts into the DB
     * @param sourceToInsert
     */
    public void insertCodeSource(CodeSource sourceToInsert){
        String query = "INSERT INTO public.codesource(\n" +
            "sourceid, name, year, description, isactive, url, notes)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";

        // create sql statement up here
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, sourceToInsert.getSourceName());
            stmt.setInt(2, sourceToInsert.getSourceYear());
            stmt.setString(3, sourceToInsert.getSourceDescription());
            stmt.setBoolean(4, sourceToInsert.isIsActive());
            stmt.setString(5, sourceToInsert.getURL());
            stmt.setString(6, sourceToInsert.getSourceNotes());
            stmt.executeUpdate();
            System.out.println("CodeIntegrator.insertCodeSource: executed update with SQL - " + stmt.toString());
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Business Object (Case, Violation, ...) Database Problem", 
                            "SQLException Caught by Integrator"));
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    } 
    
    /**
     * Updates fields in the DB for a given CodeSource objects
     * @param sourceToUpdate
     */
    public void updateCodeSource(CodeSource sourceToUpdate){
         String query = "UPDATE public.codesource\n" +
                "   SET name=?, year=?, description=?, isactive=?, url=?, \n" +
                "       notes=?\n" +
                " WHERE sourceid=?;";

        // create sql statement up here
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, sourceToUpdate.getSourceName());
            stmt.setInt(2, sourceToUpdate.getSourceYear());
            stmt.setString(3, sourceToUpdate.getSourceDescription());
            stmt.setBoolean(4, sourceToUpdate.isIsActive());
            stmt.setString(5, sourceToUpdate.getURL());
            stmt.setString(6, sourceToUpdate.getSourceNotes());
            stmt.setInt(7, sourceToUpdate.getSourceID());
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Business Object (Case, Violation, ...) Database Problem", 
                            "SQLException Caught by Integrator"));
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    /**
     * Removes the provided code source from the DB
     * @param source the CodeSource to remove from the DB
     */
    public void deleteCodeSource(CodeSource source){
        // TODO: Write guts
    }
    
    /**
     * Utility method for converting a ArrayList of CodeSource objects
     * into a map with the name of the CodeSource as the key and the 
     * CodeSource object as the value. Designed for selectItem components
     * that the view likes to eat for breakfast.
     * @return fully-baked hasmap of CodeSource names and CodeSource objects
     * @throws IntegrationException 
     */
    public HashMap getCodeSourceMap() throws IntegrationException{
        ArrayList<CodeSource> csList = getCompleteCodeSourceList();
        HashMap<String, Integer> csMap = new HashMap();
        
        ListIterator li = csList.listIterator();
        while(li.hasNext()){
            CodeSource cs = (CodeSource) li.next();
            csMap.put(cs.getSourceName(), cs.getSourceID());
        }
        return csMap;
    }
    
    /**
     * Retrieves all existing code sources from the DB and builds CodeSource
     * objects for each one, populating all the fields
     * @return all code sources in the DB as CodeSource objects
     * @throws IntegrationException 
     */
    public ArrayList getCompleteCodeSourceList() throws IntegrationException{
        String query = "SELECT sourceid, name, year, "
              + "description, isactive, url, "
              + "notes\n" 
              + " FROM public.codesource;";
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CodeSource> codeSources = new ArrayList();
        CodeSource source;
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                source = populateCodeSourceFromRS(rs, new CodeSource());
                if(source != null){
                    // figure out about this possible dereferencing warning
                    codeSources.add(source);
                }
            }
            
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Exception in CodeIntegrator", ex);
        } finally{
             if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return codeSources;
    }
    
    public CodeSet getCodeSetBySetID(int setID) throws IntegrationException{
         String query = "SELECT codesetid, name, description, municipality_municode \n" +
                        "FROM public.codeset WHERE codesetid = ?";
        
         //System.out.println("CodeIntegrator.getCodeSets | MuniCode: "+ muniCode);
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CodeSet cs = null;
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, setID);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                cs = (populateCodeSetFromRS(rs));
            }
            
        } catch (SQLException ex) { 
             System.out.println("CodeIntegrator.getCodeSetBySetID | " + ex.toString());
             throw new IntegrationException("Exception in CodeSetIntegrator", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return cs;
        
    }
    
    public String getCodeSetName(int setID){
        String codeSetName;
        return "";
        
    }
    
    private CodeSet populateCodeSetFromRS(ResultSet rs) throws SQLException, IntegrationException{
        CodeSet set = new CodeSet();
        MunicipalityIntegrator muniInt = getMunicipalityIntegrator();
        set.setCodeSetID(rs.getInt("codesetid"));
        set.setCodeSetName(rs.getString("name"));
        set.setCodeSetDescription(rs.getString("description"));
        set.setEnfCodeElementList(getEnforcableCodeElementList(rs.getInt("codesetid")));
        int muniCodeTest = rs.getInt("municipality_municode");
        set.setMuni(muniInt.getMuniFromMuniCode(muniCodeTest));
        if (set.getMuni() == null){
            throw new IntegrationException("Exception in CodeSetIntegrator: Cannot find any code sets for selected Municipality");
        }

        return set;
        
    }
    
    public ArrayList getCodeSets(int muniCode) throws IntegrationException{
         String query = "SELECT codesetid, name, description, municipality_municode \n" +
                        "FROM public.codeset WHERE municipality_municode = ?";
        
         //System.out.println("CodeIntegrator.getCodeSets | MuniCode: "+ muniCode);
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CodeSet> codeSetList = new ArrayList();
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, muniCode);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                codeSetList.add(populateCodeSetFromRS(rs));
            }
            
        } catch (SQLException ex) { 
             System.out.println("CodeIntegrator.getCodeSetByMuniCode | " + ex.toString());
             throw new IntegrationException("Exception in CodeSetIntegrator", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return codeSetList;
    }
    
    /**
     * Creates what we call an CodeElementEnforcable, which means
 we find an existing code element and add muni-specific 
 enforcement data to that element and store it in the DB
 
 This operation adds an entry to table codesetelement
 and uses the ID of the codeSet and CodeElement to make
 the many-to-many links in the database.
     * 
     * @param enforcableCodeElement
     * @param codeSetID 
     * @throws com.tcvcog.tcvce.domain.IntegrationException 
     */
    public void addEnforcableCodeElementToCodeSet(CodeElementEnforcable enforcableCodeElement, int codeSetID) throws IntegrationException{
        PreparedStatement stmt = null;
        Connection con = null;
        String query = "INSERT INTO public.codesetelement(\n" +
                    "codesetelementid, codeset_codesetid, codelement_elementid, elementmaxpenalty, \n" +
                    "elementminpenalty, elementnormpenalty, penaltynotes, normdaystocomply, \n" +
                    "daystocomplynotes)\n" +
                    " VALUES (DEFAULT, ?, ?, ?, \n" +
                    "?, ?, ?, ?, \n" +
                    "?);";
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, codeSetID);
            stmt.setInt(2, enforcableCodeElement.getCodeElement().getElementID() );
            stmt.setDouble(3, enforcableCodeElement.getMaxPenalty());
            stmt.setDouble(4, enforcableCodeElement.getMinPenalty());
            stmt.setDouble(5, enforcableCodeElement.getNormPenalty());
            stmt.setString(6, enforcableCodeElement.getPenaltyNotes());
            stmt.setInt(7, enforcableCodeElement.getNormDaysToComply());
            stmt.setString(8, enforcableCodeElement.getDaysToComplyNotes());
            System.out.println("CodeIntegrator.addCodeElementToCodeSet | ece insert: " + stmt.toString());
            
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Exception in CodeIntegrator.addEnforcableCodeElementToCodeSet", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void removeCodeElementFromCodeSet(CodeElement element ){
        
        
    }
    
    public int insertCodeSet(CodeSet codeSetToInsert) throws IntegrationException{
        
        PreparedStatement stmt = null;
        Connection con = null;
        // note that muniCode is not returned in this query since it is specified in the WHERE
        String query = "INSERT INTO public.codeset(\n" +
                "codesetid, name, description, municipality_municode)\n" +
                "VALUES (DEFAULT, ?, ?, ?);";
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, codeSetToInsert.getCodeSetName());
            stmt.setString(2, codeSetToInsert.getCodeSetDescription());
            stmt.setInt(3, codeSetToInsert.getMuniCode());
            System.out.println("CodeIntegrator.isnertCodeSet | query to insert: " + stmt.toString());
            stmt.execute();
            
        } catch (SQLException ex) {
            System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | " + ex.toString());
            throw new IntegrationException("Exception in MunicipalityIntegrator.getMuniFromMuniCode", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return 0;
    }
    
    /**
     * Creates and populates single CodeElementEnforcable object based on giving ID number. 
     * 
     * @param codeSetElementID
     * @return the fully-baked CodeElementEnforcable
     * @throws IntegrationException 
     */
    public CodeElementEnforcable getEnforcableCodeElement(int codeSetElementID) throws IntegrationException{
        CodeElementEnforcable newEce = new CodeElementEnforcable();
        PreparedStatement stmt = null;
        Connection con = null;
        String query = "SELECT codesetelementid, codeset_codesetid, codelement_elementid, elementmaxpenalty, \n" +
                " elementminpenalty, elementnormpenalty, penaltynotes, normdaystocomply, \n" +
                " daystocomplynotes\n" +
                " FROM public.codesetelement WHERE codesetelementid=?;";
        ResultSet rs = null;
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, codeSetElementID);
            rs = stmt.executeQuery();
            while(rs.next()){
                
                newEce.setCodeSetElementID(rs.getInt("codesetelementid"));
                newEce.setCodeElement(getCodeElement(rs.getInt("codelement_elementid")));
                newEce.setMaxPenalty(rs.getInt("elementmaxpenalty"));
                newEce.setMinPenalty(rs.getInt("elementminpenalty"));
                newEce.setNormPenalty(rs.getInt("elementnormpenalty"));
                newEce.setPenaltyNotes(rs.getString("penaltynotes"));
                newEce.setNormDaysToComply(rs.getInt("normdaystocomply"));
                newEce.setDaysToComplyNotes(rs.getString("daystocomplynotes"));
            }
            
        } catch (SQLException ex) {
            System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | " + ex.toString());
            throw new IntegrationException("Exception in MunicipalityIntegrator.getMuniFromMuniCode", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        return newEce;
    }
    
  /**
   * Returns a ArrayList of fully-baked CodeElementEnforcable objects based on a search with
 setID. Handy for then populating data tables of codes, such as in creating
   * a codeviolation. This involves a rather complicated object composition process
   * that draws on several other methods in this class for retrieving from the database
   * 
   * @param setID
   * @return all CodeElement objects kicked out by postgres with that setID
   * @throws com.tcvcog.tcvce.domain.IntegrationException
   */
    public ArrayList getEnforcableCodeElementList(int setID) throws IntegrationException{
        PreparedStatement stmt = null;
        Connection con = null;
        String query = "SELECT codesetelementid, codeset_codesetid, codelement_elementid, elementmaxpenalty, \n" +
                " elementminpenalty, elementnormpenalty, penaltynotes, normdaystocomply, \n" +
                " daystocomplynotes\n" +
                " FROM public.codesetelement where codeset_codesetid=?;";
        ResultSet rs = null;
        ArrayList<CodeElementEnforcable> eceList = new ArrayList();
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, setID);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                CodeElementEnforcable newEce = new CodeElementEnforcable();
                newEce.setCodeSetElementID(rs.getInt("codesetelementid"));
                newEce.setCodeElement(getCodeElement(rs.getInt("codelement_elementid")));
                newEce.setMaxPenalty(rs.getInt("elementmaxpenalty"));
                newEce.setMinPenalty(rs.getInt("elementminpenalty"));
                newEce.setNormPenalty(rs.getInt("elementnormpenalty"));
                newEce.setPenaltyNotes(rs.getString("penaltynotes"));
                newEce.setNormDaysToComply(rs.getInt("normdaystocomply"));
                newEce.setDaysToComplyNotes(rs.getString("daystocomplynotes"));
                eceList.add(newEce);
                
            }
        } catch (SQLException ex) {
            System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | " + ex.toString());
            throw new IntegrationException("Exception in MunicipalityIntegrator.getMuniFromMuniCode", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return eceList;
    }
    
    /**
     * Internal Utility method for loading up a CodeElement object given
     * a result set. Used by all sorts of CodeElement related methods
     * that are getting data for many code elements back in a single result set.
     * Note that the client method is responsible for manging ResultSet cursor
     * position 
     * @param rs With cursor positioned at the row to extract and populate from
     * @param e an empty CodeElement. Client class is responsible for instantiation
     * @return a populated CodeElement extracted from that row in the ResultSet
     */
    private CodeElement GenerateCodeElement(ResultSet rs, CodeElement e) throws SQLException, IntegrationException{
                
                // to ease the eyes, line spacing corresponds to the field spacing in CodeElement
        
                e.setElementID(rs.getInt("elementid"));
                
                e.setGuideEntry(getCodeElementGuideEntry(rs.getInt("guideentryid")));
                e.setSource(getCodeSource(rs.getInt("codesource_sourceid")));
                
                e.setOrdchapterNo(rs.getInt("ordchapterno"));
                
                e.setOrdchapterTitle(rs.getString("ordchaptertitle"));
                e.setOrdSecNum(rs.getString("ordsecnum"));
                e.setOrdsecTitle(rs.getString("ordsectitle"));
                
                e.setOrdSubSecNum(rs.getString("ordsubsecnum"));
                e.setOrdSubSecTitle(rs.getString("ordsubsectitle"));
                e.setOrdTechnicalText(rs.getString("ordtechnicaltext"));
                
                e.setOrdHumanFriendlyText(rs.getString("ordhumanfriendlytext"));
                e.setDefaultPenalty(rs.getDouble("defaultpenalty"));
                e.setIsActive(rs.getBoolean("isactive"));
                
                e.setIsEnforcementPriority(rs.getBoolean("isenforcementpriority"));
                e.setResourceURL(rs.getString("resourceurl"));
                e.setInspectionTips(rs.getString("inspectiontips"));
                
                e.setDateCreated(rs.getTimestamp("datecreated").toLocalDateTime());
                
        return e;
    }
    
    /**
     * Core method which grabs data related to a single CodeElement and populates
     * a CodeElement object which is returned to the client.
     * 
     * @param elementID
     * @return the loaded up CodeElement with database data
     * @throws IntegrationException 
     */
    public CodeElement getCodeElement(int elementID) throws IntegrationException{
        //System.out.println("CodeIntegrator.getCodeElement | fetching code element by ID");
        CodeElement newCodeElement = new CodeElement();
        PreparedStatement stmt = null;
        Connection con = null;
        // note that muniCode is not returned in this query since it is specified in the WHERE
        String query = "SELECT elementid, guideentryid, codesource_sourceid, ordchapterno, \n" +
            "ordchaptertitle, ordsecnum, ordsectitle, ordsubsecnum, ordsubsectitle, \n" +
            "ordtechnicaltext, ordhumanfriendlytext, defaultpenalty, isactive, \n" +
            "isenforcementpriority, resourceurl, inspectiontips, datecreated\n" +
            "FROM public.codeelement WHERE elementid=?;";
        ResultSet rs = null;
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, elementID);
            rs = stmt.executeQuery();
            
            // this query will only return 1 row since the WHERE clause selects from an PK column
            while(rs.next()){
                newCodeElement = GenerateCodeElement(rs, newCodeElement);
                 
            }
        } catch (SQLException ex) {
            System.out.println("CodeIntegrator.getCodeElementByElementID | " + ex.toString());
            throw new IntegrationException("Exception in CodeIntegrator.getCodeElementByElementID", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        return newCodeElement;
        
    }
    
    
    /**
     * Key method for returning a fully-assembled link of code elements by source ID.
     * Each code element returned by a single call to this method contains its own 
 instance of a CodeSource object whose field values are identical.
 
 NOTE these are CodeElement that can become CodeElementEnforcable when they are
 added to a codset (which is, in turn, associated with a single muni)
     * 
     * @param sourceID the CodeSource id used in the WHERE clause of the embedded SQL statment
     * @return Fully-baked CodeElement objects in a ArrayList
     * @throws IntegrationException Caught by backing beans and converted into
     * user messages
     */
    public ArrayList getCodeElements(int sourceID) throws IntegrationException{
        String query = "SELECT elementid from codeelement where codesource_sourceID = ?;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CodeElement> elementList = new ArrayList();
         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.setInt(1, sourceID);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                System.out.println("CodeIntegrator.getCodeElementsBySourceID | getting code element!");
                elementList.add(getCodeElement(rs.getInt("elementid")));
            }
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error Retrieving code element list", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
         return elementList;
    } // close getCodeElements
    
    public void insertCodeElement(CodeElement element) throws IntegrationException{
         String query = "INSERT INTO public.codeelement( " 
                 + "elementid, guideentryid, codesource_sourceid, "
                 + "ordchapterno, ordchaptertitle, ordsecnum, "
                 + "ordsectitle, ordsubsecnum, ordsubsectitle, " 
                 + "ordtechnicaltext, ordhumanfriendlytext, defaultpenalty, "
                 + "isactive, isenforcementpriority, "
                 + "resourceurl, inspectiontips, datecreated) " +
                    "    VALUES (DEFAULT, ?, ?, ?, \n" +
                    "            ?, ?, ?, ?, ?, \n" +
                    "            ?, ?, ?, ?, \n" +
                    "            ?, ?, ?, \n" +
                    "            now());";

        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, element.getGuideEntry().getGuideEntryID());
            stmt.setInt(2, element.getSource().getSourceID());
            
            stmt.setInt(3, element.getOrdchapterNo());
            stmt.setString(4, element.getOrdchapterTitle());
            stmt.setString(5, element.getOrdSecNum());
            
            stmt.setString(6, element.getOrdsecTitle());
            stmt.setString(7, element.getOrdSubSecNum());
            stmt.setString(8, element.getOrdSubSecTitle());
            
            stmt.setString(9, element.getOrdTechnicalText());
            stmt.setString(10, element.getOrdHumanFriendlyText());
            stmt.setDouble(11, element.getDefaultPenalty());
            
            stmt.setBoolean(12, element.isIsActive());
            stmt.setBoolean(13, element.isIsEnforcementPriority());
            stmt.setString(14, element.getResourceURL());
            
            stmt.setString(15, element.getInspectionTips());
            
            System.out.println("CodeIntegrator.insertCodeElement | insert statement: " + stmt.toString());
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void updateCodeElement(CodeElement element) throws IntegrationException{
        System.out.println("CodeIntegrator.udpateCodeElement | element to insert chapter name: " + element.getOrdchapterTitle());
          String query = "UPDATE public.codeelement\n" +
                "SET guideentryid=?, \n" +
                "ordchapterno=?, ordchaptertitle=?, ordsecnum=?, ordsectitle=?, \n" +
                "ordsubsecnum=?, ordsubsectitle=?, ordtechnicaltext=?, ordhumanfriendlytext=?, \n" +
                "defaultpenalty=?, isactive=?, isenforcementpriority=?, resourceurl=?, \n" +
                "inspectiontips=?, datecreated=now()\n" +
                " WHERE elementid=?;";

        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, element.getGuideEntry().getGuideEntryID());
            
            // no source changes on element update
            //stmt.setInt(2, element.getSource().getSourceID());
            
            stmt.setInt(2, element.getOrdchapterNo());
            stmt.setString(3, element.getOrdchapterTitle());
            stmt.setString(4, element.getOrdSecNum());
            
            stmt.setString(5, element.getOrdsecTitle());
            stmt.setString(6, element.getOrdSubSecNum());
            stmt.setString(7, element.getOrdSubSecTitle());
            
            stmt.setString(8, element.getOrdTechnicalText());
            stmt.setString(9, element.getOrdHumanFriendlyText());
            stmt.setDouble(10, element.getDefaultPenalty());
            
            stmt.setBoolean(11, element.isIsActive());
            stmt.setBoolean(12, element.isIsEnforcementPriority());
            stmt.setString(13, element.getResourceURL());
            
            stmt.setString(14, element.getInspectionTips());
            
            stmt.setInt(15, element.getElementID());
            
            System.out.println("CodeIntegrator.updateCodeElement | update statement: " + stmt.toString());
            
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    public void deleteCodeElement(CodeElement element) throws IntegrationException{
       
    }
    
   
    
    public void insertCodeElementGuideEntry(CodeElementGuideEntry cege) throws IntegrationException{
        String query =  "INSERT INTO public.codeelementguide(\n" +
                        "            guideentryid, category, subcategory, description, enforcementguidelines, \n" +
                        "            inspectionguidelines, priority)\n" +
                        "    VALUES (DEFAULT, ?, ?, ?, ?, \n" +
                        "            ?, ?);";
        
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, cege.getCategory());
            stmt.setString(2, cege.getSubCategory());
            stmt.setString(3, cege.getDescription());
            stmt.setString(4, cege.getEnforcementGuidelines());
            stmt.setString(5, cege.getInspectionGuidelines());
            stmt.setBoolean(6, cege.isPriority());
            
            System.out.println("CodeElementGuideBB.insertCodeElementGuideEntry | stmt: " + stmt.toString());
            
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element type", ex);
        } finally{
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
            
        } // close finally
    }
    
    public void updateCodeElementGuideEntry(CodeElementGuideEntry cdelType) throws IntegrationException{
        String query =  "UPDATE public.codeelementguide\n" +
                        "   SET category=?, subcategory=?, description=?, enforcementguidelines=?, \n" +
                        "       inspectionguidelines=?, priority=?\n" +
                        " WHERE guideentryid=?;";
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            // TODO: build statement
            
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error updating code element guide entry", ex);
        } finally{
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void updateCodeSet(CodeSet set) throws IntegrationException{
        String query = "UPDATE public.codeset\n" +
            "SET name=?, description=?, municipality_municode=?\n" +
            "WHERE codeSetid=?;";
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, set.getCodeSetName());
            stmt.setString(2, set.getCodeSetDescription());
            stmt.setInt(3, set.getMuniCode());
            stmt.setInt(4, set.getCodeSetID());
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error updating code set", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        
    }
    
    public void deleteCodeElementGuideEntry(CodeElementGuideEntry ge) throws IntegrationException{
        String query =  "DELETE FROM public.codeelementguide\n" +
                        " WHERE guidenetryid=?;";
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ge.getGuideEntryID());
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error deleting code element guide entry, "
                     + "probably because it has been connected to some other business object. No delete for you!", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    
    public CodeElementGuideEntry getCodeElementGuideEntry(int entryid) throws IntegrationException{
        String query =  "SELECT guideentryid, category, subcategory, description, enforcementguidelines, \n" +
                        " inspectionguidelines, priority\n" +
                        " FROM public.codeelementguide WHERE guideentryid = ?;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CodeElementGuideEntry cege = null;
         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, entryid);
            rs = stmt.executeQuery();
            while(rs.next()){
                cege = generateCodeElementGuideEntry(rs);
            }
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error retrieving code element guide entry by ID", ex);
        } finally{
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
         
         return cege;
    }
    
    public ArrayList<CodeElementGuideEntry> getCodeElementGuideEntries() throws IntegrationException{
        String query =  "SELECT guideentryid, category, subcategory, description, enforcementguidelines, \n" +
                        "       inspectionguidelines, priority\n" +
                        "  FROM public.codeelementguide;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CodeElementGuideEntry> cegelist = new ArrayList();

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                cegelist.add(generateCodeElementGuideEntry(rs));
                
            }
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error generating code element guide entry list", ex);
        } finally{
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        return cegelist;
    }
    
    private CodeElementGuideEntry generateCodeElementGuideEntry(ResultSet rs) throws SQLException{
        CodeElementGuideEntry cege = new CodeElementGuideEntry();
        cege.setCategory(rs.getString("guideentryid"));
        cege.setSubCategory(rs.getString("subcategory"));
        cege.setDescription(rs.getString("description"));
        cege.setEnforcementGuidelines(rs.getString("enforcementguidelines"));
        cege.setInspectionGuidelines(rs.getString("inspectionguidelines"));
        cege.setPriority(rs.getBoolean("priority"));
        return cege;
    }
    
} // close class
