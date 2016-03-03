package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.GameData;
import model.Sprite;
import model.GameConstants.GameMode;

/* class used for game maker application frame. This class divides main frame into two frames 
 * one of which is used as control frame to select sprite objects, add events etc and the other 
 * frame is used for preview panel 
 */

class GamePanel {

	private JSplitPane splitPaneH;

	private JPanel previewGameJPanel;
	private JPanel controlPanel;

	private GameData gameData;
	private PreviewPanel previewPanelObject;
	private ControlPanel controlPanelObject;
	private Image previewBackGroundImage;
	private JPanel panels;
	private String frameTitle;
	private GameMode gameMode;

	public GamePanel(String frameTitle, GameMode gameMode) {
		this.gameMode = gameMode;
		this.frameTitle = frameTitle;
	}

//method to split given frame into two frames
	public Component createPanes() {

		panels = new JPanel();
		panels.setLayout(new BorderLayout());
		
		gameData = new GameData();
		
		previewPanelObject = new PreviewPanel();
		previewGameJPanel = previewPanelObject.createPreviewPanel(gameData);

		controlPanelObject = new ControlPanel(gameData, previewPanelObject);
		controlPanel = controlPanelObject.createControlPanel(frameTitle, gameMode);

		splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneH.setLeftComponent(controlPanel);
		splitPaneH.setRightComponent(previewGameJPanel);
		panels.add(splitPaneH, BorderLayout.CENTER);
		return panels;

	}

}
