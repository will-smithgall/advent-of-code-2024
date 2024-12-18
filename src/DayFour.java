import Helpers.StringGrid;
import java.util.HashMap;

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
                HashMap<Integer, Integer> asMap = grid.getMasMapping(i, j, MAS_AS);
                HashMap<Integer, Integer> maMap = grid.getMasMapping(i, j, MAS_MA);

                if (grid.searchForString(i, j, MAS_AS, onlyCorners) == 2
                        && grid.searchForString(i, j, MAS_MA, onlyCorners) == 2) {
                    if (asMap.get(0) == 1 && asMap.get(2) == 1) {
                        continue;
                    }

                    if (asMap.get(1) == 1 && asMap.get(3) == 1) {
                        continue;
                    }

                    if (maMap.get(0) == 1 && maMap.get(2) == 1) {
                        continue;
                    }

                    if (maMap.get(1) == 1 && maMap.get(3) == 1) {
                        continue;
                    }

                    result += 1;
                }
            }
        }

        return result;
    }
}
