import axios from 'axios';
import { fetchActivities, updateActivity } from '../actions';

export const fetchActivitiesCall = (dispatch) => {
  axios.get(process.env.REACT_APP_BACKEND+'/activities/').then(
    response => dispatch(fetchActivities(response.data)),
    error => {throw error}
  );
}

export const addPumpkinStatsCall = (dispatch, activityId) => {
  axios.get(process.env.REACT_APP_BACKEND+'/pumpkin/'+activityId).then(
    response => dispatch(updateActivity(response.data)),
    error => {throw error}
  );
}
