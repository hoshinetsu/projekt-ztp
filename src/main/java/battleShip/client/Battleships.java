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
        setSize(1024, 768);
        setLayout(null);
        // INIT GAME
        Random rand = new Random();
        GameObj game = new GameObj(GameMode.PvC, rand, 10);
        game.setBoardGen(new BoardGenerator(rand));
        game.setPlayers(new UserPlayer("Nigger"), CPUPlayer.createAiPlayer());
        // START GAME
        game.startGame();
        // INIT RENDERERS
        SwingRenderer re1 = new SwingRenderer(game);
        game.getPlayer(1).setRenderer(re1);
        SwingRenderer re2 = new SwingRenderer(game);
        game.getPlayer(2).setRenderer(re2.setEnemyView());
        // ADD RENDERERS TO VIEWPORT
        re1.setBounds(0, 0, SwingRenderer.viewportW, SwingRenderer.viewportH);
        re2.setBounds(SwingRenderer.viewportW, 0, SwingRenderer.viewportW, SwingRenderer.viewportH);
        add(re1);
        add(re2);
        // ADD TURN LABEL
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
