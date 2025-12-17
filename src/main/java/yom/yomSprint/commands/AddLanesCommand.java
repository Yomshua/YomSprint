package yom.yomSprint.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yom.yomSprint.commands.managers.TrackSubCommands;
import yom.yomSprint.configurations.TracksConfiguration;
import yom.yomSprint.YomSprint;

public class AddLanesCommand extends TrackSubCommands {
    public AddLanesCommand(YomSprint plugin) {
        super("track", "addlane", "sprint.addlanes",plugin);
    }


    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args, Player player) {
        if (args.length != 3) return;

        TracksConfiguration tracksConfiguation = plugin.getTracksConfiguration();
        FileConfiguration config = tracksConfiguation.getConfig();
        String trackName = args[2];

        if (!config.getConfigurationSection("tracks").contains(trackName)) {
            player.sendMessage(ChatColor.RED + "Pista " + trackName + " não existe!");
            return;
        }

        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + trackName);
        meta.addEnchant(Enchantment.LUCK,0,true);
        stick.setItemMeta(meta);

        player.getInventory().addItem(stick);

//
//        final String PATH = "tracks." + args[2];
//        List<Integer> LANE_NUMBERS = new ArrayList<>();
//        if (!config.getConfigurationSection(PATH).contains("lanes")) {
//            config.getConfigurationSection(PATH).createSection("lanes");
//            config.set(PATH + ".lanes.1", player.getLocation());
//            tracksConfiguation.saveConfig();
//            player.sendMessage(ChatColor.GREEN + "Raia 1 adiciona com sucesso na pista " + trackName + "!");
//            return;
//        }
//
//        //Pega as raias da pista
//        Set<String> lanes = config.getConfigurationSection(PATH + ".lanes").getKeys(false);
//
//        for(String lane : lanes){
//            if(!NumberUtils.isNumber(lane)) return;
//            LANE_NUMBERS.add(Integer.parseInt(lane));
//        }
//
//        int maxPlayers = config.getInt(PATH + ".max_players");
//        if (LANE_NUMBERS.size() >= maxPlayers) {
//            player.sendMessage(ChatColor.RED + "Você não pode adicionar mais raias do que o número máximo de players");
//            return;
//        }
//
//        String value = String.valueOf(LANE_NUMBERS.size() + 1);
//        config.set("tracks." + trackName + ".lanes." + value, player.getLocation());
//        tracksConfiguation.saveConfig();
//
//        player.sendMessage(ChatColor.GREEN + "Raia " + value + " adiciona com sucesso na pista " + trackName + "!");
    }
}
