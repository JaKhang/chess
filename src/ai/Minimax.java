package ai;

import models.Alliance;
import models.Board;
import models.Move;

import java.util.List;

public class Minimax {

    private Alliance aiAlliance;

    private final int depth;

    public Minimax(Alliance aiAlliance, int depth, Heuristics heuristics) {
        this.aiAlliance = aiAlliance;
        this.depth = depth;
        this.heuristics = heuristics;
    }

    public Heuristics heuristics;

    public int minimax(Alliance alliance, Board state, int depth, int alpha, int beta) {
        if (depth == 0) {

            return heuristics.get(state);
        }
        //Max state
        if (this.aiAlliance.equals(alliance)) {
            int maxScore = Integer.MIN_VALUE;

            for (Move nextMove : state.getValidMove(alliance)) {
                state.makeMove(nextMove);
                int score = minimax(alliance.next(), state, depth - 1, alpha, beta);
                state.unMode();
                maxScore = Integer.max(score, maxScore);
                alpha = Integer.max(alpha, maxScore);
                if (beta <= alpha) break;
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Move nextMove : state.getValidMove(alliance)) {
                state.makeMove(nextMove);
                int score = minimax(alliance.next(), state, depth - 1, alpha, beta);
                state.unMode();
                minScore = Integer.min(score, minScore);
                beta = Integer.min(beta, minScore);
                if (beta <= alpha) break;

            }
            return minScore;
        }
    }

    public int minimax(Alliance alliance, Board state, int depth) {
        if (depth == 0) {

            return heuristics.get(state);
        }
        //Max state
        if (this.aiAlliance.equals(alliance)) {
            int maxScore = Integer.MIN_VALUE;

            for (Move nextMove : state.getValidMove(alliance)) {
                state.makeMove(nextMove);
                int score = minimax(alliance.next(), state, depth - 1);
                state.unMode();
                maxScore = Integer.max(score, maxScore);
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Move nextMove : state.getValidMove(alliance)) {
                state.makeMove(nextMove);
                int score = minimax(alliance.next(), state, depth - 1);
                state.unMode();
                minScore = Integer.min(score, minScore);

            }
            return minScore;
        }
    }

    public Move getBestMove(Board board) {


        Move best = null;
        int bestScore = Integer.MIN_VALUE;

        List<Move> legalMoves = board.getValidMove(aiAlliance);
        for (var move : legalMoves) {
            board.makeMove(move);
//            int score = minimax(aiAlliance.next(), board, depth - 1 ,Integer.MAX_VALUE, Integer.MIN_VALUE);
            int score = minimax(aiAlliance.next(), board, depth - 1);
            board.unMode();
            if (bestScore < score) {
                bestScore = score;
                best = move;
            }
        }

        return best;
    }


    private static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args) {

    }

    public int getDepth() {
        return depth;
    }
}
