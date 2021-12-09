class TripsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip, only: %i[ show edit update destroy ]

  # GET /trips or /trips.json
  def index
    @trips = current_user.trips.all
    head :not_found if @trips.nil?
  end

  # GET /trips/1 or /trips/1.json
  def show
  end

  # GET /trips/new
  def new
    @trip = current_user.trips.build
  end

  # GET /trips/1/edit
  def edit
  end

  # POST /trips or /trips.json
  def create
    @trip = Trip.new(trip_params)
    @trip.user = current_user

    respond_to do |format|
      if @trip.save
        format.html { redirect_to @trip, notice: "Trip was successfully created." }
        format.json { render :show, status: :created, location: @trip }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @trip.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/1 or /trips/1.json
  def update
    respond_to do |format|
      if @trip.update(trip_params)
        format.html { redirect_to @trip, notice: "Trip was successfully updated." }
        format.json { render :show, status: :ok, location: @trip }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @trip.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/1 or /trips/1.json
  def destroy
    @trip.destroy
    respond_to do |format|
      format.html { redirect_to trips_url, notice: "Trip was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_trip
      @trip = current_user.trips.find_by_id(params[:trip_id] || params[:id])
      head :not_found if @trip.nil?
    end

    # Only allow a list of trusted parameters through.
    def trip_params
      params.require(:trip).permit(:name, :budget, :starts_at, :ends_at)
    end
end

