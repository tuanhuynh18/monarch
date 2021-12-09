class AddFieldsToPlaces < ActiveRecord::Migration[7.0]
  def change
    add_column :places, :latitude, :decimal, default: 0
    add_column :places, :longitude, :decimal, default: 0
  end
end
