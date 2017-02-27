import java.util.Scanner;

/**
 * Created by Kaixing on 2/27/2017.
 */
public class Main {
    public static
    void AIplay(AI player){
        Scanner reader = new Scanner(System.in);
        Board playBoard = player.getBoard();
        while (!playBoard.isFull()){
            playBoard.generateRandom();
            playBoard.printBoard();
            System.out.println("type 1 to continue: ");
            int n = reader.nextInt();
            if (n == 1){
                player.playAI();
            }
        }
    }
    public static void main(String[] args) {
        Board game = new Board();
        game.startGame();
        AI virtualPlayer = new AI(game);
        AIplay(virtualPlayer);


    }

}
