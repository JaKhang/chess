package views;

import controllers.Controller;
import models.Board;
import models.Square;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BoardPane extends JPanel {
    private SquareUI[][] squareUIs;

    private Controller controller;


    public BoardPane() {
        squareUIs = new SquareUI[8][8];
        this.setLayout(new GridLayout(8, 8, 0, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    public void init() {
        boolean isWhite = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                SquareUI squareUI = new SquareUI(isWhite);
                squareUIs[row][col] = squareUI;
                this.add(squareUI);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }

    public void startGame(Square[][] squares) {
        for (var row : squares) {
            for (var square : row) {
                SquareUI squareUI = get(square);
                squareUI.setSquare(square);
                squareUI.addActionListener(this::handleSquareClick);
            }
        }
        repaint();
    }


    private void handleSquareClick(ActionEvent event) {
        if (event.getSource() instanceof SquareUI squareUI) {
            Square square = squareUI.getSquare();
            controller.handleSquareClick(square);
        }
    }

    public void renderHint(List<Square> squares) {
        for (Square s : squares) {
            if (s.havePiece())
                get(s).setState(SquareStatus.HINT_PIECE);
            else
                get(s).setState(SquareStatus.HINT);
        }
    }

    public void clearHint() {
        for (var r : squareUIs) {
            for (var s : r) {
                s.setState(SquareStatus.NONE);
            }
        }
    }


    /*------------------
            Get Alliance
    --------------------*/
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setSquareStatus(Square square, SquareStatus status) {
        get(square).setState(status);
    }

    private SquareUI get(Square square) {
        return squareUIs[square.getRow()][square.getColumn()];
    }

    private void set(Square square) {

    }



}
