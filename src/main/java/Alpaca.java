import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Alpaca extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, FileNotFoundException, IOException {

        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT);
            String token = new String (Files.readAllBytes(Paths.get("D:\\Users\\Dandy\\IdeaProjects\\alpaca-bot\\src\\main\\resources\\token.txt")));

//            while ((token != null){
//                System.out.println(token);
//            }
            System.out.println(token);
            builder.setToken(token);
            builder.addEventListener(new Alpaca());
            builder.buildAsync();
        }

        catch (LoginException | FileNotFoundException e){
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        }
    }

//    private void deleteMessage(MessageReceivedEvent event) throws InterruptedException {
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//            event.getMessage().delete();
//            event.getMessage().delete();
//        }
//
//        catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Setup
        Guild guild = event.getGuild();
        GuildController guildController = new GuildController(guild);
        TextChannel textChannel = event.getTextChannel();
        Member member = event.getMember();
        List<Role> roles = guild.getRoles();

        // List of roles
        // Role snowflake = roles.get(3);
        Role cloud = roles.get(6);

        // Alpaca bot is not allowed to talk to itself!!!
        if (event.getAuthor().isBot()){
            return;
        }

        // Alpaca bot hears every thing owo
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +

                event.getMessage().getContentDisplay());


        // Pet Alpaca bot to have him reply "Pamf!"
        if (event.getMessage().getContentRaw().equals("!pet")){
            //remember to call queue()!
            //otherwise our message will never be sent
            event.getChannel().sendMessage("Pamf!").queue();
            return;
        }

        // Give a user the Cloud role in ice prison
        if (event.getTextChannel().getId().equals("484645441749647361")) {
            if (event.getMessage().getContentRaw().equals(".iam cloud")) {
                // Delete messages to keep ice-prison clean, we aren't a dirty prison!
                event.getMessage().delete().queue();
                // Alpaca bot gives Role
                guildController.addSingleRoleToMember(member, cloud).queue();
            }
        }else { // You're special if you try to type .iam cloud in a different room
            if (event.getMessage().getContentRaw().equals(".iam cloud")) {
                event.getChannel().sendMessage("You are a special cloud, aren't you?").queue();
            }
        }
    }
}
