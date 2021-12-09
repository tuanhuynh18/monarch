json.extract! invite, :id, :status
json.sender   invite.sender.email
json.receiver invite.receiver.email
json.extract! invite, :created_at, :updated_at