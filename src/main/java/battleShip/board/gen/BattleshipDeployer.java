package battleShip.board.gen;

import battleShip.board.Battleboard;
import battleShip.board.BoardBuilder;

import java.util.Random;

public class BattleshipDeployer implements IBattleshipDeployer {

    private final Random rand;

    public BattleshipDeployer(long seed) {
        this(new Random(seed));
    }

    public BattleshipDeployer(Random rand) {
        this.rand = rand;
    }

    @Override
    public Battleboard deployBoard(int boardSize) {
        BoardBuilder bb = new BoardBuilder(boardSize);
        boolean[][] grid = new boolean[boardSize][boardSize];

        int[] shipSizes = {4, 3, 3, 2, 2, 2, 2};

        for (int shipSize : shipSizes) {
            placeShip(bb, boardSize, shipSize, grid);
        }

        return bb.toBoard();
    }

    // TODO: clean this shitass code up
    public void placeShip(BoardBuilder bb, int size, int length, boolean[][] grid) {
        boolean placed = false;
        while (!placed) {
            boolean horizontal = rand.nextBoolean();

            int row = rand.nextInt(size);
            int col = rand.nextInt(size);

            if (horizontal) {
                if (col + length > size) continue;
            } else {
                if (row + length > size) continue;
            }

            boolean valid = true;
            for (int i = 0; i < length; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;

                if (grid[r][c]) {
                    valid = false;
                    break;
                }
            }

            if (!valid) continue;

            for (int i = 0; i < length; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;
                grid[r][c] = true;
            }

            try {
                bb.add(row, col, length, horizontal);
                placed = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
