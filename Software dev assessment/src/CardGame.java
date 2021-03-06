import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) throws IOException, InvalidPlayerNum, InvalidPackLoad {
        Scanner PlayerNum = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Please enter the number of players: ");
        int PlayerCount;
        try {
            PlayerCount = PlayerNum.nextInt();
        } catch (InputMismatchException e) {
            throw new InvalidPlayerNum("Invalid player number");
        }
        System.out.println("Confirmed players = " + PlayerCount);  // Output user input
        Scanner packFile = new Scanner(System.in);
        System.out.println("Please enter pack location to load: ");
        String fileObj = packFile.nextLine();  // Read user input
        Pack pack = new Pack(fileObj, PlayerCount);
        pack.fileCheck(fileObj, PlayerCount);
        Game game = new Game(PlayerCount);
        game.play();
    }
}