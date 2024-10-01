import controllers.Controller;
import models.DefaultGameService;
import models.GameService;
import views.BoardPane;
import views.GameView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        GameService gameService = new DefaultGameService();
        BoardPane boardPane = new BoardPane();
        GameView gameView = new GameView();
        Controller controller = new Controller();

        gameView.setController(controller);
        boardPane.setController(controller);
        gameView.setBoardPane(boardPane);
        controller.setGameService(gameService);
        controller.setBoardPane(boardPane);
        controller.setGameView(gameView);

        controller.init();

        JFrame frame = new JFrame();
        frame.add(gameView);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
