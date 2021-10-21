class CreatePlaces < ActiveRecord::Migration[7.0]
  def change
    create_table :places do |t|
      t.string :title
      t.decimal :cost
      t.text :description
      t.text :note
      t.string :category
      t.decimal :rating

      t.references :addressable, polymorphic: true, null: true

      t.timestamps
    end
  end
end
