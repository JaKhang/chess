package models.piece;

import models.*;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance, PieceType.KNIGHT);

    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if(isAttackTeammate(end)){
            return false;
        }

        int x = Math.abs(start.getColumn() - end.getColumn());
        int y = Math.abs(start.getRow() - end.getRow());
        return x * y == 2;
    }

    @Override
    public String getSymbol() {
        return is(Alliance.WHITE) ? "♘" : "♞";
    }
}
