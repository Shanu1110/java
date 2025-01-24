import java.util.*;

public class LoopMaster {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        List<String> commandsList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            commandsList.add(sc.nextLine().trim());
        }
        processCommands(commandsList);
    }

    private static void processCommands(List<String> commandsList) {
        Stack<Integer> loopCounts = new Stack<>();
        Stack<Integer> currIter = new Stack<>();
        StringBuilder output = new StringBuilder();
        int index = 0;

        while (index < commandsList.size()) {
            String command = commandsList.get(index);
            if (command.startsWith("for")) {
                int times = Integer.parseInt(command.split(" ")[1]);
                loopCounts.push(times);
                currIter.push(0);
            } else if (command.equals("do")) {
                // No operation for "do"
            } else if (command.equals("done")) {
                int current = currIter.pop() + 1;
                int max = loopCounts.pop();
                if (current < max) {
                    loopCounts.push(max);
                    currIter.push(current);
                    index = findLoopStart(commandsList, index);
                    continue;
                }
            } else if (command.startsWith("break")) {
                int breakAt = Integer.parseInt(command.split(" ")[1]);
                if (currIter.peek() + 1 == breakAt) {
                    loopCounts.pop();
                    currIter.pop();
                    index = findLoopEnd(commandsList, index);
                }
            } else if (command.startsWith("continue")) {
                int continueAt = Integer.parseInt(command.split(" ")[1]);
                if (currIter.peek() + 1 == continueAt) {
                    int max = loopCounts.peek();
                    int current = currIter.pop() + 1;
                    if (current < max) {
                        currIter.push(current);
                        index = findLoopStart(commandsList, index);
                    }
                    continue;
                }
            } else if (command.startsWith("print")) {
                String message = command.substring(command.indexOf("\"") + 1, command.lastIndexOf("\""));
                output.append(message).append("\n");
            }
            index++;
        }
        System.out.print(output.toString());
    }

    private static int findLoopStart(List<String> commandsList, int ci) {
        int nestedLoops = 0;
        for (int i = ci - 1; i >= 0; i--) {
            if (commandsList.get(i).equals("done")) {
                nestedLoops++;
            } else if (commandsList.get(i).equals("do")) {
                if (nestedLoops == 0) {
                    return i;
                }
                nestedLoops--;
            }
        }
        return 0;
    }

    private static int findLoopEnd(List<String> commandsList, int ci) {
        int nestedLoops = 0;
        for (int i = ci + 1; i < commandsList.size(); i++) {
            if (commandsList.get(i).equals("do")) {
                nestedLoops++;
            } else if (commandsList.get(i).equals("done")) {
                if (nestedLoops == 0) {
                    return i;
                }
                nestedLoops--;
            }
        }
        return commandsList.size();
    }
}