package battleShip.client;

import battleShip.board.Battleboard;
import battleShip.board.game.GameMode;
import battleShip.board.game.GameObj;
import battleShip.board.gen.BoardGenerator;
import battleShip.player.CPUPlayer;
import battleShip.player.UserPlayer;
import battleShip.ui.SwingRenderer;
import battleShip.ui.TextRenderer;

import javax.swing.*;
import java.util.Random;

/* Singleton */
public class Battleships extends JFrame {

    private static Battleships instance;

    private Battleships() {
        if (instance != null) throw new IllegalStateException("Already instantiated!");
        instance = this;
        setTitle("Battleships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Random rand = new Random();

        GameObj game = new GameObj(GameMode.PvC, rand, 10);
        game.setBoardGen(new BoardGenerator(rand));
        game.setPlayers(new UserPlayer("Nigger"), CPUPlayer.createAiPlayer());

        SwingRenderer sr = new SwingRenderer(game);
        game.getPlayer(1).setRenderer(sr);

        TextRenderer tr = new TextRenderer(game);
        game.getPlayer(2).setRenderer(tr);

        add(sr);
        pack();
        setLocationRelativeTo(null);
    }

    public Battleships getBattleship() {
        return instance;
    }

    public static void main(String[] args) {
        JFrame frame = new Battleships();
        frame.setVisible(true);
    }
}
