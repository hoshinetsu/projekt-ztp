package battleShip.board.gen;

import battleShip.board.Battleboard;
import battleShip.board.BoardBuilder;
import battleShip.board.ShipObject;

import java.util.List;

public interface IBattleshipDeployer {

    Battleboard deployBoard(int size);

}
