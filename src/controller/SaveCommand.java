package controller;

import model.GameData;

//call method of model to save game 
public class SaveCommand implements Command{
	
	private String frameTitle;
	private GameData gameData;
	
	public SaveCommand(GameData gameData, String frameTitle){
			this.frameTitle = frameTitle;
			this.gameData = gameData;
	}
	
	//call method of model to save game 
	@Override
	public void execute() {
		gameData.setFrameTitle(frameTitle);
		gameData.saveMe();
	}
	
	

}
