package INTPD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ZiggyInterpreter {
    private Stack<Object> ziggies;
    private int pc;

    public ZiggyInterpreter() {
        ziggies = new Stack<>();
        pc = 0;
    }

    public void interpret(List<String> lines) {
        while (pc >= 0 && pc < lines.size()) {
            String[] parts = lines.get(pc).split(" ", 2);
            String instr = parts[0];
            String args = (parts.length > 1) ? parts[1] : "";

            switch (instr) {
                case "zig":
                    if (args.length() > 0)
                        System.out.println(args);
                    else
                        System.out.println(ziggies.pop());
                    break;

                case "retrieve":
                    Scanner scanner = new Scanner(System.in);
                    if (scanner.hasNextLine()) {
                        String input = scanner.nextLine();
                        ziggies.push(input);
                    }
                    break;

                case "multiply":
                    if (ziggies.size() < 2) {
                        System.out.println("Error: Not enough operands");
                      
                    }
                    int num1 = Integer.parseInt((String) ziggies.pop());
                    int num2 = Integer.parseInt((String) ziggies.pop());
                    int result = num1 * num2;
                    ziggies.push(Integer.toString(result));
                    break;

                case "backword":
                    String userInput = (String) ziggies.pop();
                    StringBuilder userInput2 = new StringBuilder(userInput);
                    userInput2.reverse();
                    System.out.println(userInput2);
                    break;

                case "repeat":
                    int times = Integer.parseInt(ziggies.pop().toString());
                    String character = ziggies.pop().toString();
                    StringBuilder repeatedZig = new StringBuilder();
                    for (int i = 0; i < times; i++) {
                        repeatedZig.append(character);
                    }
                    System.out.println(repeatedZig);
                    ziggies.push(repeatedZig.toString());
                    break;

                default:
                    System.out.println("Error: Unknown instruction '" + instr + "' at line " + (pc + 1));
                    System.exit(1);
            }

            pc++;
        }
    }

    public static void main(String[] args) 
    {
        String filename = "/Users/lauransenga/eclipse-workspace/InterpretedLanguage/repeater.txt";
        try {
            List<String> ziggyCode = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    ziggyCode.add(line);
                }
            }

            ZiggyInterpreter interpreter = new ZiggyInterpreter();
            interpreter.interpret(ziggyCode);

        } catch (IOException e) {
            System.out.println("Error reading file '" + filename + "': " + e.getMessage());
            System.exit(1);
        }
    }
}
