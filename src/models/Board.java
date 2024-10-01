package models;

import config.GameConfig;
import models.piece.*;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Square>{
    private GameStatus status;
    private Square[][] matrix;
    private Alliance current;
    private Stack<Move> moves;



    /*------------------
          Public
    --------------------*/

    public void reset() {
        current = GameConfig.instance().getFirstMove();
        status = GameStatus.ACTIVE;
        moves = new Stack<>();
        //init board
        matrix = new Square[8][8];
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                matrix[row][column] = new Square(column, row, null);
            }
        }
        //init black alliance
        matrix[0][0].setPiece(new Rook(Alliance.BLACK));
        matrix[0][1].setPiece(new Knight(Alliance.BLACK));
        matrix[0][2].setPiece(new Bishop(Alliance.BLACK));
        matrix[0][3].setPiece(new Queen(Alliance.BLACK));
        matrix[0][4].setPiece(new King(Alliance.BLACK));
        matrix[0][5].setPiece(new Bishop(Alliance.BLACK));
        matrix[0][6].setPiece(new Knight(Alliance.BLACK));
        matrix[0][7].setPiece(new Rook(Alliance.BLACK));
        matrix[1][0].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][1].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][2].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][3].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][4].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][5].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][6].setPiece(new Pawn(Alliance.BLACK));
        matrix[1][7].setPiece(new Pawn(Alliance.BLACK));

        matrix[7][0].setPiece(new Rook(Alliance.WHITE));
        matrix[7][1].setPiece(new Knight(Alliance.WHITE));
        matrix[7][2].setPiece(new Bishop(Alliance.WHITE));
        matrix[7][3].setPiece(new Queen(Alliance.WHITE));
        matrix[7][4].setPiece(new King(Alliance.WHITE));
        matrix[7][5].setPiece(new Bishop(Alliance.WHITE));
        matrix[7][6].setPiece(new Knight(Alliance.WHITE));
        matrix[7][7].setPiece(new Rook(Alliance.WHITE));
        matrix[6][0].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][1].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][2].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][3].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][4].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][5].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][6].setPiece(new Pawn(Alliance.WHITE));
        matrix[6][7].setPiece(new Pawn(Alliance.WHITE));

    }

    public Square square(int col, int row) {
        return matrix[row][col];
    }

    public boolean isEnd() {
        return status == GameStatus.END;
    }

    public Alliance getCurrent() {
        return current;
    }

    public Set<Piece> getCapturedPieces(Alliance alliance){
        return moves.stream().map(Move::getCaptured).filter(Objects::nonNull).collect(Collectors.toSet());
    }


    public List<Square> getValidEnd(Square start){
        List<Square> squares = new LinkedList<>();
        for (var end: this){
            if (start.getPiece().isValidMove(this, start, end)){
                squares.add(end);
            }
        }
        return squares;
    }

    public boolean makeMove(Move move) {
        Square start = move.getStart();
        Square end = move.getEnd();
        Piece selectedPiece = start.getPiece();
        if (selectedPiece == null) return false; // // valid selection
        if (!current.equals(selectedPiece.getAlliance())) return false; // valid piece
        if (!selectedPiece.isValidMove(this, start, end)) return false; // valid move

        //Capture
        Piece targetPiece = end.getPiece();
        if (targetPiece != null && !targetPiece.is(selectedPiece.getAlliance())) {
            targetPiece.setCaptured(true);
            move.setCaptured(targetPiece);
        }

        // castling?
        if (targetPiece != null && selectedPiece instanceof King king && king.isValidCastling(this, start, end)) {
            Rook rook = (Rook) end.getPiece();
            move.setCastlingMove(true);
            if(end.getColumn() == 0){
                start.setPiece(null);
                end.setPiece(null);
                square(start.getColumn() - 1, start.getRow()).setPiece(rook);
                square(start.getColumn() - 2, start.getRow()).setPiece(king);
            } else {
                start.setPiece(null);
                end.setPiece(null);
                square(start.getColumn() + 1, start.getRow()).setPiece(rook);
                square(start.getColumn() + 2, start.getRow()).setPiece(king);
            }
        } else {
            //crossing
            start.setPiece(null);
            end.setPiece(selectedPiece);
        }



        if (selectedPiece instanceof Pawn pawn && !pawn.isPromoted()) {
            if (pawn.is(Alliance.WHITE)) {
                if(end.getRow() == 0){
                    pawn.setPromotedTo(new Queen(Alliance.WHITE));
                    move.setPromoted(PieceType.QUEEN);
                }
            } else {
                if(end.getRow() == 7){
                    pawn.setPromotedTo(new Queen(Alliance.BLACK));
                    move.setPromoted(PieceType.QUEEN);

                }
            }
        }


        if (targetPiece instanceof King) {
            status = GameStatus.END;
        }

        //next turn
        selectedPiece.setMoved(true);
        current = current == Alliance.BLACK ? Alliance.WHITE : Alliance.BLACK;
        moves.push(move);
        return true;
    }

    public boolean makeRealMove(Move move){
        if(isEnd()) return false;
        return makeMove(move);
    }

    public boolean makeRealMove(int startRow, int startCol, int endRow, int endCol) {
        return makeRealMove(new Move(current, square(startCol, startRow), square(endCol, endRow)));
    }

    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        return makeMove(new Move(current, square(startCol, startRow), square(endCol, endRow)));
    }



    public void printBoard() {
        System.out.print("\t");
        for (int i = 0; i < 8; i++) {
            System.out.print((char) ('A' + i) + "\t");
        }
        System.out.print("\n");
        int counter = 1;
        for (var row : matrix) {
            System.out.print(counter++ + "\t");
            for (var square : row) {
                if (square.isBlank()) {
                    System.out.print("--\t");
                } else {
                    System.out.print(square.getPiece().getSymbol() + "\t");
                }

            }
            System.out.print("\n");
        }

    }


    /*------------------
       Check Valid move
    --------------------*/
    public boolean canHorizontalCrossing(Square start, Square end) {
        // check same row
        if (!start.isSameRow(end)) return false;

        //is col start greater than col end swap start and end
        if (start.getColumn() > end.getColumn()) {
            Square temp = start;
            start = end;
            end = temp;
        }
        // check cells between start and end
        int row = start.getRow();
        for (int column = start.getColumn() + 1; column < end.getColumn(); column++) {
            if (square(column, row).havePiece()) {
                return false;
            }
        }
        return true;
    }

    public boolean canVerticalCrossing(Square start, Square end) {
        // check same row
        if (!start.isSameCol(end)) return false;

        //is col start greater than col end swap start and end
        if (start.getRow() > end.getRow()) {
            Square temp = start;
            start = end;
            end = temp;
        }

        // check cells between start and end
        int column = start.getColumn();
        for (int row = start.getRow() + 1; row < end.getRow(); row++) {
            if (square(column, row).havePiece()) {
                return false;
            }
        }
        return true;
    }

    public boolean canDiagonalCrossing(Square start, Square end) {
        if (!start.isSameCross(end)) return false;
        int dCol = 1;
        int dRow = 1;
        if (start.getColumn() > end.getColumn())
            dCol = -1;
        if (start.getRow() > end.getRow())
            dRow = -1;
        int column = start.getColumn() + dCol;
        int row = start.getRow() + dRow;
        while (column != end.getColumn() && row != end.getRow()) {
            if (square(column, row).havePiece())
                return false;
            column += dCol;
            row += dRow;
        }
        return true;
    }

    public void unMode(){
        if (moves.isEmpty()) return;
        Move move = moves.pop();
        Square end = move.getEnd();
        Square start = move.getStart();
        Piece movedPiece = move.getMoved();
        Piece capturedPiece = move.getCaptured();
        current = move.getAlliance();
        if(move.isCastlingMove()){
            ((King)movedPiece).setCastlingDone(false);
            if(end.getColumn() == 0){
                square(2, start.getRow()).setPiece(null);
                Piece rook = square(3, start.getRow()).getPiece();
                square(3, start.getRow()).setPiece(null);
                rook.setMoved(false);
                end.setPiece(rook);
            } else {
                square(6, start.getRow()).setPiece(null);
                Piece rook = square(5, start.getRow()).getPiece();
                square(5, start.getRow()).setPiece(null);
                rook.setMoved(false);
                end.setPiece(rook);
            }
            movedPiece.setMoved(false);
            start.setPiece(movedPiece);
        } else {
            start.setPiece(movedPiece);
            end.setPiece(capturedPiece);
            movedPiece.setMoved(moves.stream().anyMatch((move1 -> move1.getMoved().equals(movedPiece))));
        }
        if(move.getPromoted() != null && movedPiece instanceof Pawn pawn){
            pawn.setPromotedTo(null);
        }
        if(status == GameStatus.END)
            status = GameStatus.ACTIVE;

    }

    public List<Move> getValidMove(Alliance alliance) {
        List<Move> moves = new LinkedList<>();
        for (Square start : this){
            if(start.havePiece() && start.getPiece().is(alliance)){
                moves.addAll(
                        getValidEnd(start).stream().map((end) -> new Move(alliance,start, end)).toList()
                );
            }
        }
        return moves;
    }

    public List<Square> getValidEndSquare(int x, int y) {
        List<Square> squares = new LinkedList<>();
        for (var square : this){
            if (square(x, y).getPiece().isValidMove(this, square(x, y), square)) {
                squares.add(square);
            }
        }
        return squares;
    }
    @Override
    public Iterator<Square> iterator() {
        return new Iterator<>() {
            private int row;
            private int col;

            @Override
            public boolean hasNext() {
                return row <= 7;
            }

            @Override
            public Square next() {
                Square square = square(col, row);
                col++;
                if (col > 7) {
                    col = 0;
                    row++;
                }

                return square;
            }
        };
    }

    public Stack<Move> getMoves() {
        return moves;
    }

    public Square[][] geMatrix() {
        return matrix;
    }
}
