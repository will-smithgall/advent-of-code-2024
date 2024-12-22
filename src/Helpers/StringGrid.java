package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.print.attribute.HashAttributeSet;

public class StringGrid {
    private ArrayList<ArrayList<String>> grid;
    private int height;
    private int width;

    private HashMap<String, int[]> directions;
    private HashMap<String, int[]> diagonals;

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

        width = grid.size();
        height = grid.get(0).size();

        directions = new HashMap<>();
        diagonals = new HashMap<>();

        directions.put("north", new int[] { -1, 0 });
        directions.put("south", new int[] { 1, 0 });
        directions.put("east", new int[] { 0, 1 });
        directions.put("west", new int[] { 0, -1 });

        diagonals.put("northwest", new int[] { -1, -1 }); // up-left
        diagonals.put("northeast", new int[] { -1, 1 }); // up-right
        diagonals.put("southwest", new int[] { 1, -1 }); // down-left
        diagonals.put("southeast", new int[] { 1, 1 }); // down-right
    }

    public int getRows() {
        return height;
    }

    public int getCols() {
        return width;
    }

    public HashMap<String, int[]> getDirections() {
        return directions;
    }

    public HashMap<String, int[]> getDiagonals() {
        return diagonals;
    }

    public String getVal(int[] pos) {
        return grid.get(pos[0]).get(pos[1]);
    }

    public void setVal(int[] pos, String val) {
        grid.get(pos[0]).set(pos[1], val);
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < width && col >= 0 && col < height;
    }

    // movement

    /**
     * Move 1 step towards direction "facing"
     * 
     * @param pos           int[] in the form (row, col)
     * @param directionType String value of direction being faced
     * @return int[] of the new position -> (row, col)
     */
    public int[] step(int[] pos, String facing) {
        int[] dir = directions.get(facing);

        int row = pos[0];
        int col = pos[1];

        row += dir[0];
        col += dir[1];

        if (!inBounds(row, col)) {
            return null;
        }

        return new int[] { row, col };
    }

    /**
     * Find location of token, or first instance of token if multiple
     * 
     * @param token Singular string token to search for
     * @return Return int[] position as (row, col), null if no token
     */
    public int[] find(String token) {
        int[] pos = new int[2];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid.get(i).get(j).equals(token)) {
                    pos[0] = i;
                    pos[1] = j;

                    return pos;
                }
            }
        }

        return null;
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

                if (!inBounds(row, col - i)) {
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

                if (!inBounds(row, col + i)) {
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

                if (!inBounds(row - i, col)) {
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

                if (!inBounds(row + i, col)) {
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

            if (!inBounds(row + i, col - i)) {
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

            if (!inBounds(row - i, col - i)) {
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

            if (!inBounds(row + i, col + i)) {
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

            if (!inBounds(row - i, col + i)) {
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
     * 0b1000: top-left
     * 0b0100: top-right
     * 0b0010: bottom-right
     * 0b0001: bottom-left
     * 
     * @param row    Row position
     * @param col    Column position
     * @param target Target string
     * @return
     */
    public int getMasBitmap(int row, int col, String target) {
        int result = 0b1111;

        // up-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (!inBounds(row - i, col - i)) {
                result &= 0b0111;
                break;
            }

            String gridVal = grid.get(col - i).get(row - i);

            if (!s.equals(gridVal)) {
                result &= 0b0111;
                break;
            }
        }

        // up-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (!inBounds(row + i, col - i)) {
                result &= 0b1011;
                break;
            }

            String gridVal = grid.get(col - i).get(row + i);

            if (!s.equals(gridVal)) {
                result &= 0b1011;
                break;
            }
        }

        // down-right
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (!inBounds(row + i, col + i)) {
                result &= 0b1101;
                break;
            }

            String gridVal = grid.get(col + i).get(row + i);

            if (!s.equals(gridVal)) {
                result &= 0b1101;
                break;
            }
        }

        // down-left
        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));

            if (!inBounds(row - i, col + i)) {
                result &= 0b1110;
                break;
            }

            String gridVal = grid.get(col + i).get(row - i);

            if (!s.equals(gridVal)) {
                result &= 0b1110;
                break;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(String.format("%d x %d\n", height, width));
        sb.append("\n");

        // if (height <= 25 && width <= 25) {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                sb.append(grid.get(i).get(j) + " ");
            }
            sb.append("\n");
        }
        // }

        return sb.toString();
    }
}
