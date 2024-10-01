package models;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

public class DefaultGameService implements GameService{

    private final Player[] players;
    private final Board board;
    private Player current;
    private Consumer<Player> handleMakeMoveSuccess;
    private Consumer<Player> handleEndGame;

    private Function<Player, PieceType> handleBeforePromoted;

    public DefaultGameService(){
        players = new Player[2];
        board = new Board();
    }
    @Override
    public void initialize(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
        board.reset();
        current = p1.is(Alliance.WHITE) ? p1 : p2;
        if (handleMakeMoveSuccess != null) handleMakeMoveSuccess.accept(current);
    }

    @Override
    public Stack<Move> getMoves() {
        return getBoard().getMoves();
    }

    @Override
    public void printBoard() {
        board.printBoard();
    }

    @Override
    public List<Square> getCanMove(int row, int col) {
        return board.getValidEndSquare(col, row);
    }

    @Override
    public Player getCurrentPlayer() {
        return current;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean makeMove(Move move) {
        boolean success = board.makeRealMove(move);
        if (success) {
            if (handleEndGame != null && board.isEnd()) handleEndGame.accept(current);
            current = current == players[0] ? players[1] : players[0];
            if (handleMakeMoveSuccess != null) handleMakeMoveSuccess.accept(current);

        }

        return success;
    }

    @Override
    public Move getLastMove() {
        return board.getMoves().isEmpty() ? null : board.getMoves().lastElement();
    }

    @Override
    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        boolean success = board.makeRealMove(startRow, startCol, endRow, endCol);
        if (success) {
            if (handleEndGame != null && board.isEnd()) handleEndGame.accept(current);
            current = current == players[0] ? players[1] : players[0];
            if (handleMakeMoveSuccess != null) handleMakeMoveSuccess.accept(current);
        }

        return success;
    }

    @Override
    public void onBeforePromoted(Function<Player, PieceType> callback) {
        this.handleBeforePromoted = callback;

    }

    @Override
    public void onMakeMoveSuccess(Consumer<Player> callBack) {
        this.handleMakeMoveSuccess = callBack;
    }

    @Override
    public void onEndGame(Consumer<Player> callBack) {
        this.handleEndGame = callBack;
    }

    @Override
    public boolean isEnd() {
        return board.isEnd();
    }

    @Override
    public void unMove() {
        board.unMode();
        board.unMode();
        if(!players[0].isHuman()){
            players[0].getAi().getBoard().unMode();
            players[0].getAi().getBoard().unMode();
        }
        if(!players[1].isHuman()){
            players[1].getAi().getBoard().unMode();
            players[1].getAi().getBoard().unMode();
        }
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public Set<Piece> getCapturedPieces() {
        return board.getCapturedPieces(null);
    }
}
