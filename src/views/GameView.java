package views;

import controllers.Controller;
import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.StringJoiner;

public class GameView extends JPanel {
    private JList<String> moves;

    private JButton unMoveButton;
    private JLabel[] players;

    private JPanel blackCaptures;
    private JPanel whiteCaptures;

    public GameView() {
        players = new JLabel[2];
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (RuntimeException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {

        setOpaque(false);
        setBorder(new EmptyBorder(20, 32, 20, 32));
        setLayout(new BorderLayout());
        add(boardPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(340, 400));
        panel.setLayout(new BorderLayout());
        moves = new JList();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(moves);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        moves.setBorder(new EmptyBorder(20, 8, 20, 8));
        moves.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        moves.setFont(moves.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        unMoveButton = new JButton("Un Move");
        unMoveButton.addActionListener(this::handleUnMove);

        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);

        panel.add(panel1, BorderLayout.SOUTH);
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));
        add(panel, BorderLayout.LINE_END);


        panel = new JPanel(new BorderLayout());
        panel.add(players[0] = new JLabel(), BorderLayout.LINE_START);
        panel.setOpaque(false);
        players[0].setBorder(new EmptyBorder(20, 0, 20, 20));
        panel.add(whiteCaptures = new JPanel());
        whiteCaptures.setOpaque(false);
        whiteCaptures.setLayout(new FlowLayout());
        add(panel, BorderLayout.SOUTH);

        panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(players[1] = new JLabel(), BorderLayout.LINE_START);
        panel.add(blackCaptures = new JPanel());
        blackCaptures.setOpaque(false);
        blackCaptures.setLayout(new FlowLayout());
        players[1].setBorder(new EmptyBorder(20, 0, 20, 20));
        add(panel, BorderLayout.NORTH);

        setBackground(new Color(0xF4F7FA));
        setOpaque(true);
    }

    private void handleUnMove(ActionEvent event) {
        controller.handleUnMove();
    }


    public void renderMoves(Collection<Move> moves) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        StringJoiner stringJoiner = new StringJoiner("    ");
        int counter = 1;
        stringJoiner.add(1 + ".");
        for (var move : moves) {
            if (counter % 2 == 1 && counter != 1) {
                listModel.addElement(stringJoiner.toString());
                stringJoiner = new StringJoiner("    ");
                stringJoiner.add((counter / 2 + 1) + ".");
            }

            stringJoiner.add(move.toString());
            if (counter == moves.size()) {
                listModel.addElement(stringJoiner.toString());
            }
            counter++;
        }
        this.moves.setModel(listModel);

    }

    public void renderCaptures(Collection<Piece> pieces) {
        for (Piece piece : pieces) {
            Image image = ImageFactory.instance().instance(piece.getAlliance(), piece.getPieceType());
            Icon icon = new ImageIcon(image);
            JLabel jLabel = new JLabel(piece.getSymbol(), JLabel.CENTER);
            jLabel.setBackground(Color.BLACK);
            jLabel.setOpaque(true);
            if (piece.is(Alliance.WHITE)) {
                whiteCaptures.add(jLabel);
                whiteCaptures.repaint();
            } else
                blackCaptures.add(jLabel);
        }
        repaint();
    }

    public void renderPlayer(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Image image = ImageFactory.instance().instance(player.getAlliance(), player.getType());
            Icon icon = new ImageIcon(image);
            this.players[i].setIcon(icon);
        }

    }


    public void setSquareStatus(Square square, SquareStatus squareStatus) {
        boardPane.setSquareStatus(square, squareStatus);
    }

    public void clearHint() {
        boardPane.clearHint();
    }

    public void renderHint(java.util.List<Square> canMove) {
        boardPane.renderHint(canMove);
    }


    /*------------------
                 Dependency
            --------------------*/
    private BoardPane boardPane;
    private Controller controller;

    public void setBoardPane(BoardPane boardPane) {
        this.boardPane = boardPane;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
