package battleShip.player;

import battleShip.board.Battleboard;

import java.util.Arrays;
import java.util.Random;

public class CPUPlayer extends Player {
    public static final String[] aiNames = {"Freddy", "Chica", "Bonnie", "Foxy"};
    private static String lastName;

    public final Random rand;
    private int hitX, hitY, missX, missY;
    private boolean hitLastTurn;

    /* factory method */
    public static CPUPlayer createAiPlayer() {
        Random rand = new Random();
        CPUPlayer p;
        String name;
        do {
            name = aiNames[rand.nextInt(aiNames.length)];
        } while (name.equals(lastName));
        lastName = name;
        p = new CPUPlayer("[AI] " + name);
        return p;
    }

    private CPUPlayer(String name) {
        super(true, name);
        rand = new Random();
        hitX = hitY = missX = missY = -1;
        hitLastTurn = false;
    }

    public void doHit(int x, int y) {
        hitLastTurn = (enemy.getBoard().shoot(x, y) != null);
        debug("RESULT", hitLastTurn ? "HIT" : "MISS");
    }

    private void debug(Object... o){
        for(Object oo : o){
            System.out.print(oo.toString());
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public void takeTurn() {
        debug("AI player", toString(), "taking turn");
        Battleboard board = enemy.getBoard();
        if (board.shotsRemaining() <= 0) return;

        int x = 0, y = 0;
        // attmept to destroy a found ship
        if (hitLastTurn) {
            boolean occupied = true;

            // find unhit tiles around last hit spot
            for (x = clipPos(hitX - 1); x < clipPos(hitX + 1) && occupied; x++) {
                for (y = clipPos(hitY - 1); y < clipPos(hitY + 1) && occupied; y++) {
                    if (board.isEmpty(x, y)) {
                        occupied = false;
                    }
                }
            }

            // attempt to randomize the next shot
            if (!occupied) {
                int tries = 0;
                do {
                    int tx = hitX + (rand.nextInt(3) - 1);
                    int ty = hitY + (rand.nextInt(3) - 1);
                    // check if picked spot is empty
                    if (board.isEmpty(tx, ty)) {
                        // it is, we hit it
                        x = tx;
                        y = ty;
                        break;
                    }
                } while (tries++ < 16);
                doHit(x, y);
                debug("Hit last, attempting", x, y);
                return;
            }
        }

        do {
            x = clipPos(rand.nextInt(board.size));
            y = clipPos(rand.nextInt(board.size));
        } while (!board.isEmpty(x, y));

        doHit(x, y);
        debug("RANDOM", x, y);
    }

    public int clipPos(int xy) {
        // boards in a match are of same size
        if (xy < 0) xy = 0;
        else if (xy >= board.size) xy = board.size - 1;
        return xy;
    }
}
