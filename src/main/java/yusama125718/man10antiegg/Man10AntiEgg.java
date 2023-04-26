package yusama125718.man10antiegg;

import com.destroystokyo.paper.event.entity.ThrownEggHatchEvent;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Man10AntiEgg extends JavaPlugin implements Listener {

    static JavaPlugin antiegg;
    static Boolean system = false;
    static List<String> worlds;

    @Override
    public void onEnable() {
        antiegg = this;
        antiegg.saveDefaultConfig();
        system = antiegg.getConfig().getBoolean("system");
        worlds = antiegg.getConfig().getStringList("worlds");
        antiegg.getServer().getPluginManager().registerEvents(this, antiegg);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        if (!sender.hasPermission("mantiegg.op")) return true;
        if (args.length == 1){
            if (args[0].equals("on")){
                system = true;
                antiegg.getConfig().set("system", system);
                antiegg.saveConfig();
                sender.sendMessage("antieggをonにしました");
                return true;
            }
            else if(args[0].equals("off")){
                system = false;
                antiegg.getConfig().set("system", system);
                antiegg.saveConfig();
                sender.sendMessage("antieggをoffにしました");
                return true;
            }
            if (args[0].equals("world")){
                if (!(sender instanceof Player)){
                    sender.sendMessage("コンソールからは実行できません");
                    return true;
                }
                if (worlds.contains(((Player) sender).getWorld().getName())){
                    worlds.remove(((Player) sender).getWorld().getName());
                    antiegg.getConfig().set("worlds", worlds);
                    antiegg.saveConfig();
                    sender.sendMessage("削除しました");
                } else {
                    worlds.add(((Player) sender).getWorld().getName());
                    antiegg.getConfig().set("worlds", worlds);
                    antiegg.saveConfig();
                    sender.sendMessage("追加しました");
                }
                return true;
            }
        }
        sender.sendMessage("/antiegg [on/off]でオンオフを切り替え");
        sender.sendMessage("/antiegg worldで今いるワールドでantieggの[on/off]を切り替え");
        return true;
    }

    @EventHandler
    public void ThrownEggHatchEvent(ThrownEggHatchEvent e){
        if (!system || !worlds.contains(e.getEgg().getWorld().getName())) return;
        e.setHatching(false);
    }
}
