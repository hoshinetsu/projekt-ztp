package battleShip.player;

import battleShip.board.Battleboard;
import battleShip.board.game.GameObj;
import battleShip.ui.IBoardRenderer;

public abstract class Player {
    protected Player enemy;
    protected Battleboard board;
    protected IBoardRenderer rend;
    protected GameObj game;
    protected final boolean isAi;
    protected final String name;
    protected int shipsAfloat;

    protected Player(boolean isAi, String name) {
        this.isAi = isAi;
        this.name = name;
    }

    public final boolean isAi(){
        return isAi;
    }

    public final String getName() {
        return name;
    }

    public final void setGame(GameObj gameObj) {
        this.game = gameObj;
    }

    public final void setShipsAfloat(int n) {
        this.shipsAfloat = n;
    }

    public final int getShipsAfloat() {
        return shipsAfloat;
    }

    public abstract void takeTurn();

    public Battleboard getBoard() {
        return board;
    }

    public void setBoard(Battleboard b){
        board = b;
    }

    public void setEnemy(Player enemy){
        this.enemy = enemy;
    }

    public Player getEnemy(){
        return enemy;
    }

    public String toString(){
        return name;
    }

    public void setRenderer(IBoardRenderer ren) {
        ren.setPerspective(this);
        rend = ren;
    }
}
