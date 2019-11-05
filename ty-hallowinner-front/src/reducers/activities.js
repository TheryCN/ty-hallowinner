import { FETCH_ACTIVITIES, UPDATE_ACTIVITY, UPDATE_ACTIVITY_IN_PROGRESS, ACTIVITY_UPDATED } from '../actions/types';

const activities = (state = {activities: [], updateActivitiesInProgress: []}, action) => {
  switch (action.type) {
    case FETCH_ACTIVITIES:
      return Object.assign({}, state, {activities: action.activities});
    case UPDATE_ACTIVITY:
      let editActivities = [];
      editActivities = editActivities.concat(state.activities);
      return Object.assign({}, state, {activities: editActivities.map(activity => (activity.id === action.activity.id) ? action.activity : activity)});
    case UPDATE_ACTIVITY_IN_PROGRESS:
      let updateActivitiesInProgressAdd = [];
      updateActivitiesInProgressAdd = updateActivitiesInProgressAdd.concat(state.updateActivitiesInProgress);
      updateActivitiesInProgressAdd.push(action.activityId);
      return Object.assign({}, state, {updateActivitiesInProgress: updateActivitiesInProgressAdd});
    case ACTIVITY_UPDATED:
      let updateActivitiesInProgressDel = [];
      updateActivitiesInProgressDel = updateActivitiesInProgressDel.concat(state.updateActivitiesInProgress);
      let activityIndex = updateActivitiesInProgressDel.findIndex(activityId => activityId === action.activityId);
      updateActivitiesInProgressDel.splice(activityIndex,1);
      return Object.assign({}, state, {updateActivitiesInProgress: updateActivitiesInProgressDel});
    default:
      return state;
  }
}

export default activities;
