package org.hotal.deathback;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Deathback extends JavaPlugin implements Listener {

    private Map<UUID,Location> deathLocations;

    @Override
    public void onEnable() {
        deathLocations = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
     }
    @Override
    public void onDisable() {
     }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location deathLocation = player.getLocation();
        deathLocations.put(player.getUniqueId(), deathLocation);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("dback")) {
                if (player.hasPermission("dback.teleport")) {
                    Location deathLocation = deathLocations.get(player.getUniqueId());
                    if (deathLocation != null) {
                        player.teleport(deathLocation);
                        player.sendMessage("§aあなたは最後に死んだ場所にテレポートしました。");
                    } else {
                        player.sendMessage("§cあなたの最後に死んだ場所は見つかりませんでした。");
                    }
                } else {
                    player.sendMessage("§7このコマンドを使用する権限がありません");
                }
                return true;
            }
        }
        return false;
    }
}
