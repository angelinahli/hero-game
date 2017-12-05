import java.awt.*;

public class Hero {
    protected Color heroColor;
    protected int xPos, yPos, size;
    protected int leftEdge, rightEdge;

    public Hero(int xPos, int yPos, int size, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.heroColor = color;

        this.leftEdge = HeroGame.BORDER;
        this.rightEdge = HeroGame.WIDTH - size - HeroGame.BORDER;
    }

    public int getLeftX() {
        return xPos;
    }

    public int getRightX() {
        return xPos + size;
    }

    public int getTopY() {
        return yPos;
    }

    public int getBottomY() {
        return yPos + size;
    }

    public void moveRight() {
        if ( (xPos + size) <= rightEdge) {
            xPos += size;
        }
    }

    public void moveLeft() {
        if ( (xPos - size) >= leftEdge) {
            xPos -= size;
        }
    }

    public void moveDown() {
        this.yPos += this.size;
    }

    public void drawCharacter(Graphics g) {
        g.setColor(heroColor);
        g.fillRect(xPos, yPos, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, size, size);
    }

    public String toString() {
        return (
            "\nSize: " + size +
            "\nLeft X: " + getLeftX() +
            "\nRight X: " + getRightX() +
            "\nTop Y: " + getTopY() +
            "\nBottom Y: " + getBottomY()
        );
    }
}