import React, { Component } from 'react';

import { LinkContainer } from 'react-router-bootstrap';
import { Panel, ListGroup, ListGroupItem } from 'react-bootstrap';

export class HomePage extends Component
{
  render(props)
  {
    return (
      <Panel>
        <ListGroup fill>
          <LinkContainer to="/positions/">
            <ListGroupItem>View LX Positions</ListGroupItem>
          </LinkContainer>
          <LinkContainer to="/patch/">
            <ListGroupItem>View LX Patch</ListGroupItem>
          </LinkContainer>
        </ListGroup>
        &nbsp;
        <ListGroup fill>
          <LinkContainer to="/plan/">
            <ListGroupItem>View LX Plans</ListGroupItem>
          </LinkContainer>
        </ListGroup>
      </Panel>
    );
  }
}
