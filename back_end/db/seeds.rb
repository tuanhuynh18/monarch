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
  Date.today+rand(10)
end

puts "Creating {Accommodations}"
while Accommodation.count <= 30
  acc = Accommodation.new title: _name, cost: _dec(10, 50), rating: _dec(0, 5)
  acc.build_address _address
  acc.save!
end

puts "Creating {Activities}"
while Activity.count <= 30
  act = Activity.new title: _name, cost: _dec(10, 50), rating: _dec(0, 5)
  act.address = act.build_address _address
  act.save!
end

puts "Creating {Places}"
while Place.count <= 30
  pl = Place.new title: _name, cost: _dec(10, 50), rating: _dec(0, 5)
  pl.address = pl.build_address _address
  pl.save!
end

puts "Creating {Users}"
@bob = User.find_by(email: "bob@example.com") || User.create!(email: "bob@example.com", password: "test123")
@nancy = User.find_by(email: "nancy@example.com") || User.create!(email: "nancy@example.com", password: "test123")

puts "Creating {Trips}"
while @bob.trips.count <= 2
  t = Trip.new name: _name, budget: _dec(30, 600),
               starts_at: _timestamp, ends_at: _timestamp,
               user: @bob
  t.save!

  Invite.create!(sender: @bob, receiver: @nancy, status: :accepted, trip: t)
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

obj = [ User, Trip, Activity, Accommodation, Place, Invite, Address ]
obj.each do |o|
  puts "#{o}: #{o.count}"
end