json.extract! place, :id, :title, :address, :description, :note, :category, :rating, :latitude, :longitude, :google_id, :created_at, :updated_at
json.estimated_cost place.cost.to_d

puts place.id, @trip.id, TrueCost.find_by(place: place, trip: @trip)

json.true_cost TrueCost.find_by(place: place, trip: @trip).nil? ? nil : TrueCost.find_by(place: place, trip: @trip).cost

