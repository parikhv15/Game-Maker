package view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.GameConstants.GameMode;
import static model.GameConstants.FRAME_HEIGHT;
import static model.GameConstants.FRAME_WIDTH;

/* Class to display initial game maker application screen consisting of control panel and preview panel*/
public class GameMaker {

	private JFrame gameMakerFrame;
	private String frameTitle;
	private GameMode gameMode;

	public GameMaker(String frameTitle, GameMode gameMode) {

		this.gameMode = gameMode;
		this.frameTitle = frameTitle+ " - Game Maker";		
		gameMakerFrame = new JFrame(this.frameTitle);
		gameMakerFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		gameMakerFrame.setLocationRelativeTo(null);
		gameMakerFrame.setVisible(true);
	}

	private void createGameMakerUI(JFrame gameMakerFrame) {
		// Create the panels
		GamePanel gamePanel = new GamePanel(frameTitle, gameMode);
		Component panes = gamePanel.createPanes();
		gameMakerFrame.add(panes);
	}

	/* set look and feel for the frame and initialize the frame parameters */
	public void initializeFrame() {

		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException e) {
			System.err
					.println("Couldn't find class for specified look and feel:");
		} catch (Exception e) {
			System.err
					.println("Couldn't find class for specified look and feel:");
			e.printStackTrace();
		}
		
		this.createGameMakerUI(gameMakerFrame);
		gameMakerFrame.setVisible(true);
		gameMakerFrame.setResizable(false);
		gameMakerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}