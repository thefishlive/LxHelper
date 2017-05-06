package uk.co.thefishlive.lx.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Luminaire
{

    public static class ControlMode
    {
        private int id;
        private String name;
        private int addresses;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getAddresses()
        {
            return addresses;
        }

        public void setAddresses(int addresses)
        {
            this.addresses = addresses;
        }

        @Override
        public String toString()
        {
            return "ControlMode [id=" + id + ", name=" + name + ", addresses=" + addresses + "]";
        }
    }

    private int id;
    private String name;
    private String type;
    private List<ControlMode> modes = new ArrayList<>();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public List<ControlMode> getModes()
    {
        return modes;
    }

    public ControlMode getMode(int mode)
    {
        return modes.get(mode);
    }

    public void setModes(List<ControlMode> modes)
    {
        this.modes = modes;
    }

    public void addMode(ControlMode mode)
    {
        this.modes.add(mode);
    }

    public int getModeByAddresses(int addresses)
    {
        for (int i = 0; i < modes.size(); i++)
        {
            if (modes.get(i).getAddresses() == addresses)
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString()
    {
        return "Luminaire [id=" + id + ", name=" + name + ", type=" + type + ", modes=" + modes + "]";
    }
}
