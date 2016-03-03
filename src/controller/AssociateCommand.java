package controller;

import model.EventActionAssociation;

import model.GameData;

//Controller to call method of model for associating events to sprite objects 
public class AssociateCommand implements Command{
	private EventActionAssociation eventEntry;
	private GameData gameData;

	public AssociateCommand (GameData gameData, EventActionAssociation eventEntry) {
		this.gameData = gameData;
		this.eventEntry = eventEntry;
	}

//call method of model to associate events to sprites
	@Override
	public void execute() {
		gameData.associateEventsToSprite(eventEntry);
	}
}
