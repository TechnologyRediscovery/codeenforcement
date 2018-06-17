/*
 * Copyright (C) 2017 cedba
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
package cedbTest;

import com.tcvcog.tcvce.entities.Property;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import java.sql.Connection;

/**
 *
 * @author cedba
 */
public class DbTest {
    
     
        public static void main(String[] args){
            Connection con = null;
            
            System.out.println("Connecting to database");
            try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.toString());
                }

            Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
    //        source.setDataSourceName("cogpgnew");
            source.setServerName("localhost:5432");
            source.setDatabaseName("cogdb");
            source.setUser("cogdba");
            source.setPassword("c0d3");
            source.setMaxConnections(10);
            try {
                con = source.getConnection();
                System.out.println("connection to db open");
            } catch (SQLException ex) {
                ex.toString();
            }
            
            int id = 1192365;

            System.out.println("Looking up property in propSearchBean");
             String query = "SELECT propertyid, parid, lotandblock, address FROM property"
                + " WHERE propertyid =" + id;
            ResultSet rs;

          
           Property p = null;

            try {
                
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                
                if(rs.next() == true){
                    System.out.println("There is a row!");
//                    rs.next();
                    p = new Property();
                    p.setPropertyID(rs.getInt("propertyid"));
                    p.setParID(rs.getString("parid"));
                    p.setLotAndBlock(rs.getString("lotandblock"));
                    p.setAddress(rs.getString("address"));
                    System.out.println(p.getAddress());
                   // System.out.println(p.getApartmetnNo());
                    System.out.println(p.getParID());
                    
                    
                } else {
                    System.out.println("there were no matches found for id: " + id);
                }
                
              
            } catch(Exception e) {
                System.out.println(e.toString());
            }
        }
    
}
