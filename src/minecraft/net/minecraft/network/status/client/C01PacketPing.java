package net.minecraft.network.status.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C01PacketPing implements Packet<INetHandlerStatusServer>
{
    private long clientTime;

    public C01PacketPing()
    {
    }

    public C01PacketPing(long ping)
    {
        this.clientTime = ping;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.clientTime = buf.readLong();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeLong(this.clientTime);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerStatusServer handler)
    {
        handler.processPing(this);
    }

    @Override
    public int getPacketID() {
        return 0xCCCC01;
    }

    public long getClientTime()
    {
        return this.clientTime;
    }
}
