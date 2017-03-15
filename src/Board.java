import java.util.*;

/**
 * Created by Kaixing on 2/25/2017.
 */
public class Board {
    public Grid[][] gridList;
    public double score;

    public void setGridList(Grid[][] gridList) {
        this.gridList = gridList;
    }
    public Grid[][] getGridList(){
        return this.gridList;
    }
    public void setScore(double score){
        this.score = score;
    }
    public void startGame() {
        Grid[][] gridList = new Grid[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Grid grid = new Grid();
                grid.setxLocation(i);
                grid.setyLocation(j);
                grid.setNumber(0);
                grid.setDisplay(false);
                gridList[i][j] = grid;
            }
        }

        Random initalGrids = new Random();
        int count = 0;
        while (count < 1) {
            int location = initalGrids.nextInt(15);
            int x = location / 4;
            int y = location % 4;
            if (gridList[x][y].getNumber() == 0){
                Grid grid = new Grid();
                grid.setNumber(2);
                grid.setDisplay(true);
                grid.setxLocation(x);
                grid.setyLocation(y);
                gridList[x][y] = grid;
                count++;

                System.out.println("X: "+x+" Y: "+y+" num: "+grid.getNumber());

            }
        }
        setGridList(gridList);
        printBoard();
    }

    // This function generate a random grid in a random location, and assign a number value to the grid.
    public void generateRandom() {
        if (isFull()) {
            return;
        }
        Random generateGrid = new Random();
        // First, we generate a random location.
        int location = generateGrid.nextInt(15);
        int x = location / 4;
        int y = location % 4;
        Grid grid = this.gridList[x][y];
        // If the location is used, generate a random location again.
        if (grid.getDisplay()) {
            generateRandom();
            return;
        }
        // Generate a random number and assign to the grid.
        int num = generateGrid.nextInt(2);
        if (num == 0) {
            grid.setNumber(2);
            grid.setDisplay(true);
        }
        if (num == 1) {
            grid.setNumber(4);
            grid.setDisplay(true);
        }
        this.gridList[x][y] = grid;
        printBoard();
    }

    public void rollLeft() {
        //roll left twice to avoid special case where these could be two merge happening in one row.
        rollLeft1();
        rollLeft1();
        rollLeft1();
    }

    public void rollLeft1() {
        rollOnerowLeft(gridList[0][1]);
        rollOnerowLeft(gridList[1][1]);
        rollOnerowLeft(gridList[2][1]);
        rollOnerowLeft(gridList[3][1]);
    }

    private void rollOnerowLeft(Grid startGrid) {
        int x = startGrid.getxLocation();
        int y = startGrid.getyLocation();
        while (y < 4) {
            this.score += startGrid.merge(this.gridList[x][y - 1]);
            if (y < 3) {
                startGrid = this.gridList[x][y + 1];
            }
            y = y + 1;
        }
    }

    public void rollRight() {
        //roll right twice to avoid special case where these could be two merge happening in one row.
        rollRight1();
        rollRight1();
        rollRight1();
    }

    public void rollRight1() {
        rollOnerowRight(gridList[0][2]);
        rollOnerowRight(gridList[1][2]);
        rollOnerowRight(gridList[2][2]);
        rollOnerowRight(gridList[3][2]);
    }

    private void rollOnerowRight(Grid startGrid) {
        int x = startGrid.getxLocation();
        int y = startGrid.getyLocation();
        while (y >= 0) {
            this.score += startGrid.merge(this.gridList[x][y + 1]);
            if (y > 0) {
                startGrid = this.gridList[x][y - 1];
            }
            y = y - 1;
        }
    }


    public void rollUp() {
        //roll left twice to avoid special case where these could be two merge happening in one row.
        rollUp1();
        rollUp1();
        rollUp1();
    }


    public void rollUp1() {
        rollOnerowUp(gridList[1][0]);
        rollOnerowUp(gridList[1][1]);
        rollOnerowUp(gridList[1][2]);
        rollOnerowUp(gridList[1][3]);
    }

    private void rollOnerowUp(Grid startGrid) {
        int x = startGrid.getxLocation();
        int y = startGrid.getyLocation();
        while (x < 4) {
            this.score += startGrid.merge(this.gridList[x - 1][y]);
            if (x < 3) {
                startGrid = this.gridList[x + 1][y];
            }
            x = x + 1;
        }
    }


    public void rotate() {
        int newRow;
        int newCol;
        int newNum;

        Grid[][] newGridList = new Grid[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Grid newGrid = new Grid();

                int curNum = gridList[i][j].getNumber();
                {
                    newCol = i;
                    newRow = 3 - j;

                    newGrid.setxLocation(newRow);
                    newGrid.setyLocation(newCol);
                    newGrid.setNumber(curNum);
                    if (curNum != 0) {
                        newGrid.setDisplay(true);
                    } else {
                        newGrid.setDisplay(false);
                    }
                    newGridList[newRow][newCol] = newGrid;

                } /*else {

                    newGrid.setDisplay(false);
                }*/
            }
        }
        this.gridList = newGridList;
        System.out.println("Rotate Board");
        printBoard();
    }





    public void rollDown() {
        //roll left twice to avoid special case where these could be two merge happening in one row.
        rollDown1();
        rollDown1();
        rollDown1();
    }

    public void rollDown1(){
        rollOnerowDown(gridList[2][0]);
        rollOnerowDown(gridList[2][1]);
        rollOnerowDown(gridList[2][2]);
        rollOnerowDown(gridList[2][3]);

    }

    private void rollOnerowDown(Grid startGrid) {
        int x = startGrid.getxLocation();
        int y = startGrid.getyLocation();
        while (x >= 0) {
            this.score += startGrid.merge(this.gridList[x + 1][y]);
            if (x > 0) {
                startGrid = this.gridList[x - 1][y];
            }
            x = x - 1;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 4; i++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < 4; j++) {
                if (this.gridList[i][j].getDisplay()) {
                    int number = this.gridList[i][j].getNumber();
                    row.add(j, number);
                } else {
                    row.add(j, -1);
                }
            }
            System.out.println(i);
            System.out.println(row);
        }
    }


    // This function check the board is full and set all grid status back to not merged.
    public boolean isFull() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.gridList[i][j].getDisplay()) {
                    count++;
                }
            }
        }
        if (count == 16) {
            return true;
        }
        return false;
    }

    public void resetStatus(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.gridList[i][j].setIsMerged(false);
            }
        }
    }

    public double getScore() {
        return this.score;
    }
}