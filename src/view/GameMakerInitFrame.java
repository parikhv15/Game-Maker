package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FilenameFilter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import controller.LoadCommand;
import model.GameConstants;
import model.GameConstants.ColorEnum;
import model.GameConstants.GameMode;
import model.PlaySound;
import model.GameConstants.SoundMode;

//Initial frame which gives option to user for creating new frame 
//or loading an already saved game
public class GameMakerInitFrame {

	private JFrame mainFrame;
	private JLabel newGameLabel;
	private JLabel loadGameLabel;
	private JTextField textTextField;
	private JComboBox cbComboBox;
	private JButton createGameButton;
	private JButton cancelButton;
	private JButton loadButton;
	private JButton loadGameButton;
	private JButton newGameButton;
	private JPanel formPanel;
	private JPanel initialOptionsPanel;
	private JPanel buttonsPanel;
	private PlaySound playSound;
	private GameMaker gameMaker;
	private JPanel cards, newGameCardPanel, loadGameCardPanel;
	private String newGameCard, loadGameCard, initCard;
	private ArrayList<File> gameList;
	private GameMode gameMode;
	private String selectedGameName;

	public GameMakerInitFrame() {

		playSound = new PlaySound();
		File dir = new File(".");
		gameList = new ArrayList<File>(Arrays.asList(dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ser"); // or something else
			}
		})));

		prepareGUI();
	}

	public static void main(String[] args) {
		GameMakerInitFrame gameMakerInitFrame = new GameMakerInitFrame();
		gameMakerInitFrame.createInitialOptionsPanel();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Game Maker");
		mainFrame.setSize(300, 150);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}

	private void createInitialOptionsPanel() {

		initialOptionsPanel = new JPanel();
		newGameButton = new JButton("New Game");
		loadGameButton = new JButton("Load Game");

		initialOptionsPanel.setLayout(new FlowLayout());
		initialOptionsPanel.setLayout(new FlowLayout());
		initialOptionsPanel.add(newGameButton, BorderLayout.WEST);
		initialOptionsPanel.add(loadGameButton, BorderLayout.EAST);

		initialOptionsPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		JPanel defaultCardPanel = new JPanel();
		JPanel newGameCardPanel = createNewGameCardPanel();
		JPanel loadGameCardPanel = createLoadGameCardPanel();
		defaultCardPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		mainFrame.add(initialOptionsPanel, BorderLayout.NORTH);

		initCard = "Default Card";
		newGameCard = "New Game";
		loadGameCard = "Load Game";

		// Create the Default, New Game and Load Game panel.
		cards = new JPanel(new CardLayout());
		cards.add(defaultCardPanel, initCard);
		cards.add(newGameCardPanel, newGameCard);
		cards.add(loadGameCardPanel, loadGameCard);
		cards.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		// Action Listener for New Game Mode
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadGameButton.setEnabled(true);
				newGameButton.setEnabled(false);
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) newGameCard);

			}
		});

		// Action Listener for Load Game Mode
		loadGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				loadGameButton.setEnabled(false);
				newGameButton.setEnabled(true);
				for (File a : gameList) {
					cbComboBox.addItem(a.getName().substring(0, a.getName().length() - 4));
				}

				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) loadGameCard);

			}
		});
		mainFrame.add(cards);
		mainFrame.validate();

	}

	private JPanel createNewGameCardPanel() {

		newGameCardPanel = new JPanel();
		newGameLabel = new JLabel("Enter Name");
		textTextField = new JTextField(15);
		createGameButton = new JButton("Create");
		cancelButton = new JButton("Cancel");
		formPanel = new JPanel();
		buttonsPanel = new JPanel();

		newGameLabel.setLabelFor(textTextField);

		// Action Listener for Create Game Button
		createGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameMode = GameMode.NEW_GAME;
				String textFieldValue = textTextField.getText();

				if (textFieldValue.equals("")) { // User have not entered
													// anything.

					try {
						playSound.playSoundMethod(SoundMode.ERROR_MESSAGE);
					} catch (UnsupportedAudioFileException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					} catch (LineUnavailableException e) {

						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Please enter game name!!");
					textTextField.requestFocusInWindow();
				} else {
					gameMaker = new GameMaker(textFieldValue, gameMode);
					gameMaker.initializeFrame();
					mainFrame.setVisible(false);
				}
			}
		});

		// Action Listener for Cancel Button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) initCard);
				loadGameButton.setEnabled(true);
				newGameButton.setEnabled(true);
			}
		});

		formPanel.add(newGameLabel);
		formPanel.add(textTextField);

		buttonsPanel.add(createGameButton);
		buttonsPanel.add(cancelButton);

		newGameCardPanel.setLayout(new BoxLayout(newGameCardPanel, BoxLayout.Y_AXIS));

		newGameCardPanel.add(formPanel);
		newGameCardPanel.add(buttonsPanel);
		textTextField.requestFocus();
		formPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		buttonsPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		newGameCardPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		return newGameCardPanel;

	}

	private JPanel createLoadGameCardPanel() {

		loadGameCardPanel = new JPanel();
		loadGameLabel = new JLabel("Select Game");
		cbComboBox = new JComboBox();
		cancelButton = new JButton("Cancel");
		loadButton = new JButton("Load");
		formPanel = new JPanel();
		buttonsPanel = new JPanel();

		loadGameLabel.setLabelFor(cbComboBox);
		// Action Listener for Load Button
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameMode = GameMode.LOAD_GAME;
				selectedGameName = (String) cbComboBox.getSelectedItem();

				selectedGameName = selectedGameName.substring(0,
						selectedGameName.length() - 13);

				if (selectedGameName.equals("")) { // User have not entered
													// anything.

					JOptionPane.showMessageDialog(null,
							"Please a Game to Load!!");
					textTextField.requestFocusInWindow();
				} else {
					gameMaker = new GameMaker(selectedGameName, gameMode);
					gameMaker.initializeFrame();
					mainFrame.setVisible(false);
				}
			}
		});

		// Action Listener for Cancel Button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) initCard);
				loadGameButton.setEnabled(true);
				newGameButton.setEnabled(true);
			}
		});

		formPanel.add(loadGameLabel);
		formPanel.add(cbComboBox);

		buttonsPanel.add(loadButton);
		buttonsPanel.add(cancelButton);

		loadGameCardPanel.setLayout(new BoxLayout(loadGameCardPanel, BoxLayout.Y_AXIS));

		loadGameCardPanel.add(formPanel);
		loadGameCardPanel.add(buttonsPanel);
		formPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		buttonsPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		loadGameCardPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		return loadGameCardPanel;

	}

}