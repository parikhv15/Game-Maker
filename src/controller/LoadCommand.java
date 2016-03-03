package controller;

import model.GameData;

//call method of model to load saved game 
public class LoadCommand implements Command{
	
	private String frameTitle;
	private GameData gameData;
	
	public LoadCommand(GameData gameData, String frameTitle){
			this.frameTitle = frameTitle;
			this.gameData = gameData;
	}

	//call method of model to load saved game 
	@Override
	public void execute() {
		gameData.setFrameTitle(frameTitle);
		gameData.loadMe();
		
	}
	
	

}
