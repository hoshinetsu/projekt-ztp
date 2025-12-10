package battleShip.ui;

import battleShip.player.Player;

public interface IBoardRenderer {
    void render();
    void updateRenderer();
    IBoardRenderer setPerspective(Player p);
}
