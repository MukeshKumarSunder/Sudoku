import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SudokuSolverAC3 {
	private static class Index {
		int row;
		int col;

		public Index(int row, int col) {
			this.row = row;
			this.col = col;
		}
		public String toString() {
			return "[" + row + "," + col + "]";
		}
	}

//	private static class Instance extends Index {
//		int exclusion;
//
//		public Instance(int row, int col, int exclusion) {
//			super(row, col);
//			this.exclusion = exclusion;
//		}
//
//		public boolean equals(Object otherObj) {
//			Instance other = (Instance) otherObj;
//			return (this.row == other.row && this.col == other.col && this.exclusion == other.exclusion);
//		}
//
//		public String toString() {
//			return super.toString() + "-" + exclusion;
//		}
//	}
	

	public static void main(String[] args) {
		int[][] Puzzle = new int[9][9];
                Scanner scanner;
            try {
            scanner = new Scanner(new File(args[0]));
            while(scanner.hasNextInt()){
             for(int i= 0;i<9;i++){
                 for(int j = 0;j<9;j++){
                     Puzzle[i][j] = scanner.nextInt();
                 }
             }
        }
         
     } catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
     }


		Sudoku puzzle = new Sudoku(Puzzle);
                System.out.println("Original Puzzle: ");
                puzzle.print();
                Long time = System.currentTimeMillis();
		Sudoku solution = backtrack(puzzle);
                time = System.currentTimeMillis()-time;
  
		if (solution == null) {
			System.out.println("No solution");
		} else {
                        System.out.println("Solution: ");
			solution.print();
                        System.out.println("Time taken (ms) : "+time);
		}
	}

	public static Sudoku backtrack(Sudoku puzzle) {
		if (isComplete(puzzle)) {
			return puzzle;
		}

		Index unassigned = findEmptyCell(puzzle);
		LinkedList<Integer> domainValues = orderDomainValues(unassigned, puzzle);

		for (int i = 0; i < domainValues.size(); i++) {
			//LinkedList<Instance> instances = null;
			int value = domainValues.get(i);
			puzzle.set(unassigned.row, unassigned.col, value);

			if (notViolated(puzzle)) {
				

				if (!AC3(puzzle)) {
					continue;
				}

				
				Sudoku result = backtrack(puzzle);

				if (result != null) {
					return result;
				}
			}

			puzzle.unset(unassigned.row, unassigned.col);
			
		}

		return null;
	}

	public static void prepareQueue(Sudoku puzzle, PriorityQueue<Index[]> arcs) {
		for (int assignedRow = 0; assignedRow < puzzle.puzzleSide; assignedRow++) {
			for (int assignedCol = 0; assignedCol < puzzle.puzzleSide; assignedCol++) {
				/* Queue squares in current unit */
				int startRow = (assignedRow / puzzle.unitSide) * puzzle.unitSide;
				int startCol = (assignedCol / puzzle.unitSide) * puzzle.unitSide;
				int endRow = startRow + puzzle.unitSide;
				int endCol = startCol + puzzle.unitSide;

				for (int row = startRow; row < endRow; row++) {
					for (int col = startCol; col < endCol; col++) {
						if (row != assignedRow && col != assignedCol) {
							arcs.enqueue(new Index[] { new Index(row, col), new Index(assignedRow, assignedCol) });
						}
					}
				}

				/* Queue squares in current row */
				for (int row = 0; row < puzzle.puzzleSide; row++) {
					if (row != assignedRow) {
						arcs.enqueue(new Index[] { new Index(row, assignedCol), new Index(assignedRow, assignedCol) });
					}
				}

				/* Queue squares in current column */
				for (int col = 0; col < puzzle.puzzleSide; col++) {
					if (col != assignedCol) {
						arcs.enqueue(new Index[] { new Index(assignedRow, col), new Index(assignedRow, assignedCol) });
					}
				}
			}
		}
	}

	public static boolean AC3(Sudoku puzzle) {
		PriorityQueue<Index[]> arcs = new PriorityQueue<>();
		prepareQueue(puzzle, arcs);

		while (arcs.size() > 0) {
			Index[] edge = arcs.dequeue();

			if (!arcReduce(puzzle, edge[0], edge[1])) {
				continue;
			}

			int xRow = edge[0].row;
			int xCol = edge[0].col;
			int yRow = edge[1].row;
			int yCol = edge[1].col;

			if (puzzle.getDomainLength(xRow, xCol) == 0) {
				return false;
			}

			/* Queue squares in current unit */
			int startRow = (xRow / puzzle.unitSide) * puzzle.unitSide;
			int startCol = (xCol / puzzle.unitSide) * puzzle.unitSide;
			int endRow = startRow + puzzle.unitSide;
			int endCol = startCol + puzzle.unitSide;

			for (int row = startRow; row < endRow; row++) {
				for (int col = startCol; col < endCol; col++) {
					if (row != xRow && col != xCol && row != yRow && col != yCol && puzzle.get(xRow, xCol)==0) {
						arcs.enqueue(new Index[] { new Index(row, col), new Index(xRow, xCol) });
					}
				}
			}

			/* Queue squares in current row */
			for (int row = 0; row < puzzle.puzzleSide; row++) {
				if (row != xRow && row != yRow && puzzle.get(xRow, xCol)==0) {
					arcs.enqueue(new Index[] { new Index(row, xCol), new Index(xRow, xCol) });
				}
			}

			/* Queue squares in current column */
			for (int col = 0; col < puzzle.puzzleSide; col++) {
				if (col != xCol && col != yCol && puzzle.get(xRow, xCol)==0) {
					arcs.enqueue(new Index[] { new Index(xRow, col), new Index(xRow, xCol) });
				}
			}
		}
		return true;
	}

	public static boolean arcReduce(Sudoku puzzle, Index i, Index j) {
		boolean revised = false;

		LinkedList<Integer> domainI = puzzle.getDomainCopy(i.row, i.col);
		LinkedList<Integer> domainJ = puzzle.getDomain(j.row, j.col);

		for (Integer x : domainI) {
			boolean satisfiable = false;

			for (Integer y : domainJ) {
				if ((int) y != (int) x) {
					satisfiable = true;
				}
			}

			if (!satisfiable) {
				puzzle.removeFromDomain(i.row, i.col, x);
				revised = true;
			}
		}

		return revised;
	}

	public static LinkedList<Integer> orderDomainValues(Index var, Sudoku puzzle) {
		LinkedList<Integer> domain = puzzle.getDomain(var.row, var.col);

		return domain;
	}

	public static Index findEmptyCell(Sudoku puzzle) {
		for (int row = 0; row < puzzle.puzzleSide; row++) {
			for (int col = 0; col < puzzle.puzzleSide; col++) {
				if (puzzle.get(row, col) == 0) {
					return new Index(row, col);
				}
			}
		}

		return null;
	}

	public static boolean isComplete(Sudoku puzzle) {
		for (int row = 0; row < puzzle.puzzleSide; row++) {
			for (int col = 0; col < puzzle.puzzleSide; col++) {
				if (puzzle.get(row, col) == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isSolved(Sudoku puzzle) {
		return isComplete(puzzle) && notViolated(puzzle);
	}

	public static boolean notViolated(Sudoku puzzle) {
		/* Check each unit */
		for (int uRow = 0; uRow < puzzle.unitSide; uRow++) {
			for (int uCol = 0; uCol < puzzle.unitSide; uCol++) {
				int[] nums = new int[9];
				int startRow = uRow * puzzle.unitSide;
				int startCol = uCol * puzzle.unitSide;
				int endRow = startRow + puzzle.unitSide;
				int endCol = startCol + puzzle.unitSide;
				int at = 0;

				for (int row = startRow; row < endRow; row++) {
					for (int col = startCol; col < endCol; col++) {
						nums[at++] = puzzle.get(row, col);
					}
				}

				Arrays.sort(nums);

				for (int i = 1; i < at; i++) {
					if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
						return false;
					}
				}
			}
		}

		/* Check each row */
		for (int row = 0; row < puzzle.puzzleSide; row++) {
			int[] nums = new int[9];

			for (int col = 0; col < puzzle.puzzleSide; col++) {
				nums[col] = puzzle.get(row, col);
			}

			Arrays.sort(nums);

			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
					return false;
				}
			}
		}

		/* Check each column */
		for (int col = 0; col < puzzle.puzzleSide; col++) {
			int[] nums = new int[9];

			for (int row = 0; row < puzzle.puzzleSide; row++) {
				nums[row] = puzzle.get(row, col);
			}

			Arrays.sort(nums);

			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
					return false;
				}
			}
		}

		return true;
	}
}