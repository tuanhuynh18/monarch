class Accommodation < ApplicationRecord
  belongs_to :trip

  has_one :address, as: :addressable
  accepts_nested_attributes_for :address

  validates_presence_of :address
  validate :address
end
