require 'active_record_union'
class User < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable

  has_many :sent_invites, foreign_key: 'sender_id', class_name: 'Invite'
  has_many :received_invites, foreign_key: 'receiver_id', class_name: 'Invite'
  has_many :accepted_invites, ->{ where(status: :accepted) }, foreign_key: 'receiver_id', class_name: 'Invite'

  has_many :owned_trips, foreign_key: "user_id", class_name: "Trip"
  has_many :accepted_trips, through: :accepted_invites, source: :trip

  has_many :accommodations, through: :trips
  has_many :activities, through: :trips
  has_many :places, through: :trips

  def sender_id; id; end
  def receiver_id; id; end

  def trips; owned_trips.union accepted_trips; end
end
