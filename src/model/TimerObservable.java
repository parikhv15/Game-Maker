package model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import view.ControlPanel;
import view.GameMakerDemoFrame;
import view.PreviewPanel;

//this is important model class which changes sprite objects as per the new actions and events
// and informs view method after every data state change

public class TimerObservable extends Observable{

	private GameMakerDemoFrame gameDemoPanel;
	private ControlPanel controlPanel;
	private Timer timer;
	private AutoEvent autoEvent;
	private CollisionEvent collisionEvent;
	private KeyBoardEvent keyBoardEvent;
	private PreviewPanel previewPanelObject;
	private int currentMinute;
	private int currentSecond;
	private int timeCounter;

	public TimerObservable(final ControlPanel controlPanel, PreviewPanel previewPanelObject) {
		//initialize the clock time and event and action classes
		this.currentMinute = 0;
		this.currentSecond = 0;
		this.timeCounter = 0;
		this.controlPanel = controlPanel;
		this.autoEvent = new AutoEvent();
		collisionEvent = new CollisionEvent();
		this.previewPanelObject = previewPanelObject;
		gameDemoPanel = new GameMakerDemoFrame(controlPanel.getGameData(), previewPanelObject, this);
		gameDemoPanel.initializeDemoGame();

	}

	public void startPreview() {
		assignKeyboardEvent();
		for (EventActionAssociation eaa : controlPanel.getGameData().getEventTable()){
			eaa.getSprite().setX(eaa.getSprite().getXinit());
			eaa.getSprite().setY(eaa.getSprite().getYinit());
			eaa.getSprite().setIsVisible(1);
			eaa.getSprite().setXdir(1);
			eaa.getSprite().setYdir(-1);
		}
		addObserver(gameDemoPanel);
		timer = new Timer();
		//create new timer task
		timer.scheduleAtFixedRate(new TimeTask(), 10, 8);
	}

	//handle keyboard event
	public void assignKeyboardEvent() {
		for (EventActionAssociation eventEntry : controlPanel.getGameData().getEventTable()) {

			for (String eventName : eventEntry.getEventNameList()) {
				switch (eventName) {
				case "KEYBOARD_PRESS_EVENT":
					ArrayList<ActionConditionAssociation> actionCondition = eventEntry.getActionList();
					gameDemoPanel.addKeyListener(new TAdapter(eventEntry.getSprite(), actionCondition));
					break;
				}
			}
		}
	}

	//handle all events and actions associated with specific sprites
	public void computePreview() {
		for (EventActionAssociation eventEntry : controlPanel.getGameData().getEventTable()) {
			for (String eventName : eventEntry.getEventNameList())
				switch (eventName) {
				//auto event
				case "AUTO":
					for (ActionConditionAssociation ac : eventEntry.getActionList()) {
						//free move sprite object when set to FREE_MOVE
						if (ac.actionType.contains("FREE_MOVE")) {
							autoEvent.FreeMove(eventEntry.getSprite());
						}
					}
					break;
				
				//collision event
				case "COLLISION_EVENT":
					for (ActionConditionAssociation ac : eventEntry.getActionList()) {
						//change the sprite object coordinates for reflect action after collision
						if (ac.actionType.contains("REFLECT_ACTION")) {

							if (ac.getAssociatedSpriteList() != null) {
								for (Sprite spriteName : ac.getAssociatedSpriteList()) {
									if (spriteName.getIsVisible() == 1 && eventEntry.getSprite().getIsVisible() == 1) {
										if (collisionEvent.detectCollision(spriteName, eventEntry.getSprite())) {
											collisionEvent.BounceOnCollision(spriteName, eventEntry.getSprite());
										}
									}
								}
							}
						}
						//play sound after collision
						if (ac.actionType.contains("PLAY_COLLISION_SOUND_ACTION")) {
							if (ac.getAssociatedSpriteList() != null) {
								for (Sprite spriteName : ac.getAssociatedSpriteList()) {
									if (spriteName.getIsVisible() == 1 && eventEntry.getSprite().getIsVisible() == 1) {
										if (collisionEvent.detectCollision(spriteName, eventEntry.getSprite())) {
											try {
												collisionEvent.PlayCollisionSoundAction();
											} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
						}
						//make the sprite invisible after collision
						if (ac.actionType.contains("VANISH_ACTION")) {
							if (ac.getAssociatedSpriteList() != null) {
								for (Sprite spriteName : ac.getAssociatedSpriteList()) {
									if (spriteName.getIsVisible() == 1 && eventEntry.getSprite().getIsVisible() == 1) {
										if (collisionEvent.detectCollision(spriteName, eventEntry.getSprite())) {
											eventEntry.getSprite().setIsVisible(0);
										}
									}
								}
							}
						}

					}
					break;
				}
		}
	}
	
	//stop timer thread
	public void closePreview(){
		deleteObservers();
		timer.cancel();
	}

	//adjust seconds and minutes for clock display
	public class TimeTask extends TimerTask {

		@Override
		public void run() {
			timeCounter = timeCounter + 5;
			if (timeCounter == 1000) {
				timeCounter = 0;
				currentSecond = currentSecond + 1;

				if (currentSecond == 60) {
					currentMinute = currentMinute + 1;
					currentSecond = 0;
				}
			}
			gameDemoPanel.setCurrentSecond(currentSecond);
			gameDemoPanel.setCurrentMinute(currentMinute);
			computePreview();
			setChanged();
			notifyObservers(null);
		}
	}

	//handle keyboard events
	private class TAdapter extends KeyAdapter {

		private Sprite sprite;
		private ArrayList<ActionConditionAssociation> actionCondition;
		private KeyBoardEvent keyBoardEvent;
		private int key;

		private TAdapter(Sprite sprite, ArrayList<ActionConditionAssociation> actionCondition) {
			this.sprite = sprite;
			this.actionCondition = actionCondition;
			keyBoardEvent = new KeyBoardEvent();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			key = e.getKeyCode();
			for (ActionConditionAssociation ac : actionCondition) {
				if (ac.actionType.contains("MOVE_RIGHT")) {
					if (key == KeyEvent.VK_RIGHT) {
						keyBoardEvent.moveRight(sprite, 2);
					}
				}
				if (ac.actionType.contains("MOVE_LEFT")) {
					if (key == KeyEvent.VK_LEFT) {
						keyBoardEvent.moveLeft(sprite, 2);
					}
				}
				if (ac.actionType.contains("MOVE_DOWN")) {
					if (key == KeyEvent.VK_DOWN) {
						keyBoardEvent.moveDown(sprite, 2);
					}
				}
				if (ac.actionType.contains("MOVE_UP")) {
					if (key == KeyEvent.VK_UP) {
						keyBoardEvent.moveUp(sprite, 2);
					}
				}
			}
		}
	}
}
