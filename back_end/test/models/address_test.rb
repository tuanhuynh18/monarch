require "test_helper"

class AddressTest < ActiveSupport::TestCase
  test "Address is .addressable (gets parent)" do
    assert_not_nil addresses(:activity_one).addressable
  end
end
