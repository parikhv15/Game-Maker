package model;

import java.io.Serializable;
import java.util.ArrayList;

//Class to implement action condition association, this class contains action type , associated 
//action condition and associated sprite object list 
public class ActionConditionAssociation implements Serializable{

	ArrayList<String> actionType;
	private ArrayList<String> actionCondition;
	private ArrayList<Sprite> associatedSpriteList;

	public ActionConditionAssociation(ArrayList<String> actionType) {
		this.actionType = actionType;

	}
	
	public ActionConditionAssociation(ArrayList<String> actionType,	ArrayList<String> actionCondition, ArrayList<Sprite> associatedSprites) {
		this.actionType = actionType;
		this.actionCondition = actionCondition;
		this.associatedSpriteList = associatedSprites;
	}
	
	public ActionConditionAssociation() {
		this.actionCondition = new ArrayList<>();
		this.associatedSpriteList = new ArrayList<>();
	}

	public ArrayList<String> getActionCondition() {
		return actionCondition;
	}

	public void setActionCondition(ArrayList<String> actionCondition) {
		this.actionCondition = actionCondition;
	}

	public ArrayList<Sprite> getAssociatedSpriteList() {
		return associatedSpriteList;
	}

	public void setAssociatedSpriteList(ArrayList<Sprite> associatedSpriteList) {
		this.associatedSpriteList = associatedSpriteList;
	}

	public ArrayList<String> getActionType() {
		return actionType;
	}

	public void setActionType(ArrayList<String> actionType) {
		this.actionType = actionType;
	}

}
