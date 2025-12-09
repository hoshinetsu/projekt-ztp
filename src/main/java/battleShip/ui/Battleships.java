package battleShip.ui;

import javax.swing.*;

/* Singleton */
public class Battleships extends JFrame {

    private static Battleships instance;
    private Battleships(){
        if(instance != null) throw new IllegalStateException("Already instantiated!");
        instance = this;
        setTitle("Battleships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GameUI());
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
