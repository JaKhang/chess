package models.piece;

import models.*;

import java.util.Objects;

public class Rook extends Piece {
    public Rook(Alliance alliance) {
        super(alliance, PieceType.ROOK);
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if(!end.isBlank() && Objects.equals(end.getPiece().getAlliance(), this.alliance)) return false;

        return board.canVerticalCrossing(start, end) || board.canHorizontalCrossing(start, end);

    }

    @Override
    public String getSymbol() {
        return is(Alliance.WHITE) ? "♖" : "♜";
    }
}
