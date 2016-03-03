package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

import controller.AssociateCommand;
import controller.DeassociateCommand;
import controller.LoadCommand;
import controller.PreviewCommand;
import controller.SaveCommand;
import model.ActionConditionAssociation;
import model.EventActionAssociation;
import model.GameConstants.GameMode;
import model.GameData;
import model.GameData.EventType;
import model.Sprite;
import model.SpriteListRenderer;
//Importing the constants required for the Game Maker application's view component 
import model.GameConstants.ColorEnum;
import model.GameConstants.GridBagContraintInsets;
import model.TimerObservable;
import static model.GameConstants.ACTION_TYPE_SCROLL_PANE_DIMENSION_HEIGHT;
import static model.GameConstants.ACTION_TYPE_SCROLL_PANE_DIMENSION_WIDTH;
import static model.GameConstants.BG_IMAGE_SCROLL_PANE_DIMENSION_HEIGHT;
import static model.GameConstants.BG_IMAGE_SCROLL_PANE_DIMENSION_WIDTH;
import static model.GameConstants.CONTROL_PANEL_HEIGHT;
import static model.GameConstants.CONTROL_PANEL_WIDTH;
import static model.GameConstants.GENERIC_SCROLL_PANE_DIMENSION_HEIGHT;
import static model.GameConstants.GENERIC_SCROLL_PANE_DIMENSION_WIDTH;
import static model.GameConstants.SUMMARY_SCROLL_PANE_DIMENSION_HEIGHT;
import static model.GameConstants.SUMMARY_SCROLL_PANE_DIMENSION_WIDTH;
import static model.GameConstants.VISIBLE_ROW_COUNT_SCROLL_PANE;
import static model.GameConstants.VISIBLE_ROW_COUNT_SUMMARY;

/**
 * 
 * Creating the Control Panel component It provides the interface to the user to
 * build the game. This is the main interface user will use to select sprites and allocate 
 * multiple events and actions to created sprites
 */
