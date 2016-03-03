package main;

import static org.junit.Assert.*;
import model.KeyBoardEvent;
import model.Sprite;

import org.junit.Test;

public class KeyBoardEventTest {

	@Test
	public void moveLeftTest() {
		int moveUnit = 2;
		int actualX= 20;
		Sprite spriteObject = new Sprite();
		spriteObject.setX(actualX);
		KeyBoardEvent keyBoardEvent = new KeyBoardEvent();
		keyBoardEvent.moveLeft(spriteObject, moveUnit);		
		int expectedResult =  actualX - moveUnit;
		assertEquals(expectedResult, spriteObject.getX());
	}

	@Test
	public void moveRightTest() {
		int moveUnit = 2;
		int actualX= 20;
		Sprite spriteObject = new Sprite();
		spriteObject.setX(actualX);
		KeyBoardEvent keyBoardEvent = new KeyBoardEvent();
		keyBoardEvent.moveRight(spriteObject, moveUnit);		
		int expectedResult =  actualX + moveUnit;
		assertEquals(expectedResult, spriteObject.getX());
	}

	@Test
	public void moveUpTest() {
		int moveUnit = 2;
		int actualY= 20;
		Sprite spriteObject = new Sprite();
		spriteObject.setY(actualY);
		KeyBoardEvent keyBoardEvent = new KeyBoardEvent();
		keyBoardEvent.moveUp(spriteObject, moveUnit);		
		int expectedResult =  actualY - moveUnit;
		assertEquals(expectedResult, spriteObject.getY());
	}

	@Test
	public void moveDownTest() {
		int moveUnit = 2;
		int actualY= 20;
		Sprite spriteObject = new Sprite();
		spriteObject.setY(actualY);
		KeyBoardEvent keyBoardEvent = new KeyBoardEvent();
		keyBoardEvent.moveDown(spriteObject, moveUnit);		
		int expectedResult =  actualY + moveUnit;
		assertEquals(expectedResult, spriteObject.getY());
	}
		
	}


