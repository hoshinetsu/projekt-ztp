package battleShip.board;

import battleShip.ui.GameUI;
import battleShip.ui.IBoardRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battleboard {
    public final int size;
    private List<ShipObject> battleShips;
    private HitboxState[] hitTable;
    private List<IBoardRenderer> renderers;

    public Battleboard(int size){
        this.size = size;
        hitTable = new HitboxState[size * size];
        Arrays.fill(hitTable, HitboxState.EMPTY);
        battleShips = new ArrayList<ShipObject>();
        renderers = new ArrayList<IBoardRenderer>();
    }

    public boolean placeShip(ShipObject so){
        for(ShipObject eo : battleShips) {
            if(so.collision(eo)) return false;
        }
        battleShips.add(so);
        updateAllRenderers();
        return true;
    }

    private void updateAllRenderers() {
        for(IBoardRenderer br : renderers) {
            if(br != null) {
                br.updateRenderer();
            }
        }
    }

    public boolean importShips(List<ShipObject> list){
        if(list == null || list.isEmpty()) return false;
        battleShips.clear();
        battleShips.addAll(list);
        updateAllRenderers();
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
        updateAllRenderers();
    }

    public HitboxState getHitState(int x, int y){
        return hitTable[x + y * size];
    }

    public void addRenderer(IBoardRenderer r) {
        renderers.add(r);
    }
}
