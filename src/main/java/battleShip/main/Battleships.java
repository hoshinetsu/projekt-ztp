package battleShip.main;

import battleShip.board.Battleboard;
import battleShip.board.BoardBuilder;
import battleShip.board.gen.BattleshipDeployer;
import battleShip.ui.GameUI;

import javax.swing.*;

/* Singleton */
public class Battleships extends JFrame {

    private static Battleships instance;
    private Battleships(){
        if(instance != null) throw new IllegalStateException("Already instantiated!");
        instance = this;
        setTitle("Battleships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BattleshipDeployer dipshit = new BattleshipDeployer(2137);
        Battleboard board = dipshit.deployBoard(10);
        add(new GameUI(board));
        pack();
        setLocationRelativeTo(null);
    }

    public Battleships getBattleship(){
        return instance;
    }

    public static void main(String[] args) {
        JFrame frame = new Battleships();
        frame.setVisible(true);
    }
}
