/*
 * Copyright (C) 2018 Turtle Creek Valley
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
package cedbTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eric C. Darsow
 */
public class DateTesting {
    
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMM yyyy, HH:mm");
        LocalDateTime earlier = LocalDateTime.of(2017, 6,9,1,1);
        LocalDateTime dateTime = LocalDateTime.now();
        Long daysBetween = earlier.atZone(ZoneId.systemDefault())
                .until(dateTime.atZone(ZoneId.systemDefault()), java.time.temporal.ChronoUnit.DAYS);
        String formattedDateTime = dateTime.format(formatter); 
        System.out.println("Formatted Date time: " + formattedDateTime);
        System.out.println("Days between: " + daysBetween);
        System.out.println("Day of the week: " + LocalDateTime.now().getDayOfWeek());
        
        
    }
    
}
