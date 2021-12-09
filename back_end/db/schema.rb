# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2021_11_03_221912) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "accommodations", force: :cascade do |t|
    t.string "title"
    t.decimal "cost"
    t.text "description"
    t.text "note"
    t.decimal "rating"
    t.string "addressable_type"
    t.bigint "addressable_id"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["addressable_type", "addressable_id"], name: "index_accommodations_on_addressable"
  end

  create_table "accommodations_trips", id: false, force: :cascade do |t|
    t.integer "accommodation_id"
    t.integer "trip_id"
  end

  create_table "activities", force: :cascade do |t|
    t.string "title"
    t.decimal "cost"
    t.text "description"
    t.text "note"
    t.decimal "rating"
    t.string "addressable_type"
    t.bigint "addressable_id"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["addressable_type", "addressable_id"], name: "index_activities_on_addressable"
  end

  create_table "activities_trips", id: false, force: :cascade do |t|
    t.integer "activity_id"
    t.integer "trip_id"
  end

  create_table "addresses", force: :cascade do |t|
    t.string "line1"
    t.string "line2"
    t.string "city"
    t.string "state"
    t.integer "zip"
    t.string "addressable_type"
    t.bigint "addressable_id"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["addressable_type", "addressable_id"], name: "index_addresses_on_addressable"
  end

  create_table "invites", force: :cascade do |t|
    t.bigint "sender_id"
    t.bigint "receiver_id"
    t.bigint "trip_id", null: false
    t.integer "status"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["receiver_id"], name: "index_invites_on_receiver_id"
    t.index ["sender_id"], name: "index_invites_on_sender_id"
    t.index ["trip_id"], name: "index_invites_on_trip_id"
  end

  create_table "places", force: :cascade do |t|
    t.string "title"
    t.decimal "cost"
    t.text "description"
    t.text "note"
    t.string "category"
    t.decimal "rating"
    t.string "addressable_type"
    t.bigint "addressable_id"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["addressable_type", "addressable_id"], name: "index_places_on_addressable"
  end

  create_table "places_trips", id: false, force: :cascade do |t|
    t.integer "place_id"
    t.integer "trip_id"
  end

  create_table "trips", force: :cascade do |t|
    t.string "name"
    t.decimal "budget"
    t.datetime "starts_at", precision: 6
    t.datetime "ends_at", precision: 6
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.bigint "user_id", null: false
    t.index ["user_id"], name: "index_trips_on_user_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "email", default: "", null: false
    t.string "encrypted_password", default: "", null: false
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at", precision: 6
    t.datetime "remember_created_at", precision: 6
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["email"], name: "index_users_on_email", unique: true
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
  end

  add_foreign_key "invites", "trips"
  add_foreign_key "invites", "users", column: "receiver_id"
  add_foreign_key "invites", "users", column: "sender_id"
  add_foreign_key "trips", "users"
end
