require 'faker'

def _name
  Faker::Name.unique.name
end

def _address
  { line1: Faker::Address.street_address,
    city:  Faker::Address.city,
    state: Faker::Address.state,
    zip:   Faker::Address.zip }
end

def _dec(min = 0, max = 100)
  Faker::Number.between(from: min, to: max).round(2)
end

def _timestamp
  Date.today+rand(10000)
end

puts "Creating {Accommodations}"
while Accommodation.count <= 30
  acc = Accommodation.new title: _name,
                          cost: _dec(10, 50),
                          rating: _dec(0, 5)
  acc.build_address _address
  acc.save!
end

puts "Creating {Activities}"
while Activity.count <= 30
  act = Activity.new title: _name,
                     cost: _dec(10, 50),
                     rating: _dec(0, 5)
  act.address = act.build_address _address
  act.save!
end

puts "Creating {Places}"
while Place.count <= 30
  pl = Place.new title: _name,
                 cost: _dec(10, 50),
                 rating: _dec(0, 5)
  pl.address = pl.build_address _address
  pl.save!
end

puts "Creating {Trips}"
while Trip.count <= 4
  Trip.create! name: _name,
               budget: _dec(30, 600),
               starts_at: _timestamp,
               ends_at: _timestamp
end

puts "Populating {Trips}"
Trip.all.each do |trip|
  while trip.activities.count <= 7
    trip.activities << Activity.find(Activity.pluck(:id).sample)
  end
  while trip.places.count <= 5
    trip.places << Place.find(Place.pluck(:id).sample)
  end
  while trip.accommodations.count <= 3
    trip.accommodations << Accommodation.find(Accommodation.pluck(:id).sample)
  end
  trip.save!
end