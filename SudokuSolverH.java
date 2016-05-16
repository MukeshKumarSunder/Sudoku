import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;
//MRV Heuristic
public class SudokuSolverH {

	int n,filled;
	static int[][] board;

  boolean[][] row;
  boolean[][] col;
  boolean[][][] box;
  public SudokuSolverH(){
    this.n = 3;

    row = new boolean[n*n][n*n+1];
    col = new boolean[n*n][n*n+1];
    box = new boolean[n*n][n][n*n+1];

    


   for(int i = 0; i<9; i++){
      for(int j = 0; j<9; j++){
        int elem = board[i][j];
        for(int digit = 1; digit<= 9; digit++){
          if(elem == digit){
            row[i][digit] = true;
            col[j][digit] = true;
            box[i/3][j/3][digit] = true;
            filled++;
          }
        }
      }
    }
  }
  static void displaySudoku() {
	    for (int row = 0; row < 9; row++) {
	        for (int col = 0; col < 9; col++){
	            if((col)%3 == 0)
	                System.out.print(" | ");
	            System.out.print(board[row][col]);
	        }
	        System.out.print(" | ");
	        if((row+1)%3 == 0){
	            System.out.println();
	            System.out.print(" -------------------");
	        }
	        System.out.println();
	    }
	 }
 

  public void fillBoard(){
    if(filled == 81){
      displaySudoku();
      return;			

    }
    int min = 9;
    int mini = -1;
    int minj = -1;
    TreeSet<Integer> possibleValues = null;

    for(int i = 0; i<9; i++){
      for(int j = 0; j<9; j++){
        if(board[i][j]!=0){continue;}
        TreeSet<Integer> t = findRemainingValues(i, j);

        if(min>t.size()){
          min = t.size();
          possibleValues = t;
          mini = i;
          minj = j;
        }				
      }
    }

 
    for(Integer x:possibleValues){
      board[mini][minj] = x;
      row[mini][x] = true;
      col[minj][x] = true;
      box[mini/n][minj/n][x] = true;
      filled++;
      fillBoard();
      filled--;
      box[mini/n][minj/n][x] = false;
      col[minj][x] = false;
      row[mini][x] = false;
      board[mini][minj] = 0;
    }
  }


  private TreeSet<Integer> findRemainingValues(int r, int c){
    int box_row = r/3;
    int box_col = c/3;
    TreeSet<Integer> remValues = new TreeSet<Integer>();			

    HashMap<Integer, Integer> rowMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> colMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> boxMap = new HashMap<Integer, Integer>();
    for(int digit = 1; digit<=9; digit++){
      if(!row[r][digit]){ 
        rowMap.put(digit, digit);
        remValues.add(digit);
      }

      if(!col[c][digit]){
        colMap.put(digit, digit);
        remValues.add(digit);
      }

      if(!box[box_row][box_col][digit]){
        boxMap.put(digit, digit);
        remValues.add(digit);
      }
    }

    TreeSet<Integer> values = new TreeSet<Integer>();

    for(Integer value:remValues){
      if(rowMap.get(value)!=null && colMap.get(value)!=null && boxMap.get(value)!=null){
        values.add(value);
      }
    }

    return values;
  }



  public static void main(String[] args) {
      
      board = new int[9][9];
      Scanner scanner;
            try {
            scanner = new Scanner(new File(args[0]));
            while(scanner.hasNextInt()){
             for(int i= 0;i<9;i++){
                 for(int j = 0;j<9;j++){
                     board[i][j] = scanner.nextInt();
                 }
             }
        }
         
     } catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
     }
	  Long time = System.currentTimeMillis();
	  SudokuSolverH sudoku = new SudokuSolverH();
     sudoku.fillBoard();
     time = System.currentTimeMillis()-time;
     System.out.println("SOLUTION\n");
     System.out.println("Time taken (ms) : "+time);
  }

}
