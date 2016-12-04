package kr.rvs.fakeplayer.listener;

import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.reflect.StructureModifier;
import kr.rvs.fakeplayer.util.Storage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Created by deide on 2016-12-04.
 */
public class PingListener extends PacketAdapter {
    public PingListener(Plugin plugin) {
        super(plugin, ConnectionSide.SERVER_SIDE, ListenerPriority.HIGH, GamePhase.LOGIN, 255);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        StructureModifier<String> packet = e.getPacket().getSpecificModifier(String.class);
        String[] datas = packet.read(0).split("\\000");
        datas[4] = Storage.count.toString();
        String field = null;
        for (String data : datas) {
            if (field == null) {
                field = data;
            } else {
                field += "\000" + data;
            }
        }
        packet.write(0, field);
    }
}
