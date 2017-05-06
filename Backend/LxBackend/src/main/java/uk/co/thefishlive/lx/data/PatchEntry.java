package uk.co.thefishlive.lx.data;

public class PatchEntry
{
    private int channel;
    private int address;
    private int universe;
    private String type;

    public PatchEntry(int channel, int address, int universe, String mode)
    {
        this.channel = channel;
        this.address = address;
        this.universe = universe;
        this.type = mode;
    }

    public int getChannel()
    {
        return channel;
    }

    public void setChannel(int channel)
    {
        this.channel = channel;
    }

    public int getAddress()
    {
        return address;
    }

    public void setAddress(int address)
    {
        this.address = address;
    }

    public int getUniverse()
    {
        return universe;
    }

    public void setUniverse(int universe)
    {
        this.universe = universe;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
