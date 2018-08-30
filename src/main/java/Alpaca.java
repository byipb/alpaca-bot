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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Alpaca extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {

        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT);
            String token = "NDg0NjIwODE5NzAwNTE0ODE3.DmkreA.5aZmPjYuzYGh5CfwJUSIauoITSU";
            builder.setToken(token);
            builder.addEventListener(new Alpaca());
            builder.buildAsync();
        }

        catch (LoginException e){
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        }
    }

    private void deleteMessage(MessageReceivedEvent event) throws InterruptedException {

        try {
            TimeUnit.SECONDS.sleep(1);
            event.getMessage().delete();
            event.getMessage().delete();
        }

        catch (InterruptedException e){
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

        // List of roles
        //Role snowflake = roles.get(3);
        Role cloud = roles.get(4);

        if (event.getAuthor().isBot()){
            return;
        }

        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );

        if (event.getMessage().getContentRaw().equals("!ping")){
            //remember to call queue()!
            //otherwise our message will never be sent
            event.getChannel().sendMessage("Pamf!").queue();
        }

        // Give a user the Cloud role
        if (event.getMessage().getContentRaw().equals(".iam Cloud")){

            // Alpaca bot gives Role
            guildController.addSingleRoleToMember(member, cloud).queue();
            event.getChannel().sendMessage("You're a cloud now, "+ event.getMember().getUser().getName() + "!").queue();
        }

        // TODO: clean up ice-prison message spam by deleting member message and bot message

    }
}
