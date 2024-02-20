import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZiggyInterpreter {
    private List<String> stack = new ArrayList<>();
    private int pc = 0;

    public void zig(String args) {
        System.out.println(args);
    }

    public void zag(String args) {
        System.out.print(args);
    }

    public String retrieve() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String remove(String args) {
        return args.replaceAll("\\n$", "");
    }

    public String backword(String args) {
        return new StringBuilder(args).reverse().toString();
    }

    public void begin() {
        // Placeholder for handling exceptions
    }

    public void end() {
        // Placeholder for handling exceptions
    }

    public void interpret(List<String> lines) {
        while (pc >= 0 && pc < lines.size()) {
            String[] parts = lines.get(pc).split(" ", 2);
            String instr = parts[0];
            String args = (parts.length > 1) ? parts[1] : "";

            switch (instr) {
                case "zig":
                    zig(args);
                    break;
                case "zag":
                    zag(args);
                    break;
                case "retrieve":
                    stack.add(retrieve());
                    break;
                case "remove":
                    stack.add(remove(args));
                    break;
                case "backword":
                    stack.add(backword(args));
                    break;
                case "begin":
                    begin();
                    break;
                case "end":
                    end();
                    break;
                default:
                    System.out.println("Error: Unknown instruction '" + instr + "' at line " + (pc + 1));
                    System.exit(1);
            }

            pc++;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ZiggyInterpreter <filename>");
            System.exit(1);
        }

        try {
            String filename = args[0];
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
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
