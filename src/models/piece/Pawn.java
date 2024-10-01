package models.piece;

import models.*;

public class Pawn extends Piece {
    private Piece promotedTo;

    public Pawn(Alliance alliance) {
        super(alliance, PieceType.PAWN);

    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if(isPromoted())
            return promotedTo.isValidMove(board, start, end);

        if(isAttackTeammate(end))
            return false;
        int y = start.getRow() - end.getRow();
        int x = start.getColumn() - end.getColumn();

        int startY = 6;

        // case black Alliance
        if (alliance.equals(Alliance.BLACK)){
            y = -y;
            startY = 1;
        }

        if (y == 1 && Math.abs(x) == 1) {
            // attack
            return !end.isBlank() && !end.getPiece().is(alliance);
        } else if (y == 1 && x == 0) {
            //move
            return end.isBlank();
        } else if (y == 2 && x == 0) {
            // start move
            if (start.getRow() != startY) return false;
            return end.isBlank() && board.canVerticalCrossing(start, end);
        } else {
            return false;
        }

    }

    @Override
    public String getSymbol() {
        if (isPromoted())
            return promotedTo.getSymbol();
        return is(Alliance.WHITE) ? "♙" : "♟";
    }

    public boolean isPromoted(){
        return promotedTo != null;
    }

    public Piece getPromotedTo() {
        return promotedTo;
    }

    public void setPromotedTo(Piece promotedTo) {
        this.promotedTo = promotedTo;
    }

    @Override
    public PieceType getPieceType() {
        if (isPromoted()){
            return promotedTo.getPieceType();
        }
        return super.getPieceType();
    }
}