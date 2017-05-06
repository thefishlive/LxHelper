package uk.co.thefishlive.lx.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class VectorworksFileReader
{
    private static final Pattern CsvSplit = Pattern.compile(",", Pattern.LITERAL);

    public ShowData readFile(File file) throws IOException
    {
        if (!file.exists())
        {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        Map<Integer, Luminaire> luminaires = new HashMap<>();
        Map<Integer, Fixture> fixtures = new HashMap<>();
        Map<Integer, Position> positions = new HashMap<>();
        List<Integer> posCounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line = "";

            while ((line = reader.readLine()) != null)
            {
                if (line.startsWith(",")
                        || line.length() == 0
                        || line.startsWith("Channel,"))
                {
                    continue;
                }

                String[] parts = CsvSplit.split(line);
                Fixture fixture = new Fixture();

                int fixtureId = fixtures.size();
                fixture.setId(fixtureId);
                fixture.setChannel(Integer.parseInt(parts[0]));
                fixture.setFocus(parts[5]);
                fixture.setColour(parts[6]);
                fixture.setUnitNumber(Integer.parseInt(parts[8]));
                fixture.setUniverse(Integer.parseInt(parts[9]));
                fixture.setAddress(Integer.parseInt(parts[10]));

                int pos = GetPositionByName(positions, parts[7]);
                if (pos == -1)
                {
                    pos = positions.size();
                    Position position = new Position();
                    position.setId(pos);
                    position.setName(parts[7]);
                    positions.put(pos, position);

                    posCounts.add(1);
                }
                else
                {
                    posCounts.set(pos, posCounts.get(pos) + 1);
                }

                fixture.setPosition(pos);

                int luminaireId = GetLuminaireByName(luminaires, parts[3]);
                if (luminaireId == -1)
                {
                    luminaireId = luminaires.size();
                    Luminaire luminaire = new Luminaire();
                    luminaire.setId(luminaireId);
                    luminaire.setName(parts[3]);
                    luminaires.put(luminaireId, luminaire);
                }

                fixture.setLamp(luminaireId);

                Luminaire luminaire = luminaires.get(luminaireId);

                int addresses = Integer.parseInt(parts[11]);
                int modeId = luminaire.getModeByAddresses(addresses);
                if (modeId == -1)
                {
                    modeId = luminaire.getModes().size();
                    Luminaire.ControlMode mode = new Luminaire.ControlMode();
                    mode.setId(modeId);
                    mode.setName(String.format("%d channel mode", addresses));
                    mode.setAddresses(addresses);
                    luminaire.addMode(mode);
                }

                fixture.setMode(modeId);

                fixtures.put(fixtureId, fixture);
            }
        }

        for (Fixture fixture : fixtures.values())
        {
            fixture.setPrev(getPreviousFixture(fixtures.values(), fixture));
            fixture.setNext(getNextFixture(fixtures.values(), fixture, posCounts.get(fixture.getPosition())));
        }

        return new ShowData(fixtures.values(), luminaires.values(), positions.values());
    }

    private static int getNextFixture(Collection<Fixture> fixtures, Fixture fixture, int posCount)
    {
        if (fixture.getUnitNumber() == posCount)
        {
            return -1;
        }

        return fixtures.stream()
                .filter(f -> f.getPosition() == fixture.getPosition() && f.getUnitNumber() == fixture.getUnitNumber() + 1)
                .map(Fixture::getId)
                .findFirst().orElseGet(() -> -1);
    }

    private static int getPreviousFixture(Collection<Fixture> fixtures, Fixture fixture)
    {
        if (fixture.getUnitNumber() == 1)
        {
            return -1;
        }

        return fixtures.stream()
                .filter(f -> f.getPosition() == fixture.getPosition() && f.getUnitNumber() == fixture.getUnitNumber() - 1)
                .map(Fixture::getId)
                .findFirst().orElseGet(() -> -1);
    }

    private static int GetPositionByName(Map<Integer, Position> positions, String name)
    {
        for (Map.Entry<Integer, Position> entry : positions.entrySet())
        {
            if (name.equals(entry.getValue().getName()))
                return entry.getKey();
        }

        return -1;
    }

    private static int GetLuminaireByName(Map<Integer, Luminaire> positions, String name)
    {
        for (Map.Entry<Integer, Luminaire> entry : positions.entrySet())
        {
            if (name.equals(entry.getValue().getName()))
                return entry.getKey();
        }

        return -1;
    }
}
