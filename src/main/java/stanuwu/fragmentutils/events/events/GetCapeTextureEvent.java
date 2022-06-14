package stanuwu.fragmentutils.events.events;

import net.minecraft.util.Identifier;

import java.util.UUID;

public class GetCapeTextureEvent extends Event {
    private UUID uuid;
    private Identifier texture;

    public GetCapeTextureEvent(UUID uuid, Identifier texture) {
        this.uuid = uuid;
        this.texture = texture;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Identifier getTexture() {
        return texture;
    }

    public void setTexture(Identifier texture) {
        this.texture = texture;
    }
}
