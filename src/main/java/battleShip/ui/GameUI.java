package battleShip.ui;

import battleShip.board.Battleboard;
import battleShip.board.HitboxState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI extends JPanel implements IBoardRenderer{

    private final Battleboard bb;
    private Graphics2D g2d;

    private final int
            cellSize = 40,
            gridSize = 10,
            boardOffsetX = 50,
            boardOffsetY = 50;


    public GameUI(Battleboard bb) {
        this.bb = bb;
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.DARK_GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (e.getX() - boardOffsetX) / cellSize;
                int y = (e.getY() - boardOffsetY) / cellSize;
                if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
                    bb.shoot(x, y);
                }
            }
        });
        bb.addRenderer(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        render();
        g2d.setColor(Color.WHITE);
        g2d.drawString("Click the grid to fire!", 50, 30);
    }

    @Override
    public void render() {
        for (int x = 0; x < gridSize; x++) {
            int sX = boardOffsetX + (x * cellSize);
            for (int y = 0; y < gridSize; y++) {
                int sY = boardOffsetY + (y * cellSize);
                HitboxState cellState = bb.getHitState(x, y);
                if (cellState.isHit()) {
                    g2d.setColor(new Color(255, 100, 100));
                } else {
                    g2d.setColor(new Color(30, 144, 255));
                }
                g2d.fillRect(sX, sY, cellSize, cellSize);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(sX, sY, cellSize, cellSize);

                if (cellState.isMiss()) { // MISS
                    g2d.setColor(Color.WHITE);
                    int size = cellSize / 4;
                    g2d.fillOval(sX + (cellSize/2) - (size/2), sY + (cellSize/2) - (size/2), size, size);
                }
                else if (cellState.isHit()) { // HIT
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(sX + 5, sY + 5, sX + cellSize - 5, sY + cellSize - 5);
                    g2d.drawLine(sX + cellSize - 5, sY + 5, sX + 5, sY + cellSize - 5);
                }
            }
        }
    }

    @Override
    public void updateRenderer() {
        if(isVisible()) {
            repaint();
        }
    }
}