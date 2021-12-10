class Trips::PlacesController < ApplicationController
  prepend_view_path 'app/views/trips/places'
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
    head :no_content if @trip.nil?
    @place = Place.find(place_params[:id]) unless place_params[:id].nil?
    @place = Place.find_by_google_id(place_params[:google_id]) unless place_params[:google_id].nil?
    if @place.nil?
      head :not_found
      return
    end

    @trip.places << @place

    respond_to do |format|
      if !@place.nil? && @trip.save
        format.json { render :show, status: :created, location: trip_place_url(@trip, @place) }
      else
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/:trip_id/places/1 or /trips/:trip_id/places/1.json
  def update
    respond_to do |format|
      if @place.update(place_params)
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
  def set_place
    @place = @trip.places.find_by(google_id: params[:google_id]) unless params[:google_id].nil?
    @place = @trip.places.find(params[:id]) if params[:google_id].nil?
  end

  def set_trip
    @trip = current_user.trips.find(params[:trip_id])
  end

  # Only allow a list of trusted parameters through.
  def place_params
    params.require(:place).permit(:id, :google_id)
  end
end
