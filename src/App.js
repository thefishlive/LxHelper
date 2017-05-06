/* eslint-disable */
import React, { Component } from 'react';
import './index.css';

import { Link } from 'react-router-dom';
import { LinkContainer } from 'react-router-bootstrap';

import { Navbar, Nav, NavItem, NavDropdown, MenuItem } from 'react-bootstrap';

export class Navigation extends Component
{
  render()
  {
    return (
      <Navbar inverse collapseOnSelect fixedTop>
        <Navbar.Header>
          <Navbar.Brand>
            <Link to="/">LX Helper</Link>
          </Navbar.Brand>
          <Navbar.Toggle />
        </Navbar.Header>

        <Navbar.Collapse>
          <Nav>
            <LinkContainer to="/positions">
              <NavItem eventKey={1}>LX Position</NavItem>
            </LinkContainer>
            <LinkContainer to="/patch">
              <NavItem eventKey={2}>LX Patch</NavItem>
            </LinkContainer>
            <NavItem />
            <LinkContainer to="/plan">
              <NavItem eventKey={3}>LX Plan</NavItem>
            </LinkContainer>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}
