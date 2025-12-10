package battleShip.ui;

import battleShip.board.Battleboard;
import battleShip.board.HitboxState;
import battleShip.board.ShipObject;
import battleShip.board.game.GameObj;
import battleShip.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class SwingRenderer
        extends JPanel
        implements IBoardRenderer {

    private GameObj game;
    private Player perspective;
    private Graphics2D g2d;
    private boolean enemyView;

    private final int
            cellSize = 40,
            gridSize = 10,
            boardOffsetX = 50,
            boardOffsetY = 50;


    public SwingRenderer(GameObj game) {
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.DARK_GRAY);
        enemyView = false;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!enemyView) return;
                int x = (e.getX() - boardOffsetX) / cellSize;
                int y = (e.getY() - boardOffsetY) / cellSize;
                if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
                    perspective.getBoard().shoot(x, y);
                    game.nextTurn();
                }
            }
        });
    }

    public SwingRenderer setEnemyView(){
        enemyView = true;
        return this;
    }

    public IBoardRenderer setPerspective(Player p) {
        if (perspective != null) {
            perspective.getBoard().removeRenderer(this);
        }
        this.perspective = p;
        if (perspective != null) {
            enemyView = perspective.isAi();
            perspective.getBoard().addRenderer(this);
        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        render();
        g2d.setColor(Color.WHITE);
        String str = "unknown player's board";
        if(perspective != null) {
            str = "Viewing " + perspective + "'s board:";
        }
        g2d.drawString(str, 50, 30);
    }

    private void drawShip(ShipObject obj) {
        int x = boardOffsetX + (obj.posX * cellSize);
        int y = boardOffsetY + (obj.posY * cellSize);

        g2d.setColor(Color.GRAY);

        if (obj.vertical) {
            g2d.fillRect(x + 5, y + 5, cellSize - 10, (cellSize * obj.length) - 10);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x + 5, y + 5, cellSize - 10, (cellSize * obj.length) - 10);
        } else {
            g2d.fillRect(x + 5, y + 5, (cellSize * obj.length) - 10, cellSize - 10);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x + 5, y + 5, (cellSize * obj.length) - 10, cellSize - 10);
        }
    }

    @Override
    public void render() {
        if (perspective == null) return;
        Battleboard bb = perspective.getBoard();
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
                    g2d.fillOval(sX + (cellSize / 2) - (size / 2), sY + (cellSize / 2) - (size / 2), size, size);
                } else if (cellState.isHit()) { // HIT
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(sX + 5, sY + 5, sX + cellSize - 5, sY + cellSize - 5);
                    g2d.drawLine(sX + cellSize - 5, sY + 5, sX + 5, sY + cellSize - 5);
                }
            }
        }

        for (Iterator<ShipObject> it = perspective.getBoard().getShips(); it.hasNext(); ) {
            ShipObject obj = it.next();
            drawShip(obj);
        }
    }

    @Override
    public void updateRenderer() {
        if (isVisible() && perspective != null) {
            repaint();
        }
    }
}