require "test_helper"

class AccommodationTest < ActiveSupport::TestCase
  setup do
    @sleep_inn = accommodations(:sleep_inn)
    @best_western = accommodations(:best_western)
  end

  test "has address" do
    assert_not_nil @sleep_inn.address
  end
end
