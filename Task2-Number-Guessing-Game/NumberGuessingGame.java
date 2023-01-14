
// Functionality:

// Generate random number
// Attempts is limited
// Ask for number of rounds to play
// Score is alloted and displayed after each round 
// Score is based on no of attempts of user


import java.util.Random;

import javax.swing.JOptionPane;

class Game {
    int inputNumber, randomNumber, noOfGuess = 0,noOfAttempts = 5;
    // random ge
    Game() { 
        Random rd = new Random();
        randomNumber = rd.nextInt(100);
    }

    // method to take input number is created
    static int takeInput(String message) {
        String s = JOptionPane.showInputDialog(null,message);
        return Integer.parseInt(s);
    }

    // method for checking the number
    boolean isCorrectNumber() {
        noOfGuess++; 
        if (inputNumber == randomNumber) {
            JOptionPane.showMessageDialog(null, inputNumber+" number is Correct.You guessed in "+noOfGuess+" attempts\n\n");
            return true;
        } else if (inputNumber < randomNumber) {
            JOptionPane.showMessageDialog(null,"The number is too low!\nYou have " + (noOfAttempts) + " guesses left");
        } else if (inputNumber > randomNumber) {
            JOptionPane.showMessageDialog(null, "The number is too high!\nYou have " + (noOfAttempts) + " guesses left");
        }
        return false;
    }
    // method for gameplay of each round
    int round() {
        boolean b = false;
        // first guessing is done here for nice gameplay reason
        inputNumber= takeInput("Guess the number:");
        noOfAttempts--;
        b = this.isCorrectNumber();
        // the loop is active until correct number is found
        while (!b && noOfAttempts >= 0) {
            if (noOfAttempts == 0) {
                JOptionPane.showMessageDialog(null, "You Lost");
                noOfAttempts--; //this is done to display correct score, it will become -1
                break;
            }
            inputNumber = takeInput("Guess again");
            noOfAttempts--;
            b = this.isCorrectNumber();

        }
        // noOfAttempts is returned to display score
        return noOfAttempts;
    }
}

public class NumberGuessingGame {
    public static void main(String[] args) {
        try {
            int rounds=Game.takeInput("How many rounds do you have to play");
            int i = 1;// it is for to keep check of rounds
            while (rounds-- > 0) {
                Game g1 = new Game(); 
                JOptionPane.showMessageDialog(null, "Round "+i+"\nYou have "+g1.noOfAttempts+" guesses\n");
                int score = g1.round();
                JOptionPane.showMessageDialog(null, "Score of this round is:"+ (score+1));// here, score+1 is there bcz noOfAttempt is decresed after checking
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
