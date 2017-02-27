/**
 * Created by Kaixing on 2/25/2017.
 */
public class Grid {
    private int number = 0;
    private boolean display = false;
    private int xLocation;
    private int yLocation;

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
    // this function merge this grid onto the mergeGrid if possible
    public void merge(Grid mergeGrid) {
        if (mergeGrid == null) {
            System.out.println("merging with null");
            return;
        }
        // if this grid is not displayed, stop the merge
        if (!this.getDisplay()) {
            return;
        }
        // if these grids are the same, merge
        if (mergeGrid.getNumber() == this.number) {
            mergeGrid.setNumber(this.number * 2);
            this.setNumber(0);
            this.setDisplay(false);
            return;
        }
        // if merge grid is empty, move this grid into the merge grid
        if (!mergeGrid.getDisplay()) {
            mergeGrid.setNumber(this.getNumber());
            mergeGrid.setDisplay(this.getDisplay());
            this.setDisplay(false);
            this.setNumber(0);
        }
    }
    public Grid clone(){
        Grid grid = new Grid();
        grid.setDisplay(getDisplay());
        grid.setNumber(getNumber());
        grid.setxLocation(getxLocation());
        grid.setyLocation(getyLocation());
        return grid;
    }
}


