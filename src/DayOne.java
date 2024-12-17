import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DayOne {
    PriorityQueue<Integer> leftList;
    PriorityQueue<Integer> rightList;
    HashMap<Integer, Integer> counts;

    public DayOne() {
        leftList = new PriorityQueue<>();
        rightList = new PriorityQueue<>();
        counts = new HashMap<>();
    }

    public static void main(String[] args) {
        DayOne dayOne = new DayOne();

        String filename = "inputs/day1.txt";
        dayOne.readInput(filename);

        int partOneAnswer = dayOne.partOne();
        int partTwoAnswer = dayOne.partTwo();

        System.out.println(String.format("Part one: %d\nPart two: %d\n", partOneAnswer, partTwoAnswer));
    }

    public int partOne() {
        //list lengths are the same
        int result = 0;
        int initSize = leftList.size();

        ArrayList<Integer> leftVals = new ArrayList<>();

        for (int i = 0; i < initSize; i++) {
            int left = leftList.poll();
            int right = rightList.poll();

            result += Math.abs(left - right);
            leftVals.add(left);
        }

        leftList.addAll(leftVals);

        return result;
    }

    public int partTwo() {
        int size = leftList.size();
        int result = 0;

        for (int i = 0; i < size; i++) {
            int left = leftList.poll();
            int rightCount = counts.containsKey(left) ? counts.get(left) : 0;

            result += left * rightCount;
        }

        return result;
    }

    public void readInput(String fp) {
        try {
            Scanner in = new Scanner(new File(fp));

            while (in.hasNextLine()) {
                String[] vals = in.nextLine().split("\\s+");

                int left = Integer.parseInt(vals[0]);
                int right = Integer.parseInt(vals[1]);
                
                leftList.add(left);
                rightList.add(right);

                if (counts.containsKey(right)) {
                    counts.put(right, counts.get(right) + 1);
                } else {
                    counts.put(right, 1);
                }
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File with name \"%s\" not found.", fp));
        }
    }
}