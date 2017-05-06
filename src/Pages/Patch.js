import React, { Component } from 'react';
import * as Data from '../Data.js';

import { Table } from 'react-bootstrap';

export class PatchViewPage extends Component
{
  constructor(props)
  {
    super(props);

    this.state = {
      patch: []
    };
  }

  componentDidMount()
  {
    var _this = this;
    this.serverRequest = Data.GetPatch().then((result) => {
      _this.setState({
        patch: result.data
      });
    });
  }

  render(props)
  {
    return (
        <PatchView patch={ this.state.patch } />
    );
  }
}

export class PatchView extends Component
{
  render(props)
  {
    var rows = [];
    var i = 0;

    this.props.patch.forEach((patch) => {
      rows.push(<PatchEntry patch={patch} key={i++} />);
    })

    return (
      <Table>
        <thead>
          <tr>
            <th>Channel</th>
            <th>Address</th>
            <th>Type</th>
          </tr>
        </thead>
        <tbody>
        {rows}
        </tbody>
      </Table>
    );
  }
}

export class PatchEntry extends Component
{
  render(props)
  {
    return (
      <tr>
        <td>{ this.props.patch.channel }</td>
        <td>{ (this.props.patch.universe || 1) } / { this.props.patch.address }</td>
        <td>{ this.props.patch.type || 'Dimmer' }</td>
      </tr>
    );
  }
}
