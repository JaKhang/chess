package views;

import models.Alliance;
import models.Square;

import javax.swing.*;
import java.awt.*;

public class SquareUI extends JButton {
    private Square square;
    private SquareStatus state = SquareStatus.NONE;
    private Image[] images;

    private boolean isWhite;

    public SquareUI(Square square, boolean isWhite) {
        this.square = square;
        images = new Image[3];
        setPreferredSize(new Dimension(72, 72));
        setBorderPainted(false);
        this.isWhite = isWhite;


    }

    public SquareUI(boolean isWhite) {
        this(null, isWhite);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Image image = ImageFactory.instance().getSquareImage(
                isWhite ? Alliance.WHITE : Alliance.BLACK,
                state
        );

        g2.drawImage(image, 0, 0, null);
        if (square.havePiece()) {
            Image piece = ImageFactory.instance().getPicecImage(
                    square.getPiece().getPieceType(),
                    square.getPiece().getAlliance()
            );


            g2.drawImage(piece, 9, 9, null);
        }


    }

    /*------------------
          Getter
    --------------------*/
    public void setState(SquareStatus state) {
        if (state == this.state)
            return;
        this.state = state;
        this.repaint();
    }

    public Square getSquare() {
        return square;
    }

    void setSquare(Square square) {
        this.square = square;
    }
}
