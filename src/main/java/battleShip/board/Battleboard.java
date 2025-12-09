package battleShip.board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battleboard {
    public final int size;
    private List<ShipObject> battleShips;
    private HitboxState[] hitTable;

    public Battleboard(int size){
        this.size = size;
        hitTable = new HitboxState[size * size];
        Arrays.fill(hitTable, HitboxState.EMPTY);
        battleShips = new ArrayList<ShipObject>();
    }

    public boolean placeShip(ShipObject so){
        for(ShipObject eo : battleShips) {
            if(so.collision(eo)) return false;
        }
        return battleShips.add(so);
    }

    public boolean importShips(List<ShipObject> list){
        if(list == null || list.isEmpty()) return false;
        battleShips.clear();
        battleShips.addAll(list);
        return true;
    }

    public void registerHit(int x, int y) {
        HitboxState state = hitTable[x + y * size];
        if(state != HitboxState.EMPTY) return;

        boolean shipFound = false;
        for(ShipObject so : battleShips){
            if(so.intersects(x, y)){
                shipFound = true;
                break;
            }
        }

        if (shipFound) {
           state = HitboxState.HIT;
        } else {
            state = HitboxState.MISS;
        }
        hitTable[x + y * size] = state;
    }

    public void render(Graphics2D g2d, int cellSize, int gridSize, int boardOffsetX, int boardOffsetY) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int sX = boardOffsetX + (x * cellSize);
                int sY = boardOffsetY + (y * cellSize);
                HitboxState cellState = hitTable[x + y * size];

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
}
