package models.piece;

import models.*;

public class King extends Piece {

    private boolean castlingDone = false;

    public King(Alliance alliance) {
        super(alliance, PieceType.KING);

    }

    public boolean isCastlingDone() {
        return castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        if (isValidCastling(board, start, end))
            return true;
        //check attack alliance's piece
        if(end.havePiece() && end.getPiece().is(alliance)){
            return false;
        }

        //check step
        int x = Math.abs(start.getColumn() - end.getColumn()); // vector x
        int y = Math.abs(start.getRow() - end.getRow()); // vector y
        if (x + y == 1) return true;
        return x == 1 && y == 1;
    }

    @Override
    public String getSymbol() {
        return is(Alliance.WHITE) ? "♔" : "♚";
    }

    public boolean isValidCastling(Board board, Square start, Square end){

        // check castling have done
        if(castlingDone || moved) return false;
        if(!board.canHorizontalCrossing(start, end))
            return false;
        if(alliance == Alliance.WHITE){
            if(board.square(7,7).equals(end) || board.square(7, 0).equals(end)){
                return end.havePiece() && end.getPiece() instanceof Rook rook && rook.is(Alliance.WHITE) && !rook.isMoved();
            }

        } else {
            if(board.square(0,0).equals(end) || board.square(0, 7).equals(end)){
                return end.havePiece() && end.getPiece() instanceof Rook rook && rook.is(Alliance.BLACK) && !rook.isMoved();
            }
        }

        return false;

    }

    public boolean isCastlingMove() {
        return false;
    }
}


