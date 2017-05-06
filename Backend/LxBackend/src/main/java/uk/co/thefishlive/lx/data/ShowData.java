package uk.co.thefishlive.lx.data;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ShowData
{

    private final List<Fixture> fixtures;
    private final List<Luminaire> luminaires;
    private final List<Position> positions;

    public ShowData(Collection<Fixture> fixtures, Collection<Luminaire> luminaires, Collection<Position> positions)
    {
        super();

        this.fixtures = fixtures.stream()
                .sorted((a, b) ->
                {
                    return a.getId() - b.getId();
                })
                .collect(Collectors.toList());
        this.luminaires = luminaires.stream()
                .sorted((a, b) ->
                {
                    return a.getId() - b.getId();
                })
                .collect(Collectors.toList());
        ;
        this.positions = positions.stream()
                .sorted((a, b) ->
                {
                    return a.getId() - b.getId();
                })
                .collect(Collectors.toList());
        ;
    }

    public List<Fixture> getFixtures()
    {
        return fixtures;
    }

    public List<Luminaire> getLuminaires()
    {
        return luminaires;
    }

    public List<Position> getPositions()
    {
        return positions;
    }

    @Override
    public String toString()
    {
        return "ShowData [fixtures=" + fixtures + ", luminaires=" + luminaires + ", positions=" + positions + "]";
    }
}
