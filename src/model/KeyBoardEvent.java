package model;

//class to implement keyboard event actions
public class KeyBoardEvent {

//move sprite objects left
	public void moveLeft(Sprite spriteObject, int moveUnit) {

		int x = spriteObject.getX();
		spriteObject.setX(x - moveUnit);
	}

//move sprite objects right
	public void moveRight(Sprite spriteObject, int moveUnit) {
		int x = spriteObject.getX();
		spriteObject.setX(x + moveUnit);

	}

//move sprite objects Up
	public void moveUp(Sprite spriteObject, int moveUnit) {

		int y = spriteObject.getY();
		spriteObject.setY(y - moveUnit);

	}

//move sprite objects Down
	public void moveDown(Sprite spriteObject, int moveUnit) {

		int y = spriteObject.getY();
		spriteObject.setY(y + moveUnit);

	}
}
