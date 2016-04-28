package view;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EtchedBorder;

import model.GameData;
import model.Sprite;
import model.SpriteListRenderer;
import static model.GameConstants.SPRITE_SELECTOR_SCROLL_HEIGHT;
import static model.GameConstants.SPRITE_SELECTOR_SCROLL_WIDTH;
import static model.GameConstants.SPRITE_IMG_RES_PATH;

/**
 * 
 * Class to create Sprites when user drags and drops sprite image from control panel 
 * to preview panel
 */
public class SpriteSelector {

	private JList spriteList;
	private ImageIcon img;
	private JLabel spriteImageLabel;
	private Sprite sprite;

	public SpriteSelector() {
		spriteImageLabel = new JLabel("Select Sprite: ");
	}


	/* 
	 * Returns a Scroll Pane with all the available sprites which the user can choose to create from
	 */
	public JScrollPane generateSprite(GameData gameData,ControlPanel controlPanel, PreviewPanel previewPanelObject) {
		
		new MyDropTargetListener(gameData, controlPanel,sprite,previewPanelObject);

		String[] nameList = { "blue.png", "blue1.png", "fire_ball.png",
				"green.png", "greena.png", "images.png", "laser-red.png",
				"laser-reda.png", "laser-yellow.png", "water-ball.png" };


		spriteList = new JList(nameList);

		spriteList.setCellRenderer(new SpriteListRenderer());
		spriteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spriteList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		spriteList.setVisibleRowCount(1);
		JScrollPane scroll = new JScrollPane(spriteList);
		scroll.setPreferredSize(new Dimension(SPRITE_SELECTOR_SCROLL_WIDTH, SPRITE_SELECTOR_SCROLL_HEIGHT));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spriteList.setDragEnabled(true);

		spriteList.setTransferHandler(new DropBoxHandler(spriteList));
		spriteList.setDropMode(DropMode.USE_SELECTION);
		spriteList.setDragEnabled(true);

		spriteImageLabel.setTransferHandler(new TransferHandler(null));
		previewPanelObject.getPreviewGameJPanel().setBorder(new EtchedBorder());
		return scroll;

		
	}

}

//mouse adapter for drag drop functionality
class DragMouseAdapter extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		JList source = (JList) e.getSource();

		DropBoxHandler th = (DropBoxHandler) source.getTransferHandler();
		th.exportAsDrag(source, e, TransferHandler.COPY);
	}
}

class DropBoxHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JList list;

	DropBoxHandler(JList list) {
		this.list = list;
	}

	public static final DataFlavor DATA_FLAVOUR = new DataFlavor(JLabel.class,
			"Sprite");

	@Override
	protected Transferable createTransferable(JComponent c) {

		return new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DATA_FLAVOUR };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return flavor.equals(DATA_FLAVOUR);
			}

			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				return list.getSelectedValue();
			}
		};

	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY;
	}

	public boolean canImport(TransferSupport support) {
		if (!support.isDataFlavorSupported(DATA_FLAVOUR)) {
			return false;
		}

		JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
		return dl.getIndex() != -1;
	}
}

class MyDropTargetListener extends DropTargetAdapter {

	private DropTarget dropTarget;
	private Sprite sprite;
	private ControlPanel controlPanel;
	private GameData gameData;
	private PreviewPanel previewPanelObject;
	

//drop target listener for drag and drop functionality	
	public MyDropTargetListener(GameData gameData, ControlPanel controlPanel, Sprite sprite, PreviewPanel previewPanelObject) {
		this.controlPanel = controlPanel;
		this.sprite = sprite;
		this.gameData = gameData;
		this.previewPanelObject = previewPanelObject;
		

		setDropTarget(new DropTarget(previewPanelObject.getPreviewGameJPanel(), DnDConstants.ACTION_COPY, this,
				true, null));
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try {

			Transferable tr = event.getTransferable();

			ImageIcon icon = new ImageIcon(
					getClass()
							.getClassLoader()
							.getResource(SPRITE_IMG_RES_PATH +
									tr
											.getTransferData(DropBoxHandler.DATA_FLAVOUR)));

			if (event.isDataFlavorSupported(DropBoxHandler.DATA_FLAVOUR)) {
				sprite = new Sprite();
				sprite.assignSpriteImage(icon, event.getLocation().x, event.getLocation().y);
				String spriteName="";
				String userEnteredSpriteName = controlPanel.spriteNameTextField.getText();
				
				
				/*
				 *  Use Hash Map to store the Sprite Name and a value which would help in computing its unique identifier value 
				 *  Ex : If user enters "Ball" as the Sprite Name and drags/creates a sprite.
				 *  This Hash Map would be checked for a key "Ball".
				 *  If found, the value would be retrieved. 
				 *  	The new name for sprite would be computed as "Key+"_"+(Value+1)". Example : "Ball_05".
				 *  If not found,
				 *  	A new name for sprite would be computed as Sprite Name entered by user + "_" + 01
				 *  	A new key with Sprite Name entered is added to the hashmap with the value 1
				 *  	i.e., a "Ball_01" in this case.
				 *  
				 *  Thus, the identifier of the ball will be calculated based on the "Value".
				 *
				 */
				
				if (controlPanel.getGameData().getSpriteNameTrackerHashMap().containsKey(userEnteredSpriteName)){
					int spriteIndex = controlPanel.getGameData().getSpriteNameTrackerHashMap().get(userEnteredSpriteName);
					spriteIndex +=1;
					controlPanel.getGameData().getSpriteNameTrackerHashMap().put(userEnteredSpriteName,spriteIndex);
					spriteName = userEnteredSpriteName + "_" + String.format("%02d", spriteIndex);
					
				}
				else {
					controlPanel.getGameData().getSpriteNameTrackerHashMap().put(userEnteredSpriteName, 1);
					spriteName = userEnteredSpriteName + "_01";
				}
				
				sprite.setName(spriteName);
				gameData.getSprites().add(sprite);
				
				//Update the Created and Associated Sprite Display list
				controlPanel.updateSpriteList();
				
				event.acceptDrop(DnDConstants.ACTION_COPY);
				event.dropComplete(true);
				controlPanel.repaint();
				previewPanelObject.getPreviewGameJPanel().repaint();
				return;
			}
			event.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			event.rejectDrop();
		}
	}

	public DropTarget getDropTarget() {
		return dropTarget;
	}

	public void setDropTarget(DropTarget dropTarget) {
		this.dropTarget = dropTarget;
	}
}
