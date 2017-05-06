import React, { Component } from 'react';
import * as Data from '../Data.js';

import { LinkContainer } from 'react-router-bootstrap';
import { ListGroup, ListGroupItem } from 'react-bootstrap';

export class PositionInfoPage extends Component
{
  constructor(props)
  {
    super(props);

    var id = parseInt(this.props.match.params.id, 10);

    this.state = {
      id: id,
      fixtures: []
    };
  }

  componentDidMount()
  {
    var _this = this;
    this.serverRequest = Data.GetFixturesForPosition(this.state.id).then((result) => {
      _this.setState({
        fixtures: result.data
      });
    });
  }

  componentWillUnmount()
  {
    // TODO find error with this line
    // this.serverRequest.abort();
  }

  render(props)
  {
    return (
      <FixtureList fixtures={ this.state.fixtures } />
    );
  }
}

export class FixtureList extends Component
{
  render(props)
  {
    var fixtures = [];

    this.props.fixtures.forEach((fixture) => {
      fixtures.push(
        <FixtureListInfo fixture={fixture} key={fixture.id}/>
      );
    });

    return (
      <ListGroup>
        {fixtures}
      </ListGroup>
    );
  }
}

export class FixtureListInfo extends Component
{
  constructor(props)
  {
    super(props);

    this.state = {
      fixture: this.props.fixture,
      lamp: 0
    };
  }

  componentDidMount()
  {
    var _this = this;
    this.serverRequest = Data.GetLampData(_this.state.fixture.lamp).then((result) => {
      _this.setState({
        lamp: result.data
      });
    });
  }

  componentWillUnmount()
  {
    // TODO find error with this line
    // this.serverRequest.abort();
  }

  render(props)
  {
    var lampInfo = this.state.lamp;

    if (lampInfo === 0)
    {
      return (
          <LinkContainer to={"/fixture/" + this.props.fixture.id}>
            <ListGroupItem>
              {this.props.fixture.unit_number } - Loading... - { this.props.fixture.focus}
            </ListGroupItem>
          </LinkContainer>
      )
    }

    return (
      <LinkContainer to={"/fixture/" + this.props.fixture.id}>
        <ListGroupItem>
          {this.props.fixture.unit_number } - {lampInfo.name} - { this.props.fixture.focus}
        </ListGroupItem>
      </LinkContainer>
    );
  }
}
