import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DayFive {
    HashMap<Integer, HashSet<Integer>> pageRules;
    ArrayList<ArrayList<Integer>> updateList;
    ArrayList<ArrayList<Integer>> validUpdates;

    public DayFive() {
        pageRules = new HashMap<Integer, HashSet<Integer>>();
        updateList = new ArrayList<ArrayList<Integer>>();
        validUpdates = new ArrayList<ArrayList<Integer>>();
    }

    public static void main(String[] args) {
        DayFive d = new DayFive();

        d.establishRules("inputs/day5.txt");

        int p1 = d.PartOne();
        int p2 = d.PartTwo();

        System.out.println(String.format("Part One: %d\nPart Two: %d", p1, p2));
    }

    public int PartOne() {
        int result = 0;

        for (ArrayList<Integer> update : updateList) {
            boolean valid = validate(update);

            if (valid) {
                validUpdates.add(update);
            }
        }

        for (ArrayList<Integer> update : validUpdates) {
            updateList.remove(update); // for part 2

            int idx = (update.size() / 2);
            result += update.get(idx);
        }

        return result;
    }

    public int PartTwo() {
        int result = 0;
        ArrayList<ArrayList<Integer>> fixed = new ArrayList<ArrayList<Integer>>();

        for (ArrayList<Integer> update : updateList) {
            ArrayList<Integer> adjustedUpdate = new ArrayList<>();

            while (!validate(update)) {
                HashSet<Integer> seenPages = new HashSet<>();
                adjustedUpdate.clear();

                for (int page : update) {
                    HashSet<Integer> rules = pageRules.get(page);

                    if (rules == null) {
                        seenPages.add(page);
                        adjustedUpdate.add(page);
                        continue;
                    }

                    seenPages.add(page);
                    adjustedUpdate.add(page);

                    for (int val : rules) {
                        if (seenPages.contains(val)) {
                            adjustedUpdate.remove(Integer.valueOf(val));
                            adjustedUpdate.add(val); // remove and add to end
                        }
                    }
                }

                update.clear();
                update.addAll(adjustedUpdate);
            }

            fixed.add(adjustedUpdate);
        }

        for (ArrayList<Integer> update : fixed) {
            int idx = (update.size() / 2);
            result += update.get(idx);
        }

        return result;
    }

    public boolean validate(ArrayList<Integer> update) {
        if (update.size() == 0) {
            return false;
        }

        HashSet<Integer> seenPages = new HashSet<>();
        boolean valid = true;

        for (int page : update) {
            HashSet<Integer> rules = pageRules.get(page);

            if (rules == null) {
                seenPages.add(page);
                continue;
            }

            for (int val : rules) {
                if (seenPages.contains(val)) {
                    valid = false;
                    break;
                }
            }

            seenPages.add(page);
        }

        return valid;
    }

    public void establishRules(String fp) {
        try {
            Scanner in = new Scanner(new File(fp));
            String line = in.nextLine();

            while (!line.isEmpty()) {
                String[] vals = line.split("\\|");

                int leftPage = Integer.parseInt(vals[0]);
                int rightPage = Integer.parseInt(vals[1]);
                HashSet<Integer> rule;

                if (pageRules.containsKey(leftPage)) {
                    rule = pageRules.get(leftPage);
                } else {
                    rule = new HashSet<>();
                }

                rule.add(rightPage);
                pageRules.put(leftPage, rule);

                line = in.nextLine();
            }

            while (in.hasNextLine()) {
                String[] vals = in.nextLine().split(",");

                ArrayList<Integer> rule = Arrays.stream(vals)
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new));

                updateList.add(rule);
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }
    }
}