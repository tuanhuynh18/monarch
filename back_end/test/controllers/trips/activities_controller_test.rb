require "test_helper"

class TripActivitiesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @activity = activities(:jetski)
  end

  test "should get index" do
    get activities_url
    assert_response :success
  end

  test "should show activity" do
    get activity_url(@activity)
    assert_response :success
  end
end
