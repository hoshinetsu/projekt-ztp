package battleShip.board.game;

public enum GameMode {
    PvP(0, "Player vs Player"),
    PvC(1, "Player vs CPU"),
    CvC(2, "CPU vs CPU");

    public final int id;
    public final String name;

    GameMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GameMode fromId(int id) {
        return switch (id) {
            case 1 -> PvC;
            case 2 -> CvC;
            default -> PvP;
        };
    }
}
