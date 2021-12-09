class CreateInvites < ActiveRecord::Migration[7.0]
  def change
    create_table :invites do |t|
      t.references :sender, foreign_key: { to_table: 'users' }
      t.references :receiver, foreign_key: { to_table: 'users' }
      t.references :trip, null: false, foreign_key: true
      t.integer :status, default: 0

      t.timestamps
    end
  end
end
