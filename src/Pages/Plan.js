import React, { Component } from 'react';
import * as Data from '../Data';

import { LinkContainer } from 'react-router-bootstrap';
import { ListGroup, ListGroupItem } from 'react-bootstrap';

export class PlansPage extends Component
{
  constructor(props)
  {
    super(props);

    this.state = {
      plans: Data.GetPlans()
    };
  }

  render(props)
  {
    var plans = [];

    this.state.plans.forEach((plan) => {
      plans.push(
        <LinkContainer to={"/plan/" + plan.id} key={plan.id}>
          <ListGroupItem>{plan.name}</ListGroupItem>
        </LinkContainer>
      );
    });

    return (
      <ListGroup>
        {plans}
      </ListGroup>
    );
  }
}

export class ViewPlanPage extends Component
{
  render(props)
  {
    var plan = Data.GetPlan(this.props.match.params.id);
    window.location.replace(plan.path);

    return (
      <div></div>
    )
  }
}
