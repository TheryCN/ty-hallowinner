import { FETCH_ACTIVITIES, UPDATE_ACTIVITY } from './types';

export const fetchActivities = activities => ({
  type: FETCH_ACTIVITIES,
  activities
});

export const updateActivity = activity => ({
  type: UPDATE_ACTIVITY,
  activity
});
