import Helpers.StringGrid;

import java.util.Arrays;
import java.util.HashMap;

public class DaySix {
    StringGrid grid;
    final String PATH = "inputs/day6.txt";

    public DaySix() {
        grid = new StringGrid(PATH);
    }

    public static void main(String[] args) {
        DaySix d = new DaySix();

        int p1 = d.partOne();
        int p2 = d.partTwo();

        System.out.println(String.format("Part One: %d\nPart Two: %d", p1, p2));
    }

    public int partOne() {
        int result = 0;

        HashMap<String, String> nextDir = new HashMap<>();

        nextDir.put("north", "east");
        nextDir.put("east", "south");
        nextDir.put("south", "west");
        nextDir.put("west", "north");

        int[] currPos = grid.find("^");
        String facing = "north";

        while (true) {
            int[] nextPos = grid.step(currPos, facing);

            if (nextPos == null) {
                grid.setVal(currPos, "X");
                result += 1;
                break;
            }

            if (!grid.getVal(currPos).equals("X")) {
                grid.setVal(currPos, "X");
                result += 1;
            }

            if (grid.getVal(nextPos).equals("#")) {
                facing = nextDir.get(facing);
            } else {
                currPos = nextPos;
            }
        }

        return result;
    }

    public int partTwo() {
        grid = new StringGrid(PATH);

        int result = 0;

        HashMap<String, String> nextDir = new HashMap<>();

        nextDir.put("north", "east");
        nextDir.put("east", "south");
        nextDir.put("south", "west");
        nextDir.put("west", "north");

        int[] currPos = grid.find("^");
        int[] start = grid.find("^");
        String facing = "north";
        while (true) {
            int[] nextPos = grid.step(currPos, facing);

            if (nextPos == null) {
                grid.setVal(currPos, "X");
                break;
            }

            if (!grid.getVal(currPos).equals("X")) {
                grid.setVal(currPos, "X");
            }

            if (grid.getVal(currPos).equals("X") && !Arrays.equals(start, nextPos)) {
                // potential for a man-made loop
                System.out.println(grid.toString());
                String newFace = nextDir.get(facing);
                int[] scanPos = currPos;

                while (true) {
                    int[] testPos = grid.step(scanPos, newFace);

                    if (testPos == null) {
                        break;
                    }

                    if (grid.getVal(testPos).equals("#") && grid.getVal(scanPos).equals("X")
                            && !grid.getVal(grid.step(currPos, newFace)).equals("#")) {
                        grid.setVal(nextPos, "O");
                        System.out.println(grid.toString());
                        grid.setVal(nextPos, "X");

                        result += 1;
                        break;
                    }

                    scanPos = testPos;
                }
            }

            if (grid.getVal(nextPos).equals("#")) {
                facing = nextDir.get(facing);
            } else {
                currPos = nextPos;
            }
        }

        return result;
    }
}
