require "test_helper"

class AccommodationsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @accommodation = accommodations(:sleep_inn)
  end

  test "should get index" do
    get accommodations_url
    assert_response :success
  end

  test "should get new" do
    get new_accommodation_url
    assert_response :success
  end
end
