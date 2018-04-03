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
import com.tcvcog.tcvce.entities.CEActionRequest;
import com.tcvcog.tcvce.entities.Exercise;
import java.sql.*;
import java.io.Serializable;
import java.util.LinkedList;
/**
 *
 * @author Adam Gutonski
 */
public class ExerciseIntegrator extends BackingBeanUtils implements Serializable {
    

    public ExerciseIntegrator(){
        
    }
    public int insertExercise(Exercise exercise) throws IntegrationException{
        String query = "INSERT INTO public.exercising(\n" +
                "         excercise, weight, max_reps, lift_id, date) \n" +
                "    VALUES (?, ?, ?, DEFAULT, ?)";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, exercise.getExerciseName());
            stmt.setDouble(2, exercise.getWeight());
            stmt.setInt(3, exercise.getMaxReps());
            if(exercise.getExerciseDate() != null){
                //keep in  mind that we note place holder "4"' because it  points to the
                //fourth "?" symbol in our query...skips DEFAULT 
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(exercise.getExerciseDate()));
                
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }
            // note that the timestamp is set by a call to postgres's now()
            //stmt.setInt(4, exercise.getLift_id());
            
            
            
            System.out.println("ExerciseIntegrator.insertExerciseCategory| sql: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Exercise", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        
        return 0;
        
    }
    
    public LinkedList<Exercise> getExerciseList() throws IntegrationException{

    String query = "SELECT exercise, weight, max_reps, lift_id, date\n" +
         
        "  FROM public.exercising";
    Connection con = getPostgresCon();
    ResultSet rs = null;
    PreparedStatement stmt = null;
    LinkedList<Exercise> exerciseList = new LinkedList();

    try {

        stmt = con.prepareStatement(query);
        //stmt.setInt(1, catID);
        rs = stmt.executeQuery();
        System.out.println("ExerciseInteegrator.getExerciseList| SQL: " + stmt.toString());

        while(rs.next()){
            //inspectableCodeElementList.add(getInspectionGuidelines(rs.getString("inspection_guidelines")));
            /* see project notes, I will have to figure out how to align the CodeElement
            property of the InspectableCodeElement to have it fit in to the rs (ResultSet)
            and get returned based on foreign key alignment between inspectablecodeleement
            and codeelements...
            */
            exerciseList.add(generateExercise(rs));
        }

    } catch (SQLException ex) {
        System.out.println(ex.toString());
        throw new IntegrationException("Cannot get event categry", ex);

    } finally{
         if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
         if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
         if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
    } // close finally

    return exerciseList;
}
    
        private Exercise generateExercise(ResultSet rs) throws IntegrationException{
        Exercise newExercise = new Exercise();
        
        
        try {
            newExercise.setExerciseName(rs.getString("exercise"));
            newExercise.setWeight(rs.getDouble("weight"));
            newExercise.setMaxReps(rs.getInt("max_reps"));
            newExercise.setLift_id(rs.getInt("lift_id"));
            java.sql.Timestamp s = rs.getTimestamp("date");
            if(s != null) {
                newExercise.setExerciseDate(s.toLocalDateTime());
                
            } else {
                newExercise.setExerciseDate(null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating inspectable code element from ResultSet", ex);
        }
         return newExercise;   
        
    }
}
    

