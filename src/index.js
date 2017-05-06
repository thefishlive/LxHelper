/* eslint-disable */
import React from 'react';
import ReactDOM from 'react-dom';
import { Navigation, } from './App';
import './index.css';
import * as Data from './Data.js';
import { BrowserRouter as Router, Route } from 'react-router-dom';

import { HomePage } from './Pages/Home'
import { PositionListPage } from './Pages/Positions';
import { PatchViewPage } from './Pages/Patch';
import { PositionInfoPage } from './Pages/Position';
import { FixtureInfoPage } from './Pages/Fixture';
import { PlansPage, ViewPlanPage } from './Pages/Plan';

const Page = (
  <Router>
    <div>
      <Navigation />

      <Route exact path="/" component={HomePage} />
      <Route exact path="/positions" component={PositionListPage} />
      <Route path="/position/:id" component={PositionInfoPage} />
      <Route path="/fixture/:id" component={FixtureInfoPage} />
      <Route path="/patch" component={PatchViewPage} />
      <Route path="/plan/:id" component={ViewPlanPage} />
      <Route path="/plan" component={PlansPage} />
    </div>
  </Router>
)

ReactDOM.render(
  Page,
  document.getElementById('root')
);
