package net.minecraft.network.status.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C00PacketServerQuery implements Packet<INetHandlerStatusServer>
{
    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerStatusServer handler)
    {
        handler.processServerQuery(this);
    }

    @Override
    public int getPacketID() {
        return 0xCCCCC;
    }
}
