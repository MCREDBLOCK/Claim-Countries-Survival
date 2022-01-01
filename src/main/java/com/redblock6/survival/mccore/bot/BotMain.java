package com.redblock6.survival.mccore.bot;

import com.redblock6.survival.Main;
import com.redblock6.survival.countries.Countries;
import com.redblock6.survival.countries.CountriesReason;
import com.redblock6.survival.mccore.achievements.AchDatabase;
import com.redblock6.survival.mccore.achievements.HAchType;
import com.redblock6.survival.mccore.events.ChatEvent;
import com.redblock6.survival.mccore.events.JoinLeaveEvent;
import com.redblock6.survival.mccore.functions.VoteType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.redblock6.survival.Main.getBot;

public class BotMain extends ListenerAdapter {
    public Main pl;
    public JDA bot;
    public static HashMap<String, String> connectQueued = new HashMap<>();
    public static ArrayList<Player> connect = new ArrayList<>();
    public static ArrayList<String> connectString = new ArrayList<>();
    public static HashMap<String, String> connectId = new HashMap<>();
    public static HashMap<String, User> connectUser = new HashMap<>();
    public static MessageChannel eventsChannel;

    public BotMain(Main main) {
        this.pl = main;
        initializeBot();
        bot.addEventListener(this);
        eventsChannel = bot.getTextChannelById("926259113564983386");
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        User user = e.getAuthor();
        PrivateChannel channel = e.getChannel();
        String message = e.getMessage().getContentRaw();
        if (!message.contains("YES") && !message.contains("NO")) {
            connectQueued.put(e.getMessage().getContentRaw(), user.getAsTag());
            connect.add(Bukkit.getPlayer(channel.getUser().getName()));
            connectString.add(e.getMessage().getContentRaw());
            connectId.put(e.getMessage().getContentRaw(), user.getId());
            connectUser.put(e.getMessage().getContentRaw(), user);

            boolean inServer = false;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals(message)) {
                    inServer = true;
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                    sendMessage(user, ":exclamation: You're on the RedSMP. Open your minecraft window to finish setting up your account.");
                    p.sendTitle(Main.translate("&fAre you &c" + connectQueued.get(p.getName()) + " &fon discord?"), Main.translate("&fType &2&lYES &for &4&lNO &fin the chat!"), 10, 1000000000, 10);
                    JoinLeaveEvent.notConnected.remove(p);
                }
            }
            if (!inServer) {
                sendMessage(user, ":exclamation: Please join the RedSMP to finish connecting your account.");
            }
        }
    }

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        if (e.getName().equals("connect")) {
            // e.reply(e.getOption("content").getAsString()).queue(); // reply immediately
            // e.deferReply().queue();
            e.reply("<:checkdms:863947573433466900> You should have just received a message from the RedSMP bot. Please check your DM's.").queue();
            sendMessage(e.getUser(), ":question: What is your Minecraft Username? (Case Sensitive)");
        }
        /*
        else if (e.getName().equals("setupchannel")) {
            // e.reply(e.getOption("content").getAsString()).queue(); // reply immediately
            // e.deferReply().queue();
            if (e.getUser().getName().equals("Redblock6") || e.getUser().getName().equals("Snake_Master66")) {
                sendMessage(e.getUser(), "<:success:865587082650845195> Channel " + e.getMessageChannel().getName() + " is now the event channel!");
            }
            eventsChannel = e.getMessageChannel();
        }

         */
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String s = e.getMessage().toString();
        Message m = e.getMessage();
        User u = e.getAuthor();
        MessageChannel channel = e.getChannel();

        if (channel == bot.getTextChannelById("926259113564983386")) {
            for (Player lp : Bukkit.getOnlinePlayers()) {
                if (lp.getName().contains(u.getName())) {
                    if (!new AchDatabase(lp).getHubAch().contains(HAchType.Link_Your_Account_With_Core)) {
                        if (s.equalsIgnoreCase("yes")) {
                            ChatEvent.addAQueue(lp);
                        } else {
                            ChatEvent.addDQueue(lp);
                        }
                        String tag = u.getAsTag();
                        lp.playSound(lp.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
                        m.reply(":exclamation: Please open your minecraft window to continue voting").queue();
                        lp.sendTitle(Main.translate("&fAre you &c" + tag + " &fon discord?"), Main.translate("&fType &2&lYES &for &4&lNO &fin the chat!"), 10, 1000000000, 10);
                        ChatEvent.addConfirm(lp);
                    }
                }
            }
        }
    }

    public void sendVotingAnnouncement(VoteType type, Player start, Player to) {
        MessageChannel channel = eventsChannel;
        if (type.equals(VoteType.TP)) {
            channel.sendMessage(":information_source: " + start.getName() + " wants to tp to " + to.getName() + " on the RedSMP! Type **YES** or **NO** in the chat to vote!").queue();
        }
    }

    public void sendVotingStatusAnnouncement(VoteType type, Player start) {
        MessageChannel channel = eventsChannel;
        if (type.equals(VoteType.Agree)) {
            channel.sendMessage(":white_check_mark: " + start.getName() + " agreed to the vote!").queue();
        } else if (type.equals(VoteType.Deny)) {
            channel.sendMessage(":x: " + start.getName() + " does not agree to the vote!").queue();
        }
    }

    public void sendNationsAnnouncement(String owner, String name, CountriesReason reason, String... args) {
        MessageChannel channel = eventsChannel;
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(name);
        if (reason.equals(CountriesReason.CREATED)) {
            eb.setColor(Color.RED);
            eb.setDescription(owner + " created a new nation called the " + name);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
        } else if (reason.equals(CountriesReason.DISBANDED)) {
            eb.setColor(Color.RED);
            eb.setDescription(owner + " disbanded the " + name);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        } else if (reason.equals(CountriesReason.MEMBER_ADDED)) {
            eb.setColor(Color.RED);
            eb.setDescription(args[0] + " joined the " + name);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        } else if (reason.equals(CountriesReason.MEMBER_KICKED)) {
            eb.setColor(Color.RED);
            eb.setDescription(args[0] + " was kicked from the " + name);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        } else if (reason.equals(CountriesReason.MEMBER_PROMOTED)) {
            eb.setColor(Color.RED);
            eb.setDescription(args[0] + " was promoted from " + args[1] + " to " + args[3] + " in the " + name);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        } else if (reason.equals(CountriesReason.CHANGED_NAME)) {
            eb.setTitle(args[0] + " -> " + args[1]);
            eb.setColor(Color.RED);
            eb.setDescription("The name of " + args[0] + " was changed to " + args[1]);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        } else if (reason.equals(CountriesReason.CHANGED_DESC)) {
            eb.setColor(Color.RED);
            eb.setDescription("The desc of " + name + " was changed to " + args[0]);
            eb.addField("Name", name, false);
            eb.addField("Leader", owner, false);
            eb.addField("Members", Arrays.toString(Countries.getTruce(name).getMembers().toArray()), false);
        }
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public void initializeBot() {
        JDABuilder builder = JDABuilder.createDefault("ODY0MTU3MDM1MTM3OTkwNjU2.YOxW9A.ug2-swMRhMzdCX8lS8eIckhwLFM");
        builder.setActivity(Activity.watching(" over the RedSMP"));
        try {
            bot = builder.build();
            bot.updateCommands()
                    .addCommands(new CommandData("connect", "Connect your minecraft account to your Discord Account")
                            /*,new CommandData("setupchannel", "Only ADMIN+ can use this command.")*/)
                    .queue();
            bot.awaitReady();

        } catch (LoginException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendMessage(User user, String content) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
    }

}
