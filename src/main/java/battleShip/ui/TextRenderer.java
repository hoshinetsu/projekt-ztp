package battleShip.ui;

import battleShip.board.game.GameObj;
import battleShip.player.Player;

public class TextRenderer
        implements IBoardRenderer {
    private GameObj game;
    private Player perspective;

    public TextRenderer(GameObj game) {
        this.game = game;
    }

    @Override
    public void render() {
        if(perspective == null) return;
        System.out.println("=======================");
        System.out.println(perspective + "'s turn: ");
        for(int x = 0; x < game.boardSize; x++){
            for(int y = 0; y < game.boardSize; y++) {
               System.out.printf("%c ", perspective.getBoard().getHitState(x, y).getChar());
            }
            System.out.println();
        }
    }

    @Override
    public void updateRenderer() {
        ;
    }

    @Override
    public IBoardRenderer setPerspective(Player p) {
        perspective = p;
        return this;
    }
}
