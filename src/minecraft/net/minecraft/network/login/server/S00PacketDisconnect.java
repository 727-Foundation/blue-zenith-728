package net.minecraft.network.login.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

public class S00PacketDisconnect implements Packet<INetHandlerLoginClient>
{
    private IChatComponent reason;

    public S00PacketDisconnect()
    {
    }

    public S00PacketDisconnect(IChatComponent reasonIn)
    {
        this.reason = reasonIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.reason = buf.readChatComponent();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeChatComponent(this.reason);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerLoginClient handler)
    {
        handler.handleDisconnect(this);
    }

    @Override
    public int getPacketID() {
        return 0xF00D;
    }

    public IChatComponent func_149603_c()
    {
        return this.reason;
    }
}
