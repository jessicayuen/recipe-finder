package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

public class ListManager {

	public static ListManager listManager = null;
	private List<String> ingredList, instrList;
	
	protected ListManager(){}
	
	public static ListManager getListManager() {
		if(listManager == null) {
			listManager = new ListManager();
			listManager.ingredList = new ArrayList<String>();
			listManager.instrList = new ArrayList<String>();
		}
		return listManager;
	}
	
	/**
	 * store the give ingredient list into the list manager
	 * @param ingred
	 */
	public void addIngredList(List<String> ingred) {
		ingredList = new ArrayList<String>();
		ingredList = ingred;		
	}
	
	/**
	 * store the given instruction list into the list manager
	 * @param instr
	 */
	public void addInstrList(List<String> instr) {
		instrList = new ArrayList<String>();
		instrList = instr;
	}
	
	/**
	 * return the current stored ingredient list
	 * @return
	 */
	public List<String> getIngredList() {
		return ingredList;
	}
	
	/**
	 * return the currently stored instruction list
	 * @return
	 */
	public List<String> getInstrList() {
		return instrList;
	}
	
	public void clearLists() {
		instrList = new ArrayList<String>();
		ingredList = new ArrayList<String>();
	}
	
}
