package battleShip.board.game;

import battleShip.board.gen.IBoardGen;
import battleShip.player.Player;
import battleShip.ui.SwingRenderer;

import java.util.Random;

public class GameObj {
    public final GameMode mode;
    public final Random rand;
    public final int boardSize;

    private int currentTurn;
    private IBoardGen boardGen;
    private final Player[] players;
    // TODO: registrationservice, matchhistoryservice

    public GameObj(GameMode mode, Random rand, int boardSize) {
        this.mode = mode;
        this.rand = rand;
        this.boardSize = boardSize;
        players = new Player[2];
        currentTurn = 0;
    }

    public void setBoardGen(IBoardGen gen){
        this.boardGen = gen;
    }

    public boolean playerExists(int ptr) {
        if (ptr < 0 || ptr >= players.length) return false;
        return players[ptr] != null;
    }

    public Player getPlayer(int ptr) {
        ptr--;
        if (ptr < 0 || ptr >= players.length) return null;
        return players[ptr];
    }

    public void setPlayers(Player one, Player two) {
        one.setEnemy(two);
        two.setEnemy(one);

        players[0] = one;
        players[1] = two;

        for(int i = 0; i < 2; i++){
            players[i].setGame(this);
            players[i].setBoard(boardGen.deployBoard(boardSize));
        }
    }

    public void nextTurn() {
        if(currentTurn == 0) currentTurn = 1;
        else currentTurn = 0;
        players[currentTurn].takeTurn();
    }
}