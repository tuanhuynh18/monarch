class Trip < ApplicationRecord
  has_many :accommodations
  has_many :places
  has_many :activities
end
