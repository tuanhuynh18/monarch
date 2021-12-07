require "test_helper"

class TripsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @trip = trips(:spring_break)
  end

  test "should get redirected on index" do
    get trips_url
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on new" do
    get new_trip_url
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on create trip" do
    post trips_url, params: { trip: { budget: @trip.budget, ends_at: @trip.ends_at, name: @trip.name, starts_at: @trip.starts_at } }
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on show trip" do
    get trip_url(@trip)
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on get edit" do
    get edit_trip_url(@trip)
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on update trip" do
    patch trip_url(@trip), params: { trip: { budget: @trip.budget, ends_at: @trip.ends_at, name: @trip.name, starts_at: @trip.starts_at } }
    assert_redirected_to new_user_session_url
  end

  test "should get unauthorized redirected on destroy trip" do
    delete trip_url(@trip)
    assert_redirected_to new_user_session_url
  end

  test "should get index (authorized)" do
    sign_in users(:bob)
    get trips_url
    assert_response :success
  end

  test "should get new (authorized)" do
    sign_in users(:bob)
    get new_trip_url
    assert_response :success
  end

  test "should create trip (authorized)" do
    sign_in users(:bob)
    assert_difference("Trip.count") do
      post trips_url, params: { trip: { name: @trip.name } }
    end

    assert_redirected_to trip_url(Trip.last)
  end

  test "should show trip (authorized)" do
    sign_in users(:bob)
    get trip_url(@trip)
    assert_response :success
  end

  test "should get edit (authorized)" do
    sign_in users(:bob)
    get edit_trip_url(@trip)
    assert_response :success
  end

  test "should update trip (authorized)" do
    sign_in users(:bob)
    patch trip_url(@trip), params: { trip: { name: @trip.name } }
    assert_redirected_to trip_url(@trip)
  end

  test "should destroy trip (authorized)" do
    sign_in users(:bob)
    assert_difference("Trip.count", -1) do
      delete trip_url(@trip)
    end

    assert_redirected_to trips_url
  end
end
