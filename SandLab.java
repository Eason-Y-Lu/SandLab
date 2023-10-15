import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  //Colors
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid = new int[120][80];
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //prevent override existing particles by other particles
    if (grid[row][col] == EMPTY)
    {
      grid [row][col] = tool;
    }else if(tool == EMPTY)
    {
      grid [row][col] = tool;
    }
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    final Color LIGHT_BLUE = new Color(51,153,255);
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid[0].length; col++)
      {
        if (grid[row][col] == EMPTY)
        {
          display.setColor(row, col, Color.BLACK);
        }
        else if (grid[row][col] == METAL)
        {
          display.setColor(row, col, Color.GRAY);
        }
        else if (grid[row][col] == SAND)
        {
          display.setColor(row, col, Color.YELLOW);
        }
        else if (grid[row][col] == WATER)
        {
          display.setColor(row, col, LIGHT_BLUE);
        }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
    Random rand = new Random();
    int row = rand.nextInt(grid.length);
    int col = rand.nextInt(grid[0].length);
    if (grid[row][col] == SAND)
    {
      if (row + 1 < grid.length && grid[row + 1][col] == EMPTY)
      {
        grid[row + 1][col] = SAND;
        grid[row][col] = EMPTY;
      }
      else if (row + 1 < grid.length && col + 1 < grid[0].length && grid[row + 1][col + 1] == EMPTY)
      {
        grid[row + 1][col + 1] = SAND;
        grid[row][col] = EMPTY;
      }
      else if (row + 1 < grid.length && col - 1 >= 0 && grid[row + 1][col - 1] == EMPTY)
      {
        grid[row + 1][col - 1] = SAND;
        grid[row][col] = EMPTY;
      }
    }
    else if (grid[row][col] == WATER)
    {
      if (row + 1 < grid.length && grid[row + 1][col] == EMPTY)
      {
        grid[row + 1][col] = WATER;
        grid[row][col] = EMPTY;
      }
      else if (row + 1 < grid.length && col + 1 < grid[0].length && grid[row + 1][col + 1] == EMPTY)
      {
        grid[row + 1][col + 1] = WATER;
        grid[row][col] = EMPTY;
      }
      else if (row + 1 < grid.length && col - 1 >= 0 && grid[row + 1][col - 1] == EMPTY)
      {
        grid[row + 1][col - 1] = WATER;
        grid[row][col] = EMPTY;
      }
      else if (col + 1 < grid[0].length && grid[row][col + 1] == EMPTY)
      {
        grid[row][col + 1] = WATER;
        grid[row][col] = EMPTY;
      }
      else if (col - 1 >= 0 && grid[row][col - 1] == EMPTY)
      {
        grid[row][col - 1] = WATER;
        grid[row][col] = EMPTY;
      }
    }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
