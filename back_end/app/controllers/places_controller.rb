class PlacesController < ApplicationController
  before_action :set_place, only: :show

  # GET /places or /places.json
  def index
    @places = Place.all
  end

  # GET /places/1 or /places/1.json
  def show
  end

  def create
    @place = Place.new(place_params)

    respond_to do |format|
      if @place.save
        format.html { redirect_to @trip, notice: "Place was successfully created." }
        format.json { render :show, status: :created, location: @place }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/1 or /trips/1.json
  def update
    respond_to do |format|
      if @place.update(place_params)
        format.html { redirect_to @place, notice: "Trip was successfully updated." }
        format.json { render :show, status: :ok, location: @place }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @place.errors, status: :unprocessable_entity }
      end
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_place
    @place = Place.find(params[:id])
  end

  def place_params
    params.require(:place).permit(:id, :google_id, :title, :cost, :rating, :description, :note,
                                  :category, :latitude, :longitude,
                                  address_attributes: [ :id, :line1, :line2, :city, :state, :zip ])
  end
end
