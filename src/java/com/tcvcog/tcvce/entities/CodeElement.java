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
package com.tcvcog.tcvce.entities;

import java.time.LocalDateTime;

/**
 *
 * @author sylvia
 */
public class CodeElement {
    
    private int elementID;
    private int codeElementTypeID;
    private String codeElementTypeName;
    
    private int sourceID;
    private String sourceName;
    private int sourceYear;
    
    private int ordchapterNo;
    
    private String ordchapterTitle;
    private String ordSecNum;
    private String ordsecTitle;
    
    private String ordSubSecNum;
    private String ordSubSecTitle;
    private String ordTechnicalText;
    
    private String ordHumanFriendlyText;
    private double defaultPenalty;
    private boolean isActive;
    
    private boolean isEnforcementPriority;
    private int defaultDaysToComply;
    private String resourceURL;
    
    private String inspectionTips;
    private LocalDateTime dateCreated;
    
    
    /**
     * @return the elementID
     */
    public int getElementID() {
        return elementID;
    }

    /**
     * @param elementID the elementID to set
     */
    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    /**
     * @return the codeElementTypeName
     */
    public String getCodeElementTypeName() {
        return codeElementTypeName;
    }

    /**
     * @param codeElementTypeName the codeElementTypeName to set
     */
    public void setCodeElementTypeName(String codeElementTypeName) {
        this.codeElementTypeName = codeElementTypeName;
    }

  
    /**
     * @return the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return the sourceYear
     */
    public int getSourceYear() {
        return sourceYear;
    }

    /**
     * @param sourceYear the sourceYear to set
     */
    public void setSourceYear(int sourceYear) {
        this.sourceYear = sourceYear;
    }

 

    /**
     * @return the ordchapterNo
     */
    public int getOrdchapterNo() {
        return ordchapterNo;
    }

    /**
     * @param ordchapterNo the ordchapterNo to set
     */
    public void setOrdchapterNo(int ordchapterNo) {
        this.ordchapterNo = ordchapterNo;
    }

    /**
     * @return the ordchapterTitle
     */
    public String getOrdchapterTitle() {
        return ordchapterTitle;
    }

    /**
     * @param ordchapterTitle the ordchapterTitle to set
     */
    public void setOrdchapterTitle(String ordchapterTitle) {
        this.ordchapterTitle = ordchapterTitle;
    }

    /**
     * @return the ordSecNum
     */
    public String getOrdSecNum() {
        return ordSecNum;
    }

    /**
     * @param ordSecNum the ordSecNum to set
     */
    public void setOrdSecNum(String ordSecNum) {
        this.ordSecNum = ordSecNum;
    }

    /**
     * @return the ordsecTitle
     */
    public String getOrdsecTitle() {
        return ordsecTitle;
    }

    /**
     * @param ordsecTitle the ordsecTitle to set
     */
    public void setOrdsecTitle(String ordsecTitle) {
        this.ordsecTitle = ordsecTitle;
    }

    /**
     * @return the ordSubSecNum
     */
    public String getOrdSubSecNum() {
        return ordSubSecNum;
    }

    /**
     * @param ordSubSecNum the ordSubSecNum to set
     */
    public void setOrdSubSecNum(String ordSubSecNum) {
        this.ordSubSecNum = ordSubSecNum;
    }

    /**
     * @return the ordSubSecTitle
     */
    public String getOrdSubSecTitle() {
        return ordSubSecTitle;
    }

    /**
     * @param ordSubSecTitle the ordSubSecTitle to set
     */
    public void setOrdSubSecTitle(String ordSubSecTitle) {
        this.ordSubSecTitle = ordSubSecTitle;
    }

    /**
     * @return the ordTechnicalText
     */
    public String getOrdTechnicalText() {
        return ordTechnicalText;
    }

    /**
     * @param ordTechnicalText the ordTechnicalText to set
     */
    public void setOrdTechnicalText(String ordTechnicalText) {
        this.ordTechnicalText = ordTechnicalText;
    }

    /**
     * @return the ordHumanFriendlyText
     */
    public String getOrdHumanFriendlyText() {
        return ordHumanFriendlyText;
    }

    /**
     * @param ordHumanFriendlyText the ordHumanFriendlyText to set
     */
    public void setOrdHumanFriendlyText(String ordHumanFriendlyText) {
        this.ordHumanFriendlyText = ordHumanFriendlyText;
    }

    /**
     * @return the defaultPenalty
     */
    public double getDefaultPenalty() {
        return defaultPenalty;
    }

    /**
     * @param defaultPenalty the defaultPenalty to set
     */
    public void setDefaultPenalty(double defaultPenalty) {
        this.defaultPenalty = defaultPenalty;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the isEnforcementPriority
     */
    public boolean isIsEnforcementPriority() {
        return isEnforcementPriority;
    }

    /**
     * @param isEnforcementPriority the isEnforcementPriority to set
     */
    public void setIsEnforcementPriority(boolean isEnforcementPriority) {
        this.isEnforcementPriority = isEnforcementPriority;
    }

    /**
     * @return the defaultDaysToComply
     */
    public int getDefaultDaysToComply() {
        return defaultDaysToComply;
    }

    /**
     * @param defaultDaysToComply the defaultDaysToComply to set
     */
    public void setDefaultDaysToComply(int defaultDaysToComply) {
        this.defaultDaysToComply = defaultDaysToComply;
    }

    /**
     * @return the resourceURL
     */
    public String getResourceURL() {
        return resourceURL;
    }

    /**
     * @param resourceURL the resourceURL to set
     */
    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    /**
     * @return the inspectionTips
     */
    public String getInspectionTips() {
        return inspectionTips;
    }

    /**
     * @param inspectionTips the inspectionTips to set
     */
    public void setInspectionTips(String inspectionTips) {
        this.inspectionTips = inspectionTips;
    }

    /**
     * @return the dateCreated
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the sourceID
     */
    public int getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * @return the codeElementTypeID
     */
    public int getCodeElementTypeID() {
        return codeElementTypeID;
    }

    /**
     * @param codeElementTypeID the codeElementTypeID to set
     */
    public void setCodeElementTypeID(int codeElementTypeID) {
        this.codeElementTypeID = codeElementTypeID;
    }
    
}
