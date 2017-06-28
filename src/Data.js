import axios from 'axios';

const PLANS = [
  { id: 1, name:"Sheet 1 - Grid", path:"/lx-plan-sht-1.pdf" },
  { id: 2, name:"Sheet 2 - Bars", path:"/lx-plan-sht-1.pdf" },
  { id: 3, name:"Sheet 3 - Tech Balcony", path:"/lx-plan-sht-1.pdf" },
  { id: 4, name:"Sheet 4 - Floor", path:"/lx-plan-sht-1.pdf" }
];

const BACKEND = 'https://lx.thefishlive.co.uk:8080/'

export function GetPositions()
{
  return axios.get(BACKEND + "positions/");
}

export function GetFixtures()
{
  return axios.get(BACKEND + "fixtures/");
}

export function GetLamps()
{
  return axios.get(BACKEND + "luminaires/");
}

export function GetPatch()
{
  return axios.get(BACKEND + "patch/");
}

export function GetFixturesForPosition(position)
{
  return axios.get(BACKEND + "position_fixtures/" + position);
}

export function GetPreviousFixture(fixture)
{
  return fixture.prev === -1 ? false : fixture.prev;
}

export function GetNextFixture(fixture)
{
  return fixture.next === -1 ? false : fixture.next;
}

export function GetPositionData(position)
{
  return axios.get(BACKEND + "position/" + position);
}

export function GetFixtureData(fixture)
{
  return axios.get(BACKEND + "fixture/" + fixture);
}

export function GetLampData(lampId)
{
  return axios.get(BACKEND + "luminaire/" + lampId);
}

export function GetPlans()
{
  return PLANS.sort((a, b) => {
    return a.id - b.id;
  });
}

export function GetPlan(id)
{
  return GetPlans()[id];
}
