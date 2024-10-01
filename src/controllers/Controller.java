package controllers;

import ai.AI;
import models.*;
import views.BoardPane;
import views.GameView;
import views.SquareStatus;

import javax.swing.*;

public class Controller {

    private Square focusSquare;

    public void init() {
        boardPane.init();
        view.init();
        gameService.onMakeMoveSuccess((player -> {
            if (gameService.isEnd()) {
                JOptionPane.showMessageDialog(boardPane, player.getAlliance().next() + " WIN");

                return;
            }
            view.renderCaptures(gameService.getCapturedPieces());
            if (player.isHuman()) {
                return;
            }
            ;
            player.getAi().makeEnemyMove(gameService.getLastMove());
            makeAiMove();

        }));

        gameService.onEndGame((player -> {
            System.out.println("END");
        }));
        gameService.initialize(
                new Player(Alliance.WHITE, PlayerType.HUMAN),
                new Player(Alliance.BLACK, PlayerType.BOT)
        );

        view.renderPlayer(gameService.getPlayers());

        boardPane.startGame(gameService.getBoard().geMatrix());

    }

    public void resetGame() {
        gameService.initialize(
                new Player(Alliance.WHITE, PlayerType.BOT),
                new Player(Alliance.BLACK, PlayerType.BOT)
        );
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public void setBoardPane(BoardPane boardPane) {
        this.boardPane = boardPane;
    }

    public void handleSquareClick(Square square) {
        if (!gameService.getCurrentPlayer().isHuman())
            return;
        if (gameService.isEnd()) return;

        if (focusSquare == null) {
            Alliance currentAlliance = gameService.getCurrentPlayer().getSet();
            if (square.havePiece() && square.getPiece().is(currentAlliance)) {
                focus(square);
            }
            return;
        }

        Move move = new Move(gameService.getCurrentPlayer().getSet(), focusSquare, square);
        boolean success = gameService.makeMove(move);
        if (success) {
            gameService.getCurrentPlayer().getAi().makeEnemyMove(move);
            view.renderMoves(gameService.getMoves());

        }
        removeFocus(square);
        view.repaint();
    }

    private void makeAiMove() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            AI ai1 = gameService.getCurrentPlayer().getAi();
            Move move = ai1.getBestMove();
            gameService.makeMove(move.getStart().getRow(), move.getStart().getColumn(), move.getEnd().getRow(), move.getEnd().getColumn());
            view.renderMoves(gameService.getMoves());
            view.repaint();

        });
        thread.start();
    }

    private void removeFocus(Square square) {
        focusSquare = null;
        view.setSquareStatus(square, SquareStatus.NONE);
        view.clearHint();
    }

    private void focus(Square square) {
        focusSquare = square;
        view.setSquareStatus(square, SquareStatus.ACTIVE);
        view.renderHint(gameService.getCanMove(square.getRow(), square.getColumn()));
    }


    public void setGameView(GameView gameView) {
        this.view = gameView;
    }

    public void handleUnMove() {
        gameService.unMove();
        view.repaint();
        view.renderMoves(gameService.getMoves());
    }

    /*------------------
          Dependency
    --------------------*/
    private GameService gameService;
    private BoardPane boardPane;
    private GameView view;


}
