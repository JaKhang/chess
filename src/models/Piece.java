package models;

public abstract class Piece {
    protected Alliance alliance;
    protected PieceType pieceType;
    protected boolean moved;
    protected boolean captured;
    @Override
    public String toString() {
        return getSymbol();
    }
    protected boolean isAttackTeammate(Square end) {
        return !end.isBlank() && end.getPiece().is(alliance);
    }

    public Piece(Alliance alliance, PieceType pieceType) {
        this.alliance = alliance;
        this.pieceType = pieceType;
    }

    /*------------------
              public
        --------------------*/
    public boolean is(Alliance alliance) {
        return this.alliance == alliance;
    }
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public boolean isMoved() {
        return moved;
    }

    /*------------------
              Abstract
        --------------------*/
    public abstract boolean isValidMove(Board board, Square start, Square end);
    public abstract String getSymbol();
}
