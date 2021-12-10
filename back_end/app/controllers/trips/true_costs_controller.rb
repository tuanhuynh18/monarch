class Trips::TrueCostsController < ApplicationController
  before_action :authenticate_user!
  before_action :set_trip
  before_action :set_true_cost, only: %i[ show edit update destroy ]

  # GET /true_costs or /true_costs.json
  def index
    @true_costs = @trip.true_costs.all
  end

  # GET /true_costs/1 or /true_costs/1.json
  def show
  end

  # GET /true_costs/new
  def new
    @true_cost = TrueCost.new
  end

  # POST /true_costs or /true_costs.json
  def create
    place = Place.find_by(google_id: true_cost_params[:google_id])
    @true_cost = TrueCost.new
    @true_cost.trip = @trip
    @true_cost.user = current_user
    @true_cost.place = place
    @true_cost.cost = true_cost_params[:cost]

    @trip.places << place unless place.nil?

    respond_to do |format|
      if @true_cost.save
        format.json { render :show, status: :created, location: @true_cost }
      else
        format.json { render json: @true_cost.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /true_costs/1 or /true_costs/1.json
  def update
    respond_to do |format|
      obj = true_cost_params.except(:google_id)
      obj[:place] = Place.find_by_google_id(true_cost_params[:google_id])
      if @true_cost.update(obj)
        format.json { render :show, status: :ok, location: @true_cost }
      else
        format.json { render json: @true_cost.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /true_costs/1 or /true_costs/1.json
  def destroy
    @true_cost.destroy
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_true_cost
      @true_cost = TrueCost.find(params[:id])
    end

    def set_trip
      @trip = current_user.trips.find(params[:trip_id])
    end

    # Only allow a list of trusted parameters through.
    def true_cost_params
      params.require(:true_cost).permit(:trip_id, :google_id, :user_id, :cost)
    end
end
