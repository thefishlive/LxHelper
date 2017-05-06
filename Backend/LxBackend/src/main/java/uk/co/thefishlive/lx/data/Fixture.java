package uk.co.thefishlive.lx.data;

public class Fixture
{

    private int id;
    private int unit_number;
    private int position;
    private int lamp;
    private String colour;
    private int mode;
    private int address;
    private int universe;
    private int channel;
    private String focus;

    private int next;
    private int prev;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUnitNumber()
    {
        return unit_number;
    }

    public void setUnitNumber(int unit_number)
    {
        this.unit_number = unit_number;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getLamp()
    {
        return lamp;
    }

    public void setLamp(int lamp)
    {
        this.lamp = lamp;
    }

    public String getColour()
    {
        return colour;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
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

    public int getChannel()
    {
        return channel;
    }

    public void setChannel(int channel)
    {
        this.channel = channel;
    }

    public String getFocus()
    {
        return focus;
    }

    public void setFocus(String focus)
    {
        this.focus = focus;
    }

    public int getNext()
    {
        return next;
    }

    public void setNext(int next)
    {
        this.next = next;
    }

    public int getPrev()
    {
        return prev;
    }

    public void setPrev(int prev)
    {
        this.prev = prev;
    }

    @Override
    public String toString()
    {
        return "Fixture [id=" + id + ", unit_number=" + unit_number + ", position=" + position + ", lamp=" + lamp
                + ", colour=" + colour + ", mode=" + mode + ", address=" + address + ", universe=" + universe
                + ", channel=" + channel + ", focus=" + focus + "]";
    }
}
