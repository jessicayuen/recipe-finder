package team13.cmput301.recipefinder.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporarily holds lists of ingredient strings and instruction strings
 * to aid the recover of data when user switches the orientation of the phone
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class ListManager {

	// Singleton
	public static ListManager listManager = null;
	private List<String> ingredList, instrList;
	
	/**
	 * Constructor - DO NOT USE
	 * Exists only to defeat instantiation
	 */
	protected ListManager(){}
	
	/**
	 * Retrieves the singleton listManager
	 * @return the singleton listManager
	 */
	public static ListManager getListManager() {
		if(listManager == null) {
			listManager = new ListManager();
			listManager.ingredList = new ArrayList<String>();
			listManager.instrList = new ArrayList<String>();
		}
		return listManager;
	}
	
	/**
	 * Store the give ingredient list into the list manager
	 * @param ingred The list of ingredients to be added
	 */
	public void addIngredList(List<String> ingred) {
		ingredList = new ArrayList<String>();
		ingredList = ingred;		
	}
	
	/**
	 * Store the given instruction list into the list manager
	 * @param instr The list of instructions
	 */
	public void addInstrList(List<String> instr) {
		instrList = new ArrayList<String>();
		instrList = instr;
	}
	
	/**
	 * @return The current ingredient list
	 */
	public List<String> getIngredList() {
		return ingredList;
	}
	
	/**
	 * @return The current instruction list
	 */
	public List<String> getInstrList() {
		return instrList;
	}
	
	/**
	 * Clear both the ingredients and instructions lists
	 */
	public void clearLists() {
		instrList = new ArrayList<String>();
		ingredList = new ArrayList<String>();
	}
	
}
