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

import com.tcvcog.tcvce.entities.User;
import java.util.LinkedList;

/**
 *
 * @author sylvia
 */
public class UserIntegrator {

    /**
     * Creates a new instance of UserIntegrator
     */
    public UserIntegrator() {
    }
    
    public int insertUser(User userToInsert){
        
        User insertedUser = null;
        
        return 0;
        
    }
    
    public void updateUser(User userToUpdate){
        
    }
    
    public void deleteUser(User userToDelete){
        
        
        
    }
    
    public User getUserByUserID(int userID){
        
        return new User();
    }
    
    public LinkedList getCompleteUserList(){
        return new LinkedList();
        
    }
    
}