public class ControlPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private PreviewCommand previewCommand;
	private AssociateCommand associateCommand;
	private DeassociateCommand deassociateCommand;
	private JPanel gameMakerControlButtonsPanel;
	private JPanel controlPanel;
	private JLabel warningLabel;
	private SpriteSelector selectorSelector;
	private String textFieldString;
	private String borderTitleString;
	private JList actionTypeList;
	private JList actionConditionList;
	private JList spriteAssociationList;
	private GameData gameData;
	private PreviewPanel previewPanelObject;
	private SaveCommand saveCommand;
	private String frameTitle;
	private JComboBox eventTypeComboBox;
	private JCheckBox clockCheckBox;
	private StringBuffer gameSummaryStringBuffer;
	private TimerObservable timerObs;

	JTextField spriteNameTextField = new JTextField(10);

	/*
	 * Hash Map to store the Sprite Name and a value which would help in
	 * computing its unique identifier value Ex : If user enters "Ball" as the
	 * Sprite Name and drags/creates a sprite. This Hash Map would be checked
	 * for a key "Ball". If found, the value would be retrieved. The new name
	 * for sprite would be computed as "Key+"_"+(Value+1)". Example : "Ball_05".
	 * If not found, A new name for sprite would be computed as Sprite Name
	 * entered by user + "_" + 01 A new key with Sprite Name entered is added to
	 * the hashmap with the value 1 i.e., a "Ball_01" in this case.
	 * 
	 * Thus, the identifier of the ball will be calculated based on the "Value".
	 */

	// private HashMap<String, Integer> spriteNameTrackerHashMap;

	private String eventSelectedValue;

	private String spriteNameSelected;

	private DefaultListModel actionTypeListModel;
	private DefaultComboBoxModel createdSpriteNamesListModel; 
	private DefaultComboBoxModel associatedSpriteNamesListModel;
	private DefaultListModel actionConditionListModel;
	private Dimension actionTypeScrollPaneDimension;
	private Dimension genericScrollPaneDimension;
	private Dimension summaryScrollPaneDimension;
	private Dimension backgroundImageScrollPaneDimension;
	private LoadCommand loadCommnad;

	// Creating an empty constructor for JUnit test cases

	ControlPanel(GameData gameData, PreviewPanel previewPanelObject) {

		setPreviewPanelObject(previewPanelObject);
		setGameData(gameData);
		gameSummaryStringBuffer = new StringBuffer();

		// Initializing the dimensions of the different panes
		genericScrollPaneDimension = new Dimension(GENERIC_SCROLL_PANE_DIMENSION_WIDTH, GENERIC_SCROLL_PANE_DIMENSION_HEIGHT);
		actionTypeScrollPaneDimension = new Dimension(ACTION_TYPE_SCROLL_PANE_DIMENSION_WIDTH,
				ACTION_TYPE_SCROLL_PANE_DIMENSION_HEIGHT);
		summaryScrollPaneDimension = new Dimension(SUMMARY_SCROLL_PANE_DIMENSION_WIDTH, SUMMARY_SCROLL_PANE_DIMENSION_HEIGHT);
		backgroundImageScrollPaneDimension = new Dimension(BG_IMAGE_SCROLL_PANE_DIMENSION_WIDTH,
				BG_IMAGE_SCROLL_PANE_DIMENSION_HEIGHT);

		createdSpriteNamesListModel = new DefaultComboBoxModel();
		associatedSpriteNamesListModel = new DefaultComboBoxModel();
		warningLabel = new JLabel("Only alphanumeric characters and underscore allowed !");

	}

	public ControlPanel() {
	}

	/*
	 * Creates the control panel and adds the below sub-panels to it. 1.
	 * Background Selector 2. Sprite Selector 3. Action Event Associator 4. Game
	 * Maker Summary 5. Control Buttons
	 */
	public JPanel createControlPanel(String frameTitle, GameMode gameMode) {

		this.frameTitle = frameTitle;

		if (gameMode == GameMode.LOAD_GAME) {
			loadCommnad = new LoadCommand(gameData, frameTitle);
			loadCommnad.execute();
			updateSpriteList();
		}
		// setPreviewPanel(spriteBGImageDetailWrapper.getPreviewGamePanel());
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

		// Create and add Background Selection and Clock Option Panel
		controlPanel.add(createBGSelectorAndClockPanel());

		// Create and add Sprite Detail Panel
		controlPanel.add(createSpriteDetailPanel());

		// Create and add Action Event Association Panel
		controlPanel.add(createActionEventAssociationPanel());

		// Create and add Game Maker Summary Panel);
		controlPanel.add(createGameSummaryPanel());

		// Create and add Game Maker Control Button Panel);
		controlPanel.add(createGameMakerControlButtonsPanel());

		// Set the title, background color, size
		setBorderTitleString("Control Panel");
		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());

		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		controlPanel.setBorder(titledBorder);

		controlPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
		controlPanel.setMinimumSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
		controlPanel.setMaximumSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));

		return controlPanel;
	}

	private JPanel createActionEventAssociationPanel() {
		// Panel for Selecting Background Image
		JPanel actionEventAssociationPanel = new JPanel();

		actionEventAssociationPanel.setLayout(new BoxLayout(actionEventAssociationPanel, BoxLayout.PAGE_AXIS));

		setBorderTitleString("Event Action Association");
		actionEventAssociationPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		// Set the title and its color
		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		actionEventAssociationPanel.setBorder(titledBorder);

		// Create a Sprite List panel
		JPanel existingSpritesSelectorPanel = createExistingSpriteSelectorPanel();

		// Create an EventType panel
		JPanel eventTypePanel = createEventTypePanel();

		// Create a Panel to hold the Sprite List and Event Type panels
		JPanel spriteNamesAndEventTypesPanel = new JPanel();

		spriteNamesAndEventTypesPanel.setLayout(new BoxLayout(spriteNamesAndEventTypesPanel, BoxLayout.LINE_AXIS));

		// Add the two panels in the holder
		spriteNamesAndEventTypesPanel.add(existingSpritesSelectorPanel);
		spriteNamesAndEventTypesPanel.add(eventTypePanel);

		// Create a pane for Action Type, Game Condition, Sprite Association
		JScrollPane actionTypeScrollPane = createActionTypeScrollPane();
		JScrollPane actionConditionScrollPane = createActionConditionScrollPane();
		JScrollPane spriteAssociationPane = createSpriteAssociationScrollPane();

		// Create panels for Action Type, Game Condition and Sprite Association

		JPanel actionTypeGameDetailListPanel = createActionTypeActionConditionSpriteAssociationPanel(actionConditionScrollPane,
				actionTypeScrollPane, spriteAssociationPane);
		JPanel associateControlPanel = createAssociateControlPanel();

		// Add the sub panels to actionEventAssociationPanel
		// actionEventAssociationPanel.add(eventTypePanel);
		actionEventAssociationPanel.add(spriteNamesAndEventTypesPanel);
		actionEventAssociationPanel.add(actionTypeGameDetailListPanel);
		actionEventAssociationPanel.add(associateControlPanel);

		setVisible(true);

		// Set the background color

		spriteNamesAndEventTypesPanel.setBackground(ColorEnum.COLOR_INNER_CONTROL_PANEL.getColorCode());

		actionTypeGameDetailListPanel.setBackground(ColorEnum.COLOR_INNER_CONTROL_PANEL.getColorCode());
		actionEventAssociationPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		return actionEventAssociationPanel;
	}

	/**
	 * Creates a pane for Action Type
	 * 
	 * @param actionTypeList
	 * @return
	 */
	private JScrollPane createActionTypeScrollPane() {

		actionTypeListModel = new DefaultListModel();

		actionTypeList = new JList(actionTypeListModel);
		actionTypeList.setBackground(ColorEnum.COLOR_LIST_BACKGROUND.getColorCode());
		actionTypeList.setVisibleRowCount(VISIBLE_ROW_COUNT_SCROLL_PANE);
		actionTypeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane actionTypeScrollPane = new JScrollPane(actionTypeList);

		// Create and add a list listener
		ListSelectionListener actionTypeListListener = createListSelectionListener(actionTypeList);
		actionTypeList.addListSelectionListener(actionTypeListListener);

		actionTypeScrollPane.setMinimumSize(actionTypeScrollPaneDimension);

		return actionTypeScrollPane;
	}

	/**
	 * Creates a pane for Sprite Association
	 * 
	 * @param spriteAssociationList
	 * @return
	 */
	private JScrollPane createSpriteAssociationScrollPane() {

		spriteAssociationList = new JList(associatedSpriteNamesListModel);
		spriteAssociationList.setBackground(ColorEnum.COLOR_LIST_BACKGROUND.getColorCode());
		spriteAssociationList.setVisibleRowCount(VISIBLE_ROW_COUNT_SCROLL_PANE);
		spriteAssociationList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane spriteAssociationPane = new JScrollPane(spriteAssociationList);

		// Create and add a list listener
		ListSelectionListener spriteAssociationListListener = createListSelectionListener(spriteAssociationList);
		spriteAssociationList.addListSelectionListener(spriteAssociationListListener);

		spriteAssociationPane.setMinimumSize(genericScrollPaneDimension);
		return spriteAssociationPane;
	}

	/**
	 * Creates a pane for Game Condition
	 * 
	 * @param actionConditionList
	 * @return
	 */
	private JScrollPane createActionConditionScrollPane() {

		actionConditionListModel = new DefaultListModel();

		actionConditionList = new JList(actionConditionListModel);
		actionConditionList.setBackground(ColorEnum.COLOR_LIST_BACKGROUND.getColorCode());
		actionConditionList.setVisibleRowCount(VISIBLE_ROW_COUNT_SCROLL_PANE);
		actionConditionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane actionConditionScrollPane = new JScrollPane(actionConditionList);

		// Create and add a list listener
		ListSelectionListener actionConditionListListener = createListSelectionListener(actionConditionList);
		actionConditionList.addListSelectionListener(actionConditionListListener);
		actionConditionScrollPane.setMinimumSize(genericScrollPaneDimension);
		return actionConditionScrollPane;
	}

	/*
	 * Generic method to create a List Selection Listener to the passed JList
	 */
	private ListSelectionListener createListSelectionListener(final JList sourceJList) {
		ListSelectionListener sourceJListListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String destinations = "";
				Object[] objSelections = sourceJList.getSelectedValues();
				String[] selections = Arrays.copyOf(objSelections, objSelections.length, String[].class);

				for (String selection : selections) {
					destinations += selection.toString();
				}
			}
		};
		return sourceJListListener;
	}

	/*
	 * Creates the Game Summary panel
	 */
	private JPanel createGameSummaryPanel() {

		JPanel gameSummaryPanel = new JPanel();
		gameSummaryPanel.setLayout(new FlowLayout());

//		gameSummaryModel = new DefaultListModel();

		JList gameSummaryList = new JList(gameData.getGameSummaryModel());
		gameSummaryList.setBackground(Color.WHITE);

		gameSummaryList.setVisibleRowCount(VISIBLE_ROW_COUNT_SUMMARY);
		gameSummaryList.setEnabled(false);
		gameSummaryList.setBackground(ColorEnum.COLOR_SUMMARY_LIST.getColorCode());

		JScrollPane gameSummaryPane = new JScrollPane(gameSummaryList);

		gameSummaryPane.setMinimumSize(summaryScrollPaneDimension);
		gameSummaryPane.setMaximumSize(summaryScrollPaneDimension);
		gameSummaryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		gameSummaryPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		gameSummaryPanel.add(gameSummaryPane);

		// Set the title, background color, size
		setBorderTitleString("Game Summary");

		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		gameSummaryPanel.setBorder(titledBorder);
		gameSummaryPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		return gameSummaryPanel;
	}

	private JPanel createAssociateControlPanel() {

		final JPanel associateControlPanel = new JPanel();
		associateControlPanel.setLayout(new FlowLayout());

		JButton associateButton = new JButton("Associate");
		JButton deassociateButton = new JButton("Deassociate");

		associateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				int[] getSelectedIndex;
				// String Buffers for Populating the Summary
				StringBuffer associatedSpriteSummaryStringBuffer = new StringBuffer();
				StringBuffer actionConditionTypeSummaryStringBuffer = new StringBuffer();
				StringBuffer actionTypeSummaryStringBuffer = new StringBuffer();

				Sprite spriteSelected = getGameData().getSelectedSprite(spriteNameSelected);
				if (gameData.checkSpriteEntry(spriteSelected) == false) {
					EventActionAssociation eventEntry = new EventActionAssociation();
					ArrayList<String> actionCondType = new ArrayList<String>();
					ArrayList<String> actionType = new ArrayList<String>();
					ArrayList<Sprite> associatedSprite = new ArrayList<Sprite>();

					if (spriteNameSelected == null) {
						JOptionPane.showMessageDialog(ControlPanel.this.controlPanel, "Create a sprite first !");
					} else if (spriteNameSelected.equals("")) {
						JOptionPane.showMessageDialog(ControlPanel.this.controlPanel, "Select a sprite !");
					} else if (eventSelectedValue == null || eventSelectedValue.equals("NONE")) {
						JOptionPane.showMessageDialog(ControlPanel.this.controlPanel, "Select an Event Type !");
					} else {
						gameSummaryStringBuffer.append(spriteNameSelected + " | ");
						eventEntry.getEventNameList().add(eventSelectedValue);
						gameSummaryStringBuffer.append(eventSelectedValue + " | ");

						spriteSelected = getGameData().getSelectedSprite(spriteNameSelected);

						if (spriteSelected != null)
							eventEntry.setSprite(spriteSelected);
						else
							JOptionPane.showMessageDialog(ControlPanel.this.controlPanel, "Sprite Not Found!");

						if (actionConditionList != null) {
							getSelectedIndex = actionConditionList.getSelectedIndices();
							// Get all the selected items using the indices
							for (int i = 0; i < getSelectedIndex.length; i++) {

								String actionCond = actionConditionList.getModel().getElementAt(getSelectedIndex[i]).toString();

								switch (actionCond) {
								case "FRAME_RIGHT":
									spriteSelected.setRight("FRAME_RIGHT");
									break;
								case "FRAME_TOP":
									spriteSelected.setTop("FRAME_TOP");
									break;
								case "FRAME_BOTTOM":
									spriteSelected.setBottom("FRAME_BOTTOM");
									break;
								case "FRAME_LEFT":
									spriteSelected.setLeft("FRAME_LEFT");
									break;
								}
								actionCondType.add(actionCond);
								actionConditionTypeSummaryStringBuffer.append(actionCond + " | ");
							}
						}

						if (spriteAssociationList != null) {
							getSelectedIndex = spriteAssociationList.getSelectedIndices();
							for (int i = 0; i < getSelectedIndex.length; i++) {
								Sprite associatedSpriteSelect = getGameData().getSelectedSprite(
										spriteAssociationList.getModel().getElementAt(getSelectedIndex[i]).toString());
								associatedSprite.add(associatedSpriteSelect);
								associatedSpriteSummaryStringBuffer.append(associatedSpriteSelect.getName());
							}
						}

						if (actionTypeList != null) {
							getSelectedIndex = actionTypeList.getSelectedIndices();
							String actionTypeString;
							for (int i = 0; i < getSelectedIndex.length; i++) {
								actionTypeString = actionTypeList.getModel().getElementAt(getSelectedIndex[i]).toString();
								actionType.add(actionTypeString);
								actionTypeSummaryStringBuffer.append(actionTypeString + " | ");
							}
						}

						if (actionCondType != null && associatedSprite != null) {
							eventEntry.getActionList().add(
									new ActionConditionAssociation(actionType, actionCondType, associatedSprite));
						} else {
							eventEntry.getActionList().add(new ActionConditionAssociation(actionType));
						}
						associateCommand = new AssociateCommand(gameData, eventEntry);
						associateCommand.execute();

					}

				}

				else {
					gameSummaryStringBuffer.append(spriteSelected.getName() + " | ");
					for (EventActionAssociation ea : gameData.getEventTable()) {
						if (ea.getSprite() == spriteSelected) {
							ea.getEventNameList().add(eventSelectedValue);
							gameSummaryStringBuffer.append(eventSelectedValue + " | ");
							if (actionTypeList != null) {
								getSelectedIndex = actionTypeList.getSelectedIndices();
								for (int i = 0; i < getSelectedIndex.length; i++) {
									for (ActionConditionAssociation aca : ea.getActionList()) {
										String actionTypeString = actionTypeList.getModel().getElementAt(getSelectedIndex[i])
												.toString();
										aca.getActionType().add(actionTypeString);
										actionTypeSummaryStringBuffer.append(actionTypeString + " | ");
									}
								}
							}

							if (spriteAssociationList != null) {
								getSelectedIndex = spriteAssociationList.getSelectedIndices();
								for (int i = 0; i < getSelectedIndex.length; i++) {
									for (ActionConditionAssociation aca : ea.getActionList()) {
										Sprite associatedSpriteName = gameData.getSelectedSprite(spriteAssociationList.getModel()
												.getElementAt(getSelectedIndex[i]).toString());
										aca.getAssociatedSpriteList().add(associatedSpriteName);
										associatedSpriteSummaryStringBuffer.append(associatedSpriteName.getName());

									}
								}

								if (actionConditionList != null) {
									getSelectedIndex = actionConditionList.getSelectedIndices();
									for (int i = 0; i < getSelectedIndex.length; i++) {
										for (ActionConditionAssociation aca : ea.getActionList()) {
											{
												String actionCondString = actionConditionList.getModel()
														.getElementAt(getSelectedIndex[i]).toString();
												switch (actionCondString) {
												case "FRAME_RIGHT":
													spriteSelected.setRight("FRAME_RIGHT");
													break;
												case "FRAME_TOP":
													spriteSelected.setTop("FRAME_TOP");
													break;
												case "FRAME_BOTTOM":
													spriteSelected.setBottom("FRAME_BOTTOM");
													break;
												case "FRAME_LEFT":
													spriteSelected.setLeft("FRAME_LEFT");
													break;
												}
												aca.getActionCondition().add(actionCondString);
												actionConditionTypeSummaryStringBuffer.append(actionCondString + " | ");

											}
										}
									}
								}
							}
						}
					}
				}
				// Updating the main string buffer of Game Summary
				gameSummaryStringBuffer.append(actionTypeSummaryStringBuffer.toString());
				gameSummaryStringBuffer.append(actionConditionTypeSummaryStringBuffer.toString());
				gameSummaryStringBuffer.append(associatedSpriteSummaryStringBuffer.toString());

				// Update the Game Summary Model / Pane only if the item doesn't
				// already exist
				// "  " has been added only for the better UI viewing purpose
				String gameSummaryItem = gameSummaryStringBuffer.toString().trim();
				if (gameSummaryItem.length() != 0 && gameData.getGameSummaryModel().contains(gameSummaryItem + "  ") == false) {
					gameData.getGameSummaryModel().addElement(gameSummaryItem + "  ");
				}
				ControlPanel.this.controlPanel.revalidate();
				gameSummaryStringBuffer.setLength(0);
			}
		});

		// add event listener for the button Remove
		deassociateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Sprite spriteSelected = getGameData().getSelectedSprite(spriteNameSelected);
				deassociateCommand = new DeassociateCommand(gameData, spriteSelected);
				deassociateCommand.execute();
				gameData.getGameSummaryModel().addElement(spriteSelected.getName() + " - Deassociated !  ");
				ControlPanel.this.controlPanel.revalidate();
			}
		});
		associateControlPanel.add(associateButton);
		associateControlPanel.add(deassociateButton);
		associateControlPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		return associateControlPanel;

	}

	private void highlightSprite(String spriteName) {
		int x, y, width, height;
		Sprite selectedSprite = getGameData().getSelectedSprite(spriteName);
		x = selectedSprite.getX() - 1;
		y = selectedSprite.getY() - 1;
		width = selectedSprite.getImageIcon().getImage().getWidth(null) + 1;
		height = selectedSprite.getImageIcon().getImage().getHeight(null) + 1;

		previewPanelObject.setHighlightMode(true);
		previewPanelObject.highlightSprite(x, y, width, height);
		previewPanelObject.getPreviewGameJPanel().repaint();

	}

	/**
	 * @param actionEventAssociationPanel
	 */
	private JPanel createExistingSpriteSelectorPanel() {

		JComboBox createdSpriteNamesComboBox = new JComboBox();
		createdSpriteNamesComboBox.setModel(createdSpriteNamesListModel);
		createdSpriteNamesComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				JComboBox combo = (JComboBox) event.getSource();

				if (combo.getSelectedIndex() < 1) {
					previewPanelObject.setHighlightMode(false);
					previewPanelObject.getPreviewGameJPanel().repaint();
					spriteNameSelected = "";
				} else {
					spriteNameSelected = (String) combo.getSelectedItem();
					highlightSprite(spriteNameSelected);

				}
				combo.getModel();
				ControlPanel.this.controlPanel.revalidate();
			}
		});

		JPanel createdSpriteNamesPanel = new JPanel();
		JLabel createdSpriteNamesLabel = new JLabel("Select Sprite");
		createdSpriteNamesLabel.setForeground(ColorEnum.COLOR_JLABEL_TEXT.getColorCode());
		createdSpriteNamesLabel.setLabelFor(createdSpriteNamesComboBox);
		createdSpriteNamesPanel.setLayout(new FlowLayout());
		createdSpriteNamesPanel.add(createdSpriteNamesLabel);
		createdSpriteNamesPanel.add(createdSpriteNamesComboBox);
		createdSpriteNamesPanel.setBackground(ColorEnum.COLOR_INNER_CONTROL_PANEL.getColorCode());
		return createdSpriteNamesPanel;

	}

	/**
	 * @param actionEventAssociationPanel
	 */
	private JPanel createEventTypePanel() {

		eventTypeComboBox = new JComboBox(gameData.getEnumStringArray(gameData.getEventType()));

		// add an event listener for the combo box
		eventTypeComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox combo = (JComboBox) event.getSource();
				eventSelectedValue = (String) combo.getSelectedItem();

				combo.getModel();

				EventType eventSelected = EventType.valueOf(eventSelectedValue);

				switch (eventSelected) {
				case NONE:
					actionTypeListModel.clear();
					break;

				case KEYBOARD_PRESS_EVENT:
					actionTypeListModel.clear();
					addItemToScrollPane(actionTypeListModel,
							gameData.getEnumStringArray(gameData.getKeyboardPressEventActionType()));

					actionConditionListModel.clear();
					break;

				case COLLISION_EVENT:
					actionTypeListModel.clear();
					addItemToScrollPane(actionTypeListModel, gameData.getEnumStringArray(gameData.getCollisionEventActionType()));

					actionConditionListModel.clear();
					addItemToScrollPane(actionConditionListModel, gameData.getEnumStringArray(gameData.getReflectActionType()));
					break;

				case AUTO:
					actionTypeListModel.clear();
					addItemToScrollPane(actionTypeListModel, gameData.getEnumStringArray(gameData.getAutoEventActionType()));
					actionConditionListModel.clear();

					break;

				default:
					break;
				}
				ControlPanel.this.controlPanel.revalidate();
			}
		});

		JPanel eventTypePanel = new JPanel();
		JLabel eventTypeLabel = new JLabel("Event Type");
		eventTypeLabel.setForeground(ColorEnum.COLOR_JLABEL_TEXT.getColorCode());
		eventTypeLabel.setLabelFor(eventTypeComboBox);
		eventTypePanel.add(eventTypeLabel);
		eventTypePanel.add(eventTypeComboBox);
		eventTypePanel.setBackground(ColorEnum.COLOR_INNER_CONTROL_PANEL.getColorCode());
		return eventTypePanel;

	}

	/*
	 * Adds a string to DefaultComboBoxModell and sorts it
	 */
	public void addItemToDefaultComboBoxModel(DefaultComboBoxModel defaultComboBoxModel, String item) {
		defaultComboBoxModel.addElement(item);
		sortModel(defaultComboBoxModel);

	}

	/*
	 * Update the Created and Associated Sprite Display list
	 */
	public void updateSpriteList() {

		createdSpriteNamesListModel.removeAllElements();
		associatedSpriteNamesListModel.removeAllElements();
		for (Sprite sprite : getGameData().getSprites()) {
			createdSpriteNamesListModel.addElement(sprite.getName());
			associatedSpriteNamesListModel.addElement(sprite.getName());
		}
		sortModel(createdSpriteNamesListModel);
		sortModel(associatedSpriteNamesListModel);
		createdSpriteNamesListModel.insertElementAt("<Select Sprite>", 0);
		createdSpriteNamesListModel.setSelectedItem("<Select Sprite>");
		previewPanelObject.setHighlightMode(false);

	}

	/*
	 * Adds a string to DefaultListModel and sorts it
	 */
	public void addItemToScrollPane(DefaultListModel defaultListModel, String item) {
		defaultListModel.addElement(item);
		sortModel(defaultListModel);
	}

	/*
	 * Adds the items of a string array to DefaultListModel and sorts it
	 */
	public void addItemToScrollPane(DefaultListModel defaultListModel, String[] itemArray) {

		Arrays.sort(itemArray);
		for (String item : itemArray) {
			defaultListModel.addElement(item);
		}
		sortModel(defaultListModel);

	}

	/*
	 * Sorts a DefaultListModel and sorts it
	 */
	public void sortModel(DefaultListModel model) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < model.size(); i++) {
			list.add((String) model.get(i));
		}
		Collections.sort(list);
		model.removeAllElements();
		for (String s : list) {
			model.addElement(s);
		}
	}

	/*
	 * Sorts a DefaultComboBoxModel and sorts it
	 */
	public void sortModel(DefaultComboBoxModel model) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < model.getSize(); i++) {
			list.add((String) model.getElementAt(i));
		}
		// list.removeAll(Collections.singleton(null));
		Collections.sort(list);
		model.removeAllElements();
		for (String s : list) {
			model.addElement(s);
		}

	}

	/**
	 * @param actionConditionScrollPane
	 * @param actionTypeScrollPane
	 * @param spriteAssociationPane
	 * @return
	 */
	private JPanel createActionTypeActionConditionSpriteAssociationPanel(JScrollPane actionConditionScrollPane,
			JScrollPane actionTypeScrollPane, JScrollPane spriteAssociationPane) {
		JPanel actionTypeGameDetailListPanel = new JPanel();

		String[] actionTypeGameDetailLabelStringArray = { "Action Type", "Action Condition", "Association with Sprite" };
		JLabel[] actionTypeGameDetailLabelArray = new JLabel[actionTypeGameDetailLabelStringArray.length];
		for (int i = 0; i < actionTypeGameDetailLabelArray.length; i++) {
			actionTypeGameDetailLabelArray[i] = new JLabel(actionTypeGameDetailLabelStringArray[i]);
			actionTypeGameDetailLabelArray[i].setForeground(ColorEnum.COLOR_JLABEL_TEXT.getColorCode());
			actionTypeGameDetailListPanel.add(actionTypeGameDetailLabelArray[i]);
		}

		GridBagLayout gridBagLayout = new GridBagLayout();
		actionTypeGameDetailListPanel.setLayout(gridBagLayout);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		// Retrieve the insets for the JLabes to be used in Grid Bag Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JLABEL_INSETS.getTop(), GridBagContraintInsets.JLABEL_INSETS.getLeft(),
				GridBagContraintInsets.JLABEL_INSETS.getBottom(), GridBagContraintInsets.JLABEL_INSETS.getRight());

		actionTypeGameDetailListPanel.add(actionTypeGameDetailLabelArray[0], gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		// Retrieve the insets for the JLabes to be used in Grid Bag Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JLABEL_INSETS.getTop(), GridBagContraintInsets.JLABEL_INSETS.getLeft(),
				GridBagContraintInsets.JLABEL_INSETS.getBottom(), GridBagContraintInsets.JLABEL_INSETS.getRight());

		actionTypeGameDetailListPanel.add(actionTypeGameDetailLabelArray[1], gbc);

		gbc.gridx = 5;
		gbc.gridy = 0;
		// Retrieve the insets for the JLabes to be used in Grid Bag Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JLABEL_INSETS.getTop(), GridBagContraintInsets.JLABEL_INSETS.getLeft(),
				GridBagContraintInsets.JLABEL_INSETS.getBottom(), GridBagContraintInsets.JLABEL_INSETS.getRight());

		actionTypeGameDetailListPanel.add(actionTypeGameDetailLabelArray[2], gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.ipady = 20;
		gbc.gridx = 0;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JSCROLL_PANE_INSETS.getTop(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getLeft(), GridBagContraintInsets.JSCROLL_PANE_INSETS.getBottom(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getRight());
		actionTypeGameDetailListPanel.add(actionTypeScrollPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JSCROLL_PANE_INSETS.getTop(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getLeft(), GridBagContraintInsets.JSCROLL_PANE_INSETS.getBottom(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getRight());
		actionTypeGameDetailListPanel.add(actionConditionScrollPane, gbc);

		gbc.gridx = 5;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JSCROLL_PANE_INSETS.getTop(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getLeft(), GridBagContraintInsets.JSCROLL_PANE_INSETS.getBottom(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getRight());
		actionTypeGameDetailListPanel.add(spriteAssociationPane, gbc);
		return actionTypeGameDetailListPanel;
	}

	private JPanel createBGSelectorAndClockPanel() {
		JPanel bgSelectorAndClockPanel = new JPanel();
		bgSelectorAndClockPanel.setLayout(new FlowLayout());

		// Create and add Background Selection
		bgSelectorAndClockPanel.add(createBackgroundSelectionPanel());

		// Create and add Clock Option Panel
		bgSelectorAndClockPanel.add(createClockCheckPanel());
		bgSelectorAndClockPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		return bgSelectorAndClockPanel;
	}

	private JPanel createBackgroundSelectionPanel() {

		// Panel for Selecting Background Image
		JPanel backgroundSelectionPanel = new JPanel();
		// backgroundSelectionPanel.setLayout(new BorderLayout());
		backgroundSelectionPanel.setLayout(new FlowLayout());

		setBorderTitleString("Select Game Background");
		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		backgroundSelectionPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		backgroundSelectionPanel.setBorder(titledBorder);

		backgroundImageSelector(backgroundSelectionPanel);

		return backgroundSelectionPanel;
	}

	public void backgroundImageSelector(JPanel backGroundPanel) {

		final JList spriteList;

		String[] nameList = { "bg1.jpg", "bg2.jpg", "bg3.jpg", "bg4.jpg", "bg5.jpg", "bg6.jpg", "breakout_bg1.jpg",
				"breakout_bg1.png", "breakout_bg2.jpg", "breakout_bg2a (2).jpg", "breakout_bg2a.jpg" };

		spriteList = new JList(nameList);

		spriteList.setCellRenderer(new SpriteListRenderer());
		spriteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spriteList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		spriteList.setVisibleRowCount(1);
		JScrollPane backgroundImageScrollPane = new JScrollPane(spriteList);
		backgroundImageScrollPane.setPreferredSize(backgroundImageScrollPaneDimension);
		backgroundImageScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		spriteList.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				ImageIcon bgImageIcon = new ImageIcon(getClass().getClassLoader().getResource(
						"img/" + spriteList.getSelectedValue().toString()));

				Image backgroundImage = bgImageIcon.getImage();
				Image scaledBackgroundImage = backgroundImage.getScaledInstance(getPreviewPanelObject().getPreviewGameJPanel()
						.getWidth(), getPreviewPanelObject().getPreviewGameJPanel().getHeight(), Image.SCALE_SMOOTH);

				bgImageIcon = new ImageIcon(scaledBackgroundImage);
				gameData.setPreviewPanelBackGroundImage(bgImageIcon);
				getPreviewPanelObject().getPreviewGameJPanel().repaint();
			}
		});
		getPreviewPanelObject().getPreviewGameJPanel().setBorder(new EtchedBorder());

		backGroundPanel.add(backgroundImageScrollPane);
	}

	public JPanel createClockCheckPanel() {

		JPanel clockCheckPanel = new JPanel();

		JLabel clockCheckBoxLabel = new JLabel("Add Clock?");
		clockCheckBox = new JCheckBox();

		// Adding listener to the checkbox.
		// If checked, add Clock to the Preview Panel and the Demo Panel
		// Else, do no such action
		clockCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					previewPanelObject.addClockPanel();
					previewPanelObject.setClockPanelPresent(true);
					previewPanelObject.getPreviewGameJPanel().revalidate();
				} else {
					previewPanelObject.removeClockPanel();
					previewPanelObject.setClockPanelPresent(false);
					previewPanelObject.getPreviewGameJPanel().revalidate();

				}

			}
		});

		clockCheckPanel.add(clockCheckBoxLabel);
		clockCheckPanel.add(clockCheckBox);

		setBorderTitleString("Clock");

		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		clockCheckPanel.setBorder(titledBorder);

		clockCheckPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		return clockCheckPanel;
	}

	public JPanel createSpriteDetailPanel() {

		// Panel for Sprite Details
		JPanel spriteDetailPanel = new JPanel();
		// Sprite Name
		setTextFieldString("Sprite Name");
		JLabel spriteNameTextFieldLabel = new JLabel(getTextFieldString() + ": ");
		spriteNameTextFieldLabel.setForeground(ColorEnum.COLOR_JLABEL_TEXT.getColorCode());

		setTextFieldString("Drag a Sprite");
		final JLabel spriteSelectLabel = new JLabel(getTextFieldString() + ": ");
		spriteSelectLabel.setForeground(ColorEnum.COLOR_JLABEL_TEXT.getColorCode());
		warningLabel.setForeground(ColorEnum.COLOR_WARNING_LABEL_TEXT.getColorCode());
		warningLabel.setVisible(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		spriteDetailPanel.setLayout(gridBagLayout);
		GridBagConstraints gbc = new GridBagConstraints();

		// JPanel spriteSelectorPanel = new JPanel();
		selectorSelector = new SpriteSelector();
		final JScrollPane spriteSelectorScrollPane = selectorSelector
				.generateSprite(getGameData(), this, getPreviewPanelObject());

		spriteSelectorScrollPane.setVisible(false);
		spriteSelectLabel.setVisible(false);

		// Validate the Sprite name entered by user
		spriteNameTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// User presses Backspace/Delete
				String validateText = spriteNameTextField.getText().replaceAll("[a-zA-Z0-9_]", "");
				// Check if the spriteNameTextField has any Non alpha
				// numeric characters (except '_' )
				// or Check if the spriteNameTextField is empty
				// If yes, hide the Sprite Selector Scroll
				if (!validateText.isEmpty() || spriteNameTextField.getText().isEmpty()) {
					spriteSelectorScrollPane.setVisible(false);
					spriteSelectLabel.setVisible(false);
					warningLabel.setVisible(true);
					getPreviewPanelObject().getPreviewGameJPanel().repaint();
				}
				// If no, show the Sprite Selector Scroll
				else {
					spriteSelectorScrollPane.setVisible(true);
					spriteSelectLabel.setVisible(true);
					warningLabel.setVisible(false);
					getPreviewPanelObject().getPreviewGameJPanel().repaint();
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// User inserts a character
				String validateText = spriteNameTextField.getText().replaceAll("[a-zA-Z0-9_]", "");

				// Check if the spriteNameTextField has entered any
				// other Non alpha
				// numeric characters (except '_' )
				// If yes, hide the Sprite Selector Scroll
				if (!validateText.isEmpty()) {
					spriteSelectorScrollPane.setVisible(false);
					spriteSelectLabel.setVisible(false);
					warningLabel.setVisible(true);
					getPreviewPanelObject().getPreviewGameJPanel().repaint();
				}
				// If no, show the Sprite Selector Scroll
				else {
					spriteSelectorScrollPane.setVisible(true);
					spriteSelectLabel.setVisible(true);
					warningLabel.setVisible(false);
					getPreviewPanelObject().getPreviewGameJPanel().repaint();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		});

		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		// Retrieve the insets for the JLabes to be used in Grid Bag Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JLABEL_INSETS.getTop(), GridBagContraintInsets.JLABEL_INSETS.getLeft(),
				GridBagContraintInsets.JLABEL_INSETS.getBottom(), GridBagContraintInsets.JLABEL_INSETS.getRight());

		spriteDetailPanel.add(spriteNameTextFieldLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		// Retrieve the insets for the JLabes to be used in Grid Bag Constraints
		gbc.insets = new Insets(GridBagContraintInsets.SPRITE_NAME_TEXTFIELD_INSETS.getTop(),
				GridBagContraintInsets.SPRITE_NAME_TEXTFIELD_INSETS.getLeft(),
				GridBagContraintInsets.SPRITE_NAME_TEXTFIELD_INSETS.getBottom(),
				GridBagContraintInsets.SPRITE_NAME_TEXTFIELD_INSETS.getRight());

		spriteDetailPanel.add(spriteNameTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JSCROLL_PANE_INSETS.getTop(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getLeft(), GridBagContraintInsets.JSCROLL_PANE_INSETS.getBottom(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getRight());
		spriteDetailPanel.add(spriteSelectLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JSCROLL_PANE_INSETS.getTop(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getLeft(), GridBagContraintInsets.JSCROLL_PANE_INSETS.getBottom(),
				GridBagContraintInsets.JSCROLL_PANE_INSETS.getRight());
		spriteDetailPanel.add(spriteSelectorScrollPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		// Retrieve the insets for the JScroll Panes to be used in Grid Bag
		// Constraints
		gbc.insets = new Insets(GridBagContraintInsets.JLABEL_INSETS.getTop(), GridBagContraintInsets.JLABEL_INSETS.getLeft(),
				GridBagContraintInsets.JLABEL_INSETS.getBottom(), GridBagContraintInsets.JLABEL_INSETS.getRight());
		spriteDetailPanel.add(warningLabel, gbc);

		setBorderTitleString("New Sprite");

		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		spriteDetailPanel.setBorder(titledBorder);

		spriteDetailPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		return spriteDetailPanel;
	}

	public JPanel createGameMakerControlButtonsPanel() {
		gameMakerControlButtonsPanel = new JPanel();
		gameMakerControlButtonsPanel.setLayout(new FlowLayout());

		JButton saveButton = new JButton("Save Game");
		JButton resetButton = new JButton("Reset Game");
		JButton gamePreviewButton = new JButton("Game Preview");

		gameMakerControlButtonsPanel.add(saveButton);
		gameMakerControlButtonsPanel.add(resetButton);
		gameMakerControlButtonsPanel.add(gamePreviewButton);

		setBorderTitleString("Control Buttons");
		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());
		titledBorder.setTitleColor(ColorEnum.COLOR_OUTER_CONTROL_PANEL_TITLE.getColorCode());
		gameMakerControlButtonsPanel.setBorder(titledBorder);
		gameMakerControlButtonsPanel.setBackground(ColorEnum.COLOR_OUTER_CONTROL_PANEL.getColorCode());

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveCommand = new SaveCommand(gameData, frameTitle);
				saveCommand.execute();
			}
		});

		// Clear the Game Data and Sprite Data on the click of Reset Button
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				createdSpriteNamesListModel.removeAllElements();
				associatedSpriteNamesListModel.removeAllElements();
				getGameData().getSprites().clear();
				getGameData().getEventTable().clear();
				eventTypeComboBox.setSelectedIndex(0);
				actionTypeListModel.clear();
				actionConditionListModel.clear();
				getSpriteNameTextField().setText("");
				warningLabel.setVisible(false);
				gameData.getGameSummaryModel().removeAllElements();
				previewPanelObject.setHighlightMode(false);
				previewPanelObject.getPreviewGameJPanel().repaint();
			}
		});

		gamePreviewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timerObs = new TimerObservable(ControlPanel.this, getPreviewPanelObject());
				previewCommand = new PreviewCommand(timerObs);
				previewCommand.execute();
			}
		});

		return gameMakerControlButtonsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	public String getBorderTitleString() {
		return borderTitleString;
	}

	public void setBorderTitleString(String borderTitleString) {
		this.borderTitleString = borderTitleString;
	}

	public String getTextFieldString() {
		return textFieldString;
	}

	public void setTextFieldString(String textFieldString) {
		this.textFieldString = textFieldString;
	}

	public JTextField getSpriteNameTextField() {
		return spriteNameTextField;
	}

	public void setSpriteNameTextField(JTextField spriteNameTextField) {
		this.spriteNameTextField = spriteNameTextField;
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

	public PreviewPanel getPreviewPanelObject() {
		return previewPanelObject;
	}

	public void setPreviewPanelObject(PreviewPanel previewPanelObject) {
		this.previewPanelObject = previewPanelObject;
	}

}
