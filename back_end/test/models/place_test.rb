require "test_helper"

class PlaceTest < ActiveSupport::TestCase
  setup do
    @state_park = places(:state_park)
    @caves = places(:caves)
  end
  test "has address" do
    assert_not_nil @caves.address
  end
end
