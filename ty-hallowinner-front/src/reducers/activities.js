import { FETCH_ACTIVITIES, UPDATE_ACTIVITY } from '../actions/types';

const activities = (state = [], action) => {
  switch (action.type) {
    case FETCH_ACTIVITIES:
      return Object.assign({}, state, {activities: action.activities});
    case UPDATE_ACTIVITY:
      let editActivities = [];
      editActivities = editActivities.concat(state.activities);
      return Object.assign({}, state, {activities: editActivities.map(activity => (activity.id === action.activity.id) ? action.activity : activity)});
    default:
      return state;
  }
}

export default activities;
