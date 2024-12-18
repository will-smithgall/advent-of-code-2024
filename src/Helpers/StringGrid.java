package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StringGrid {
    private ArrayList<ArrayList<String>> grid;
    private int numRows;
    private int numCols;

    // empty grid
    public StringGrid() {
        grid = new ArrayList<ArrayList<String>>();
        numRows = numCols = 0;
    }

    public int getRows() {
        return numRows;
    }

    public int getCols() {
        return numCols;
    }

    // create new grid from input file
    public StringGrid(String fp) {
        grid = new ArrayList<ArrayList<String>>();

        try {
            Scanner in = new Scanner(new File(fp));

            while (in.hasNextLine()) {
                String[] line = in.nextLine().split("");
                ArrayList<String> chars = new ArrayList<>();

                Collections.addAll(chars, line);

                grid.add(chars);
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }

        numCols = grid.size();
        numRows = grid.get(0).size();
    }

    /**
     * Search for a string at (row, col) in all 8 directions
     * 
     * @param row    Row position
     * @param col    Column position
     * @param target Target string to search
     * @return The number of occurances of target
     */
    public int searchForString(int row, int col, String target, boolean onlyCorners) {
        int result = onlyCorners ? 4 : 8;

        if (!onlyCorners) {
            // up
            for (int i = 0; i < target.length(); i++) {
                String s = String.valueOf(target.charAt(i));

                if (col - i < 0) {
                    result -= 1;
                    break;
                }

                String gridVal = grid.get(col - i).get(row);

                if (!s.equals(gridVal)) {
                    result -= 1;
                    break;
                }
            }

            // down
            for (int i = 0; i < target.length(); i++) {
                String s = String.valueOf(target.charAt(i));

                if (col + i >= numCols) {
                    result -= 1;
                    break;
                }

                String gridVal = grid.get(col + i).get(row);

                if (!s.equals(gridVal)) {
                    result -= 1;
                    break;
                }
            }

            // left
            for (int i = 0; i < target.length(); i++) {
                String s = String.valueOf(target.charAt(i));

                if (row - i < 0) {
                    result -= 1;
                    break;
                }

                String gridVal = grid.get(col).get(row - i);

                if (!s.equals(gridVal)) {
                    result -= 1;
                    break;
                }
            }

            // right
            for (int i = 0; i < target.length(); i++) {
                String s = String.valueOf(target.charAt(i));

                if (row + i >= numRows) {
                    result -= 1;
                    break;
                }

                String gridVal = grid.get(col).get(row + i);

                if (!s.equals(gridVal)) {
                    result -= 1;
                    break;
                }
            }
        }

        // up-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col - i < 0 || row + i >= numRows) {
                result -= 1;
                break;
            }

            String gridVal = grid.get(col - i).get(row + i);

            if (!s.equals(gridVal)) {
                result -= 1;
                break;
            }
        }

        // up-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col - i < 0 || row - i < 0) {
                result -= 1;
                break;
            }

            String gridVal = grid.get(col - i).get(row - i);

            if (!s.equals(gridVal)) {
                result -= 1;
                break;
            }
        }

        // down-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col + i >= numCols || row + i >= numRows) {
                result -= 1;
                break;
            }

            String gridVal = grid.get(col + i).get(row + i);

            if (!s.equals(gridVal)) {
                result -= 1;
                break;
            }
        }

        // down-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col + i >= numCols || row - i < 0) {
                result -= 1;
                break;
            }

            String gridVal = grid.get(col + i).get(row - i);

            if (!s.equals(gridVal)) {
                result -= 1;
                break;
            }
        }

        return result;
    }

    /**
     * Keeps track of the corners where target is found
     * 0: top-left
     * 1: top-right
     * 2: bottom-right
     * 3: bottom-left
     * 
     * @param row    Row position
     * @param col    Column position
     * @param target Target string
     * @return
     */
    public HashMap<Integer, Integer> getMasMapping(int row, int col, String target) {
        HashMap<Integer, Integer> mapping = new HashMap<>();

        mapping.put(0, 1);
        mapping.put(1, 1);
        mapping.put(2, 1);
        mapping.put(3, 1);

        // up-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col - i < 0 || row - i < 0) {
                mapping.put(0, 0);
                break;
            }

            String gridVal = grid.get(col - i).get(row - i);

            if (!s.equals(gridVal)) {
                mapping.put(0, 0);
                break;
            }
        }

        // up-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col - i < 0 || row + i >= numRows) {
                mapping.put(1, 0);
                break;
            }

            String gridVal = grid.get(col - i).get(row + i);

            if (!s.equals(gridVal)) {
                mapping.put(1, 0);
                break;
            }
        }

        // down-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col + i >= numCols || row + i >= numRows) {
                mapping.put(2, 0);
                break;
            }

            String gridVal = grid.get(col + i).get(row + i);

            if (!s.equals(gridVal)) {
                mapping.put(2, 0);
                break;
            }
        }

        // down-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (col + i >= numCols || row - i < 0) {
                mapping.put(3, 0);
                break;
            }

            String gridVal = grid.get(col + i).get(row - i);

            if (!s.equals(gridVal)) {
                mapping.put(3, 0);
                break;
            }
        }

        return mapping;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(String.format("%d x %d\n", numRows, numCols));
        sb.append("\n");

        if (numRows <= 25 && numCols <= 25) {
            for (int i = 0; i < grid.size(); i++) {
                for (int j = 0; j < grid.get(i).size(); j++) {
                    sb.append(grid.get(i).get(j) + " ");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
