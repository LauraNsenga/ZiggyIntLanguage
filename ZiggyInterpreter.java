package INTPD;
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
        // Placeholder for handling a block of code
        // You can extend this to handle expressions, calculations, etc.
    }

    public void end() {
        // Placeholder for handling the end of a block
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
            case "repeat":
                repeat(args, lines);
                break;
            default:
                System.out.println("Error: Unknown instruction '" + instr + "' at line " + (pc + 1));
                System.exit(1);
        }

        pc++;
    }
}

private void repeat(String args, List<String> lines) {
    // Extract repeat parameters
    String[] repeatParams = args.split(" ");
    if (repeatParams.length != 1 || !repeatParams[0].equals("end")) {
        System.out.println("Error: Malformed 'repeat' block at line " + (pc + 1));
        System.exit(1);
    }

    // Retrieve the number of repetitions from the stack
    if (!stack.isEmpty()) {
        String repeatCountStr = stack.remove(stack.size() - 1);
        int repeatCount = Integer.parseInt(repeatCountStr);

        // Repeat the block of code within the repeat block
        for (int i = 0; i < repeatCount; i++) {
            interpret(lines.subList(pc + 1, lines.size()));  // Start interpreting from the next line until the end
        }

        // Skip the lines inside the repeat block when interpreting resumes
        pc = lines.size();
    } else {
        System.out.println("Error: Missing repeat count on the stack at line " + (pc + 1));
        System.exit(1);
    }
}

    public static void main(String[] args) {
        /*if (args.length < 1) {
            System.out.println("Usage: java ZiggyInterpreter <filename1> <filename2> ...");
            System.exit(1);
        }
*/
    	
       // for (String filename : args) {
    	//System.out.println("here");
    
    	//System.out.println("here");
    	String filename = "/Users/lauransenga/eclipse-workspace/InterpretedLanguage/multiply.txt";
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
            //}
        }
    }
}
