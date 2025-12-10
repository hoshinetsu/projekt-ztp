package battleShip.board;

public class BoardBuilder {
    private final Battleboard bb;
    private boolean done = false;

    public BoardBuilder(int size) {
        bb = new Battleboard(size);
    }

    public BoardBuilder addSingle(int x, int y) {
        return add(x, y, 1, false);
    }

    public BoardBuilder addDouble(int x, int y, boolean vert) {
        return add(x, y, 2, vert);
    }

    public BoardBuilder addTriple(int x, int y, boolean vert) {
        return add(x, y, 3, vert);
    }

    public BoardBuilder addQuad(int x, int y, boolean vert) {
        return add(x, y, 4, vert);
    }

    public BoardBuilder add(int x, int y, int len, boolean vert) {
        if (bb.placeShip(new ShipObject(x, y, len, vert))) return this;
        throw new IllegalArgumentException("Ship collision detected!");
    }

    public Battleboard toBoard() {
        if (done) return null;
        done = true;
        return bb;
    }
}
