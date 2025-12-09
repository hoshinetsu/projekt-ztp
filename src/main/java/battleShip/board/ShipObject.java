package battleShip.board;

public class ShipObject {
    public final int posX, posY;
    public final int length;
    public final boolean vertical;

    private int health;

    public ShipObject(int x, int y, int len, boolean vert) {
        this.posX = x;
        this.posY = y;
        this.length = len;
        if (len == 1) {
            vert = false;
        }
        this.vertical = vert;
        this.health = len;
    }

    public void hit() {
        if (health > 0) health--;
    }

    public boolean isSunken() {
        return health <= 0;
    }

    public boolean intersects(int cx, int cy) {
        if (vertical) {
            return cx == posX && cy >= posY && cy < posY + length;
        } else {
            return cy == posY && cx >= posX && cx < posX + length;
        }
    }

    public boolean collision(ShipObject so) {
        int myX1 = this.posX;
        int myX2 = this.posX + (this.vertical ? 1 : this.length);
        int myY1 = this.posY;
        int myY2 = this.posY + (this.vertical ? this.length : 1);

        int theirX1 = so.posX;
        int theirX2 = so.posX + (so.vertical ? 1 : so.length);
        int theirY1 = so.posY;
        int theirY2 = so.posY + (so.vertical ? so.length : 1);

        boolean overlapX = myX1 < theirX2 && myX2 > theirX1;
        boolean overlapY = myY1 < theirY2 && myY2 > theirY1;

        return overlapX && overlapY;
    }

    public String toString() {
        return String.format("ShipObj[x:%d,y:%d,len:%d,vert:%s]", posX, posY, length, vertical ? "yes" : "no");
    }

    public int getHp() {
        return health;
    }
}
