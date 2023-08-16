package com.example.guessnumberappdemo;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static jdk.tools.jlink.internal.plugins.PluginsResourceBundle.getMessage;

public class HelloController {
    private final IntegerProperty countDownTime = new SimpleIntegerProperty();
    int systemGeneratedNumber, guessCount = 0, randomNumber;
    @FXML
    private Label hintLabel, countGuessLabel, timerLabel;
    @FXML
    private TextField numberInputField;
    @FXML
    private Button guessButton, onStartClick;
    private int COUNT_DOWN = 30;
    private boolean stopCountdown = false;


    @FXML
    private void initialize() {
        timerLabel.textProperty().bind(Bindings.format("Time remaining: %d", countDownTime));
        disableButton(true);
    }

    public void disableButton(boolean flag) {
        numberInputField.setDisable(flag);
        guessButton.setDisable(flag);
        countGuessLabel.setVisible(!flag);
    }


    public void Counter() {
        guessCount++;
        countGuessLabel.setText("Guess :" + guessCount);
    }

    public int generateRandomNumber() {
        randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("random number :"+randomNumber);
        return randomNumber;
    }

    public void displayGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Game Over!!");
        alert.setContentText("Correct number : %d\nTotal attempt : %d".formatted(systemGeneratedNumber, guessCount));
        alert.showAndWait();
    }

    public void displaySuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Congratulation!!");
        alert.setContentText("Correct number : %d\nTotal attempt : %d".formatted(systemGeneratedNumber, guessCount));
        alert.showAndWait();
    }


    public void startCountDown(int inputValue) {
        if (inputValue > 0) {
            stopCountdown = false;
            countDownTime.set(inputValue);
            Thread countdownThread = new Thread(() -> {
                while (countDownTime.get() > 0 && !stopCountdown) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int newValue = countDownTime.get() - 1;
                    Platform.runLater(() -> countDownTime.set(newValue));
                }

                if (countDownTime.get() <= 0) {
                    Platform.runLater(() -> {
                        countDownTime.set(0);
                        disableButton(true);
                        displayGameOverDialog();
                    });
                }
            });
            countdownThread.start();


        }
    }


    public void OnGuessButtonClick(ActionEvent actionEvent) {

        try {
            String value = numberInputField.getText();

            if (value.equals("")) {
                hintLabel.setText("Please enter provided number");
            }

            int guessNumber = Integer.parseInt(value);

            if (systemGeneratedNumber <= 0 || systemGeneratedNumber >= 100) {
                systemGeneratedNumber = this.generateRandomNumber();
            }


            if (guessNumber > systemGeneratedNumber) {
                hintLabel.setText("Provide lower number then this!!");
                Counter();
            } else if (guessNumber < systemGeneratedNumber) {
                hintLabel.setText("Provide higher number then this");
                Counter();
            } else {
                hintLabel.setText("Congratulation you have guessed correct number!!");
                displaySuccessDialog();
            }


        } catch (NumberFormatException numberFormatException) {
            hintLabel.setText("Provide valid number!!");
        } catch (Exception err) {
            hintLabel.setText("Something went wrong [Reason:" + err.getMessage() + "]");
        }
    }

    public void onStartClick(ActionEvent actionEvent) {
        disableButton(false);
        systemGeneratedNumber = this.generateRandomNumber();
        guessCount = 0;
        onStartClick.setText("Restart");
        countGuessLabel.setText("Count : " + guessCount);
        numberInputField.setText("");
        startCountDown(COUNT_DOWN);

    }

}