class Trip < ApplicationRecord
  belongs_to :user

  has_and_belongs_to_many :accommodations
  has_and_belongs_to_many :places
  has_and_belongs_to_many :activities

  validates :accommodations
  validates :places
  validates :activities

  validates_presence_of :name
end
