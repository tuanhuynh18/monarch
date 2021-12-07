class Trips::ActivitiesController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip
  before_action :set_activity, only: %i[ show edit update destroy ]

  # GET /trips/:trip_id/activities or /trips/:trip_id/activities.json
  def index
    @activities = @trip.activities.all
  end

  # GET /trips/:trip_id/activities/1 or /trips/:trip_id/activities/1.json
  def show
  end

  # GET /trips/:trip_id/activities/new
  def new
    @activity = @trip.activities.build
  end

  # GET /trips/:trip_id/activities/1/edit
  def edit
  end

  # POST /trips/:trip_id/activities or /trips/:trip_id/activities.json
  def create
    @activity = Activity.find(accommodation_params[:id])
    @trip.activities << @activity

      respond_to do |format|
      if !@activity.nil? && @trip.save
        format.json { render :show, status: :created, location: trip_activity_url(@trip, @activity) }
      else
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /trips/:trip_id/activities/1 or /trips/:trip_id/activities/1.json
  def update
    respond_to do |format|
      if @activity.update(accommodation_params)
        format.json { render :show, status: :ok, location: trip_activity_url(@trip, @activity) }
      else
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/:trip_id/activities/1 or /trips/:trip_id/activities/1.json
  def destroy
    @trip.activities.delete(@activity)
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_activity
    @activity = @trip.activities.find(params[:id])
  end

  def set_trip
    @trip = current_user.trips.find(params[:trip_id])
  end

  # Only allow a list of trusted parameters through.
  def accommodation_params
    params.require(:activity).permit(:id)
  end
end
