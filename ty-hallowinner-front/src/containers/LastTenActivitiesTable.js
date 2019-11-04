import { connect } from 'react-redux';
import { fetchActivitiesCall, addPumpkinStatsCall } from '../api';
import ActivitiesTable from '../components/ActivitiesTable';

const mapStateToProps = state => ({
  activities: state.activities
});

const mapDispatchToProps = dispatch => ({
  fetchActivities: () => fetchActivitiesCall(dispatch),
  addPumpkinStats: (activityId) => addPumpkinStatsCall(dispatch, activityId)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivitiesTable);
