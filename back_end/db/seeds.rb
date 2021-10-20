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

while Trip.count <= 10
  puts "Creating {Trip} #{Trip.count + 1}"
  t = Trip.create! name: _name,
                   budget: _dec(30, 600),
                   starts_at: _timestamp,
                   ends_at: _timestamp

  while t.accommodations.count <= 3
    puts " --> Creating {Accommodations} for {Trip##{Trip.count + 1}}"
    acc = t.accommodations.build title: _name,
                             cost: _dec(10, 50),
                             rating: _dec(0, 5)
    acc.address = acc.build_address _address
    acc.save!
  end

  while t.activities.count <= 3
    puts " --> Creating {Activitiess} for {Trip##{Trip.count + 1}}"
    acc = t.activities.build title: _name,
                             cost: _dec(10, 50),
                             rating: _dec(0, 5)
    acc.address = acc.build_address _address
    acc.save!
  end

  while t.places.count <= 3
    puts " --> Creating {Places} for {Trip##{Trip.count + 1}}"
    acc = t.places.build title: _name,
                         cost: _dec(10, 50),
                         rating: _dec(0, 5)
    acc.address = acc.build_address _address
    acc.save!
  end
end
