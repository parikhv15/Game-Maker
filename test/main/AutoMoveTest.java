package main;

import static model.GameConstants.GAME_AREA_WIDTH;


import static org.junit.Assert.*;
import model.AutoEvent;
import model.Sprite;

import org.junit.Before;
import org.junit.Test;

public class AutoMoveTest {
	
	private Sprite spriteObject;
	private AutoEvent autoEvent;
	 @Before
	 public void executedBeforeEach() {
		 spriteObject = new Sprite();
		 autoEvent = new AutoEvent();
	 }
	
	@Test
	public void freeMoveFirstTest() {	
	
		spriteObject.setX(GAME_AREA_WIDTH - spriteObject.getXdir());
		spriteObject.setRight("FRAME_RIGHT");	
		
		autoEvent.FreeMove(spriteObject);
		assertEquals(-1, spriteObject.getXdir());
	}
	
	
	@Test
	public void freeMoveSecondTest() {	
	
		spriteObject.setY(0 - spriteObject.getYdir());
		spriteObject.setTop("FRAME_TOP");	
		
		autoEvent.FreeMove(spriteObject);
		assertEquals(1, spriteObject.getYdir());
	}

	@Test
	public void freeMoveThirdTest() {	

		spriteObject.setX(1);
		spriteObject.setLeft("FRAME_LEFT");	
		spriteObject.setXdir(-1);	
		
		autoEvent.FreeMove(spriteObject);
		assertEquals(1,spriteObject.getXdir());
	}
	
	@Test
	public void freeMoveFourthTest() {	
		Sprite spriteObject = new Sprite();
		spriteObject.setY(GAME_AREA_WIDTH);
		spriteObject.setBottom("FRAME_BOTTOM");	
		spriteObject.setXdir(0);
		spriteObject.setYdir(0);

		autoEvent.FreeMove(spriteObject);
		assertEquals(1,spriteObject.getXdir());
		assertEquals(-1,spriteObject.getYdir());
	}
	


}
