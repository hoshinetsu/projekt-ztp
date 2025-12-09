package battleShip.ui;

import battleShip.board.Battleboard;
import battleShip.board.BoardBuilder;

import javax.swing.*;

/* Singleton */
public class Battleships extends JFrame {

    private static Battleships instance;
    private Battleships(){
        if(instance != null) throw new IllegalStateException("Already instantiated!");
        instance = this;
        setTitle("Battleships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BoardBuilder bob = new BoardBuilder(10);
        bob.addSingle(0, 0).addSingle(1, 0).addSingle(0, 1).addSingle(9, 9).addSingle(8, 9).addSingle(9, 8);
        add(new GameUI(bob.toBoard()));
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
