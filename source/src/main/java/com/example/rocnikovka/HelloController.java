package com.example.rocnikovka;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class HelloController {
    private ArrayList<Button> shipButtons = new ArrayList<Button>();
    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonAction(ActionEvent event) {

        String id = ((Button) event.getSource()).getId(); // 0-1-0
        String[] string_splitter = id.split("-");
        int[] int_splitter = new int[3];

        for (int i = 0; i < 3; i++) {
            int_splitter[i] = Integer.parseInt(string_splitter[i]);
        }

        System.out.println(int_splitter[0] + " - " + int_splitter[1] + " - " + int_splitter[2]);

        if (int_splitter[0] == 1) { // p 1
            if (HelloApplication.logics.getGameStatus() == 0) { // selecting
                int chooseKey = HelloApplication.logics.chooseShip(int_splitter[1], int_splitter[2], true);
                switch (chooseKey) {
                    case 1:
                        label.setText("Ship choosed " + HelloApplication.logics.getPlayerOneAliveShips() + "/10");
                        ((Button) event.getSource()).setText("■");
                        shipButtons.add((Button) event.getSource());
                        break;
                    case 2:
                        label.setText("Player 1 chose every ship\n" +
                                "Please player 2 choose your ships");
                        for (Button b : shipButtons) {
                            b.setText("O");
                        }
                        break;
                    case -1:
                        label.setText("Ship is already at this location\n" +
                                "Please choose again");
                        break;
                    case -2:
                        label.setText("Some ship is nearby your location\n" +
                                "Please choose again");
                        break;

                }
            } else if (HelloApplication.logics.getGameStatus() == 3) {// shooting
                switch (HelloApplication.logics.shoot(int_splitter[1], int_splitter[2], false)) {
                    case 0:
                        label.setText("Something went wrong.\nPlease try again");
                        break;
                    case 1:
                        label.setText("Player 2 already shot at this location\nPlayer 2 shooting again");
                        break;
                    case 2:
                        label.setText("Player 2 missed his/her shot\nPlayer 1 shooting");
                        ((Button) event.getSource()).setText("X");
                        break;
                    case 3:
                        label.setText("Player 2 drowned a ship!\nPlayer 2 shooting again");
                        ((Button) event.getSource()).setText("■");
                        break;
                    case 4:
                        label.setText("Player 1 won! Congratulations!");
                        ((Button) event.getSource()).setText("■");
                        resetGame(((Button) event.getSource()).getParent().getParent());
                        break;
                    case 5:
                        label.setText("Player 2 won! Congratulations!");
                        ((Button) event.getSource()).setText("■");
                        resetGame(((Button) event.getSource()).getParent().getParent());
                        break;
                }
            }
        } else { // p 2
            if (HelloApplication.logics.getGameStatus() == 1) { // selecting
                int chooseKey = HelloApplication.logics.chooseShip(int_splitter[1], int_splitter[2], false);
                switch (chooseKey) {
                    case 1:
                        label.setText("Ship choosed " + HelloApplication.logics.getPlayerTwoAliveShips() + "/10\n" +
                                "Please choose again!");
                        ((Button) event.getSource()).setText("■");
                        shipButtons.add((Button) event.getSource());
                        break;
                    case 3:
                        label.setText("Player 2 chose every ship\n" +
                                "Please player 1 start shooting");
                        for (Button b : shipButtons) {
                            b.setText("O");
                        }
                        break;
                    case -1:
                        label.setText("Ship is already at this location\n" +
                                "Please choose again");
                        break;
                    case -2:
                        label.setText("Some ship is nearby your location\n" +
                                "Please choose again");
                        break;

                }
            } else if (HelloApplication.logics.getGameStatus() == 2) {// shooting
                switch (HelloApplication.logics.shoot(int_splitter[1], int_splitter[2], true)) {
                    case 0:
                        label.setText("Something went wrong.\nPlease try again");
                        break;
                    case 1:
                        label.setText("Player 1 already shot at this location\nPlayer 2 shooting again");
                        break;
                    case 2:
                        label.setText("Player 1 missed his/her shot\nPlayer 2 shooting");
                        ((Button) event.getSource()).setText("X");
                        break;
                    case 3:
                        label.setText("Player 1 drowned a ship!\nPlayer 1 shooting again");
                        ((Button) event.getSource()).setText("■");
                        break;
                    case 4:
                        label.setText("Player 1 won! Congratulations!");
                        ((Button) event.getSource()).setText("■");
                        resetGame(((Button) event.getSource()).getParent().getParent());
                        break;
                    case 5:
                        label.setText("Player 2 won! Congratulations!");
                        ((Button) event.getSource()).setText("■");
                        resetGame(((Button) event.getSource()).getParent().getParent());
                        break;
                }
            }
        }

    }

    private void resetGame(Parent parent){

        HelloApplication.logics = new GameLogics();
        for(int i = 0; i < parent.getChildrenUnmodifiable().size(); i++){

            if(parent.getChildrenUnmodifiable().get(i) instanceof GridPane){

                GridPane temp = (GridPane) parent.getChildrenUnmodifiable().get(i);

                for (int j = 0; j < temp.getChildrenUnmodifiable().size(); j++){

                    if(temp.getChildrenUnmodifiable().get(j) instanceof Button){

                        ((Button)temp.getChildrenUnmodifiable().get(j)).setText("O");

                    }

                }

            }

        }

        label.setText("Player 1 please chose your ships");

    }

}