import React from 'react';
import './App.css';

import Grid from '@material-ui/core/Grid';

import LastTenActivitiesTable from './containers/LastTenActivitiesTable';
import Top from './components/Top';

function App() {
  return (
    <div className="App">
      <Grid container>
        <Grid item xs={12}>
          <Top />
        </Grid>
        <Grid item xs={1} />
        <Grid item xs={10}>
          <LastTenActivitiesTable />
        </Grid>
        <Grid item xs={1} />
      </Grid>
    </div>
  );
}

export default App;
