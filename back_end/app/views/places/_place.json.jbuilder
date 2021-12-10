json.extract! place, :id, :title, :address, :description, :note, :category, :rating, :latitude, :longitude, :google_id, :created_at, :updated_at
json.estimated_cost place.cost.to_d
json.true_cost TrueCost.find_by(place: place, trip: @trip).nil? ? nil : TrueCost.where(place: place, trip: @trip).sort_by(&:created_at).last.cost

