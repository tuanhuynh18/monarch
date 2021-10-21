class AccommodationsController < ApplicationController
  before_action :set_trip
  before_action :set_accommodation, only: %i[ show edit update destroy ]

  # GET /accommodations or /accommodations.json
  def index
    @accommodations = (@trip&.accommodations || Accommodation).all
  end

  # GET /accommodations/1 or /accommodations/1.json
  def show
  end

  # GET /accommodations/new
  def new
    @accommodation = Accommodation.new
  end

  # GET /accommodations/1/edit
  def edit
  end

  # POST /accommodations or /accommodations.json
  def create
    @accommodation = Accommodation.new(accommodation_params) if @trip.nil?
    unless @trip.nil?
      if accommodation_params[:id].nil?
        @accommodation = @trip.accommodations.build(accommodation_params)
      else
        @accommodation = Accommodation.find(accommodation_params[:id])
        @trip.accommodations << @accommodation
      end
    end

    respond_to do |format|
      if @accommodation.save
        format.html { redirect_to @accommodation, notice: "Accommodation was successfully created." }
        format.json { render :show, status: :created, location: trip_accommodation_url(@trip, @accommodation) }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @accommodation.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /accommodations/1 or /accommodations/1.json
  def update
    respond_to do |format|
      if @accommodation.update(accommodation_params)
        format.html { redirect_to @accommodation, notice: "Accommodation was successfully updated." }
        format.json { render :show, status: :ok, location: trip_accommodation_url(@trip, @accommodation) }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @accommodation.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /accommodations/1 or /accommodations/1.json
  def destroy
    @accommodation.destroy
    respond_to do |format|
      format.html { redirect_to accommodations_url, notice: "Accommodation was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_accommodation
    @accommodation = Accommodation.find(params[:accommodation_id] || params[:id])
  end

  def set_trip
    @trip = Trip.find(params[:trip_id]) if params[:trip_id]
  end

  def trip_accommodation_url(trip, accommodation)
    return accommodation_url(accommodation) if @trip.nil?
    super(trip, accommodation)
  end

  # Only allow a list of trusted parameters through.
  def accommodation_params
    params.require(:accommodation).permit(:id, :title, :cost, :description, :note, :rating,
                                          address_attributes: [:line1, :line2, :city, :state, :zip])
  end
end
