require "test_helper"

class TripPlacesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @place = places(:state_park)
  end

  test "should get index" do
    get places_url
    assert_response :success
  end

  test "should get show" do
    get place_url(@place)
    assert_response :success
  end
end
