import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

public class DayThree {
    ArrayList<String> validMult;
    boolean enabled;

    public static void main(String[] args) {
        DayThree d = new DayThree();

        d.readInput("inputs/day3.txt");

        int partOne = d.PartOne();
        int partTwo = d.PartTwo("inputs/day3.txt");

        System.out.println(String.format("Part One: %d\nPart Two: %d", partOne, partTwo));
    }

    public DayThree() {
        validMult = new ArrayList<>();
        enabled = true;
    }

    public int PartOne() {
        return calculate();
    }

    public int PartTwo(String fp) {
        validMult.clear();

        // reread the input, using do and dont
        try {
            Scanner in = new Scanner(new File(fp));

            while (in.hasNextLine()) {
                String line = in.nextLine();

                Pattern pattern = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");
                Pattern offPattern = Pattern.compile("don't\\(\\)");
                Pattern onPattern = Pattern.compile("do\\(\\)");

                Matcher matcher = pattern.matcher(line);
                Matcher offMatcher = offPattern.matcher(line);
                Matcher onMatcher = onPattern.matcher(line);

                int idx = 0;

                while (idx < line.length()) {
                    if (offMatcher.find(idx) && offMatcher.start() == idx) {
                        enabled = false;
                    }

                    if (onMatcher.find(idx) && onMatcher.start() == idx) {
                        enabled = true;
                    }

                    if (matcher.find(idx) && matcher.start() == idx && enabled) {
                        validMult.add(matcher.group());
                    }

                    idx += 1;
                }
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }

        // perform operations
        return calculate();
    }

    public int calculate() {
        int result = 0;

        for (String instr : validMult) {
            // extract int,int
            Pattern pattern = Pattern.compile("([0-9]+),([0-9]+)");
            Matcher matcher = pattern.matcher(instr);

            if (matcher.find()) {
                String[] values = matcher.group().split(",");
                result += Integer.parseInt(values[0]) * Integer.parseInt(values[1]);
            }
        }

        return result;
    }

    public void readInput(String fp) {
        try {
            Scanner in = new Scanner(new File(fp));

            while (in.hasNextLine()) {
                String line = in.nextLine();

                Pattern pattern = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String match = matcher.group();
                    validMult.add(match);
                }
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }
    }
}
