package de.kune.sudoku;

public class Sudoku {

    byte[][] grid;

    public Sudoku(byte[][] grid) {
        if (grid.length != 9) {
            throw new IllegalArgumentException("Grid must have 9 rows and 9 columns");
        }
        this.grid = new byte[grid.length][];
        for (int row = 0; row < grid.length; row++) {
                if (grid[row].length != 9) {
                    throw new IllegalArgumentException("Grid must have 9 rows and 9 columns");
                }
            this.grid[row] = new byte[grid[row].length];
            System.arraycopy(grid[row], 0, this.grid[row], 0, grid[row].length);
        }
    }

    public boolean isEmpty(int column, int row) {
        return grid[row][column] == 0;
    }

    public boolean isValidValue(int column, int row, byte value) {
        return isEmpty(column, row) && isValidValueInRow(row, value) && isValidValueInColumn(column, value) && isValidValueInBlock(column, row, value);
    }

    private boolean isValidValueInBlock(int column, int row, byte value) {
        for (int r = Math.floorDiv(row, 3) * 3; r < Math.floorDiv(row, 3) * 3 + 3; r++) {
            for (int c = Math.floorDiv(column, 3) * 3; c < Math.floorDiv(column, 3) * 3 + 3; c++) {
                if (grid[r][c] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidValueInRow(int row, byte value) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[row][c] == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidValueInColumn(int column, byte value) {
        for (byte[] row : grid) {
            if (row[column] == value) {
                return false;
            }
        }
        return true;
    }

    public Sudoku withValue(int column, int row, byte value) {
        if (row < 0 || row >= grid.length || column < 0 || column > grid[0].length) {
            throw new IllegalArgumentException("Column and row must be between 1 and 8");
        }
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Value must be between 0 and 9");
        }
        Sudoku result = new Sudoku(this.grid);
        result.grid[row][column] = value;
        return result;
    }

    public Sudoku solve() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (isEmpty(column, row)) {
                    for (byte v = 1; v < 10; v++) {
                        if (isValidValue(column, row, v)) {
                            Sudoku cand = withValue(column, row, v);
                            if (row < grid.length - 1 || column < grid[row].length - 1) {
                                cand = cand.solve();
                            }
                            if (cand != null) {
                                return cand;
                            }
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(100);
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                result.append(grid[row][column]);
                if ((column+1) % 3 == 0) {
                    result.append(" ");
                }
            }
            result.append("\n");
            if ((row+1) % 3 == 0) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
//        Sudoku sudoku = new Sudoku(new byte[][] {
//                {0,6,4, 3,5,0, 2,0,1,},
//                {3,0,0, 2,0,0, 6,0,0,},
//                {0,9,7, 1,0,4, 3,5,0,},
//
//                {1,3,2, 4,0,0, 9,6,0,},
//                {0,0,9, 0,1,0, 8,2,4,},
//                {4,8,6, 9,0,0, 0,3,0,},
//
//                {7,0,0, 0,0,6, 0,1,3,},
//                {0,0,3, 8,4,0, 7,9,2,},
//                {9,4,1, 7,0,0, 0,8,0,},
//        });
        Sudoku sudoku = new Sudoku(new byte[][] {
                {1,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},

                {0,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},

                {0,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},
                {0,0,0, 0,0,0, 0,0,0,},
        });
        System.out.println(sudoku);
        System.out.println(sudoku.solve());

//        System.out.println(sudoku.isValidValue(0, 7, (byte)5));

//        System.out.println(new Sudoku(new byte[][] {
//            { 8,6,4, 3,5,9, 2,7,1 },
//            { 3,1,5, 2,7,8, 6,4,9 },
//            { 2,9,7, 1,6,4, 3,5,8 },
//            { 1,3,2, 4,8,5, 9,6,7 },
//            { 5,7,9, 6,1,3, 8,2,4 },
//            { 4,8,6, 9,2,7, 1,3,5 },
//            { 7,2,8, 5,9,6, 4,1,3 },
//            { 6,5,3, 8,4,1, 7,9,2 },
//            { 9,4,1, 7,3,2, 5,8,0 },
//        }).isValidValue(8, 8, (byte)6));
    }

}
