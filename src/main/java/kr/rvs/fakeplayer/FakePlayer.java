package kr.rvs.fakeplayer;

import com.comphenix.protocol.ProtocolLibrary;
import kr.rvs.fakeplayer.listener.PingListener;
import kr.rvs.fakeplayer.util.Storage;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by deide on 2016-12-04.
 */
public class FakePlayer extends JavaPlugin {
    private static final String FAKE_COUNT = "fake-count";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Storage.count = getConfig().getInt(FAKE_COUNT, 1000);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PingListener(this));
    }

    @Override
    public void onDisable() {
        getConfig().set(FAKE_COUNT, Storage.count);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length > 0) {
                String arg = args[0];
                if (NumberUtils.isNumber(arg)) {
                    Storage.count = NumberUtils.toInt(arg);
                    sender.sendMessage("설정했습니다.");
                } else {
                    sender.sendMessage("숫자를 입력해주세요");
                }
            } else {
                sender.sendMessage("사용법: /동접조작 인원");
            }
        }
        return true;
    }
}
