package battleShip.board.game;

import battleShip.board.Battleboard;
import battleShip.board.gen.IBoardGen;
import battleShip.player.Player;

import java.util.Random;

public class GameObj {
    public final GameMode mode;
    public final Random rand;
    public final int boardSize;
    private volatile int currentTurn;
    private int turnCount;
    private Player winner;
    private IBoardGen boardGen;
    private final Player[] players;
    private long duration;

    // TODO: registrationservice, matchhistoryservice

    public GameObj(GameMode mode, Random rand, int boardSize) {
        this.mode = mode;
        this.rand = rand;
        this.boardSize = boardSize;
        players = new Player[2];
        turnCount = 0;
        currentTurn = rand.nextInt(2);
    }

    public void setBoardGen(IBoardGen gen){
        this.boardGen = gen;
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
            Battleboard bb = boardGen.deployBoard(boardSize);
            bb.setGame(this);
            players[i].setGame(this);
            players[i].setBoard(bb);
        }
    }

    public void startGame(){
        if(isOver()) throw new IllegalStateException("Reuse of game objects not permitted");
        duration = System.currentTimeMillis();
        nextTurn();
    }

    private void endGame(){
        duration = System.currentTimeMillis() - duration;
        System.out.println("Winner: " + winner);
    }

    public long getGameTime(){
        if(!isOver()) throw new IllegalStateException("Game is in progress!");
        return duration;
    }

    public void nextTurn() {
        if(isOver()) return;
        System.out.printf("== Turn %d\n", ++turnCount);

        players[currentTurn].setOwnsCurrentTurn(false);

        if(currentTurn == 0) currentTurn = 1;
        else currentTurn = 0;

        players[currentTurn].setOwnsCurrentTurn(true);
        players[currentTurn].takeTurn();

        for(Player p : players){
            if(p.getBoard().allShips() == 0) {
                winner = p.getEnemy();
                endGame();
                break;
            }
        }
    }

    public boolean isOver() {
        return winner != null;
    }
}