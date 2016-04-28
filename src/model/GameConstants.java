package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

//class containing constant values used throughout the game maker application
public class GameConstants {

               public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
               public static final int FRAME_WIDTH = (int) (0.8 * screenSize.width);
               public static final int FRAME_HEIGHT = (int) (0.9 * screenSize.height);
               public static final int PREVIEW_PANEL_WIDTH = (int) (0.5 * FRAME_WIDTH);
               public static final int PREVIEW_PANEL_HEIGHT = (int) (0.95 *FRAME_HEIGHT);
               public static final int CONTROL_PANEL_WIDTH = (int) (0.5 * FRAME_WIDTH);
               public static final int CONTROL_PANEL_HEIGHT = (int) (0.95 *FRAME_HEIGHT);
    public static final int VISIBLE_ROW_COUNT_SCROLL_PANE = 4;
               public static final int VISIBLE_ROW_COUNT_SUMMARY = 4;
               public static final int GENERIC_SCROLL_PANE_DIMENSION_WIDTH = (int) (0.3 * CONTROL_PANEL_WIDTH);
               public static final int GENERIC_SCROLL_PANE_DIMENSION_HEIGHT = (int) (0.2 * CONTROL_PANEL_HEIGHT);
               public static final int ACTION_TYPE_SCROLL_PANE_DIMENSION_WIDTH = (int) (0.3 * CONTROL_PANEL_WIDTH);
               public static final int ACTION_TYPE_SCROLL_PANE_DIMENSION_HEIGHT = (int) (0.2 * CONTROL_PANEL_HEIGHT);
               public static final int SUMMARY_SCROLL_PANE_DIMENSION_WIDTH = (int) (0.8 * CONTROL_PANEL_WIDTH);
               public static final int SUMMARY_SCROLL_PANE_DIMENSION_HEIGHT = (int) (0.5 * CONTROL_PANEL_HEIGHT);
               public static final int BG_IMAGE_SCROLL_PANE_DIMENSION_WIDTH = (int) (0.65 * CONTROL_PANEL_WIDTH);
               public static final int BG_IMAGE_SCROLL_PANE_DIMENSION_HEIGHT = (int) (0.1 * CONTROL_PANEL_HEIGHT);
               public static final int SPRITE_SELECTOR_SCROLL_WIDTH = (int) (0.65 * CONTROL_PANEL_WIDTH);
               public static final int SPRITE_SELECTOR_SCROLL_HEIGHT = (int) (0.1 * CONTROL_PANEL_HEIGHT);
               public static final int SPRITE_IMAGE_ICON_DIMENSION = (int) (0.07 * CONTROL_PANEL_HEIGHT);
               public static final int CLOCK_PANEL_HEIGHT = (int) (0.05 * CONTROL_PANEL_HEIGHT);
    public static final String SPRITE_IMG_RES_PATH = "img/";
               public static final String SOUND_RES_PATH = "sounds/";
               public static int GAME_AREA_HEIGHT;
               public static int GAME_AREA_WIDTH;
               
               

               // Insets for actionConditionScrollPane
               public enum GridBagContraintInsets {
                              JLABEL_INSETS(5, 5, 5, 5), JSCROLL_PANE_INSETS(5, 5, 20, 20), SPRITE_NAME_TEXTFIELD_INSETS(5, 5, 5, 200);

                              private int top;
                              private int left;
                              private int bottom;
                              private int right;

                              GridBagContraintInsets(int top, int left, int bottom, int right) {
                                             this.top = top;
                                             this.left = left;
                                             this.bottom = bottom;
                                             this.right = right;
                              }

                              public int getBottom() {
                                             return bottom;
                              }

                              public int getLeft() {
                                             return left;
                              }

                              public int getTop() {
                                             return top;
                              }

                              public int getRight() {
                                             return right;
                              }

               }

               public enum GameMode {
                              NEW_GAME, LOAD_GAME
               }

               public enum SoundMode {
                              ERROR_MESSAGE,COLLISION
               }
               
               public enum ColorEnum {
                              COLOR_PREVIEW_GAME_PANEL(Color.WHITE), COLOR_PREVIEW_GAME_PANEL_TITLE(Color.YELLOW), COLOR_INNER_CONTROL_PANEL(new Color(
                                                            220, 240, 240, 250)), COLOR_OUTER_CONTROL_PANEL(new Color(220, 240, 240, 250)), COLOR_OUTER_CONTROL_PANEL_TITLE(
                                                            new Color(0, 100, 250, 250)), COLOR_JLABEL_TEXT(Color.GRAY), COLOR_WARNING_LABEL_TEXT(Color.RED), COLOR_LIST_BACKGROUND(
                                                            Color.WHITE), COLOR_SUMMARY_LIST(new Color(250, 250, 250, 240));

                              private final Color colorCode;

                              ColorEnum(Color colorCode) {
                                             this.colorCode = colorCode;
                              }

                              public Color getColorCode() {
                                             return colorCode;
                              }
               }

             
}
