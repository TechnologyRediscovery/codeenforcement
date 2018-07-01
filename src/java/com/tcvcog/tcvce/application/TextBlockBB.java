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
package com.tcvcog.tcvce.application;

import com.tcvcog.tcvce.entities.TextBlock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sylvia
 */
public class TextBlockBB extends BackingBeanUtils implements Serializable{

    private ArrayList<TextBlock> blockList;
    private ArrayList<TextBlock> filteredBlockList;
    
    private TextBlock selectedBlock;
    
    private HashMap<String, Integer> categoryList;
    private int selectedCategoryID;
    private HashMap<String, Integer> muniList;
    private int selectedMuniID;
    
    private String formBlockName;
    private String formBlockText;
    
    /**
     * Creates a new instance of TextBlockBB
     */
    public TextBlockBB() {
    }
    
    public String updateTextBlock(){
        
        return "";
    }
    
    public String commitUpdatesToTextBlock(){
        
        return "";
    }
    
    public String addNewTextBlock(){
        
        return "";
    }
    
    public String nukeTextBlock(){
        
        return "";
    }

    /**
     * @return the blockList
     */
    public ArrayList<TextBlock> getBlockList() {
        return blockList;
    }

    /**
     * @return the filteredBlockList
     */
    public ArrayList<TextBlock> getFilteredBlockList() {
        return filteredBlockList;
    }

    /**
     * @return the selectedBlock
     */
    public TextBlock getSelectedBlock() {
        return selectedBlock;
    }

    /**
     * @return the categoryList
     */
    public HashMap<String, Integer> getCategoryList() {
        return categoryList;
    }

    /**
     * @return the selectedCategoryID
     */
    public int getSelectedCategoryID() {
        return selectedCategoryID;
    }

    /**
     * @return the muniList
     */
    public HashMap<String, Integer> getMuniList() {
        return muniList;
    }

    /**
     * @return the selectedMuniID
     */
    public int getSelectedMuniID() {
        return selectedMuniID;
    }

    /**
     * @return the formBlockName
     */
    public String getFormBlockName() {
        return formBlockName;
    }

    /**
     * @return the formBlockText
     */
    public String getFormBlockText() {
        return formBlockText;
    }

    /**
     * @param blockList the blockList to set
     */
    public void setBlockList(ArrayList<TextBlock> blockList) {
        this.blockList = blockList;
    }

    /**
     * @param filteredBlockList the filteredBlockList to set
     */
    public void setFilteredBlockList(ArrayList<TextBlock> filteredBlockList) {
        this.filteredBlockList = filteredBlockList;
    }

    /**
     * @param selectedBlock the selectedBlock to set
     */
    public void setSelectedBlock(TextBlock selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    /**
     * @param categoryList the categoryList to set
     */
    public void setCategoryList(HashMap<String, Integer> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * @param selectedCategoryID the selectedCategoryID to set
     */
    public void setSelectedCategoryID(int selectedCategoryID) {
        this.selectedCategoryID = selectedCategoryID;
    }

    /**
     * @param muniList the muniList to set
     */
    public void setMuniList(HashMap<String, Integer> muniList) {
        this.muniList = muniList;
    }

    /**
     * @param selectedMuniID the selectedMuniID to set
     */
    public void setSelectedMuniID(int selectedMuniID) {
        this.selectedMuniID = selectedMuniID;
    }

    /**
     * @param formBlockName the formBlockName to set
     */
    public void setFormBlockName(String formBlockName) {
        this.formBlockName = formBlockName;
    }

    /**
     * @param formBlockText the formBlockText to set
     */
    public void setFormBlockText(String formBlockText) {
        this.formBlockText = formBlockText;
    }
    
}
