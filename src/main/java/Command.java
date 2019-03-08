import java.lang.reflect.Array;
import java.util.ArrayList;

/*
 **  Handles all command-related code
 **  e.g. storing new commands
 */
public class Command {

    // Constructor
    private ArrayList<String> commands;

    // Initialize command list and add default commands: help and pet
    public Command() {
        commands = new ArrayList<String>();
        commands.add(".help");
        commands.add(".pet");
    }

    // Do something based on the command passed in
    public void executeCommand(String command){
        switch (command){
            case ".help": printCommands(); break;
            case ".pet": break;

        }
        // something else
    }

    // Returns a string of all commands, formatted to each print on new lines
    public String printCommands(){
        String result = "";
        for (String s : commands){
            result = result.concat("\n "+s);
        }
        return result;
    }

    // Getters and Setters
    public ArrayList getCommands(){
        return commands;
    }

    public void addCommand(String newCommand){
        commands.add(newCommand);
    }

    // Return an explanation of the requested command
    public String explainCommand(String command){
        return ""; //TODO
    }
}
