import React, { Component } from 'react';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import Button from '@material-ui/core/Button';

import NumberFormat from 'react-number-format';
import moment from 'moment';

class ActivitiesTable extends Component {

  componentDidMount() {
    this.props.fetchActivities();
  }

  render() {
    let activityRows = [];
    if(this.props.activities) {
      activityRows = this.props.activities.map(activity => (
        <TableRow key={activity.id}>
          <TableCell><a href={"https://www.strava.com/activities/" + activity.id}>{activity.name}</a></TableCell>
          <TableCell>{moment(activity.start_date).format('LLL')}</TableCell>
          <TableCell><NumberFormat value={activity.distance / 1000} displayType={'text'} decimalScale={2} /></TableCell>
          <TableCell>{activity.elev_high}</TableCell>
          <TableCell>{activity.elev_low}</TableCell>
          <TableCell>
            <Button variant="contained" onClick={() => this.props.addPumpkinStats(activity.id)}>
              Add Pumpkin!
            </Button>
          </TableCell>
        </TableRow>
      ))
    }
    return (
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Date</TableCell>
            <TableCell>Distance (km)</TableCell>
            <TableCell>D+ (m)</TableCell>
            <TableCell>D- (m)</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {activityRows}
        </TableBody>
      </Table>
    );
  }
};

export default ActivitiesTable;
