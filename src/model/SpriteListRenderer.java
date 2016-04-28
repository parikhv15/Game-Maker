package model;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import static model.GameConstants.SPRITE_IMG_RES_PATH;
import static model.GameConstants.SPRITE_IMAGE_ICON_DIMENSION;

//this class is used to convert the user selected sprite images into 
//sprite label so as to disply them on preview panel
public class SpriteListRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(SPRITE_IMG_RES_PATH+ value));
		Image img = icon.getImage();
		icon.setImage(img.getScaledInstance(SPRITE_IMAGE_ICON_DIMENSION, SPRITE_IMAGE_ICON_DIMENSION, Image.SCALE_SMOOTH));
		
		label.setText("");
		label.setIcon(icon);
		
		return label;
	}
}


