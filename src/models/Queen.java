package models;

public class Queen extends Piece {
    public Queen(Alliance alliance) {
        super(alliance,PieceType.QUEEN);

    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if(isAttackTeammate(end)){
            return false;
        }
        return board.canDiagonalCrossing(start, end) || board.canHorizontalCrossing(start, end) || board.canVerticalCrossing(start, end);
    }

    @Override
    public String getSymbol() {
        return is(Alliance.WHITE) ? "♕" : "♛";
    }
}
