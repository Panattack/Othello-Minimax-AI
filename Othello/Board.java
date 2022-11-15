import java.util.ArrayList;

public class Board
{
    public static final int X = 1;//BLACK - Bot
    public static final int O = -1;//WHITE
    public static final int EMPTY = 0;

    private int[][] gameBoard;

    /* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
    private int lastPlayer;

    //Immediate move that lead to this board
    private Move lastMove;

    Board()
    {
        this.lastMove = new Move();
        this.lastPlayer = O;
        this.gameBoard = new int[6][6];
        for(int i = 0; i < this.gameBoard.length; i++)
        {
            for(int j = 0; j < this.gameBoard.length; j++)
            {
                this.gameBoard[i][j] = EMPTY;
                if ((i == 3 && j == 3) || (i == 2 && j == 2))
                {
                    this.gameBoard[i][j] = Board.X;
                }
                if ((i == 2 && j == 3) || (i == 3 && j == 2))
                {
                    this.gameBoard[i][j] = Board.O;
                }
            }
        }
    }

    Board(Board board)
    {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[6][6];
        for(int i = 0; i < this.gameBoard.length; i++)
        {
            for(int j = 0; j < this.gameBoard.length; j++)
            {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
    }

    ArrayList<MyPair> getNeighborPairs(int row, int col, int letter)
    {
        ArrayList<MyPair> positions = new ArrayList<>();

        if (this.isInBounds(row, col - 1))
        {
            if (this.gameBoard[row][col - 1] != EMPTY && this.gameBoard[row][col - 1] != letter)
            {
                positions.add(new MyPair(row, col - 1));// left
            }
        }

        if (this.isInBounds(row - 1, col - 1))
        {
            if (this.gameBoard[row - 1][col - 1] != EMPTY && this.gameBoard[row - 1][col - 1] != letter)
            {
                positions.add(new MyPair(row - 1, col - 1));// left - up
            }
        }

        if (this.isInBounds(row - 1, col))
        {
            if (this.gameBoard[row - 1][col] != EMPTY && this.gameBoard[row - 1][col] != letter)
            {
                positions.add(new MyPair(row - 1, col));/// up
            }
        }
            

        if (this.isInBounds(row - 1, col + 1))
        {
            if (this.gameBoard[row - 1][col + 1] != EMPTY && this.gameBoard[row - 1][col + 1] != letter)
            {
                positions.add(new MyPair(row - 1, col + 1));// right up
            }
        }
            

        if (this.isInBounds(row, col + 1))
        {
            if (this.gameBoard[row][col + 1] != EMPTY && this.gameBoard[row][col + 1] != letter)
            {
                positions.add(new MyPair(row, col + 1));// right
            }
        }

        if (this.isInBounds(row + 1, col + 1))
        {
            if (this.gameBoard[row + 1][col + 1] != EMPTY && this.gameBoard[row + 1][col + 1] != letter)
            {
                positions.add(new MyPair(row + 1, col + 1));// right down
            }
        }

        if (this.isInBounds(row + 1, col))
        {
            if (this.gameBoard[row + 1][col] != EMPTY && this.gameBoard[row + 1][col] != letter)
            {
                positions.add(new MyPair(row + 1, col));// down
            }
        }
            
        if (this.isInBounds(row + 1, col - 1))
        {
            if (this.gameBoard[row + 1][col - 1] != EMPTY && this.gameBoard[row + 1][col - 1] != letter)
            {
                positions.add(new MyPair(row + 1, col - 1));// left down
            }
        }
            
        return positions;
    }

    //Make a move; it places a letter in the board
    void makeMove(int row, int col, int letter)
    {
        if (this.isValidMove(row, col, letter))
        {
            this.gameBoard[row][col] = letter;
            this.lastMove = new Move(row, col);
            this.lastPlayer = letter;
            //System.out.println("Row: " + row + " Col: " + col);
            this.flipflopcheckers(row, col, letter);
        }
        else
        {
            System.out.println("Invalid move");
        }
    }

    boolean isValidMove(int row, int col, int letter)
    {
        
        // First check if it is inside the table
        if (!this.isInBounds(row, col))
        {
            return false;
        }
        // Second check if this position is occupied
        if (this.gameBoard[row][col] != EMPTY)
        {
            return false;
        }
        // First check if this position is empty to put a checker
        if (this.gameBoard[row][col] == EMPTY)
        {
            if (this.found_same_color_checker(row, col, letter))
            {
                //System.out.println(row + "   " + col);
                return true;
            }   
        }
        return false;
    }

    boolean found_same_color_checker(int row, int col, int letter)
    {
        ArrayList<MyPair> start_positions = this.getNeighborPairs(row, col, letter);
        if (start_positions.isEmpty())
        {
            return false;
        }
        else
        {
            // System.out.println("Row :" + row + "Col :" + col );
            
            for (MyPair pair : start_positions)
            {
                //System.out.println(pair.getRow() + "   " + pair.getCol());
                if (pair.getRow() + 1 == row && pair.getCol() + 1 == col)
                {// left top
                    for (int i = row - 2, j = col - 2; (i >= 0 && j >= 0);i--, j--)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
                if (pair.getRow() + 1 == row && pair.getCol() == col)
                {// top
                    for (int i = row - 2; i >= 0;i--)
                    {             
                        if (this.isInBounds(i, pair.getCol()))
                        {
                            if (this.gameBoard[i][pair.getCol()] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][pair.getCol()] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
                if (pair.getRow() + 1 == row && pair.getCol() - 1 == col)
                {// right top
                    for (int i = row - 2, j = col + 2; (i >= 0 && j <= this.gameBoard.length - 1);i--, j++)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
                if (pair.getRow() == row && pair.getCol() - 1 == col)
                {// right
                    for (int j = col + 2; j <= this.gameBoard.length - 1; j++)
                    {
                        if (this.isInBounds(pair.getRow(), j))
                        {
                            if (this.gameBoard[pair.getRow()][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[pair.getRow()][j] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() - 1 == col)
                {// right down
                    for (int i = row + 2, j = col + 2; (i <= this.gameBoard.length - 1 && j <= this.gameBoard.length - 1);i++, j++)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                return true;
                            }
                        }   
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() == col)
                {// down
                    for (int i = row + 2; i <= this.gameBoard.length - 1;i++)
                    {
                        if (this.isInBounds(i, pair.getCol()))
                        {
                            if (this.gameBoard[i][pair.getCol()] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][pair.getCol()] == letter)
                            {
                                return true;
                            }
                        }
                        
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() + 1 == col)
                {// left down
                    for (int i = row + 2, j = col - 2; (i <= this.gameBoard.length - 1 && j >= 0);i++, j--)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
                if (pair.getRow() == row && pair.getCol() + 1 == col)
                {// left 
                    for (int j = col - 2; j >= 0; j--)
                    {
                        if (this.isInBounds(pair.getRow(), j))
                        {
                            if (this.gameBoard[pair.getRow()][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[pair.getRow()][j] == letter)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    boolean isInBounds(int row, int col)
    {
        if (row >= 0 && row <= this.gameBoard.length - 1 && col >= 0 && col <= this.gameBoard.length - 1)
        {
            return true;
        }
        return false;
    }

    ArrayList<Board> getChildren(int letter)
    {
        ArrayList<Board> children = new ArrayList<>();
        for (int row = 0; row < this.gameBoard.length; row++)
        {
            for (int col = 0; col < this.gameBoard.length; col++)
            {
                
                if (this.isValidMove(row, col, letter))
                {
                    //System.out.println("Row :" + row + "Col :" + col );
                    Board child = new Board(this);
                    child.makeMove(row, col, letter);
                    children.add(child);
                }
            }
        }
        return children;
    }

    int evaluate(int color)
    {
        // Friendly reminder
        // Bot -> Black, Me -> White

        int sumX = 0;
        int sum0 = 0;
        int sum_edge_X = 0;
        int sum_edge_0 = 0; 
        
        for (int row = 0; row < this.gameBoard.length; row++)
        {
            for (int col = 0; col < this.gameBoard.length; col++)
            {
                if  (this.gameBoard[row][col] == Board.X)
                {
                    sumX++;
                }
                if (this.gameBoard[row][col] == Board.O)
                {
                    sum0++;
                }
                if ((row == 0 && col == this.gameBoard.length - 1) || (col == this.gameBoard.length - 1 && row == this.gameBoard.length - 1) || (row == this.gameBoard.length - 1 && col == 0) || (col == 0 && row == 0))
                {
                    if (this.gameBoard[row][col] == Board.X)
                    {
                        sum_edge_X++;
                    }
                    else if (this.gameBoard[row][col] == Board.O)
                    {
                        sum_edge_0++;
                    }
                }
            }
        }

        int sum_crucial_X = 0;
        int sum_crucial_0 = 0;

        for (int col = 1; col < this.gameBoard.length - 1; col++)
        {
            if (this.gameBoard[0][col] == Board.X)
            {
                sum_crucial_X++;
            }
            if (this.gameBoard[this.gameBoard.length-1][col] == Board.X)
            {
                sum_crucial_X++;
            }
            if (this.gameBoard[col][0] == Board.X)
            {
                sum_crucial_X++;
            }
            if (this.gameBoard[col][this.gameBoard.length-1] == Board.X)
            {
                sum_crucial_X++;
            }

            if (this.gameBoard[0][col] == Board.O)
            {
                sum_crucial_0++;
            }
            if (this.gameBoard[this.gameBoard.length-1][col] == Board.O)
            {
                sum_crucial_0++;
            }
            if (this.gameBoard[col][0] == Board.O)
            {
                sum_crucial_0++;
            }
            if (this.gameBoard[col][this.gameBoard.length-1] == Board.O)
            {
                sum_crucial_0++;
            }
        }

        int result = (sumX - sum0) + 3*(sum_edge_X - sum_edge_0) + 2*(sum_crucial_X - sum_crucial_0);
        return result;

    }

    boolean isTerminal()
    {
        int sumO = 0;
        int sumX = 0;
        int all_sum = 0;

        for (int row = 0; row <= this.gameBoard.length - 1; row++)
        {
            for (int col = 0; col <= this.gameBoard.length - 1; col++)
            {
                if (this.gameBoard[row][col] == Board.O)
                {
                    sumO++;
                }
                else if (this.gameBoard[row][col] == Board.X)
                {
                    sumX++;
                }
                if (this.gameBoard[row][col] != EMPTY)
                {
                    all_sum++;
                }
            }
        }

        if (sumO == 0 || sumX == 0)
            return true;
        if (all_sum == this.gameBoard.length*this.gameBoard.length)
            return true;

        // check if neither player can move
        ArrayList<Board> black_children = this.getChildren(Board.X);
        ArrayList<Board> white_children = this.getChildren(Board.O);
        if (black_children.isEmpty() && white_children.isEmpty())
            return true;
        return false;
    }

    void print()
    {
        // char white = '\u25CB';
        // char black = '\u25CF';

        //System.out.println("*********");
        for(int row=0; row< this.gameBoard.length; row++)
        {
            System.out.print("* ");
            for(int col=0; col< this.gameBoard.length; col++)
            {
                switch (this.gameBoard[row][col]) {
                    case X -> System.out.print("X ");
                    case O -> System.out.print("O ");
                    case EMPTY -> System.out.print("- ");
                    default -> {
                    }
                }
            }
            System.out.println("*");
        }
        //System.out.println("*********");
    }

    Move getLastMove()
    {
        return this.lastMove;
    }

    int getLastPlayer()
    {
        return this.lastPlayer;
    }

    int[][] getGameBoard()
    {
        return this.gameBoard;
    }

    void setGameBoard(int[][] gameBoard)
    {
        for(int i=0; i<gameBoard.length; i++)
        {
            for(int j=0; j<gameBoard.length; j++)
            {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    void setLastMove(Move lastMove)
    {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }

    void flipflopcheckers(int row, int col, int letter)
    {
        ArrayList<MyPair> start_positions = this.getNeighborPairs(row, col, letter);
        if (start_positions.isEmpty())
        {
            return ;
        }
        else
        {
            boolean color_it = false;
            for (MyPair pair : start_positions)
            {
                
                if (pair.getRow() + 1 == row && pair.getCol() + 1 == col)
                {// left top
                    for (int i = row - 2, j = col - 2; (i >= 0 && j >= 0);i--, j--)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        color_it = false;
                        for (int i = row - 1, j = col - 1; (i >= 0 && j >= 0);i--, j--)
                        {
                            if (this.isInBounds(i, j))
                            {
                                if (this.gameBoard[i][j] == EMPTY || this.gameBoard[i][j] == letter)
                                {
                                    break;
                                }
                                if (this.gameBoard[i][j] != letter)
                                {
                                    this.gameBoard[i][j] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() + 1 == row && pair.getCol() == col)
                {// top
                    for (int i = row - 2; i >= 0;i--)
                    {             
                        if (this.isInBounds(i, pair.getCol()))
                        {
                            if (this.gameBoard[i][pair.getCol()] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][pair.getCol()] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        //System.out.println("i am here top");
                        color_it = false;
                        for (int i = row - 1; i >= 0;i--)
                        {             
                            if (this.isInBounds(i, pair.getCol()))
                            {
                                if (this.gameBoard[i][pair.getCol()] == EMPTY || this.gameBoard[i][pair.getCol()] == letter)
                                {
                                    //System.out.println("i am here top break");
                                    break;
                                }
                                if (this.gameBoard[i][pair.getCol()] != letter)
                                {
                                    //System.out.println("i am here top inside");
                                    this.gameBoard[i][pair.getCol()] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() + 1 == row && pair.getCol() - 1 == col)
                {// right top
                    for (int i = row - 2, j = col + 2; (i >= 0 && j <= this.gameBoard.length - 1);i--, j++)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        color_it = false;
                        for (int i = row - 1, j = col + 1; (i >= 0 && j <= this.gameBoard.length - 1);i--, j++)
                        {
                            if (this.isInBounds(i, j))
                            {
                                if (this.gameBoard[i][j] == EMPTY || this.gameBoard[i][j] == letter)
                                {
                                    break;
                                }
                                if (this.gameBoard[i][j] != letter)
                                {
                                    this.gameBoard[i][j] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() == row && pair.getCol() - 1 == col)
                {// right
                    for (int j = col + 2; j <= this.gameBoard.length - 1; j++)
                    {
                        if (this.isInBounds(pair.getRow(), j))
                        {
                            if (this.gameBoard[pair.getRow()][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[pair.getRow()][j] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        //System.out.println("i am here right");
                        color_it = false;
                        for (int j = col + 1; j <= this.gameBoard.length - 1; j++)
                        {
                            if (this.isInBounds(pair.getRow(), j))
                            {
                                if (this.gameBoard[pair.getRow()][j] == EMPTY || this.gameBoard[pair.getRow()][j] == letter)
                                {
                                    //System.out.println("i am here right break");
                                    break;
                                }
                                if (this.gameBoard[pair.getRow()][j] != letter)
                                {
                                    //System.out.println("i am here right inside");
                                    this.gameBoard[pair.getRow()][j] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() - 1 == col)
                {// right down
                    for (int i = row + 2, j = col + 2; (i <= this.gameBoard.length - 1 && j <= this.gameBoard.length - 1);i++, j++)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                color_it = true;
                            }
                        }   
                    }
                    if (color_it)
                    {
                        color_it = false;
                        for (int i = row + 1, j = col + 1; (i <= this.gameBoard.length - 1 && j <= this.gameBoard.length - 1);i++, j++)
                        {
                            if (this.isInBounds(i, j))
                            {
                                if (this.gameBoard[i][j] == EMPTY || this.gameBoard[i][j] == letter)
                                {
                                    break;
                                }
                                if (this.gameBoard[i][j] != letter)
                                {
                                    this.gameBoard[i][j] = letter;
                                }
                            }   
                        }
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() == col)
                {// down
                    for (int i = row + 2; i <= this.gameBoard.length - 1;i++)
                    {
                        if (this.isInBounds(i, pair.getCol()))
                        {
                            if (this.gameBoard[i][pair.getCol()] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][pair.getCol()] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        color_it = false;
                        //System.out.println("i am here down");
                        color_it = false;
                        for (int i = row + 1; i <= this.gameBoard.length - 1;i++)
                        {
                            if (this.isInBounds(i, pair.getCol()))
                            {
                                if (this.gameBoard[i][pair.getCol()] == EMPTY || this.gameBoard[i][pair.getCol()] == letter)
                                {
                                    //System.out.println("i am here down break");
                                    break;
                                }
                                if (this.gameBoard[i][pair.getCol()] != letter)
                                {
                                    //System.out.println("i am here down inside");
                                    this.gameBoard[i][pair.getCol()] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() - 1 == row && pair.getCol() + 1 == col)
                {// left down
                    for (int i = row + 2, j = col - 2; (i <= this.gameBoard.length - 1 && j >= 0);i++, j--)
                    {
                        if (this.isInBounds(i, j))
                        {
                            if (this.gameBoard[i][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[i][j] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        color_it = false;
                        for (int i = row + 1, j = col - 1; (i <= this.gameBoard.length - 1 && j >= 0);i++, j--)
                        {
                            if (this.isInBounds(i, j))
                            {
                                if (this.gameBoard[i][j] == EMPTY || this.gameBoard[i][j] == letter)
                                {
                                    break;
                                }
                                if (this.gameBoard[i][j] != letter)
                                {
                                    this.gameBoard[i][j] = letter;
                                }
                            }
                        }
                    }
                }
                if (pair.getRow() == row && pair.getCol() + 1 == col)
                {// left 
                    for (int j = col - 2; j >= 0; j--)
                    {
                        if (this.isInBounds(pair.getRow(), j))
                        {
                            if (this.gameBoard[pair.getRow()][j] == EMPTY)
                            {
                                break;
                            }
                            if (this.gameBoard[pair.getRow()][j] == letter)
                            {
                                color_it = true;
                            }
                        }
                    }
                    if (color_it)
                    {
                        color_it = false;
                        //System.out.println("i am here left");
                        for (int j = col - 1; j >= 0; j--)
                        {
                            if (this.isInBounds(pair.getRow(), j))
                            {
                                if (this.gameBoard[pair.getRow()][j] == EMPTY || this.gameBoard[pair.getRow()][j] == letter)
                                {
                                    //System.out.println("i am here left break");
                                    break;
                                }
                                if (this.gameBoard[pair.getRow()][j] != letter)
                                {
                                    //System.out.println("i am here left break inside");
                                    this.gameBoard[pair.getRow()][j] = letter;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}



// boolean hasNeighbor(int row, int col)
    // {
    //     // If it is in the left-top edge
    //     if (row == 0 && col == 0)
    //     {
    //         if (this.gameBoard[row + 1][col] == EMPTY && this.gameBoard[row][col + 1] == EMPTY && this.gameBoard[row + 1][col + 1] == EMPTY)
    //             return false;
    //     }
    //     // If it is in the left-down edge
    //     if (row == this.gameBoard.length - 1 && col == 0)
    //     {
    //         if (this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row][col + 1] == EMPTY && this.gameBoard[row - 1][col + 1] == EMPTY)
    //             return false;
    //     }
    //     // If it is in the right-down edge
    //     if (row == this.gameBoard.length - 1 && col == this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row - 1][col - 1] == EMPTY)
    //             return false;
    //     }
    //     // If it is in the right-up edge 
    //     if (row == 0 && col == this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row + 1][col] == EMPTY && this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row + 1][col - 1] == EMPTY)
    //             return false;
    //     }
    //     // crucial top
    //     if (row == 0 && col != 0 && col != this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row + 1][col - 1] == EMPTY && this.gameBoard[row + 1][col] == EMPTY && this.gameBoard[row + 1][col + 1] == EMPTY && this.gameBoard[row][col + 1] == EMPTY)
    //         {
    //             return false;
    //         }
    //     }
    //     // crucial left
    //     if (row != 0 && row != this.gameBoard.length - 1 && col == 0)
    //     {
    //         if (this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row - 1][col + 1] == EMPTY && this.gameBoard[row][col + 1] == EMPTY && this.gameBoard[row + 1][col + 1] == EMPTY && this.gameBoard[row + 1][col] == EMPTY)
    //         {
    //             return false;
    //         }
    //     }
    //     // crucial down
    //     if (row == this.gameBoard.length - 1 && col != 0 && col != this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row - 1][col - 1] == EMPTY && this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row - 1][col + 1] == EMPTY && this.gameBoard[row][col + 1] == EMPTY)
    //         {
    //             return false;
    //         }
    //     }
    //     //  crucial right
    //     if (row != 0 && row != this.gameBoard.length - 1 && col == this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row - 1][col - 1] == EMPTY && this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row + 1][col - 1] == EMPTY && this.gameBoard[row + 1][col] == EMPTY)
    //         {
    //             return false;
    //         }
    //     }

    //     // Somewhere in the middle 
    //     if (row >= 0 & row <= this.gameBoard.length - 1 & col >= 0 & col <= this.gameBoard.length - 1)
    //     {
    //         if (this.gameBoard[row][col - 1] == EMPTY && this.gameBoard[row + 1][col - 1] == EMPTY && this.gameBoard[row + 1][col] == EMPTY && this.gameBoard[row + 1][col + 1] == EMPTY && this.gameBoard[row][col + 1] == EMPTY && this.gameBoard[row - 1][col + 1] == EMPTY && this.gameBoard[row - 1][col] == EMPTY && this.gameBoard[row - 1][col - 1] == EMPTY)
    //         {
    //             return false;
    //         }
    //     }
    //     return true;
    // }
