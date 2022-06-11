package stanuwu.fragmentutils.events.events;

import net.minecraft.network.Packet;

public class ReceivePacketEvent extends Event {
    private Packet packet;

    public ReceivePacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
