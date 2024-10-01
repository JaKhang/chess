package models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

public interface GameService {

    void initialize(Player p1, Player p2);
    Stack<Move> getMoves();
    void printBoard();
    List<Square> getCanMove(int row, int col);
    Player getCurrentPlayer();
    Board getBoard();
    boolean makeMove(Move move);
    Move getLastMove();

    boolean makeMove(int startRow, int startCol, int endRow, int endCol);
    void onBeforePromoted(Function<Player, PieceType> callback);
    void onMakeMoveSuccess(Consumer<Player> callBack);
    void onEndGame(Consumer<Player> callBack);

    boolean isEnd();

    void unMove();

    Player[] getPlayers();

    Set<Piece> getCapturedPieces();
}
