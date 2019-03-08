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

    public static void main(String[] args) throws IOException {

        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT);
            String token = new String (Files.readAllBytes(Paths.get("./src/main/resources/token.txt")));
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

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Setup
        Guild guild = event.getGuild();
        GuildController guildController = new GuildController(guild);
        TextChannel textChannel = event.getTextChannel();
        Member member = event.getMember();
        List<Role> roles = guild.getRoles();
        boolean areYouDumb = false;

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

        // Alpaca bot will reach out to help list all the commands!
        if (event.getMessage().getContentRaw().equalsIgnoreCase(".help")){
            event.getChannel().sendMessage("Here are the commands pamf: \n .help \n .pet" ).queue();
        }

        // Pet Alpaca bot to have him reply "Pamf!"
        if (event.getMessage().getContentRaw().equalsIgnoreCase(".pet")){
            //remember to call queue()!
            //otherwise our message will never be sent
            event.getChannel().sendMessage("Pamf!").queue();
        }

        // Give a user the Cloud role in ice prison
        if (event.getTextChannel().getId().equals("484645441749647361")) {
            if (event.getMessage().getContentRaw().equals(".iam cloud")) {
                // Delete messages to keep ice-prison clean, we aren't a dirty prison!
                event.getMessage().delete().queue();
                // Alpaca bot gives Role
                guildController.addSingleRoleToMember(member, cloud).queue();
                // Give error message when typed an incorrect command
            } else if(event.getMessage().getContentRaw().contains(".iam") && !event.getMessage().getContentRaw().equals(".iam cloud")){
                try {
                    TimeUnit.SECONDS.sleep(2);
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage("Hah you fool! You should check <#423123478028484609> again!").queue();
                    //TimeUnit.SECONDS.sleep(60);
                    event.getMessage().delete().queue();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }else { // You're special if you try to type .iam cloud in a different room
            if (event.getMessage().getContentRaw().equals(".iam cloud")) {
                event.getChannel().sendMessage("You are a special cloud, aren't you?").queue();
            }
        }

        // Alpaca bot is the cutest in #test
        if (event.getTextChannel().getId().equals("484628587392008203")) {
            if (event.getAuthor().getId().equals("90837629502771200")) {
                event.getChannel().sendMessage("GALAXY!").queue();
        }
            if (event.getAuthor().getId().equals("139247155582992384")) {
                event.getChannel().sendMessage("WORLD!!!").queue();
            }
        }
    }
}
