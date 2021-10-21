class Trip < ApplicationRecord
  has_and_belongs_to_many :accommodations
  has_and_belongs_to_many :places
  has_and_belongs_to_many :activities
end
