class TrueCost < ApplicationRecord
  belongs_to :trip
  belongs_to :place
  belongs_to :user

  validates_presence_of :trip, :place, :user, :cost
end
