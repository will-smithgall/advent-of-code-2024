import Helpers.StringGrid;

public class DayFour {
    final String TARGET = "XMAS";
    final String MAS_AS = "AS";
    final String MAS_MA = "AM";
    StringGrid grid;

    public DayFour() {
        grid = new StringGrid("inputs/day4.txt");
    }

    public static void main(String[] args) {
        DayFour d = new DayFour();

        int partOne = d.partOne();
        int partTwo = d.partTwo();

        System.out.println(String.format("Part One: %d\nPart Two: %d", partOne, partTwo));

        System.out.println(d.grid.toString());
    }

    public int partOne() {
        int result = 0;

        int rows = grid.getRows();
        int cols = grid.getCols();
        boolean onlyCorners = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result += grid.searchForString(i, j, TARGET, onlyCorners);
            }
        }

        return result;
    }

    public int partTwo() {
        int result = 0;

        int rows = grid.getRows();
        int cols = grid.getCols();
        boolean onlyCorners = true;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid.searchForString(i, j, MAS_AS, onlyCorners) != 2
                        || grid.searchForString(i, j, MAS_MA, onlyCorners) != 2) {
                    continue;
                }

                // Make sure diag corners are not the same char
                int asMap = grid.getMasBitmap(i, j, MAS_AS);
                int maMap = grid.getMasBitmap(i, j, MAS_MA);

                if (asMap == 0b1010 || asMap == 0b0101) {
                    continue;
                }

                if (maMap == 0b1010 || maMap == 0b0101) {
                    continue;
                }

                result += 1;
            }
        }

        return result;
    }
}
