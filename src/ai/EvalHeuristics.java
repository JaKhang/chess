package ai;

import models.Alliance;
import models.Board;
import models.Square;
import models.Piece;

public class EvalHeuristics implements Heuristics{

    @Override
    public int get(Board state) {
        return calcAlliance(state.getCurrent(), state) ;
    }

    private int calcAlliance(Alliance alliance, Board board){
        int score = 0;
        for (var square : board){
            if (square.havePiece() && square.getPiece().is(alliance)){
                score += calcSingle(square, alliance);
            }
        }

        return score;
    }

    private int calcSingle(Square square, Alliance alliance){
        if (square.isBlank())
                return 0;
        Piece piece = square.getPiece();
        int index = getIndex(square);
        if(alliance == Alliance.WHITE){
            return switch (piece.getPieceType()) {
                case KING -> WHITE_KING_PREFERRED_COORDINATES[index];
                case PAWN -> WHITE_PAWN_PREFERRED_COORDINATES[index];
                case ROOK -> WHITE_ROOK_PREFERRED_COORDINATES[index];
                case BISHOP -> WHITE_BISHOP_PREFERRED_COORDINATES[index];
                case KNIGHT -> WHITE_KNIGHT_PREFERRED_COORDINATES[index];
                case QUEEN -> WHITE_QUEEN_PREFERRED_COORDINATES[index];
                default -> 0;
            };
        } else {
            return switch (piece.getPieceType()) {
                case KING -> BLACK_KING_PREFERRED_COORDINATES[index];
                case PAWN -> BLACK_PAWN_PREFERRED_COORDINATES[index];
                case ROOK -> BLACK_ROOK_PREFERRED_COORDINATES[index];
                case BISHOP -> BLACK_BISHOP_PREFERRED_COORDINATES[index];
                case KNIGHT -> BLACK_KNIGHT_PREFERRED_COORDINATES[index];
                case QUEEN -> BLACK_QUEEN_PREFERRED_COORDINATES[index];
                default -> 0;
            };
        }
        

    }
    
    private int getIndex (Square square){
        return square.getRow() * 8 + square.getColumn();
    }

    private final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            90, 90, 90, 90, 90, 90, 90, 90,
            30, 30, 40, 60, 60, 40, 30, 30,
            10, 10, 20, 40, 40, 20, 10, 10,
            5,  5, 10, 20, 20, 10,  5,  5,
            0,  0,  0,-10,-10,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0,-10,-10,  0,  0,  0,
            5,  5, 10, 20, 20, 10,  5,  5,
            10, 10, 20, 40, 40, 20, 10, 10,
            30, 30, 40, 60, 60, 40, 30, 30,
            90, 90, 90, 90, 90, 90, 90, 90,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };

    private final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };

    private final static int[] WHITE_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 15, 15, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };

    private final static int[] BLACK_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 15, 15, 10,  0,-10,
            -10,  5, 10, 15, 15, 10,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };

    private final static int[] WHITE_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 20, 20, 20, 20, 20, 20,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };

    private final static int[] BLACK_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  5,  5,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 20, 20, 20, 20, 20, 20,  5,
            0,  0,  0,  0,  0,  0,  0,  0,
    };

    private final static int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -5,  0,  5, 10, 10,  5,  0, -5,
            -5,  0,  5, 10, 10,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -5,  0,  5, 10, 10,  5,  0, -5,
            -5,  0,  5, 10, 10,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] WHITE_KING_PREFERRED_COORDINATES = {
            -50,-30,-30,-30,-30,-30,-30,-50,
            -30,-30,  0,  0,  0,  0,-30,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-20,-10,  0,  0,-10,-20,-30,
            -50,-40,-30,-20,-20,-30,-40,-50
    };

    private final static int[] BLACK_KING_PREFERRED_COORDINATES = {
            -50,-40,-30,-20,-20,-30,-40,-50,
            -30,-20,-10,  0,  0,-10,-20,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 30, 40, 40, 30,-10,-30,
            -30,-10, 20, 30, 30, 20,-10,-30,
            -30,-30,  0,  0,  0,  0,-30,-30,
            -50,-30,-30,-30,-30,-30,-30,-50
    };

}
//    public static Move findBestMove(Board board) {
//        int bestScore = Integer.MAX_VALUE; // Initialize to maximum value for minimization
//        Move bestMove = null;
//
//        for (Move move : generateLegalMoves(board)) {
//            board.AImove(move);
//            int score = minimax(board, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true); // Pass true for minimizing
//            board.undoAIMove(move);
//
//            if (score < bestScore) { // Change the comparison to find the minimum score
//                bestScore = score;
//                bestMove = move;
//            }
//        }
//
//        return bestMove;
//    }
//
//    private static int minimax(Board board, int depth, int alpha, int beta, boolean minimizingPlayer) {
//        if (depth == 0 || board.isGameOver()) {
//            return evalPosition();
//        }
//
//        List<Move> legalMoves = generateLegalMoves(board);
//
//        if (!minimizingPlayer) { // Change condition to check for minimizing player
//            int maxScore = Integer.MIN_VALUE;
//            for (Move move : legalMoves) {
//                board.AImove(move);
//                int score = minimax(board, depth - 1, alpha, beta, true);
//                board.undoAIMove(move);
//                maxScore = Math.max(maxScore, score);
//                alpha = Math.max(alpha, score);
//                if (beta <= alpha) {
//                    break; // Alpha-Beta pruning
//                }
//            }
//            return maxScore;
//        } else {
//            int minScore = Integer.MAX_VALUE;
//            for (Move move : legalMoves) {
//                board.AImove(move);
//                int score = minimax(board, depth - 1, alpha, beta, false);
//                board.undoAIMove(move);
//                minScore = Math.min(minScore, score);
//                beta = Math.min(beta, score);
//                if (beta <= alpha) {
//                    break; // Alpha-Beta pruning
//                }
//            }
//            return minScore;
//        }
//    }
//public static Move minMax(int depth, Board board) {
//    Move bestMove = null;
//    // if red, best is -INFINITE
//    // if black, best is INFINITE
//
//    int bestScore = Board.isRedTurn() ? Integer.MIN_VALUE :Integer.MAX_VALUE;
//    int posScore;
//    List<Move> moves = generateLegalMoves(board);
//
//    for (Move move : moves) {
//        board.AImove(move);
//
//        posScore = searchScores(depth, board);
//
//        if (posScore < bestScore) {
//            bestScore = posScore;
//            bestMove = move;
//        }
//
//        board.undoAIMove(move);
//    }
//
//    return bestMove;
//}
//
//    public static int searchScores(int depth, Board board) {
//        // if red, best is -INFINITE
//        // if black, best is INFINITE
//        int bestScore = Board.isRedTurn() ? Integer.MIN_VALUE :Integer.MAX_VALUE;
//        int posScore;
//        List<Move> moves = generateLegalMoves(board);
//
//        for (Move move : moves) {
//            board.AImove(move);
//
//            if (depth > 0) {
//                posScore = searchScores(depth - 1, board);
//            } else {
//                posScore = evalPosition();
//            }
//
//            if (posScore < bestScore) {
//                bestScore = posScore;
//            }
//
//            board.undoAIMove(move);
//            ;
//        }
//
//        return bestScore;
//    }