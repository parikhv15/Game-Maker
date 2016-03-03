package model;

import java.io.Serializable;
import java.util.ArrayList;

//Class to implement event action association, this class contains sprite name, event names ,  
//associated action type, action condition and associated sprite object list 

public class EventActionAssociation implements Serializable{
	Sprite sprite;
	ArrayList<String> eventNameList;
	ArrayList<ActionConditionAssociation> actionList;

	public EventActionAssociation() {
		actionList = new ArrayList<>();
		eventNameList = new ArrayList<>();
	}

	public ArrayList<ActionConditionAssociation> getActionList() {
		return this.actionList;
	}

	public void setActionList(ArrayList<ActionConditionAssociation> actionList) {
		this.actionList = actionList;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public ArrayList<String> getEventNameList() {
		return eventNameList;
	}

	public void setEventNameList(ArrayList<String> eventNameList) {
		this.eventNameList = eventNameList;
	}

}
