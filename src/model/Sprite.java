package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

//sprite class to hold information about sprite objects
public class Sprite implements Serializable{

	private String name;
	private int x;
	private int y;
	private int xinit;
	private int yinit;
	private int width;
	private int height;
	private ImageIcon imageIcon;
	private String imagePath;
	private int xdir;
	private int ydir;
	private int isVisible;
	private String top;
	private String left;
	private String right;
	private String bottom;

	public Sprite() {
		xdir = 1;
		ydir = -1;
		isVisible = 1;
		top = "";
		left = "";
		right = "";
		bottom = "";
	}

	public String getTop() {
		return top;
	}

	public int getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}

	//assign image to sprite object
	public void assignSpriteImage(ImageIcon img, int xinit, int yinit) {
		imageIcon = img;
		this.xinit = xinit;
		this.yinit = yinit;
		x = xinit;
		y = yinit;
	}

	//get rectangle for intersection of two objects
	Rectangle getRect() {
		return new Rectangle(x, y, imageIcon.getImage().getWidth(null),  imageIcon.getImage().getHeight(null));
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public int getXdir() {
		return xdir;
	}

	public void setXdir(int xdir) {
		this.xdir = xdir;
	}

	public int getYdir() {
		return ydir;
	}

	public void setYdir(int ydir) {
		this.ydir = ydir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setMyImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getXinit() {
		return xinit;
	}

	public void setXinit(int xinit) {
		this.xinit = xinit;
	}

	public int getYinit() {
		return yinit;
	}

	public void setYinit(int yinit) {
		this.yinit = yinit;
	}

}
