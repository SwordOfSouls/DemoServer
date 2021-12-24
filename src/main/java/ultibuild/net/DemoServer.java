package ultibuild.net;

import net.minecraft.server.v1_12_R1.PacketPlayOutRespawn;
import net.ultibuild.BUILDER.Backend.TextGUI;
import net.ultibuild.BUILDER.GUIS.CREATION.CREATE_MENU;
import net.ultibuild.GUI.GUI;
import net.ultibuild.UltimateGUI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public final class DemoServer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
        player.addAttachment(this,"gui.main",true);
    }

    @EventHandler
    public void onDamageTaken(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamage() > 0.1) {
                e.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
                ((Player) e.getEntity()).setHealth(20);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void cmd(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Player p = event.getPlayer();
        if(!p.isOp()) {
            CommandBlocked cmdEvent = new CommandBlocked(event.getMessage());
            Bukkit.getServer().getPluginManager().callEvent(cmdEvent);
            System.out.println(!cmdEvent.isCancelled());
            if(!cmdEvent.isCancelled()) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cSorry you can't do that!"));
            } else
                event.setCancelled(false);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void cmd(CommandBlocked event) {
        if(event.command.equals("/plugins")) {
            event.setCancelled(true);
        }

    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void placeBlock(BlockPlaceEvent event) {
        if(!event.getPlayer().isOp())
        event.setCancelled(true);
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSorry you can't chat here!"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerAction(PlayerInteractEvent event) {
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
