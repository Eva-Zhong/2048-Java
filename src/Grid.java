
/**
 * Created by Kaixing on 2/25/2017.
 */
public class Grid {
    private int number = 0;
    private boolean display = false;
    private int xLocation;
    private int yLocation;
    private boolean isMerged;

    public int getNumber() {
        return this.number;
    }

    public boolean getDisplay() {
        return this.display;
    }

    public int getxLocation() {
        return this.xLocation;
    }
    public int getyLocation() {
        return this.yLocation;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }
    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }
    public void setIsMerged(boolean status){
        this.isMerged = status;
    }
    public boolean getIsMerged(){
        return this.isMerged;
    }
    // this function merge this grid onto the mergeGrid if possible 
    // it also return a score as double: the int part is the real score and the decimal part
    // indicates whether this board can be moved or not when we call playable() in Gamepage 
    public double merge(Grid mergeGrid) {
        if (mergeGrid == null) {
            System.out.println("merging with null");
            return 0;
        }
        // if this grid is not displayed, stop the merge
        if (!this.getDisplay()) {
            return 0;
        }
        // if these grids are the same, merge
        if (mergeGrid.getNumber() == this.number && (!mergeGrid.getIsMerged())&& (!this.getIsMerged())) {
            mergeGrid.setNumber(this.number * 2);
            this.setIsMerged(false);
            mergeGrid.setIsMerged(true);
            this.setNumber(0);
            this.setDisplay(false);
            return mergeGrid.getNumber()*2;
        }
        // if merge grid is empty, move this grid into the merge grid
        if (!mergeGrid.getDisplay()) {
            mergeGrid.setNumber(this.getNumber());
            mergeGrid.setDisplay(this.getDisplay());
            this.setDisplay(false);
            this.setNumber(0);
            mergeGrid.setIsMerged(false);
            this.setIsMerged(false);
            // return 0.01 to indicate there is a possible move in playable().
            return 0.000001;
        }
        return 0;
    }
    public Grid clone(){
        Grid grid = new Grid();
        grid.setDisplay(getDisplay());
        grid.setNumber(getNumber());
        grid.setxLocation(getxLocation());
        grid.setyLocation(getyLocation());
        return grid;
    }
    public double subtract(Grid mergeGrid) {
        if (mergeGrid == null) {
            System.out.println("merging with null");
            return 0;
        }
        // if this grid is not displayed, stop the merge
        if (!this.getDisplay()) {
            return 0;
        }
        // if these grids are the same, subtract
        if (mergeGrid.getNumber() == this.number && (!mergeGrid.getIsMerged()) && (!this.getIsMerged())) {
            int score = mergeGrid.getNumber();
            mergeGrid.setNumber(0);
            this.setIsMerged(false);
            mergeGrid.setIsMerged(true);
            this.setNumber(0);
            this.setDisplay(false);
            return score*4;
        }
        // if merge grid is empty, move this grid into the merge grid
        if (!mergeGrid.getDisplay()) {
            mergeGrid.setNumber(this.getNumber());
            mergeGrid.setDisplay(this.getDisplay());
            this.setDisplay(false);
            this.setNumber(0);
            mergeGrid.setIsMerged(false);
            this.setIsMerged(false);
            // return 0.01 to indicate there is a possible move in playable().
            return 0.000001;
        }
        return 0;
    }
}