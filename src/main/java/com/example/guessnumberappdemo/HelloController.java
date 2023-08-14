package com.example.guessnumberappdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static jdk.tools.jlink.internal.plugins.PluginsResourceBundle.getMessage;

public class HelloController {
    int systemGeneratedNumber;
    int guessCount = 0;
    @FXML
    private Label hintLabel;
    @FXML
    private TextField numberInputField;
    @FXML
    private Label countGuessLabel;


    public HelloController() {
        this.systemGeneratedNumber = this.generateRandomNumber();
    }

    public void Counter() {
        guessCount++;
        countGuessLabel.setText("Guess :" + guessCount);
    }

    public int generateRandomNumber() {
        return (int) (Math.random() * 100) + 1;
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
            }


        } catch (NumberFormatException numberFormatException) {
            hintLabel.setText("Provide valid number!!");
        } catch (Exception err) {
            hintLabel.setText("Something went wrong [Reason:" + err.getMessage() + "]");
        }
    }

    public void OnRestartClick(ActionEvent actionEvent) {
        guessCount = 0;
        countGuessLabel.setText("Count : " + guessCount);
    }
}