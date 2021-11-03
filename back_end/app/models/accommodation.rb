class Accommodation < ApplicationRecord
  has_and_belongs_to_many :trip

  has_one :address, as: :addressable
  accepts_nested_attributes_for :address
  validates_presence_of :address
  validate :address

  validates_presence_of :title
  validates :cost, numericality: { in: 0..1_000_000 }
  validates :rating, numericality: { in: 0..5 }
end
