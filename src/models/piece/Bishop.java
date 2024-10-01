package models.piece;

import models.*;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance, PieceType.BISHOP);
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if(isAttackTeammate(end)) return false;
        return board.canDiagonalCrossing(start, end);
    }

    @Override
    public String getSymbol() {
        return is(Alliance.WHITE) ? "♗" : "♝";
    }
}
