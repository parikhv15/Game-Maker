package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.GameData;
import model.GameConstants.ColorEnum;
import static model.GameConstants.CLOCK_PANEL_HEIGHT;
//import static main.GameConstants.ColorEnum;
import static model.GameConstants.PREVIEW_PANEL_HEIGHT;
import static model.GameConstants.PREVIEW_PANEL_WIDTH;

public class PreviewPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel previewGameJPanel;
	private String borderTitleString;
	private JPanel clockPanel;
	private int currentMinute;
	private int currentSecond;
	private JLabel timeLabel;
	private boolean highlightMode;
	private boolean clockPanelPresent;
	private int xHighlightRectangle;
	private int yHighlightRectangle;
	private int widthHighlightRectangle;
	private int heightHighlightRectangle;
	private boolean clockFlag;

	PreviewPanel() {
		this.highlightMode = false;
		this.setClockPanelPresent(false);
		this.xHighlightRectangle = 0;
		this.yHighlightRectangle = 0;
		this.widthHighlightRectangle = 0;
		this.heightHighlightRectangle = 0;
		this.timeLabel = new JLabel("00:00");
		this.currentMinute = 0;
		this.currentSecond = 0;
	}

	public void highlightSprite(int x, int y, int width, int height) {
		setxHighlightRectangle(x);
		setyHighlightRectangle(y);
		setWidthHighlightRectangle(width);
		setHeightHighlightRectangle(height);
	}

	//display selected sprites as per position given by user
	public JPanel createPreviewPanel(final GameData gameDataWrapper) {
		previewGameJPanel = new JPanel() {

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (clockFlag) {
					timeLabel.setText(String.format("%02d:%02d", currentMinute, currentSecond));
				}

				// Reset the Background Image of the Preview Panel and Paint it.
				if (gameDataWrapper.getPreviewPanelBackGroundImage() != null)
				g.drawImage(gameDataWrapper.getPreviewPanelBackGroundImage().getImage(), 0, 0, this);

				if (PreviewPanel.this.isHighlightMode()) {
					g.setColor(new Color(150, 250, 0, 250));
					g.fillRect(xHighlightRectangle - 4, yHighlightRectangle - 4, widthHighlightRectangle + 8,
							heightHighlightRectangle + 8);

				}

				if (!gameDataWrapper.getSprites().isEmpty()) {
					for (int i = 0; i < gameDataWrapper.getSprites().size(); i++) {

						g.drawImage(gameDataWrapper.getSprites().get(i).getImageIcon().getImage(), gameDataWrapper.getSprites()
								.get(i).getXinit(), gameDataWrapper.getSprites().get(i).getYinit(), this);
					}
				}
			}
		};

		setBorderTitleString("Game Preview");
		previewGameJPanel.setMinimumSize(new Dimension(PREVIEW_PANEL_WIDTH, PREVIEW_PANEL_HEIGHT));
		previewGameJPanel.setMaximumSize(new Dimension(PREVIEW_PANEL_WIDTH, PREVIEW_PANEL_HEIGHT));

		TitledBorder titledBorder = BorderFactory.createTitledBorder(getBorderTitleString());

		titledBorder.setTitleColor(ColorEnum.COLOR_PREVIEW_GAME_PANEL_TITLE.getColorCode());
		previewGameJPanel.setBorder(titledBorder);

		previewGameJPanel.setBackground(ColorEnum.COLOR_PREVIEW_GAME_PANEL.getColorCode());

		JLabel background = new JLabel();
		background.setBackground(ColorEnum.COLOR_PREVIEW_GAME_PANEL.getColorCode());
		previewGameJPanel.add(background);
		previewGameJPanel.setLayout(new BorderLayout());
		setPreviewGameJPanel(previewGameJPanel);
		return previewGameJPanel;
	}

	//if user selects clock, add clock panel
	public void addClockPanel() {
		clockPanel = createClockPanel();
		previewGameJPanel.add(clockPanel, BorderLayout.SOUTH);
		clockFlag = true;
		previewGameJPanel.repaint();
	}

	//if user does no selects clock, remove clock panel
	public void removeClockPanel() {
		previewGameJPanel.remove(clockPanel);
		clockFlag = false;
		previewGameJPanel.repaint();
	}

	public JPanel createClockPanel() {
		clockPanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		clockPanel.setLayout(layout);
		clockPanel.add(timeLabel);
		clockPanel.setMaximumSize(new Dimension(PREVIEW_PANEL_WIDTH, CLOCK_PANEL_HEIGHT));
		timeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		timeLabel.setForeground(Color.white);
		clockPanel.setBackground(Color.black);

		return clockPanel;
	}

	public JPanel getPreviewGameJPanel() {
		return previewGameJPanel;
	}

	public void setPreviewGameJPanel(JPanel previewGameJPanel) {
		this.previewGameJPanel = previewGameJPanel;
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

	public boolean isHighlightMode() {
		return highlightMode;
	}

	public void setHighlightMode(boolean highlightMode) {
		this.highlightMode = highlightMode;
	}

	public int getxHighlightRectangle() {
		return xHighlightRectangle;
	}

	public void setxHighlightRectangle(int xHighlightRectangle) {
		this.xHighlightRectangle = xHighlightRectangle;
	}

	public int getyHighlightRectangle() {
		return yHighlightRectangle;
	}

	public void setyHighlightRectangle(int yHighlightRectangle) {
		this.yHighlightRectangle = yHighlightRectangle;
	}

	public int getWidthHighlightRectangle() {
		return widthHighlightRectangle;
	}

	public void setWidthHighlightRectangle(int widthHighlightRectangle) {
		this.widthHighlightRectangle = widthHighlightRectangle;
	}

	public int getHeightHighlightRectangle() {
		return heightHighlightRectangle;
	}

	public void setHeightHighlightRectangle(int heightHighlightRectangle) {
		this.heightHighlightRectangle = heightHighlightRectangle;
	}

	public boolean isClockPanelPresent() {
		return clockPanelPresent;
	}

	public void setClockPanelPresent(boolean clockPanelPresent) {
		this.clockPanelPresent = clockPanelPresent;
	}

}
