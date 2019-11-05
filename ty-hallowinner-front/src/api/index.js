import axios from 'axios';
import { fetchActivities, updateActivity, updateActivityInProgress, activityUpdated } from '../actions';

export const fetchActivitiesCall = (dispatch) => {
  axios.get(process.env.REACT_APP_BACKEND+'/activities/').then(
    response => dispatch(fetchActivities(response.data)),
    error => {throw error}
  );
}

export const addPumpkinStatsCall = (dispatch, activityId) => {
  dispatch(updateActivityInProgress(activityId));
  axios.get(process.env.REACT_APP_BACKEND+'/pumpkin/'+activityId)
    .then(function (response) {
      dispatch(updateActivity(response.data));
    })
    .catch(function (error) {
      throw error;
    })
    .then(function () {
      // finally
      dispatch(activityUpdated(activityId));
    });
}
