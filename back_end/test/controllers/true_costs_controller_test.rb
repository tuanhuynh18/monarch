require "test_helper"

class TrueCostsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @true_cost = true_costs(:one)
  end

  test "should get index" do
    get true_costs_url
    assert_response :success
  end

  test "should get new" do
    get new_true_cost_url
    assert_response :success
  end

  test "should create true_cost" do
    assert_difference("TrueCost.count") do
      post true_costs_url, params: { true_cost: { cost: @true_cost.cost, place_id: @true_cost.place_id, trip_id: @true_cost.trip_id, user_id: @true_cost.user_id } }
    end

    assert_redirected_to true_cost_url(TrueCost.last)
  end

  test "should show true_cost" do
    get true_cost_url(@true_cost)
    assert_response :success
  end

  test "should get edit" do
    get edit_true_cost_url(@true_cost)
    assert_response :success
  end

  test "should update true_cost" do
    patch true_cost_url(@true_cost), params: { true_cost: { cost: @true_cost.cost, place_id: @true_cost.place_id, trip_id: @true_cost.trip_id, user_id: @true_cost.user_id } }
    assert_redirected_to true_cost_url(@true_cost)
  end

  test "should destroy true_cost" do
    assert_difference("TrueCost.count", -1) do
      delete true_cost_url(@true_cost)
    end

    assert_redirected_to true_costs_url
  end
end
