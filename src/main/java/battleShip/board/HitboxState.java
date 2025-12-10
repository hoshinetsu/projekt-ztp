package battleShip.board;

public enum HitboxState {
    EMPTY(0,'▢'), HIT(1, '◉'), MISS(2, '◎');

    final int id;
    final char textForm;

    HitboxState(int id, char c) {
        this.id = id;
        this.textForm = c;
    }

    public static HitboxState fromId(int id) {
        return switch (id) {
            case 1 -> HIT;
            case 2 -> MISS;
            default -> EMPTY;
        };
    }

    public boolean isHit() {
        return this == HIT;
    }

    public boolean isMiss() {
        return this == MISS;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public char getChar() {
        return textForm;
    }
}
