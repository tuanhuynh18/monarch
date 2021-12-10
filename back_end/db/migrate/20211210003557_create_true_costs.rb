class CreateTrueCosts < ActiveRecord::Migration[7.0]
  def change
    create_table :true_costs do |t|
      t.references :trip, null: false, foreign_key: true
      t.references :place, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true
      t.integer :cost

      t.timestamps
    end
  end
end

