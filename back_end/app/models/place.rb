class Place < ApplicationRecord
  has_and_belongs_to_many :trip

  has_one :address, as: :addressable
  accepts_nested_attributes_for :address
  validates_presence_of :address
  validate :address
end
