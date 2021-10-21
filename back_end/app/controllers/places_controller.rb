class PlacesController < ApplicationController
  before_action :set_trip
  before_action :set_place, only: %i[ show edit update destroy ]

  # GET /places or /places.json
  def index
    @places = (@trip&.places || Place).all
  end

  # GET /places/1 or /places/1.json
  def show
  end

  # GET /places/new
  def new
    @place = Place.new
  end

  # GET /places/1/edit
  def edit
  end

  # POST /places or /places.json
  def create
    @place = Place.new(place_params) if @trip.nil?
    unless @trip.nil?
      if place_params[:id].nil?
        @place = @trip.places.build(place_params)
      else
        @place = Place.find(place_params[:id])
        @trip.places << @place
      end
    end

    respond_to do |format|
      if @place.save
        format.html { redirect_to @place, notice: "Place was successfully created." }
        format.json { render :show, status: :created, location: trip_place_url(@trip, @place) }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /places/1 or /places/1.json
  def update
    respond_to do |format|
      if @place.update(place_params)
        format.html { redirect_to @place, notice: "Place was successfully updated." }
        format.json { render :show, status: :ok, location: trip_place_url(@trip, @place) }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /places/1 or /places/1.json
  def destroy
    @place.destroy
    respond_to do |format|
      format.html { redirect_to trip_places_url, notice: "Place was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_place
    @place = Place.find(params[:place_id] || params[:id])
  end

  def set_trip
    @trip = Trip.find(params[:trip_id]) if params[:trip_id]
  end

  def trip_place_url(trip, place)
    return place_url(place) if @trip.nil?
    super(trip, place)
  end

  # Only allow a list of trusted parameters through.
  def place_params
    params.require(:place).permit(:id, :title, :address, :cost, :description, :note, :category, :rating,
                                  address_attributes: [:line1, :line2, :city, :state, :zip])
  end
end
