require "test_helper"

class ActivityTest < ActiveSupport::TestCase
  setup do
    @jetski = activities(:jetski)
    @parachuting = activities(:parachuting)
  end

  test "has address" do
    assert_not_nil @jetski.address
  end
end
