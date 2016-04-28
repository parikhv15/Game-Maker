package controller;

import model.GameData;
import model.Sprite;

//Controller to call method of model for deassociating events to sprite objects 
public class DeassociateCommand implements Command {
	private Sprite spriteSelected;
	private GameData gameData;

	public DeassociateCommand(GameData gameData,
			Sprite spriteSelected) {
		this.gameData = gameData;
		this.spriteSelected = spriteSelected;
	}

	//call method of model to deassociate events to sprites
	@Override
	public void execute() {
		gameData.deassociateEventsToSprite(spriteSelected);
		
	}
}
