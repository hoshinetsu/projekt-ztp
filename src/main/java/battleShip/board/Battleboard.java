package battleShip.board;

import battleShip.ui.IBoardRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battleboard {
    public final int size;
    private int shotsLeft;
    private List<ShipObject> battleShips;
    private HitboxState[] hitTable;
    private List<IBoardRenderer> renderers;

    public Battleboard(int size) {
        this.size = size;
        this.shotsLeft = size * size;
        hitTable = new HitboxState[shotsLeft];
        Arrays.fill(hitTable, HitboxState.EMPTY);
        battleShips = new ArrayList<ShipObject>();
        renderers = new ArrayList<IBoardRenderer>();
    }

    public boolean placeShip(ShipObject so) {
        for (ShipObject eo : battleShips) {
            if (so.collision(eo)) return false;
        }
        battleShips.add(so);
        updateAllRenderers();
        return true;
    }

    private void updateAllRenderers() {
        for (IBoardRenderer br : renderers) {
            if (br != null) {
                br.updateRenderer();
            }
        }
    }

    public boolean importShips(List<ShipObject> list) {
        if (list == null || list.isEmpty()) return false;
        battleShips.clear();
        battleShips.addAll(list);
        updateAllRenderers();
        return true;
    }

    public ShipObject shoot(int x, int y) {
        ShipObject ret = null;
        HitboxState state = hitTable[x + y * size];

        if (state != HitboxState.EMPTY) return ret;

        boolean shipFound = false;
        for (ShipObject so : battleShips) {
            if (so.intersects(x, y)) {
                ret = so;
                shipFound = true;
                break;
            }
        }

        if (shipFound) {
            ret.hit();
            System.out.println("Ship " + ret + " is at " + ret.getHp());
            if (ret.isSunken()) {
                System.out.println("Ship " + ret + " got rekt, ez");
                battleShips.remove(ret);
            }
            if (battleShips.isEmpty()) {
                System.out.println("No more ships afloat!");
            }
            state = HitboxState.HIT;
        } else {
            state = HitboxState.MISS;
        }
        shotsLeft--;
        hitTable[x + y * size] = state;
        updateAllRenderers();
        return ret;
    }

    public HitboxState getHitState(int x, int y) {
        return hitTable[x + y * size];
    }

    public boolean isHit(int x, int y) {
        return getHitState(x, y).isHit();
    }

    public boolean isMiss(int x, int y) {
        return getHitState(x, y).isMiss();
    }

    public boolean isEmpty(int x, int y) {
        return getHitState(x, y).isEmpty();
    }

    public int shotsRemaining() {
        return shotsLeft;
    }

    public void addRenderer(IBoardRenderer r) {
        renderers.add(r);
        r.updateRenderer();
    }

    public void removeRenderer(IBoardRenderer r) {
        renderers.remove(r);
    }
}
