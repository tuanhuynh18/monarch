class AddMtmToObjects < ActiveRecord::Migration[7.0]
  def change
    create_table :places_trips, :id => false do |t|
      t.integer :place_id
      t.integer :trip_id
    end

    create_table :activities_trips, :id => false do |t|
      t.integer :activity_id
      t.integer :trip_id
    end

    create_table :accommodations_trips, :id => false do |t|
      t.integer :accommodation_id
      t.integer :trip_id
    end
  end
end
