class Trips::AccommodationsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip
  before_action :set_accommodation, only: %i[ show edit update destroy ]

  # GET /trips/:trip_id/accommodations or /trips/:trip_id/accommodations.json
  def index
    @accommodations = @trip.accommodations.all
  end

  # GET /trips/:trip_id/accommodations/1 or /trips/:trip_id/accommodations/1.json
  def show
  end

  # GET /trips/:trip_id/accommodations/new
  def new
    @accommodation = @trip.accommodations.build
  end

  # GET /trips/:trip_id/accommodations/1/edit
  def edit
  end

  # POST /trips/:trip_id/accommodations or /trips/:trip_id/accommodations.json
  def create
    @accommodation = Accommodation.find(accommodation_params[:id])
    @trip.accommodations << @accommodation

    respond_to do |format|
      if !@accommodation.nil? && @trip.save
        format.json { render :show, status: :created, location: accommodation_url(@accommodation) }
      else
        format.json { render json: @accommodation.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/:trip_id/accommodations/1 or /trips/:trip_id/accommodations/1.json
  def update
    respond_to do |format|
      if @accommodation.update(accommodation_params)
        format.json { render :show, status: :ok, location: trip_accommodation_url(@trip, @accommodation) }
      else
        format.json { render json: @accommodation.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/:trip_id/accommodations/1 or /trips/:trip_id/accommodations/1.json
  def destroy
    @trip.accommodations.delete(@accommodation)
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_accommodation
    @accommodation = Accommodation.find(params[:id])
  end

  def set_trip
    @trip = Trip.find(params[:trip_id])
  end

  # Only allow a list of trusted parameters through.
  def accommodation_params
    params.require(:accommodation).permit(:id)
  end
end
