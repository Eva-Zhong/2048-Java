/**
 * Created by Kaixing on 2/27/2017.
 */
public class AI {
    private Board board ;
    public AI(Board game){
        this.board = game;
    }
    public void setBoard(Board game){
        this.board = game;
    }
    public Board getBoard(){
        return this.board;
    }
    private Grid[][] copyBoard(){
        Grid[][] virtualGird = new Grid[4][4];
        Grid[][] girdList = getBoard().getGridList();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                virtualGird[i][j] = girdList[i][j].clone();
            }
        }
        return virtualGird;
    }
    private Board getVirtualBoard(){
        Board virtualGame = new Board();
        Grid[][] virtualGird = copyBoard();
        virtualGame.setGridList(virtualGird);
        return virtualGame;
    }
    public void playAI(){
        int scoreLeft, scoreRight, scoreUp, scoreDown;
        Board leftBoard = getVirtualBoard();
        leftBoard.rollLeft();
        scoreLeft = leftBoard.getScore();
        Board rightBoard = getVirtualBoard();
        rightBoard.rollRight();
        scoreRight = rightBoard.getScore();
        Board upBoard = getVirtualBoard();
        upBoard.rollUp();
        scoreUp = upBoard.getScore();
        Board downBoard = getVirtualBoard();
        downBoard.rollDown();
        scoreDown = downBoard.getScore();
        if (scoreLeft >= scoreDown && scoreLeft >= scoreRight && scoreLeft >= scoreUp){
            this.board.rollLeft();
            System.out.println("Left");
            return;
        }
        if (scoreRight >= scoreDown && scoreRight >= scoreLeft && scoreRight >= scoreUp){
            this.board.rollRight();
            System.out.println("Right");
            return;
        }
        if (scoreUp >= scoreDown && scoreUp >= scoreRight && scoreUp >= scoreLeft){
            this.board.rollUp();
            System.out.println("Up");
            return;
        }
        if (scoreDown >= scoreUp && scoreDown >= scoreRight && scoreDown >= scoreLeft){
            System.out.println("Down");
            this.board.rollDown();
            return;
        }
    }
}