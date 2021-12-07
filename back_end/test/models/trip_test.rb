require "test_helper"

class TripTest < ActiveSupport::TestCase
  setup do
    @winter_break = trips(:winter_break)
    @spring_break = trips(:spring_break)
  end

  test "has accommodations" do
    assert_not_nil @winter_break.accommodations
  end

  test "has addresses of accommodations" do
    assert_not_nil @winter_break.accommodations.first.address
  end
end
