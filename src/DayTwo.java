import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DayTwo {
    ArrayList<ArrayList<Integer>> reports;

    public static void main(String[] args) {
        DayTwo d = new DayTwo();

        d.readInput("inputs/day2.txt");

        int partOne = d.PartOne();
        int partTwo = d.PartTwo();

        System.out.println(String.format("Part One: %d\nPart Two: %d\n", partOne, partTwo));
    }

    public DayTwo() {
        reports = new ArrayList<ArrayList<Integer>>();
    }

    public int PartOne() {
        // verify
        int verified = 0;

        for (ArrayList<Integer> report : reports) {
            if (isSafe(report)) {
                verified += 1;
            }
        }

        return verified;
    }

    public int PartTwo() {
        int verified = 0;

        for (ArrayList<Integer> report : reports) {
            if (isSafe(report)) {
                verified += 1;
                continue;
            }

            // try removing a single label at a time
            for (int i = 0; i < report.size(); i++) {
                ArrayList<Integer> dampened = new ArrayList<>(report);
                dampened.remove(i);

                if (isSafe(dampened)) {
                    verified += 1;
                    break;
                }
            }
        }

        return verified;
    }

    public boolean isSafe(ArrayList<Integer> report) {
        if (!isSorted(report)) {
            return false;
        }

        for (int i = 1; i < report.size(); i++) {
            int left = report.get(i - 1);
            int right = report.get(i);

            // check diff
            if (Math.abs(left - right) < 1 || Math.abs(left - right) > 3) {
                return false;
            }
        }

        return true;
    }

    public boolean isSorted(ArrayList<Integer> input) {
        // check both descending and ascending -> NO EQUALITY ALLOWED
        boolean failed = false;

        for (int i = 1; i < input.size(); i++) {
            if (input.get(i - 1) <= input.get(i)) {
                failed = true;
                break;
            }
        }

        for (int i = 1; i < input.size(); i++) {
            if (input.get(i - 1) >= input.get(i)) {
                if (failed) {
                    return false;
                }
            }
        }

        return true;
    }

    public void readInput(String fp) {
        try {
            Scanner in = new Scanner(new File(fp));

            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(" ");
                ArrayList<Integer> report = Arrays.stream(line)
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new));

                reports.add(report);
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }
    }
}
