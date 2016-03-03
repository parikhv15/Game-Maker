package main;

import static org.junit.Assert.*;

import java.awt.Event;
import java.util.Arrays;

import model.EventActionAssociation;
import model.GameData;
import model.GameData.EventType;
import model.GameData.KeyboardPressEventActionType;
import model.GameData.ActionCondition;
import model.GameData.ReflectActionType;
import model.GameData.CollisionEventActionType;
import model.GameData.AutoEventActionType;
import model.Sprite;

import org.junit.Before;
import org.junit.Test;

import view.SpriteSelector;

public class GameDataTest {

	private EventActionAssociation eventEntryOne;
	private EventActionAssociation eventEntryTwo;
	private Sprite spriteObjectOne;
	private Sprite spriteObjectTwo;
	
	
	 @Before
	 public void executedBeforeEach() {
		 eventEntryOne = new EventActionAssociation();
		 eventEntryTwo = new EventActionAssociation();
		 spriteObjectOne = new Sprite();
		 spriteObjectTwo = new Sprite();
	 }
	
	@Test
	public void associateEventsToSpriteTest() {
		eventEntryOne.getEventNameList().add("COLLISION_EVENT");
		GameData gameData = new GameData();
		gameData.associateEventsToSprite(eventEntryOne);
		String expectedString = gameData.getEventTable().get(0).getEventNameList().get(0);
		assertEquals(expectedString, "COLLISION_EVENT");
	}
	
	@Test
	public void deassociateEventsToSpriteTest(){
		eventEntryOne.setSprite(spriteObjectOne);
		eventEntryTwo.setSprite(spriteObjectTwo);
		GameData gameData = new GameData();
		gameData.getEventTable().add(eventEntryOne);
		gameData.getEventTable().add(eventEntryTwo);
		gameData.deassociateEventsToSprite(spriteObjectOne);
		assertFalse(gameData.getEventTable().contains(spriteObjectOne));	
	}
	
	@Test
	public void checkSpriteEntryTest(){
		eventEntryOne.setSprite(spriteObjectOne);
		GameData gameData = new GameData();	
		gameData.getEventTable().add(eventEntryOne);
		assertTrue(gameData.checkSpriteEntry(spriteObjectOne));	
	}
	
	
	@Test
	public void getSelectedSpriteTest(){
		spriteObjectOne.setName("Object_01");
		Sprite spriteObjectTwo = new Sprite();
		spriteObjectTwo.setName("Object_02");
		GameData gameData = new GameData();
		gameData.getSprites().add(spriteObjectOne);
		gameData.getSprites().add(spriteObjectTwo);
		Sprite expectedObject = gameData.getSelectedSprite("Object_01");
		assertTrue(expectedObject.equals(spriteObjectOne));
	}
	
	@Test
	public void getActionConditionEnumStringArrayTest(){
		GameData gameData = new GameData();
		ActionCondition actionCondition = null;
		String[] actionConditionEnumStringValues = gameData.getEnumStringArray(actionCondition);
		
		String[] expectedActionConditionEnumStringValues = {"TOP_FRAME", 
				"BOTTOM_FRAME", 
				"LEFT_FRAME", 
				"RIGHT_FRAME", 
				"SPRITE"};	
		assertTrue(Arrays.equals(expectedActionConditionEnumStringValues,actionConditionEnumStringValues));
	
	}

	@Test
	public void getEventTypeEnumStringArrayTest(){
		GameData gameData = new GameData();
		EventType eventType = null;
		String[] eventTypeEnumStringValues = gameData.getEnumStringArray(eventType);
		
		String[] expectedEventTypeEnumStringValues = { 
				"NONE",
				"KEYBOARD_PRESS_EVENT",
				"COLLISION_EVENT",
				"AUTO"};	
		assertTrue(Arrays.equals(expectedEventTypeEnumStringValues,eventTypeEnumStringValues));
	
	}

	@Test
	public void getReflectActionTypeEnumStringArrayTest(){
		GameData gameData = new GameData();
		ReflectActionType reflectActionType = null;
		String[] reflectActionTypeEnumStringValues = gameData.getEnumStringArray(reflectActionType);
		
		String[] expectedReflectActionTypeEnumStringValues = { 
				"FRAME_TOP",
				"FRAME_BOTTOM",
				"FRAME_LEFT",
				"FRAME_RIGHT",
				"SPRITE"};	
		assertTrue(Arrays.equals(expectedReflectActionTypeEnumStringValues,reflectActionTypeEnumStringValues));
	}	
	
	
	@Test
	public void getKeyboardPressEventActionTypeEnumStringArrayTest(){
		GameData gameData = new GameData();
		KeyboardPressEventActionType keyboardPressEventActionType = null;
		String[] keyboardPressEventActionTypeEnumStringValues = gameData.getEnumStringArray(keyboardPressEventActionType);
		
		String[] expectedKeyboardPressEventActionTypeEnumStringValues = { 
				"MOVE_LEFT",
				"MOVE_RIGHT",
				"MOVE_UP",
				"MOVE_DOWN"};	
		assertTrue(Arrays.equals(expectedKeyboardPressEventActionTypeEnumStringValues,keyboardPressEventActionTypeEnumStringValues));
	}	
	
	
	@Test
	public void getAutoEventActionTypeEnumStringArrayTest(){
		GameData gameData = new GameData();
		AutoEventActionType autoEventActionType = null;
		String[] autoEventActionTypeEnumStringValues = gameData.getEnumStringArray(autoEventActionType);	
		String[] expectedEventActionTypeEnumStringValues = { 
				"FREE_MOVE"};	
		assertTrue(Arrays.equals(expectedEventActionTypeEnumStringValues,autoEventActionTypeEnumStringValues));
	}	
	
	@Test
	public void getCollisionEventActionTypeEnumStringArrayTest(){
		GameData gameData = new GameData();
		CollisionEventActionType collisionEventActionType = null;
		String[] collisionEventActionTypeEnumStringValues = gameData.getEnumStringArray(collisionEventActionType);
		
		String[] expectedCollisionEventActionTypeEnumStringValues = { 
				"REFLECT_ACTION",
				"PLAY_COLLISION_SOUND_ACTION",
				"VANISH_ACTION",
				"STATIC_ACTION"};	
		assertTrue(Arrays.equals(expectedCollisionEventActionTypeEnumStringValues,collisionEventActionTypeEnumStringValues));
	}		
	
}
