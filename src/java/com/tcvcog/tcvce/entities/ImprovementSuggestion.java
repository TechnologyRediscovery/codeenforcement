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
package com.tcvcog.tcvce.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Eric C. Darsow
 */
public class ImprovementSuggestion {
    private int suggestionID;
    private User submitter;
    private int improvementTypeID;
    private String reply;
    private String suggestionText;
    private int statusID;
    private LocalDateTime submissionTimeStamp;

    /**
     * @return the suggestionID
     */
    public int getSuggestionID() {
        return suggestionID;
    }

    /**
     * @return the submitter
     */
    public User getSubmitter() {
        return submitter;
    }

    /**
     * @return the improvementTypeID
     */
    public int getImprovementTypeID() {
        return improvementTypeID;
    }

    /**
     * @return the reply
     */
    public String getReply() {
        return reply;
    }

    /**
     * @return the suggestionText
     */
    public String getSuggestionText() {
        return suggestionText;
    }

    /**
     * @return the statusID
     */
    public int getStatusID() {
        return statusID;
    }

    /**
     * @return the submissionTimeStamp
     */
    public LocalDateTime getSubmissionTimeStamp() {
        return submissionTimeStamp;
    }

    /**
     * @param suggestionID the suggestionID to set
     */
    public void setSuggestionID(int suggestionID) {
        this.suggestionID = suggestionID;
    }

    /**
     * @param submitter the submitter to set
     */
    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    /**
     * @param improvementTypeID the improvementTypeID to set
     */
    public void setImprovementTypeID(int improvementTypeID) {
        this.improvementTypeID = improvementTypeID;
    }

    /**
     * @param reply the reply to set
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * @param suggestionText the suggestionText to set
     */
    public void setSuggestionText(String suggestionText) {
        this.suggestionText = suggestionText;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    /**
     * @param submissionTimeStamp the submissionTimeStamp to set
     */
    public void setSubmissionTimeStamp(LocalDateTime submissionTimeStamp) {
        this.submissionTimeStamp = submissionTimeStamp;
    }
    
}
