class ActivitiesController < ApplicationController
  before_action :set_trip
  before_action :set_activity, only: %i[ show edit update destroy ]

  # GET /activities or /activities.json
  def index
    @activities = (@trip&.activities || Activity).all
  end

  # GET /activities/1 or /activities/1.json
  def show
  end

  # GET /activities/new
  def new
    @activity = Activity.new
  end

  # GET /activities/1/edit
  def edit
  end

  # POST /activities or /activities.json
  def create
    @activity = Activity.new(activity_params) if @trip.nil?
    unless @trip.nil?
      if activity_params[:id].nil?
        @activity = @trip.activities.build(activity_params)
      else
        @activity = Activity.find(activity_params[:id])
        @trip.activities << @activity
      end
    end

    respond_to do |format|
      if @activity.save
        format.html { redirect_to @activity, notice: "Activity was successfully created." }
        format.json { render :show, status: :created, location: trip_activity_url(@trip, @activity) }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activities/1 or /activities/1.json
  def update
    respond_to do |format|
      if @activity.update(activity_params)
        format.html { redirect_to @activity, notice: "Activity was successfully updated." }
        format.json { render :show, status: :ok, location: trip_activity_url(@trip, @activity) }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activities/1 or /activities/1.json
  def destroy
    @activity.destroy
    respond_to do |format|
      format.html { redirect_to activities_url, notice: "Activity was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_activity
    @activity = Activity.find(params[:activity_id] || params[:id])
  end

  def set_trip
    @trip = Trip.find(params[:trip_id]) if params[:trip_id]
  end

  def trip_activity_url(trip, activity)
    return activity_url(activity) if @trip.nil?
    super(trip, activity)
  end

  # Only allow a list of trusted parameters through.
  def activity_params
    params.require(:activity).permit(:id, :title, :address, :cost, :description, :note, :rating,
                                     address_attributes: [:line1, :line2, :city, :state, :zip])
  end
end
