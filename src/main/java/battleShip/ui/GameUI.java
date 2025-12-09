package battleShip.ui;

import battleShip.board.Battleboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI extends JPanel {

    private Battleboard board;
    private final int CELL_SIZE = 40;
    private final int GRID_SIZE = 10;
    private final int BOARD_OFFSET_X = 50;
    private final int BOARD_OFFSET_Y = 50;


    public GameUI() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.DARK_GRAY);

        board = new Battleboard(10);

        placeShips();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void placeShips() {

    }

    private void handleMouseClick(int mouseX, int mouseY) {
        int x = (mouseX - BOARD_OFFSET_X) / CELL_SIZE;
        int y = (mouseY - BOARD_OFFSET_Y) / CELL_SIZE;

        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
            board.registerHit(x, y);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        board.render(g2d, CELL_SIZE, GRID_SIZE, BOARD_OFFSET_X, BOARD_OFFSET_Y);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Click the grid to fire!", BOARD_OFFSET_X, 30);
    }

}