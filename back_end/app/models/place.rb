class Place < ApplicationRecord
  belongs_to :trip

  has_one :address, as: :addressable
  accepts_nested_attributes_for :address
end
