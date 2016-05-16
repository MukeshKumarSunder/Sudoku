
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuSolverBT {

 // dimension of input
 static int N = 9;

 // sample input
 static int grid[][] = new int[9][9]; 

 /**
  * Utility function to check whether @param value is valid for @param cell
  */

 static boolean validValue(Cell cell, int value) {

  if (grid[cell.row][cell.col] != 0) {
   throw new RuntimeException(
     "Cannot change a pre-assigned value");
  }

  // check row
  for (int c = 0; c < 9; c++) {
   if (grid[cell.row][c] == value)
    return false;
  }

  // check column
  for (int r = 0; r < 9; r++) {
   if (grid[r][cell.col] == value)
    return false;
  }

  // check 3X3 box
  int x1 = 3 * (cell.row / 3);
  int y1 = 3 * (cell.col / 3);
  int x2 = x1 + 2;
  int y2 = y1 + 2;

  for (int x = x1; x <= x2; x++)
   for (int y = y1; y <= y2; y++)
    if (grid[x][y] == value)
     return false;

  // if value is valif return true
  return true;
 }

 // function to get the next cell
 static Cell getNextCell(Cell currentCell) {

  int row = currentCell.row;
  int col = currentCell.col;
  col++;
  if (col > 8) {
   col = 0;
   row++;
  }
  if (row > 8)
   return null; // reached end

  Cell nextCell = new Cell(row, col);
  return nextCell;
 }

 static boolean solveBT(Cell currentCell) {

  // reached end return
    if (currentCell == null)
        return true;

  // cell already assigned return
    if (grid[currentCell.row][currentCell.col] != 0) {
        return solveBT(getNextCell(currentCell));
    }

    for (int i = 1; i <= 9; i++) {
        boolean valid = validValue(currentCell, i);

        if (!valid) // i not valid for this cell, try other values
            continue;
        grid[currentCell.row][currentCell.col] = i;
        boolean solved = solveBT(getNextCell(currentCell));
   // if solved, return, else try other values
        if (solved)
            return true;
        else
            grid[currentCell.row][currentCell.col] = 0;
    }
    return false;
 }

 public static void main(String[] args) {
     //displaySudoku(grid);
     
     Scanner scanner;
     try {
         scanner = new Scanner(new File(args[0]));
         while(scanner.hasNextInt()){
             for(int i= 0;i<9;i++){
                 for(int j = 0;j<9;j++){
                     grid[i][j] = scanner.nextInt();
                 }
             }
        }
         
     } catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
     }

     displaySudoku(grid);
  Long time = System.currentTimeMillis();
  boolean solved = solveBT(new Cell(0, 0));
  time = System.currentTimeMillis()-time;
  
  if (!solved) {
   System.out.println("SUDOKU cannot be solved.");
   return;
  }
  System.out.println("SOLUTION\n");
  displaySudoku(grid);
     System.out.println("Time taken (ms) : "+time);
 }


 static void displaySudoku(int grid[][]) {
    for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++){
            if((col)%3 == 0)
                System.out.print(" | ");
            System.out.print(grid[row][col]);
        }
        System.out.print(" | ");
        if((row+1)%3 == 0){
            System.out.println();
            System.out.print(" -------------------");
        }
        System.out.println();
    }
 }
}
