json.extract! place, :id, :title, :address, :description, :note, :category, :rating, :latitude, :longitude, :google_id, :created_at, :updated_at
json.estimated_cost place.cost.to_d

cost = TrueCost.where(place: place, trip: @trip, user: current_user)
invalid = cost.nil? || cost.length == 0

json.true_cost invalid ? nil : cost.sort_by(&:created_at).last.cost

