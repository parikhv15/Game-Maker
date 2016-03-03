package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ActionConditionAssociation;
import model.EventActionAssociation;
import model.GameData;
import model.Sprite;

import org.junit.Before;
import org.junit.Test;

import controller.AssociateCommand;
import controller.DeassociateCommand;

public class DeassociateCommandTest {

	
	private Sprite spriteSelected;
	private GameData gameData;
	private DeassociateCommand deassociateCommand;
	private EventActionAssociation eventEntry;
	
	@Before
	public void executedBefore() {
		spriteSelected = new Sprite();
		gameData = new GameData();
		eventEntry = new EventActionAssociation();
		eventEntry.setSprite(spriteSelected);
		gameData.getEventTable().add(eventEntry);
		deassociateCommand = new DeassociateCommand(gameData, spriteSelected);
		
	}
	@Test
	public void executeTest() {
		deassociateCommand.execute();
		assertFalse(gameData.getEventTable().contains(spriteSelected));	
		
	}


}
