class AddFieldsToTrips < ActiveRecord::Migration[7.0]
  def change
    add_column :trips, :destination, :string
  end
end
