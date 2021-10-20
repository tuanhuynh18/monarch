class Address < ApplicationRecord
  belongs_to :addressable, polymorphic: true

  validates_presence_of :line1, :city, :state, :zip
end
