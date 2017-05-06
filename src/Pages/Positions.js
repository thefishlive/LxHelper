import React, { Component } from 'react';
import * as Data from '../Data.js';

import { LinkContainer } from 'react-router-bootstrap';
import { ListGroup, ListGroupItem } from 'react-bootstrap';

export class PositionListPage extends Component
{
  constructor(props)
  {
    super(props);

    this.state = {
      positions: []
    };
  }

  componentDidMount ()
  {
    var _this = this;
    this.serverRequest = Data.GetPositions().then((result) => {
      _this.setState({
        positions: result.data
      })
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
      <PositionList positions={ this.state.positions } />
    );
  }
}

export class PositionList extends Component
{
  render(props)
  {
    var positions = [];

    this.props.positions.forEach((position) => {
      positions.push(
        <PositionListEntry position={position} key={position.id}/>
      );
    });

    return (
      <ListGroup>
        {positions}
      </ListGroup>
    );
  }
}

export class PositionListEntry extends Component
{
  render(props)
  {
    return (
      <LinkContainer to={"/position/" + this.props.position.id}>
        <ListGroupItem>{this.props.position.name}</ListGroupItem>
      </LinkContainer>
    )
  }
}
