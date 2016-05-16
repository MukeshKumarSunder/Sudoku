class Cell {

  int row, col;

  public Cell(int row, int col) {
   super();
   this.row = row;
   this.col = col;
  }

  @Override
  public String toString() {
   return "Cell [row=" + row + ", col=" + col + "]";
  }
  
  public boolean equals(Cell c){
      if(this.row == c.row && this.col == c.col)
          return true;
      return false;
  }
  
}