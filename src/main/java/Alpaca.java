import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

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

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
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
            event.getChannel().sendMessage("Pong!").queue();
        }

    }
}
