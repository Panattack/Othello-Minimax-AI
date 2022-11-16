import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        //String white = '\x25CB';
        //String black = "\u25CF";
        // System.out.println("▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
        
        //We create the players and the board
        //MaxDepth for the MiniMax algorithm is set to 2; feel free to change the values
        Player playerX = new Player(5, Board.X);
        Player playerO = new Player(5, Board.O);
        Board board = new Board();
        board.print();
        //System.out.println("▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        //Put this out of comments for the O to play first

        while(!board.isTerminal())
        {
            System.out.println();
            switch(board.getLastPlayer())
            {
                //If X played last, then Ο plays now
                case Board.X:
                    System.out.println("O plays");
                    Move moveO = playerO.MiniMax(board);
                    //System.out.println("Row : " + moveO.getRow() + " Col : " + moveO.getCol());
                    board.makeMove(moveO.getRow(), moveO.getCol(), Board.O);
                    
                    System.out.println("Press to continue");
                    myObj.nextLine();  // Read user input
                    break;
                //If O played last, then X plays now
                case Board.O:
                    System.out.println("X plays");
                    Move moveX = playerX.MiniMax(board);
                    board.makeMove(moveX.getRow(), moveX.getCol(), Board.X);

                    System.out.println("Press to continue");
                    myObj.nextLine();  // Read user input
                    break;
                default:
                    break;
            }
            board.print();
        }
        int sumx = board.score(Board.X);
        int sumo = board.score(Board.O);
        System.out.println("Score for Black : " + sumx + "\n" +"Score for White : "+ sumo);
        myObj.close();
    }
}
