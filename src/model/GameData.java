package model;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

//Class used to provide functionality for load and save. It is also used to provide functionality 
//of associate and deassociate events and actions

public class GameData implements Serializable {

	public enum ActionCondition {
		TOP_FRAME, BOTTOM_FRAME, LEFT_FRAME, RIGHT_FRAME, SPRITE;// GAME_WIN,
																	// GAME_LOSE,
																	// GAME_CONTINUE;
	};

	// Constants for the various Event types
	public enum EventType {
		NONE, KEYBOARD_PRESS_EVENT, COLLISION_EVENT, AUTO;
	};

	// Constants for the various KeyBoard Press Event Action types
	public enum KeyboardPressEventActionType {
		MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN;
	};

	// Constants for the various Collision Event Action types
	public enum CollisionEventActionType {
		REFLECT_ACTION, PLAY_COLLISION_SOUND_ACTION, VANISH_ACTION, STATIC_ACTION
	};

	public enum ReflectActionType {
		FRAME_TOP, FRAME_BOTTOM, FRAME_LEFT, FRAME_RIGHT, SPRITE
	};

	// Constants for the various Automatic / Free Movement action types
	public enum AutoEventActionType {
		FREE_MOVE
	};

	private ActionCondition gameCondition;
	private EventType eventType;
	private KeyboardPressEventActionType keyboardPressEventActionType;
	private CollisionEventActionType collisionEventActionType;
	private AutoEventActionType autoEventActionType;
	private ReflectActionType reflectActionType;
	private String frameTitle;
	
	private DefaultListModel gameSummaryModel;

	private ArrayList<EventActionAssociation> eventTable;
	private ImageIcon previewPanelBackGroundImage;
	private ArrayList<Sprite> sprites;

	private HashMap<String, Integer> spriteNameTrackerHashMap;

	public GameData() {
		eventTable = new ArrayList<EventActionAssociation>();
		sprites = new ArrayList<Sprite>();
		spriteNameTrackerHashMap = new HashMap<String, Integer>();
		gameSummaryModel = new DefaultListModel();
	}

	public void associateEventsToSprite(EventActionAssociation eventEntry) {
		eventTable.add(eventEntry);
	}

	public void deassociateEventsToSprite(Sprite spriteSelected) {
		for (EventActionAssociation eaa : eventTable) {
			if (eaa.getSprite() == spriteSelected) {
				eventTable.remove(eaa);
				break;
			}
		}

	}

	public Boolean checkSpriteEntry(Sprite spriteSelected) {
		for (EventActionAssociation eaa : eventTable) {
			if (eaa.getSprite() == spriteSelected) {
				return true;
			}
		}
		return false;
	}

	//functions to implement the save functionality
	public void saveMe() {
		File file = new File(frameTitle + ".ser");
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//functions to implement the load functionality
	public void loadMe() {

		FileInputStream fileLoad;
		GameData tempGameData = null;
		try {
			fileLoad = new FileInputStream(frameTitle + ".ser");
			ObjectInputStream loadStream = new ObjectInputStream(fileLoad);
			try {
				tempGameData = (GameData) loadStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.setEventTable(tempGameData.getEventTable());
			this.setSprites(tempGameData.getSprites());
			this.setSpriteNameTrackerHashMap(tempGameData.getSpriteNameTrackerHashMap());
			this.setPreviewPanelBackGroundImage(tempGameData.getPreviewPanelBackGroundImage());
			this.setGameSummaryModel(tempGameData.getGameSummaryModel());
			loadStream.close();
			fileLoad.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite getSelectedSprite(String spriteNameSelected) {
		for (Sprite sprite : sprites) {
			if (sprite.getName().equals(spriteNameSelected)) {
				return sprite;
			}
		}
		return null;
	}

	public String[] getEnumStringArray(ActionCondition actionCondition) {
		return Arrays.toString(ActionCondition.values()).replaceAll("^.|.$", "").split(", ");
	}

	public String[] getEnumStringArray(EventType eventType) {
		return Arrays.toString(EventType.values()).replaceAll("^.|.$", "").split(", ");
	}

	public String[] getEnumStringArray(ReflectActionType reflectEventActionType) {
		return Arrays.toString(ReflectActionType.values()).replaceAll("^.|.$", "").split(", ");
	}

	public String[] getEnumStringArray(KeyboardPressEventActionType actionType) {
		return Arrays.toString(KeyboardPressEventActionType.values()).replaceAll("^.|.$", "").split(", ");
	}

	public String[] getEnumStringArray(CollisionEventActionType collisionEventActionType) {
		return Arrays.toString(CollisionEventActionType.values()).replaceAll("^.|.$", "").split(", ");
	}

	public String[] getEnumStringArray(AutoEventActionType autoEventActionType) {
		return Arrays.toString(AutoEventActionType.values()).replaceAll("^.|.$", "").split(", ");
	}

	public ArrayList<EventActionAssociation> getEventTable() {
		return eventTable;
	}

	public void setEventTable(ArrayList<EventActionAssociation> eventTable) {
		this.eventTable = eventTable;
	}

	public EventType getEventType() {
		return eventType;
	}

	public KeyboardPressEventActionType getKeyboardPressEventActionType() {
		return keyboardPressEventActionType;
	}

	public CollisionEventActionType getCollisionEventActionType() {
		return collisionEventActionType;
	}

	public AutoEventActionType getAutoEventActionType() {
		return autoEventActionType;
	}

	public ReflectActionType getReflectActionType() {
		return reflectActionType;
	}

	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}

	public ImageIcon getPreviewPanelBackGroundImage() {
		return previewPanelBackGroundImage;
	}

	public void setPreviewPanelBackGroundImage(ImageIcon previewPanelBackGroundImage) {
		this.previewPanelBackGroundImage = previewPanelBackGroundImage;
	}

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}

	public HashMap<String, Integer> getSpriteNameTrackerHashMap() {
		return spriteNameTrackerHashMap;
	}

	public void setSpriteNameTrackerHashMap(HashMap<String, Integer> spriteNameTrackerHashMap) {
		this.spriteNameTrackerHashMap = spriteNameTrackerHashMap;
	}

	public DefaultListModel getGameSummaryModel() {
		return gameSummaryModel;
	}

	public void setGameSummaryModel(DefaultListModel gameSummaryModel) {
		this.gameSummaryModel = gameSummaryModel;
	}
}
