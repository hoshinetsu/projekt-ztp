package battleShip.player;

public class UserPlayer extends Player {

    public UserPlayer(String name) {
        super(false, name);
    }

    @Override
    public void takeTurn() {
        System.out.print("Waiting for ");
        System.out.print(name);
        System.out.println("'s move..");
    }
}
