class CreateTrips < ActiveRecord::Migration[7.0]
  def change
    create_table :trips do |t|
      t.string :name
      t.decimal :budget
      t.timestamp :starts_at
      t.timestamp :ends_at

      t.timestamps
    end
  end
end
