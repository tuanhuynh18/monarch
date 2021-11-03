class Trips::PlacesController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip
  before_action :set_place, only: %i[ show edit update destroy ]

  # GET /trips/:trip_id/places or /trips/:trip_id/places.json
  def index
    @places = @trip.places.all
  end

  # GET /trips/:trip_id/places/1 or /trips/:trip_id/places/1.json
  def show
  end

  # GET /trips/:trip_id/places/new
  def new
    @place = @trip.places.build
  end

  # GET /trips/:trip_id/places/1/edit
  def edit
  end

  # POST /trips/:trip_id/places or /trips/:trip_id/places.json
  def create
    @trip.places << Place.find(accommodation_params[:id])

    respond_to do |format|
      if @trip.save
        format.json { render :show, status: :created, location: trip_place_url(@trip, @place) }
      else
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/:trip_id/places/1 or /trips/:trip_id/places/1.json
  def update
    respond_to do |format|
      if @place.update(accommodation_params)
        format.json { render :show, status: :ok, location: trip_place_url(@trip, @place) }
      else
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/:trip_id/places/1 or /trips/:trip_id/places/1.json
  def destroy
    @trip.places.delete(@place)
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_accommodation
    @place = Place.find(params[:id])
  end

  def set_trip
    @trip = Trip.find(params[:trip_id])
  end

  # Only allow a list of trusted parameters through.
  def accommodation_params
    params.require(:place).permit(:id)
  end
end
