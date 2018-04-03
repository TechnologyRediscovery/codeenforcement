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
import com.tcvcog.tcvce.entities.Weather;
import java.sql.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Adam Gutonski
 */
public class WeatherIntegrator extends BackingBeanUtils implements Serializable{
    
    public WeatherIntegrator(){
    }
    
    public int insertWeather(Weather weather) throws IntegrationException{
        String query = "INSERT INTO public.weather(\n" +
            "            name, temphi, templo, prcp, date, observation_id) \n" +
            "    VALUES (?, ?, ?, ?, ?, DEFAULT);";
        //DEFAULT observation_id is set by Postgres
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, weather.getName());
            stmt.setInt(2, weather.getTempHi());
            stmt.setInt(3, weather.getTempLo());
            // note that the timestamp is set by a call to postgres's now()
            stmt.setDouble(4, weather.getPrcp());
            //newPerson.setLastUpdated(rs.getTimestamp("lastupdated").toLocalDateTime());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(weather.getDate()));
//            stmt.setInt(5, weather.getObservation_id());
            
            
            System.out.println("WeatherIntegrator.insertWeatherCategory| sql: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Weather", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        
        return 0;
    }
    
    
    
}
