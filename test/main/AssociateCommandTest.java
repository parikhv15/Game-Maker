package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ActionConditionAssociation;
import model.EventActionAssociation;
import model.GameData;

import org.junit.Before;
import org.junit.Test;

import controller.AssociateCommand;

public class AssociateCommandTest {

	private AssociateCommand associateCommand;
	private EventActionAssociation eventEntry;
	private GameData gameData;
	ActionConditionAssociation actionCondition;
	ArrayList<String> actualTypeList;
	
	@Before
	public void executeTest() {
		eventEntry = new EventActionAssociation();
		actualTypeList = new ArrayList<String>();
		actualTypeList.add("AUTO");
		actionCondition = new ActionConditionAssociation(actualTypeList);
		eventEntry.getActionList().add(actionCondition);
		gameData = new GameData();
		gameData.getEventTable().add(eventEntry);
		associateCommand  = new AssociateCommand(gameData, eventEntry );
	}
	
	
	@Test
	public void test() {
		associateCommand.execute();
		String expectedResult = gameData.getEventTable().get(0).getActionList().get(0).getActionType().get(0);
		assertEquals("AUTO", expectedResult);
	
	}

}
