package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CloseCommand;
import static model.GameConstants.FRAME_HEIGHT;
import static model.GameConstants.PREVIEW_PANEL_HEIGHT;
import static model.GameConstants.PREVIEW_PANEL_WIDTH;
import static model.GameConstants.CLOCK_PANEL_HEIGHT;
import static model.GameConstants.GAME_AREA_HEIGHT;
import static model.GameConstants.GAME_AREA_WIDTH;
import model.GameData;
import model.TimerObservable;
import model.GameConstants.ColorEnum;

/* This class is used to display the demo frame after user clicks on the */
public class GameMakerDemoFrame extends JFrame implements Observer {
	private JPanel gameAreaPanel;
	private JPanel clockPanel;
	private JPanel mainPanel;
	private JLabel timeLabel;
	private int currentMinute;
	private int currentSecond;
	private PreviewPanel previewPanelObject;
	private boolean clockFlag;
	private TimerObservable timerObs;

	private GameData gameData;
	private CloseCommand closeCommand;

// Initial game demo frame which add demo frame and clock	
	public GameMakerDemoFrame(GameData gameData, PreviewPanel previewPanelObject, TimerObservable timerObs) {
		this.timerObs = timerObs;
		this.currentMinute = 0;
		this.currentSecond = 0;
		this.gameData = gameData;
		this.previewPanelObject = previewPanelObject;
		this.clockFlag = previewPanelObject.isClockPanelPresent();
		this.timeLabel = new JLabel("00:00");
	}

	public void initializeDemoGame() {

		add(createMainPanel());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeCommand = new CloseCommand(timerObs);
				closeCommand.execute();
			}
		});
		setTitle("Game Preview");
		setBackground(Color.BLACK);

		setSize(PREVIEW_PANEL_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		repaint();
		pack();
		GAME_AREA_HEIGHT = this.gameAreaPanel.getHeight();
		GAME_AREA_WIDTH = this.gameAreaPanel.getWidth();

	}

	private JPanel createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		createGameAreaPanel();
		mainPanel.add(this.gameAreaPanel);

		if (previewPanelObject.isClockPanelPresent()) {
			clockFlag = true;
			addClockPanel();
		}

		mainPanel.setMaximumSize(new Dimension(PREVIEW_PANEL_WIDTH, PREVIEW_PANEL_HEIGHT));
		mainPanel.setPreferredSize(new Dimension(PREVIEW_PANEL_WIDTH, PREVIEW_PANEL_HEIGHT));
		mainPanel.setBackground(ColorEnum.COLOR_PREVIEW_GAME_PANEL.getColorCode());
		return mainPanel;
	}

	//Clock panel
	private void addClockPanel() {
		createClockPanel();

		mainPanel.add(clockPanel);
		clockFlag = true;
		mainPanel.validate();
		mainPanel.repaint();

	}

	// main demo panel
	// main demo panel
	private void createGameAreaPanel() {
		this.gameAreaPanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				if (clockFlag) {
					timeLabel.setText(String.format("%02d:%02d", currentMinute, currentSecond));
				}
				super.paint(g);
				if (gameData.getPreviewPanelBackGroundImage() != null)
					g.drawImage(gameData.getPreviewPanelBackGroundImage().getImage(), 0, 0, this);
				if (!gameData.getSprites().isEmpty()) {
					for (int i = 0; i < gameData.getSprites().size(); i++) {
						if (gameData.getSprites().get(i).getIsVisible() == 1) {
							g.drawImage(gameData.getSprites().get(i).getImageIcon().getImage(), gameData.getSprites().get(i)
									.getX(), gameData.getSprites().get(i).getY(), this);
						}
					}
				}
			}
		};
		gameAreaPanel.setBackground(ColorEnum.COLOR_PREVIEW_GAME_PANEL.getColorCode());
	}

	private void createClockPanel() {
		this.clockPanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		clockPanel.setLayout(layout);
		clockPanel.add(timeLabel);
		clockPanel.setMaximumSize(new Dimension(PREVIEW_PANEL_WIDTH, CLOCK_PANEL_HEIGHT));
		clockPanel.setPreferredSize(new Dimension(PREVIEW_PANEL_WIDTH, CLOCK_PANEL_HEIGHT));
		timeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		timeLabel.setForeground(Color.white);
		clockPanel.setBackground(Color.black);
	}

	// Clock panel
	@Override
	public void update(Observable obs, Object arg1) {
		timerObs = (TimerObservable) obs;
		requestFocusInWindow();
		repaint();
	}

	public int getCurrentMinute() {
		return currentMinute;
	}

	public void setCurrentMinute(int currentMinute) {
		this.currentMinute = currentMinute;
	}

	public int getCurrentSecond() {
		return currentSecond;
	}

	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}

}
