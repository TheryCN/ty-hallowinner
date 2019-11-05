import { FETCH_ACTIVITIES, UPDATE_ACTIVITY, UPDATE_ACTIVITY_IN_PROGRESS, ACTIVITY_UPDATED } from './types';

export const fetchActivities = activities => ({
  type: FETCH_ACTIVITIES,
  activities
});

export const updateActivity = activity => ({
  type: UPDATE_ACTIVITY,
  activity
});

export const updateActivityInProgress = activityId => ({
  type: UPDATE_ACTIVITY_IN_PROGRESS,
  activityId
});

export const activityUpdated = activityId => ({
  type: ACTIVITY_UPDATED,
  activityId
});
