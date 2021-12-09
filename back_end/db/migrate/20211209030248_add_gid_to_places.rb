class AddGidToPlaces < ActiveRecord::Migration[7.0]
  def change
    add_column :places, :google_id, :integer
    add_index :places, :google_id
  end
end
