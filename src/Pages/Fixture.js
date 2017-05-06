import React, { Component } from 'react';
import * as Data from '../Data.js';
import axios from 'axios';

import { LinkContainer } from 'react-router-bootstrap';

import { Table, Panel } from 'react-bootstrap';
import { Grid, Row, Col, Button } from 'react-bootstrap';

export class FixtureInfoPage extends Component
{
  constructor(props)
  {
    super(props);

    var id = parseInt(props.match.params.id, 10);

    this.state = {
      id: id,
      fixture: {},
      position: {},
      lamp: {},

      next: {},
      prev: {},

      loaded: false
    };
  }

  componentWillReceiveProps(nextProps)
  {
    var id = parseInt(nextProps.match.params.id, 10);
    console.log(id);
    if (id !== this.state.id)
    {
      this.setState({
        id: id
      });

      this.update(nextProps.match.params);
    }
  }

  update(props)
  {
    var _this = this;

    this.setState({
      loaded: false
    });

    Data.GetFixtureData(props.id).then((result) => {
      var fixture = result.data;
      _this.setState({
        fixture: fixture,
        prev: Data.GetPreviousFixture(fixture),
        next: Data.GetNextFixture(fixture)
      });

      axios.all(
        [
          Data.GetPositionData(fixture.position),
          Data.GetLampData(fixture.lamp)
        ]
      ).then(axios.spread((pos, lamp) => {
          _this.setState({
            position: pos.data,
            lamp: lamp.data,
            loaded: true
          });
      }));
    });
  }

  componentDidMount()
  {
    this.update(this.props.match.params);
  }

  render(props)
  {
    if (!this.state.loaded)
    {
      return (
        <div></div>
      );
    }

    var fixture = this.state.fixture;

    var prevFixture = this.state.prev;
    var nextFixture = this.state.next;

    var prev = (
      <Col xs={4} md={2} mdOffset={2}><Button disabled block>Previous</Button></Col>
    )

    if (prevFixture !== false)
    {
      prev = (
        <LinkContainer to={"/fixture/" + prevFixture}>
          <Col xs={4} md={2} mdOffset={2}><Button block>Previous</Button></Col>
        </LinkContainer>
      );
    }

    var next = (
      <Col xs={4} xsOffset={4} md={2} mdOffset={4}><Button disabled block>Next</Button></Col>
    )

    if (nextFixture !== false)
    {
      next = (
        <LinkContainer to={"/fixture/" + nextFixture}>
          <Col xs={4} xsOffset={4} md={2} mdOffset={4}><Button block>Next</Button></Col>
        </LinkContainer>
      );
    }

    return (
      <Grid>
        <Row>
          <Col xs={12} md={8} mdOffset={2}>
            <FixtureInfo fixture={ fixture } position={ this.state.position } lamp={ this.state.lamp } />
          </Col>
        </Row>
        <Row>
          {prev}
          {next}
        </Row>
      </Grid>
    );
  }
}

export class FixtureInfo extends Component
{
  constructor(props)
  {
    super(props);

    var fixture = this.props.fixture;
    var lamp = this.props.lamp;

    this.state = {
      fixture: fixture,
      lamp: lamp,
      pos: this.props.position,
      mode: lamp.modes[fixture.mode]
    };
  }

  render(props)
  {
    var fixture = this.props.fixture;
    var lamp = this.state.lamp;
    var pos = this.state.pos;
    var mode = this.state.mode;

    const title = (
      <h1>{lamp.name}</h1>
    )

    return (
      <Panel header={title}>
        <Table fill striped bordered>
          <tbody>
            <tr>
              <td>Position / Unit</td>
              <td>{pos.name}</td>
              <td>{fixture.unit_number}</td>
            </tr>

            <tr>
              <td>Purpose</td>
              <td colSpan="2">{fixture.focus}</td>
            </tr>

            <tr>
              <td>Colour</td>
              <td colSpan="2">{fixture.colour || "N/A"}</td>
            </tr>

            <tr>
              <td colSpan="3"><h5>Control</h5></td>
            </tr>

            <tr>
              <td>Channel</td>
              <td colSpan="2">{fixture.channel}</td>
            </tr>

            <tr>
              <td>Profile</td>
              <td colSpan="2">{mode.name}</td>
            </tr>

            <tr>
              <td>Address</td>
              <td>{fixture.universe}</td>
              <td>{fixture.address}</td>
            </tr>

            <tr>
              <td>Addresses</td>
              <td colSpan="2">{mode.addresses}</td>
            </tr>

            <tr>
              <td colSpan="3"><h5>Luminaire</h5></td>
            </tr>

            <tr>
              <td>Name</td>
              <td colSpan="2">{lamp.name}</td>
            </tr>

            <tr>
              <td>Type</td>
              <td colSpan="2">{lamp.type}</td>
            </tr>

          </tbody>
        </Table>
      </Panel>
    );
  }
}
