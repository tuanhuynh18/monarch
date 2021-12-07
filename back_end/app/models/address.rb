class Address < ApplicationRecord
  belongs_to :addressable, polymorphic: true

  belongs_to :accommodation, optional: true
  belongs_to :activity, optional: true
  belongs_to :place, optional: true

  validates_presence_of :line1, :city, :state, :zip
end
