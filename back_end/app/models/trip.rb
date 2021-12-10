class Trip < ApplicationRecord
  belongs_to :user
  has_many :invites, dependent: :delete_all
  has_many :accepted_invites, -> { where(status: :accepted) }, class_name: 'Invite'
  has_many :invited_users, through: :accepted_invites, source: :receiver, class_name: 'User'

  has_many :true_costs

  has_and_belongs_to_many :accommodations
  has_and_belongs_to_many :places
  has_and_belongs_to_many :activities

  validate :accommodations
  validate :places
  validate :activities

  validates_presence_of :name

  def owner? user
    self.user == user
  end
end
