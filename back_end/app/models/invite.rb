class Invite < ApplicationRecord
  belongs_to :trip
  belongs_to :sender, class_name: 'User', foreign_key: 'sender_id'
  belongs_to :receiver, class_name: 'User', foreign_key: 'receiver_id'

  scope :pending, ->{ where(status: :sent) }

  enum status: {
    sent: 0, accepted: 1, revoked: 2, declined: 3
  }

  validates_presence_of :trip, :sender, :receiver

  # Only allow one invite per receiver to a trip
  validates_uniqueness_of :receiver_id, scope: :trip
end
